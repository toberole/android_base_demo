#include "sogouvad.h"
#include <string.h>

Client_Vad::Client_Vad( int fs, int win_size, int shift_size, int fft_size,
                        int seg_max_len, int pre_reseve_len, int half_freq_win_len,
                        float alfa_ff, float alfa_sf, float beta_sf, float alfa_snr,
                        float thres_02, float thres_24, float thres_46, float thres_68,
                        int inital_frame_num, float dB_bound )
{
    m_fs = fs;
    m_win_size = win_size;
    m_shift_size = shift_size;
    m_max_wav_len = seg_max_len + m_win_size;

    m_reserve_len = 0;
    m_wav_len = 0;
    m_pre_reserve_len = pre_reseve_len;

    m_frame_sum = 0;
    m_ini_frame = inital_frame_num;
    m_dB_bound = dB_bound;

    m_alfa_ff = alfa_ff;
    m_alfa_sf = alfa_sf;
    m_beta_sf = beta_sf;
    m_alfa_snr = alfa_snr;
    m_last_non_speech_frame = -1;

    // Raw packet data
    m_raw_wav = new short[m_max_wav_len];
    if ( m_raw_wav == NULL )
    {
        printf( "Failed to alloc memory for m_raw_wav[%lu].\n", sizeof(short) * m_max_wav_len );
        return;
    }

    // Wave data of previous packet
    m_out_wav = new short[m_max_wav_len];
    m_out_wav_len = 0;
    if ( m_out_wav == NULL )
    {
        printf( "Failed to alloc memory for m_out_wav[%lu].\n", sizeof(short) * seg_max_len );
    }
    m_out_wav_pre = new short[m_pre_reserve_len];
    m_out_wav_pre_len = 0;
    if ( m_out_wav_pre == NULL )
    {
        printf( "Failed to alloc memory for m_out_wav_pre[%lu].\n", sizeof(short) * seg_max_len );
    }

    // Analysis window
    m_ana_win = new float[m_win_size];
    if ( m_ana_win == NULL )
    {
        printf( "Failed to alloc memory for m_ana_win[%lu].\n", sizeof(float) * m_win_size );
        return;
    }
    for ( int i = 0; i < m_win_size; ++i )
    {
        m_ana_win[i] = 0.54 - 0.46*cos( (2*i+1)*pi/(m_win_size) );
    }

    //  FFT Related
    m_fft_size = fft_size;
    m_sp_size = m_fft_size / 2 + 1;
    m_ind_2k = 2000 * m_fft_size / m_fs;
    m_ind_4k = 4000 * m_fft_size / m_fs;
    m_ind_6k = 6000 * m_fft_size / m_fs;
    m_thres_02 = thres_02;
    m_thres_24 = thres_24;
    m_thres_46 = thres_46;
    m_thres_68 = thres_68;

    // FFT Related Array
    m_rev = new int[m_fft_size];
    if ( m_rev == NULL )
    {
        printf( "Failed to alloc memory for m_rev[%lu].\n", sizeof(int) * m_fft_size );
        return;
    }
    m_sin_fft = new float[m_fft_size/2];
    if ( m_sin_fft == NULL )
    {
        printf( "Failed to alloc memory for m_sin_fft[%lu].\n", sizeof(float) * m_fft_size/2 );
        return;
    }
    m_cos_fft = new float[m_fft_size/2];
    if ( m_cos_fft == NULL )
    {
        printf( "Failed to alloc memory for m_cos_fft[%lu].\n", sizeof(float) * m_fft_size/2 );
        return;
    }
    // Windowed Data
    m_win_wav = new float[m_fft_size];
    if ( m_win_wav == NULL )
    {
        printf( "Failed to alloc memory for m_win_wav[%lu].\n", sizeof(float) * m_fft_size );
        return;
    }
    memset( m_win_wav, 0, sizeof(float)*m_fft_size );
    m_v_re = new float[m_fft_size];
    if ( m_v_re == NULL )
    {
        printf( "Failed to alloc memory for m_v_re[%lu].\n", sizeof(float) * m_fft_size );
        return;
    }
    m_v_im = new float[m_fft_size];
    if ( m_v_im == NULL )
    {
        printf( "Failed to alloc memory for m_v_im[%lu].\n", sizeof(float) * m_fft_size );
        return;
    }
    // Spectral Power
    m_sp = new float[m_sp_size];
    if ( m_sp == NULL )
    {
        printf( "Failed to alloc memory for m_sp[%lu].\n", sizeof(float) * m_sp_size );
        return;
    }
    m_sp_smooth = new float[m_sp_size];
    if ( m_sp_smooth == NULL )
    {
        printf( "Failed to alloc memory for m_sp_smooth[%lu].\n", sizeof(float) * m_sp_size );
        return;
    }
    m_sp_ff = new float[m_sp_size];
    if ( m_sp_ff == NULL )
    {
        printf( "Failed to alloc memory for m_sp_ff[%lu].\n", sizeof(float) * m_sp_size );
        return;
    }
    memset( m_sp_ff, 0, sizeof(float) * m_sp_size );
    m_sp_sf = new float[m_sp_size];
    if ( m_sp_sf == NULL )
    {
        printf( "Failed to alloc memory for m_sp_sf[%lu].\n", sizeof(float) * m_sp_size );
        return;
    }
    memset( m_sp_sf, 0, sizeof(float) * m_sp_size );
    m_sp_ff_pre = new float[m_sp_size];
    if ( m_sp_ff_pre == NULL )
    {
        printf( "Failed to alloc memory for m_sp_ff_pre[%lu].\n", sizeof(float) * m_sp_size );
        return;
    }
    memset( m_sp_ff_pre, 0, sizeof(float) * m_sp_size );
    m_sp_snr = new float[m_sp_size];
    if ( m_sp_snr == NULL )
    {
        printf( "Failed to alloc memory for m_sp_snr[%lu]\n", sizeof(float)*m_sp_size );
        return;
    }
    for ( int i = 0; i < m_sp_size; ++i )
    {
        m_sp_snr[i] = 1;
    }

    // Frequency Smoothed Window
    m_freq_win_len = half_freq_win_len;
    m_freq_win = new float[2*m_freq_win_len+1];
    if ( m_freq_win == NULL )
    {
        printf( "Failed to alloc memory for m_freq_win[%lu]", sizeof(float)*(2*m_freq_win_len+1) );
    }
    float tmp = 1.0 / ( m_freq_win_len + 1 );
    for ( int i = 0; i < m_freq_win_len; ++i )
    {
        m_freq_win[i] = (i+1) * tmp;
        m_freq_win[2*m_freq_win_len-i] = (i+1) * tmp;
    }
    m_freq_win[m_freq_win_len] = 1.0;

    initial_fft();
}
int Client_Vad::reset()
{
    m_reserve_len = 0;
    m_wav_len = 0;
    m_frame_sum = 0;
    m_lowfreq_hang_sum = 0;
    m_lowfreq_sph_sum = 0;

    memset( m_win_wav, 0, sizeof(float)*m_fft_size );
    memset( m_sp, 0, sizeof(float)*m_sp_size );
    memset( m_sp_smooth, 0, sizeof(float)*m_sp_size );
    memset( m_sp_ff, 0, sizeof(float)*m_sp_size );
    memset( m_sp_sf, 0, sizeof(float)*m_sp_size );
    memset( m_sp_ff_pre, 0, sizeof(float)*m_sp_size );
    for ( int i = 0; i < m_sp_size; ++i )
    {
        m_sp_snr[i] = 1;
    }
    m_last_non_speech_frame = -1;
    m_out_wav_len = 0;
    m_out_wav_pre_len = 0;
    m_eng_ff = 0;
    m_eng_min = 0;

    return 0;
}



