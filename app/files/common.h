//
// Created by Apple on 2018/6/25.
//

#ifndef SPEECH_COMMON_H
#define SPEECH_COMMON_H

#define MAX_WAV_LEN         16384

#define WIN_SIZE            400
#define SHIFT_SIZE          160
#define SHORT_LEN	        2048 //8192//4096 //8192
#define PRE_RESERVE_LEN     8192//2048//8000 //2048
#define ALFA_FF				0.75
#define ALFA_SF             0.995
#define BETA_SF             0.96
#define ALFA_SNR            0.98 //0.99
#define THRES_02            2.3
#define THRES_24            2.5
#define THRES_46            3.1 // 3.5
#define THRES_68            4.0

#define FFT_SIZE            512
#define SAMPLE_RATE	        16000
#define CHANNEL_NUM	        1
#define HALF_FRQ_WIN_LEN    8
#define DB_BOUND            58 //50
#define INI_FRAME	        10//20//5 //20

#define MAX_BEGIN_WAIT_TIME 3.0 //30
#define MAX_END_WAIT_TIME   1.0//0.3 //1.17

#define	MAX_VAD_PACKNUM		1000


#endif //SPEECH_COMMON_H

