package com.xiaoge.org.base_test

import android.media.audiofx.AcousticEchoCanceler
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xiaoge.org.R
import com.xiaoge.org.media.AppAudioRecorder
import com.xiaoge.org.util.LogUtil
import kotlinx.android.synthetic.main.activity_acoustic_echo_canceler.*
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * android自带组件AcousticEchoCanceler
 * 回声消除
 */
class AcousticEchoCancelerActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG = AcousticEchoCancelerActivity::class.java.simpleName

        val testFile = "${Environment.getExternalStorageDirectory()}${File.separator}fuck_android${File.separator}"
    }

    private var out: FileOutputStream? = null

    private var acousticEchoCanceler: AcousticEchoCanceler? = null

    private var appAudioRecorder: AppAudioRecorder? = null

    private var audioSessionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acoustic_echo_canceler)
        btn_record.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_record -> {
                appAudioRecorder = AppAudioRecorder("", 2048,
                        object : AppAudioRecorder.AudioReceiverCallback {
                            override fun onRecorderError(errorCode: Int, errorMsg: String?) {
                                LogUtil.i(TAG, "onRecorderError errorCode:$errorCode,errorMsg:$errorMsg")
                            }

                            override fun onStarted(_audioSessionId: Int) {
                                LogUtil.i(TAG, "onStarted audioSessionId:$audioSessionId")
                                audioSessionId = _audioSessionId
                                var file = File(testFile)
//                                    initAEC()
//                                    APPMediaPlayer.getInstance().play(testFile)
                            }

                            override fun onExternalVoiceEnd() {
                                LogUtil.i(TAG, "onExternalVoiceEnd")
                            }

                            override fun onCaptureVoice(voices: ShortArray?, pkgId: Int) {
                                out?.write(shortToBytes(voices))
                            }
                        })

                appAudioRecorder?.start()
            }

            R.id.btn_stop_record -> {
                out?.close()
                out = null
            }
        }
    }

    private fun initAEC() {
        if (AcousticEchoCanceler.isAvailable()) {
            if (acousticEchoCanceler == null) {
                acousticEchoCanceler = AcousticEchoCanceler.create(audioSessionId)
                Log.d(TAG, "initAEC: ---->$acousticEchoCanceler\t$audioSessionId")
                if (acousticEchoCanceler == null) {
                    Log.e(TAG, "initAEC: ----->AcousticEchoCanceler create fail.")
                } else {
                    acousticEchoCanceler?.setEnabled(true)
                }
            }
        } else {
            Log.i(TAG, "AcousticEchoCanceler is unAvailable")
        }
    }

    private fun bytesToShort(bytes: ByteArray?): ShortArray? {
        if (bytes == null) {
            return null
        }
        val shorts = ShortArray(bytes.size / 2)
        ByteBuffer.wrap(bytes).order(ByteOrder.nativeOrder()).asShortBuffer().get(shorts)
        return shorts
    }

    private fun shortToBytes(shorts: ShortArray?): ByteArray? {
        if (shorts == null) {
            return null
        }
        val bytes = ByteArray(shorts.size * 2)
        ByteBuffer.wrap(bytes).order(ByteOrder.nativeOrder()).asShortBuffer().put(shorts)
        return bytes
    }
}