Client_Vad::~Client_Vad()
{
    if ( m_raw_wav )
    {
        delete []m_raw_wav;
        m_raw_wav = NULL;
    }
    if ( m_out_wav  )
    {
        delete []m_out_wav;
        m_out_wav = NULL;
    }
    if ( m_out_wav_pre )
    {
        delete []m_out_wav_pre;
        m_out_wav_pre = NULL;
    }
    if ( m_ana_win )
    {
        delete []m_ana_win;
        m_ana_win = NULL;
    }
    if ( m_rev )
    {
        delete []m_rev;
        m_rev = NULL;
    }
    if ( m_sin_fft )
    {
        delete []m_sin_fft;
        m_sin_fft = NULL;
    }
    if ( m_cos_fft )
    {
        delete []m_cos_fft;
        m_cos_fft = NULL;
    }
    if ( m_win_wav )
    {
        delete []m_win_wav;
        m_win_wav = NULL;
    }
    if ( m_v_re )
    {
        delete []m_v_re;
        m_v_re = NULL;
    }
    if ( m_v_im )
    {
        delete []m_v_im;
        m_v_im = NULL;
    }
    if ( m_sp )
    {
        delete []m_sp;
        m_sp = NULL;
    }
    if ( m_sp_smooth )
    {
        delete []m_sp_smooth;
        m_sp_smooth = NULL;
    }
    if ( m_sp_ff )
    {
        delete []m_sp_ff;
        m_sp_ff = NULL;
    }
    if ( m_sp_sf )
    {
        delete []m_sp_sf;
        m_sp_sf = NULL;
    }
    if ( m_sp_ff_pre )
    {
        delete []m_sp_ff_pre;
        m_sp_ff_pre = NULL;
    }
    if ( m_sp_snr )
    {
        delete []m_sp_snr;
        m_sp_snr = NULL;
    }
    if ( m_freq_win )
    {
        delete []m_freq_win;
        m_freq_win = NULL;
    }
}

int Client_Vad::initial_fft()
{
    int i, j;
    int tmp;
    m_log_fft_size = 0;
    tmp = 1;
    while ( tmp != m_fft_size )
    {
        m_log_fft_size += 1;
        tmp *= 2;
    }
    for ( i = 0; i < m_fft_size; ++i )
    {
        m_rev[i] = 0;
        tmp = i;
        for ( j = 0; j < m_log_fft_size; ++j )
        {
            m_rev[i] = (m_rev[i]<<1) | (tmp&1); // rev_i = rev_i*2+tmp%2
            tmp = tmp>>1;
        }
    }

    for ( i = 0; i < m_fft_size/2; ++i )
    {
        m_sin_fft[i] = sin( 2*pi*i/m_fft_size );
        m_cos_fft[i] = cos( 2*pi*i/m_fft_size );
    }
    return 1;
}

