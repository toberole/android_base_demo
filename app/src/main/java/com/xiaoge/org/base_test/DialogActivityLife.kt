package com.xiaoge.org.base_test

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil
import kotlinx.android.synthetic.main.activity_dialog_life.*

/**
 * 弹出dialog
 * 不会导致activity的onPause方法调用
 */
class DialogActivityLife : AppCompatActivity(), View.OnClickListener {
    private var TAG = DialogActivityLife::class.java.simpleName + "-xxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_life)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }

    private var dialog: AlertDialog? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn1 -> {
                if (dialog == null) {
                    var dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("test")
                    dialogBuilder.setPositiveButton("ok", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            LogUtil.i(TAG, "ok ...")
                        }
                    })

                    dialogBuilder.setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            LogUtil.i(TAG, "cancel ...")
                        }
                    })

                    dialog = dialogBuilder.create()
                }

                if (dialog?.isShowing!!) {
                    dialog?.dismiss()
                } else {
                    dialog?.show()
                }
            }
            R.id.btn2 -> {

            }
        }
    }
}
