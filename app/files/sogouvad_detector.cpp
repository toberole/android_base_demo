//
// Created by Apple on 2018/6/25.
//
#include <string.h>
#include <stdlib.h>

#include "com_sogo_speech_sogovad.h"
#include "log.h"
#include "common.h"
#include "sogouvad.h"
#include "sogouvad_proxy.h"

static jboolean __enable_debug_log___ = false;

JNIEXPORT jlong JNICALL Java_com_sogou_speech_sogovad_SogouVadDetector_init_1jni
        (JNIEnv *env, jobject jobj, jlong sampleRate,jobject jextra){
    struct sogouvad_proxy *instance = (struct sogouvad_proxy *)malloc(sizeof(struct sogouvad_proxy));
    if(NULL!=instance){
        // LOGI("sampleRate: %d ",sampleRate);

        Client_Vad *vad = new Client_Vad(
                sampleRate,                // sampling frequency
                WIN_SIZE,                   // window size
                SHIFT_SIZE,                 // shift size
                FFT_SIZE,                   // fft size
                MAX_WAV_LEN,				// SHORT_LEN, // packet length
                PRE_RESERVE_LEN,			// PRE_RESERVE_LEN+SHORT_LEN,  // previous reserve length
                HALF_FRQ_WIN_LEN,           // half_freq_win_len
                ALFA_FF,                    // alfa_ff
                ALFA_SF,                    // alfa_sf
                BETA_SF,                    // beta_sf
                ALFA_SNR,                   // alfa_snr
                THRES_02,                   // threshold from 0kHz to 2kHz
                THRES_24,                   // threshold from 2kHz to 4kHz
                THRES_46,                   // threshold from 4kHz to 6kHz
                THRES_68,                   // threshold from 6kHz to 8kHz
                INI_FRAME,                  // initial frame number
                DB_BOUND                    // energy bound
        );
        if(NULL == vad){
            return -1;
        }

        instance->_client_vad = vad;
        instance->_vad_result.reset();
        instance->_client_vad->reset();

        return (long)instance;
    }

    return -1;
}


// (long instance, short[] voice, long[] start_end, int length, int sn, Object extra);
///////////////////888888888888888888888888////////////////
JNIEXPORT jshortArray JNICALL Java_com_sogou_speech_sogovad_SogouVadDetector_detect_1jni
        (JNIEnv *env, jobject jobj, jlong instance, jshortArray jvoice ,jlongArray jstart_end, jint sn,jobject jextra){

    struct sogouvad_proxy *vad_proxy_ = (struct sogouvad_proxy *)instance;
    if(NULL == vad_proxy_){
        return NULL;
    }

    //内存1
    jshort* data =  env->GetShortArrayElements(jvoice, NULL);
    jsize len = env->GetArrayLength(jvoice);
//    if(__enable_debug_log___){
//        LOGD("input data length = %d  (%d)",len);
//    }

    Client_Vad *_client_vad = vad_proxy_->_client_vad;
    short *y_vad_raw = vad_proxy_->y_vad_raw;

    _client_vad->detect_speech(data, len ,sn, vad_proxy_->_vad_result);

    if ( vad_proxy_->_vad_result.m_is_speech_found ) // speech has already be detected
    {
        // LOGI("m_is_speech: %s ",(_vad_result.m_is_speech?"true":"false"));
        if ( vad_proxy_->_vad_result.m_is_speech )   // current segment in speech
        {
            int output_pre_speech_voice_len = -1;
            int output_speech_voice_len = -1;

            short * output_pre_speech_voice = NULL;
            // LOGI("m_is_first_found: %d ",(_vad_result.m_is_first_found?0:-1));

            if (vad_proxy_->_vad_result.m_is_first_found )// 第一次发现语音 会把缓存前面的一部分语音一起返回
            {
                _client_vad->output_pre_speech(y_vad_raw, len);
                output_pre_speech_voice_len = len;
                // 拷贝 y_vad_raw 结果里面的len长度值
                output_pre_speech_voice=(short *)malloc(sizeof(short)*output_pre_speech_voice_len); //内存2
                memcpy(output_pre_speech_voice, y_vad_raw, sizeof(short)*output_pre_speech_voice_len );
            }

            // 拷贝 y_vad_raw 结果里面的len长度值
            short * output_speech_voice = NULL;

            _client_vad->output_speech(y_vad_raw, len);
            output_speech_voice_len = len;
            output_speech_voice=(short *)malloc(sizeof(short)*output_speech_voice_len); //内存3
            memcpy(output_speech_voice, y_vad_raw, sizeof(short)*output_speech_voice_len );

            // 返回 output_pre_speech + output_speech
            if(-1!=output_pre_speech_voice_len && -1!=output_speech_voice_len){
                short*res = (short *)malloc(sizeof(short)*(output_pre_speech_voice_len+output_speech_voice_len)); //内存4
                memcpy(res, output_pre_speech_voice, sizeof(short)*output_pre_speech_voice_len );
                memcpy(res+output_pre_speech_voice_len, output_speech_voice, sizeof(short)*output_speech_voice_len );

                jshortArray ret = env->NewShortArray(output_pre_speech_voice_len+output_speech_voice_len); //内存5
                env->SetShortArrayRegion(ret, 0, output_pre_speech_voice_len+output_speech_voice_len,res);

                env->ReleaseShortArrayElements( jvoice, data, 0); //release(内存1)
                free(output_pre_speech_voice); //release(内存2)
                free(output_speech_voice);//release(内存3)
                free(res);//release(内存4)

                // TODO 设置jstart_end
                jlong temp1 = vad_proxy_->_vad_result.start,temp2 = vad_proxy_->_vad_result.end;
                env->SetLongArrayRegion(jstart_end,0,1,&temp1);
                env->SetLongArrayRegion(jstart_end,1,1,&temp2);

                return ret; //release(内存5)
            }else{

                jshortArray ret1 = env->NewShortArray(output_speech_voice_len);
                env->SetShortArrayRegion(ret1, 0, output_speech_voice_len,output_speech_voice);

                env->ReleaseShortArrayElements( jvoice, data, 0); //release(内存1)
                free(output_speech_voice); //release(内存3)

                // TODO 设置jstart_end
                jlong temp1 =  vad_proxy_->_vad_result.start,temp2 =  vad_proxy_->_vad_result.end;
                env->SetLongArrayRegion(jstart_end,0,1,&temp1);
                env->SetLongArrayRegion(jstart_end,1,1,&temp2);

                return ret1;
            }
        }
        else
        {
            // 前面发现过语音 当前帧不是语音
            env->ReleaseShortArrayElements( jvoice, data, 0); //release(内存1)
            return NULL;
        }
    }
    else    // speech has not been detected yet
    {
        env->ReleaseShortArrayElements(jvoice, data, 0); //release(内存1)

        return NULL;
    }

    env->ReleaseShortArrayElements(jvoice, data, 0); //release(内存1)

    return NULL;
}

JNIEXPORT void JNICALL Java_com_sogou_speech_sogovad_SogouVadDetector_release_1jni
        (JNIEnv * env, jobject jobj, jlong instance ,jobject jextra){
    struct sogouvad_proxy *vad_proxy_ = (struct sogouvad_proxy *)instance;
    vad_proxy_->_vad_result.reset();
    vad_proxy_->_client_vad->reset();
    delete(vad_proxy_->_client_vad);
    free(vad_proxy_);
}

JNIEXPORT void JNICALL Java_com_sogou_speech_sogovad_SogouVadDetector_enable_1debug_1log(JNIEnv *env, jobject jobj, jboolean enable){
    __enable_debug_log___ = enable;
}