int Client_Vad::fft_dit( const float *x, float *v_re, float *v_im )
{
    int i, j, k;
    int p, q, m, n;
    float tmp1, tmp2;

    for ( i = 0; i < m_fft_size; ++i )
    {
        v_re[m_rev[i]] = x[i];
        v_im[m_rev[i]] = 0;
    }
    p = m_fft_size/2;
    q = 1;

    for ( i = 1; i <= m_log_fft_size; ++i )
    {
        m = 0;
        n = m + q;
        for ( j = 0; j < p; ++j )
        {
            for ( k = 0; k < q; ++k )
            {
                tmp1 = v_re[n]*m_cos_fft[k*p] + v_im[n]*m_sin_fft[k*p];
                tmp2 = v_im[n]*m_cos_fft[k*p] - v_re[n]*m_sin_fft[k*p];
                v_re[n] = v_re[m] - tmp1;
                v_im[n] = v_im[m] - tmp2;
                v_re[m] = v_re[m] + tmp1;
                v_im[m] = v_im[m] + tmp2;
                ++m;
                ++n;
            }
            m = n;
            n = m + q;
        }
        p = p>>1;
        q = q<<1;
    }
    return 1;
}

int Client_Vad::detect_sp_ratio_whisper( int pack_id, int &speech_num, int &non_speech_num, int &last_speech_id )
{
    int sta;
    int i, j, t = 0;
    float tmp, energy_db;
    float num_sub[4] = { 0 };

    speech_num = non_speech_num = 0;

    for ( sta = 0; sta + m_win_size < m_wav_len; sta += m_shift_size )
    {
        t++;
        m_frame_sum++;
        //printf("pack_id:%d\t", pack_id);
        energy_db = 0;

        // add window
        for ( i = 0; i < m_win_size; ++i )
        {
            m_win_wav[i] = m_raw_wav[i+sta] * m_ana_win[i];
            energy_db += m_raw_wav[i+sta] * m_raw_wav[i+sta];
        }

        energy_db /= m_win_size;
        energy_db = (energy_db > 100) ? energy_db : 100 ;

//		printf("\nenergy_db, %f\tm_eng_min:%.2f\n", 10 * log10(energy_db+1), 10 * log10(m_eng_min+1));
#ifdef _DEBUG_ENABLE
        //printf("sta:%d\tenergy_db: %f\n", sta, 10 * log10(energy_db+1));
#endif
        if ( sta == 0 && pack_id == 1 )
        {
            m_eng_ff = energy_db;
            m_eng_min = energy_db;

        }

        m_eng_ff = 0.8 * m_eng_ff + 0.2 * energy_db;
        if ( energy_db > m_eng_min )
        {
//			m_eng_ff = (m_eng_min > 45) ? 0.8 * m_eng_ff + 0.2 * energy_db : 0.7 * m_eng_ff + 0.3 * energy_db;
            m_eng_min = 0.995*m_eng_min + 0.005*energy_db;
        }
        else
        {
            m_eng_ff = 0.5 * m_eng_ff + 0.5 * energy_db ;
            m_eng_min = energy_db;
        }

        //energy_db = 10 * log10( energy_db + 1 );
        energy_db = 10 * log10( m_eng_ff + 1 );
        // fft
        fft_dit( m_win_wav, m_v_re, m_v_im );
        m_sp[0] = 0.0;

        for ( i = 1; i < m_sp_size; ++i )
        {
            m_sp[i] =  m_v_re[i]*m_v_re[i] + m_v_im[i]*m_v_im[i];
        }

        m_sp[0] = m_sp[1] = 0.0;

        // smooth in frequency domain
        for ( i = 1; i < m_freq_win_len; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = 0; j <= i+m_freq_win_len; ++j  )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }
        for ( i = m_freq_win_len; i < m_sp_size-1-m_freq_win_len; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = i-m_freq_win_len; j <= i+m_freq_win_len; ++j )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }
        for ( i = m_sp_size-1-m_freq_win_len; i < m_sp_size-1; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = i-m_freq_win_len; j < m_sp_size; ++j )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }

        for( i = 1; i < m_sp_size-1; ++i )
        {
            if(m_sp_smooth[i] < 10)
            {
                printf("m_sp_smooth[%d]:%.2f < 10\n", i, m_sp_smooth[i]);
                m_sp_smooth[i] = 10;
            }
        }

        // initialize for first several frames
        if ( pack_id == 1 && m_frame_sum <= m_ini_frame )
        {
            //memcpy( m_sp_ff, m_sp_smooth, sizeof(float)*m_sp_size );
            //memcpy( m_sp_sf, m_sp_smooth, sizeof(float)*m_sp_size );
            //memcpy( m_sp_ff_pre, m_sp_smooth, sizeof(float)*m_sp_size );
            for ( i = 0; i < m_sp_size; ++i )
            {
                m_sp_ff[i] += m_sp_smooth[i] / m_ini_frame;
                m_sp_sf[i] += m_sp_smooth[i] / m_ini_frame;
                m_sp_ff_pre[i] += m_sp_smooth[i] / m_ini_frame;
            }
            continue;
        }
        // ff smooth
        for ( i = 0; i < m_sp_size; ++i )
        {
            m_sp_ff[i] = m_alfa_ff * m_sp_ff[i] + (1-m_alfa_ff) * m_sp_smooth[i];
        }

        // sf smooth
        for ( i = 0; i < m_sp_size; ++i )
        {
            if ( m_sp_sf[i] < m_sp_ff[i] )
            {
                m_sp_sf[i] = m_alfa_sf*m_sp_sf[i] + (1-m_alfa_sf) * (m_sp_ff[i]-m_beta_sf*m_sp_ff_pre[i]) / ( 1-m_beta_sf );
            }
            else
            {
                m_sp_sf[i] = m_sp_ff[i];
            }
        }
        for ( i = 0; i < m_sp_size; ++i )
        {
            if ( m_sp_ff[i] / (m_sp_sf[i] + eps) > m_sp_snr[i] )
            {
                m_sp_snr[i] = m_alfa_snr * m_sp_snr[i] + ( 1-m_alfa_snr ) * ( m_sp_ff[i] / (m_sp_sf[i] + eps) );
            }
            else
            {
                float beta_snr = 0.994;
                m_sp_snr[i] = beta_snr*m_sp_snr[i] + (1-beta_snr)*( m_sp_ff[i] / (m_sp_sf[i] + eps) );
            }
            //	if(i < 20)
            //		printf("%d[%.2f]\t", i, m_sp_snr[i]);

        }
        //	printf("\n");

        //low freq statistic

        int lowfreq_sum1 = 0;
        int lowfreq_sum2 = 0;
        float lowfreq_thres = 2.5;
        float mean1 = 0;
        float mean2 = 0;
        float mean3 = 0;
        for ( i = 1; i < m_ind_4k; ++i)
        {
            if(i < m_ind_2k/4){mean1 += m_sp_snr[i];}
            else if(i < m_ind_2k){mean2 += m_sp_snr[i];}
            else{mean3 += m_sp_snr[i];}

            //	printf("[%d]%.2f\t", i, m_sp_snr[i]);
            if ( i < m_ind_2k/4 && m_sp_snr[i] >= lowfreq_thres )
            {
                lowfreq_sum1 ++;
            }
            else if ( i >= m_ind_2k/4 && m_sp_snr[i] >= lowfreq_thres )
            {
                lowfreq_sum2 ++;
            }

        }
        if(lowfreq_sum1 == 0) lowfreq_sum1 = 1;

        mean1 /= (m_ind_2k/4 - 1 );
        mean2 /= (m_ind_2k - m_ind_2k/4);
        mean3 /= (m_ind_4k - m_ind_2k);

        //if( lowfreq_sum2 >= m_ind_2k * 0.75 * 0.7 && lowfreq_sum2 > lowfreq_sum1 * 3.7 )
        if ( (lowfreq_sum2 >= m_ind_2k * 0.525 && mean2 - mean1 > 0.6) ||  mean3 - mean1 > 0.6 )
        {
            m_lowfreq_hang_sum = 0;
            m_lowfreq_sph_sum ++;
        }
        else
        {
            if(m_lowfreq_sph_sum > 0 )
            {
                m_lowfreq_hang_sum ++;
                //if((m_lowfreq_hang_sum > 10 && (mean2 - mean1 < 0.2) && (mean3 - mean1 < 0.2) && (lowfreq_sum1 > (m_ind_2k * 0.1875))) || m_lowfreq_hang_sum > 30)
                if((m_lowfreq_hang_sum > LOWFREQ_HANG_SUM && (mean2 - mean1 < 0.2) && (mean3 - mean1 < 0.2) && (lowfreq_sum1 > (m_ind_2k * 0.1875))) || m_lowfreq_hang_sum > LOWFREQ_HANG_SUM * 2)
                {
                    m_lowfreq_hang_sum = 0;
                    m_lowfreq_sph_sum = 0;
                }
            }
            else
            {
                m_lowfreq_hang_sum = 0;
            }
        }
#ifdef _DEBUG_ENABLE

        printf("lfs_log:\tpack_id:%d\tmean1:%.2f\tmean2:%.2f\tmean3:%.2f\tdelta1:%.2f(>0.6)\tdelta2:%.2f(>0.6)\t",
			pack_id, mean1, mean2, mean3, mean2-mean1 , mean3-mean1);
		printf("lowfreq_sum2/m_ind_2k:%.2f(>0.525)\tlowfreq_sum1/m_ind_2k:%.2f(>0.1875)\t",
			lowfreq_sum2/(float)m_ind_2k, lowfreq_sum1/(float)m_ind_2k );

		printf("m_lowfreq_hang_sum: %d\tm_lowfreq_sph_sum: %d\n\n", m_lowfreq_hang_sum, m_lowfreq_sph_sum);
#endif
        //lowfs end
        memset( num_sub, 0, sizeof(float)*4 );
        for ( i = 1; i < m_ind_2k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_02 )
            {
                //	printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[0] += 1;
            }
        }
        for ( i = m_ind_2k; i < m_ind_4k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_24 )
            {
                //	printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[1] += 1;
            }
        }
        for ( i = m_ind_4k; i < m_ind_6k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_46 )
            {
                //		printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[2] += 1;
            }
        }
        for ( i = m_ind_6k; i <= m_sp_size-2; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_68 )
            {
                //		printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[3] += 1;
            }
        }
        //	printf("\n");
        int unvoice = 0;
        if ( ( num_sub[2] + num_sub[3] ) / ( m_sp_size-1-m_ind_4k) >= 0.45 )
        {
            unvoice = 1;
        }
        num_sub[0] = num_sub[0] / ( m_ind_2k-1 ) ;
        num_sub[1] = num_sub[1] / ( m_ind_4k-m_ind_2k );
        num_sub[2] = num_sub[2] / ( m_ind_6k-m_ind_4k );
        num_sub[3] = num_sub[3] / ( m_sp_size-1-m_ind_4k );
#ifdef _DEBUG_ENABLE
        printf( "snr_sub\npack_id:%d\tnum_sub_res\t\t%lf\t%lf\t%lf\t%lf\t%lf\n", pack_id, num_sub[0], num_sub[1], num_sub[2], num_sub[3], ( num_sub[2] + num_sub[3] ) / 2 );
#endif
        int num = 0;
//		for ( i = 0; i < 4; ++i )
        for ( i = 0; i < 2; ++i )
        {
            if ( num_sub[i] >= 0.3 )
            {
                ++num;
            }
        }
#ifdef _DEBUG_ENABLE
        printf("energy_db: %.2f\tm_dB_bound: %.2f\tnum:%d\tunvoice:%d\n", energy_db, m_dB_bound, num, unvoice);
#endif
        if ( (energy_db >= m_dB_bound  && num >= 1 ) || (energy_db >= m_dB_bound - 5 && unvoice == 1) )
        {
            speech_num++;
            printf("sphnum_log: %d\n", speech_num);
            last_speech_id = t;
        }
        else
        {
            non_speech_num++;
        }
        memcpy( m_sp_ff_pre, m_sp_ff, sizeof(float) * m_sp_size );
    } // end for
    m_reserve_len = m_wav_len - sta;
    return 0;
}


int Client_Vad::detect_sp_ratio( int pack_id, int &speech_num, int &non_speech_num, int &last_speech_id )
{
    int sta;
    int i, j, t = 0;
    float tmp, energy_db;
    float num_sub[4] = { 0 };

    speech_num = non_speech_num = 0;

    for ( sta = 0; sta + m_win_size < m_wav_len; sta += m_shift_size )
    {
        t++;
        m_frame_sum++;
        energy_db = 0;

        // add window
        for ( i = 0; i < m_win_size; ++i )
        {
            m_win_wav[i] = m_raw_wav[i+sta] * m_ana_win[i];
            energy_db += m_raw_wav[i+sta] * m_raw_wav[i+sta];
        }

        energy_db /= m_win_size;
        energy_db = (energy_db > 100) ? energy_db : 100 ;

        if ( sta == 0 && pack_id == 1 )
        {
            m_eng_ff = energy_db;
            m_eng_min = energy_db;

        }

        m_eng_ff = 0.8 * m_eng_ff + 0.2 * energy_db;
        if ( energy_db > m_eng_min )
        {
            m_eng_min = 0.995*m_eng_min + 0.005*energy_db;
        }
        else
        {
            m_eng_ff = 0.5 * m_eng_ff + 0.5 * energy_db ;
            m_eng_min = energy_db;
        }

        //energy_db = 10 * log10( energy_db + 1 );
        energy_db = 10 * log10( m_eng_ff + 1 );
        // fft
        fft_dit( m_win_wav, m_v_re, m_v_im );
        m_sp[0] = 0.0;

        for ( i = 1; i < m_sp_size; ++i )
        {
            m_sp[i] =  m_v_re[i]*m_v_re[i] + m_v_im[i]*m_v_im[i];
        }

        m_sp[0] = m_sp[1] = 0.0;

        // smooth in frequency domain
        for ( i = 1; i < m_freq_win_len; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = 0; j <= i+m_freq_win_len; ++j  )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }
        for ( i = m_freq_win_len; i < m_sp_size-1-m_freq_win_len; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = i-m_freq_win_len; j <= i+m_freq_win_len; ++j )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }
        for ( i = m_sp_size-1-m_freq_win_len; i < m_sp_size-1; ++i )
        {
            m_sp_smooth[i] = 0;
            tmp = 0;
            for ( j = i-m_freq_win_len; j < m_sp_size; ++j )
            {
                m_sp_smooth[i] += m_sp[j] * m_freq_win[j-i+m_freq_win_len];
                tmp += m_freq_win[j-i+m_freq_win_len];
            }
            m_sp_smooth[i] /= (tmp + eps);
        }

        for( i = 1; i < m_sp_size-1; ++i )
        {
            if(m_sp_smooth[i] < 10)
            {
                printf("m_sp_smooth[%d]:%.2f < 10\n", i, m_sp_smooth[i]);
                m_sp_smooth[i] = 10;
            }
        }

        // initialize for first several frames
        if ( pack_id == 1 && m_frame_sum <= m_ini_frame )
        {
            //memcpy( m_sp_ff, m_sp_smooth, sizeof(float)*m_sp_size );
            //memcpy( m_sp_sf, m_sp_smooth, sizeof(float)*m_sp_size );
            //memcpy( m_sp_ff_pre, m_sp_smooth, sizeof(float)*m_sp_size );
            for ( i = 0; i < m_sp_size; ++i )
            {
                m_sp_ff[i] += m_sp_smooth[i] / m_ini_frame;
                m_sp_sf[i] += m_sp_smooth[i] / m_ini_frame;
                m_sp_ff_pre[i] += m_sp_smooth[i] / m_ini_frame;
            }
            continue;
        }
        // ff smooth
        for ( i = 0; i < m_sp_size; ++i )
        {
            m_sp_ff[i] = m_alfa_ff * m_sp_ff[i] + (1-m_alfa_ff) * m_sp_smooth[i];
        }

        // sf smooth
        for ( i = 0; i < m_sp_size; ++i )
        {
            if ( m_sp_sf[i] < m_sp_ff[i] )
            {
                m_sp_sf[i] = m_alfa_sf*m_sp_sf[i] + (1-m_alfa_sf) * (m_sp_ff[i]-m_beta_sf*m_sp_ff_pre[i]) / ( 1-m_beta_sf );
            }
            else
            {
                m_sp_sf[i] = m_sp_ff[i];
            }
        }
        for ( i = 0; i < m_sp_size; ++i )
        {
            if ( m_sp_ff[i] / (m_sp_sf[i] + eps) > m_sp_snr[i] )
            {
                m_sp_snr[i] = m_alfa_snr * m_sp_snr[i] + ( 1-m_alfa_snr ) * ( m_sp_ff[i] / (m_sp_sf[i] + eps) );
            }
            else
            {
                float beta_snr = 0.994;
                m_sp_snr[i] = beta_snr*m_sp_snr[i] + (1-beta_snr)*( m_sp_ff[i] / (m_sp_sf[i] + eps) );
            }

        }

        memset( num_sub, 0, sizeof(float)*4 );
        for ( i = 1; i < m_ind_2k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_02 )
            {
                //	printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[0] += 1;
            }
        }
        for ( i = m_ind_2k; i < m_ind_4k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_24 )
            {
                //	printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[1] += 1;
            }
        }
        for ( i = m_ind_4k; i < m_ind_6k; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_46 )
            {
                //		printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[2] += 1;
            }
        }
        for ( i = m_ind_6k; i <= m_sp_size-2; ++i )
        {
            if ( m_sp_snr[i] >= m_thres_68 )
            {
                //		printf("[%d]%.2f\t", i, m_sp_snr[i]);
                num_sub[3] += 1;
            }
        }
        //	printf("\n");
        int unvoice = 0;
        if ( ( num_sub[2] + num_sub[3] ) / ( m_sp_size-1-m_ind_4k) >= 0.45 )
        {
            unvoice = 1;
        }
        num_sub[0] = num_sub[0] / ( m_ind_2k-1 ) ;
        num_sub[1] = num_sub[1] / ( m_ind_4k-m_ind_2k );
        num_sub[2] = num_sub[2] / ( m_ind_6k-m_ind_4k );
        num_sub[3] = num_sub[3] / ( m_sp_size-1-m_ind_4k );
#ifdef _DEBUG_ENABLE
        printf( "snr_sub\npack_id:%d\tnum_sub_res\t\t%lf\t%lf\t%lf\t%lf\t%lf\n", pack_id, num_sub[0], num_sub[1], num_sub[2], num_sub[3], ( num_sub[2] + num_sub[3] ) / 2 );
#endif
        int num = 0;
//		for ( i = 0; i < 4; ++i )
        for ( i = 0; i < 2; ++i )
        {
            if ( num_sub[i] >= 0.3 )
            {
                ++num;
            }
        }
#ifdef _DEBUG_ENABLE
        printf("energy_db: %.2f\tm_dB_bound: %.2f\tnum:%d\tunvoice:%d\n", energy_db, m_dB_bound, num, unvoice);
#endif
        if ( (energy_db >= m_dB_bound  && num >= 1 ) || (energy_db >= m_dB_bound - 5 && unvoice == 1) )
        {
            speech_num++;
            printf("sphnum_log: %d\n", speech_num);
            last_speech_id = t;
        }
        else
        {
            non_speech_num++;
        }
        memcpy( m_sp_ff_pre, m_sp_ff, sizeof(float) * m_sp_size );
    } // end for
    m_reserve_len = m_wav_len - sta;
    return 0;
}

int Client_Vad::detect_speech( short raw_wav[], int length, int pack_id, CVadRes &res)
{
    // 1. Input arguments check
    if ( raw_wav == NULL || length <= 0 )
    {
        return -1;
    }

    if ( pack_id == 1 || pack_id == -1  )
    {
        //reset();
    }

    // 2. copy new raw data
    m_wav_len = m_reserve_len +length;
    if ( m_wav_len > m_max_wav_len )
    {
        printf( "m_raw_wav is overflow." );
        return -1;
    }
    memcpy( m_raw_wav + m_reserve_len, raw_wav, sizeof(short)*length );


    // 3. voice activity detection
#ifdef _DEBUG_ENABLE
    printf( "%6dms,", m_frame_sum*10 );
#endif
    int speech_frame_num = 0, non_speech_frame_num = 0, last_speech_id = 0;

    detect_sp_ratio( pack_id, speech_frame_num, non_speech_frame_num, last_speech_id );


    // 4. Store current packet data
    memcpy( m_out_wav, m_raw_wav, sizeof(short)*(m_wav_len-m_reserve_len) );
    m_out_wav_len = m_wav_len - m_reserve_len;
    memmove( m_raw_wav, m_raw_wav + m_out_wav_len, sizeof(short)*m_reserve_len );

    // 5. Decide speech exists or not
    bool is_speech;
    if ( speech_frame_num*6 > non_speech_frame_num)
    {
#ifdef _DEBUG_ENABLE
        printf( "pack %3d:speech present, speech_num:%2d, non_speech_num:%2d\n", pack_id, speech_frame_num, non_speech_frame_num );
#endif
        is_speech = true;
    }
    else
    {
#ifdef _DEBUG_ENABLE
        printf( "pack %3d:speech  absent, speech_num:%2d, non_speech_num:%2d\n", pack_id, speech_frame_num,non_speech_frame_num );
#endif
        is_speech = false;
    }

    // 6. If no speech, calculate waiting time
    if ( res.m_is_speech == true && is_speech == false )
    {
        res.m_end_wait_time = ( m_last_non_speech_frame + speech_frame_num + non_speech_frame_num ) * 0.01;
#ifdef _DEBUG_ENABLE
        printf( "m_last_non_speech_frame[%d]\n", m_last_non_speech_frame );
#endif
    }
    else if ( res.m_is_speech == false && is_speech == false )
    {
        res.m_end_wait_time += ( speech_frame_num + non_speech_frame_num ) * 0.01;
    }


    m_last_non_speech_frame = speech_frame_num + non_speech_frame_num - last_speech_id;

    if ( is_speech == false )   // current packet is non-speech
    {
        reserve_pre_speech();
    }
    else                        // current packet is speech
    {
        if ( res.m_is_speech == true ) // previous packet is speech, so no need to reserve
        {
            m_out_wav_pre_len = 0;
        }
    }

    res.m_is_speech = is_speech;

    if ( res.m_is_speech == true &&  res.m_is_speech_found == false )
    {
        res.m_is_first_found = true;
        res.m_is_speech_found = true;
    }
    else if ( res.m_is_speech == true && res.m_is_speech_found == true )
    {
        res.m_is_first_found = false;
    }

    if ( res.m_is_speech_found == false )
    {
        res.m_begin_wait_time += ( speech_frame_num + non_speech_frame_num ) * 0.01;
    }

    return 0;
}


int Client_Vad::detect_speech( short raw_wav[], int length, int pack_id, CVadRes &res, CWhispRes &whisp_res)
//int &has_whisper, float out_energy, int &low_len)
{
    // 1. Input arguments check
    if ( raw_wav == NULL || length <= 0 )
    {
        return -1;
    }

    // 2. copy new raw data
    m_wav_len = m_reserve_len +length;
    if ( m_wav_len > m_max_wav_len )
    {
        printf( "m_raw_wav is overflow." );
        return -1;
    }

    memcpy( m_raw_wav + m_reserve_len, raw_wav, sizeof(short)*length );


    // 3. voice activity detection
#ifdef _DEBUG_ENABLE
    printf( "%6dms,", m_frame_sum*10 );
#endif
    int speech_frame_num = 0, non_speech_frame_num = 0, last_speech_id = 0;

    detect_sp_ratio_whisper( pack_id, speech_frame_num, non_speech_frame_num, last_speech_id );

    // 4. whisper detection

    if((whisp_res.m_out_energy + whisp_res.m_background >= BACKGROUND_THRES) ||
       (speech_frame_num*6 < non_speech_frame_num && res.m_end_wait_time >= WAIT_TIME_THRES))
    {
        if(m_lowfreq_sph_sum > 0){m_lowfreq_hang_sum = 1;}
        m_lowfreq_sph_sum	= 0;
    }

    whisp_res.m_has_whisper =
            ((whisp_res.m_low_len >= LOWLEN_SUM_THRES && (speech_frame_num*6 > non_speech_frame_num || res.m_end_wait_time < WAIT_TIME_THRES))
             || m_lowfreq_sph_sum >= LOWFREQ_SUM_THRES) ? 1 : 0;

    printf("out_eng:\t%.2f\tlow_len:\t%d\tlfs_sum:\t%d\tsphnum:\t%d\tnonsphnum:\t%d\thas_whp\t%d\n",
           whisp_res.m_out_energy, whisp_res.m_low_len, m_lowfreq_sph_sum, speech_frame_num, non_speech_frame_num, whisp_res.m_has_whisper);

    // 5. Store current packet data
    memcpy( m_out_wav, m_raw_wav, sizeof(short)*(m_wav_len-m_reserve_len) );
    m_out_wav_len = m_wav_len - m_reserve_len;
    memmove( m_raw_wav, m_raw_wav + m_out_wav_len, sizeof(short)*m_reserve_len );

    // 6. Decide speech exists or not
    bool is_speech;
    if ( speech_frame_num*6 > non_speech_frame_num)
    {
        //#ifdef _DEBUG_ENABLE
        printf( "pack %3d:speech present, speech_num:%2d, non_speech_num:%2d\n", pack_id, speech_frame_num, non_speech_frame_num );
        //#endif
        is_speech = true;
    }
    else
    {
        //#ifdef _DEBUG_ENABLE
        printf( "pack %3d:speech  absent, speech_num:%2d, non_speech_num:%2d\n", pack_id, speech_frame_num,non_speech_frame_num );
        //#endif
        is_speech = false;
    }

    // 7. If no speech, calculate waiting time
    if ( res.m_is_speech == true && is_speech == false )
    {
        res.m_end_wait_time = ( m_last_non_speech_frame + speech_frame_num + non_speech_frame_num ) * 0.01;
#ifdef _DEBUG_ENABLE
        printf( "m_last_non_speech_frame[%d]\n", m_last_non_speech_frame );
#endif
    }
    else if ( res.m_is_speech == false && is_speech == false )
    {
        res.m_end_wait_time += ( speech_frame_num + non_speech_frame_num ) * 0.01;
    }


    m_last_non_speech_frame = speech_frame_num + non_speech_frame_num - last_speech_id;

    if ( is_speech == false )   // current packet is non-speech
    {
        reserve_pre_speech();
    }
    else                        // current packet is speech
    {
        if ( res.m_is_speech == true ) // previous packet is speech, so no need to reserve
        {
            m_out_wav_pre_len = 0;
        }
    }

    res.m_is_speech = is_speech;
    whisp_res.m_is_speech = is_speech;

    if ( res.m_is_speech == true &&  res.m_is_speech_found == false )
    {
        res.m_is_first_found = true;
        res.m_is_speech_found = true;
    }
    else if ( res.m_is_speech == true && res.m_is_speech_found == true )
    {
        res.m_is_first_found = false;
    }

    if ( res.m_is_speech_found == false && res.m_is_speech == false)
    {
        res.m_begin_wait_time += ( speech_frame_num + non_speech_frame_num ) * 0.01;
    }
    printf("m_is_speech_found:%d\tm_is_speech:%d\tm_begin_wait_time:%.2f\n", res.m_is_speech_found, res.m_is_speech, res.m_begin_wait_time);

    return 0;
}

int Client_Vad::output_speech( short out_wav[], int &length )
{
    if ( out_wav == NULL )
    {
        printf( "Invalid Input Argument." );
        length = 0;
        return -1;
    }

    if ( m_out_wav_len <= 0 )
    {
        length = 0;
        return 0;
    }

    length = m_out_wav_len;
    memcpy( out_wav, m_out_wav, sizeof(short)*m_out_wav_len );

    return 0;
}

int Client_Vad::output_pre_speech( short out_wav[], int &length )
{
    if ( out_wav == NULL )
    {
        printf( "Invalid Input Argument." );
        length = 0;
        return -1;
    }

    if ( m_out_wav_pre_len <= 0 )
    {
        length = 0;
        return 0;
    }

    length = m_out_wav_pre_len;
    //printf("m_out_wav_pre_len: %d\n", m_out_wav_pre_len);
    memcpy( out_wav, m_out_wav_pre, sizeof(short)*m_out_wav_pre_len );

    return 0;
}

int Client_Vad::reserve_pre_speech()
{
    if ( m_out_wav_pre_len + m_out_wav_len > m_pre_reserve_len )
    {


        if ( m_out_wav_len >= m_pre_reserve_len )
        {
            memcpy( m_out_wav_pre, m_out_wav+m_out_wav_len-m_pre_reserve_len, sizeof(short)*m_pre_reserve_len );
            m_out_wav_pre_len = m_pre_reserve_len;
        }
        else // m_out_wav_len < m_pre_reserve_len
        {
            int move_len = m_out_wav_pre_len + m_out_wav_len-m_pre_reserve_len;
            memmove( m_out_wav_pre, m_out_wav_pre+move_len, sizeof(short)*(m_out_wav_pre_len-move_len) );
            memcpy( m_out_wav_pre+m_pre_reserve_len-m_out_wav_len, m_out_wav, sizeof(short)*m_out_wav_len );
            m_out_wav_pre_len = m_pre_reserve_len;
        }
    }
    else
    {
        //printf( " m_out_wav:%d m_out_wav_pre_len:%d m_pre_reserve_len:%d ", m_out_wav_len, m_out_wav_pre_len, m_pre_reserve_len );
        memcpy( m_out_wav_pre+m_out_wav_pre_len, m_out_wav, sizeof(short)*m_out_wav_len );
        m_out_wav_pre_len += m_out_wav_len;
    }

    return 0;
}

