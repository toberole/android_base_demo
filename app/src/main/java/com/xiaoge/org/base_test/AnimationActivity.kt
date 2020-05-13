package com.xiaoge.org.base_test

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.xiaoge.org.R
import com.xiaoge.org.util.LogUtil
import kotlinx.android.synthetic.main.activity_animation.*

/*

    AccelerateDecelerateInterpolator           在动画开始与介绍的地方速率改变比较慢，在中间的时候加速
    AccelerateInterpolator                     在动画开始的地方速率改变比较慢，然后开始加速
    AnticipateInterpolator                     开始的时候向后然后向前甩
    AnticipateOvershootInterpolator            开始的时候向后然后向前甩一定值后返回最后的值
    BounceInterpolator                         动画结束的时候弹起
    CycleInterpolator                          动画循环播放特定的次数，速率改变沿着正弦曲线
    DecelerateInterpolator                     在动画开始的地方快然后慢
    LinearInterpolator                         以常量速率改变
    OvershootInterpolator                      向前甩一定值后再回到原来位置

 */
class AnimationActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG = AnimationActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        btn_alpha.setOnClickListener(this)
        btn_scale.setOnClickListener(this)
        btn_rotate.setOnClickListener(this)
        btn_trans.setOnClickListener(this)
        tvView.setOnClickListener(this)
        test()
    }

    private fun test() {
        var propertyValuesHolder_x = PropertyValuesHolder.ofFloat("X", 1.0f, 2.0f)
        var propertyValuesHolder_y = PropertyValuesHolder.ofFloat("Y", 1.0f, 2.0f)
        var objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tvView, propertyValuesHolder_x, propertyValuesHolder_y)
        objectAnimator.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_alpha -> {

            }
            R.id.btn_scale -> {
                var animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
                tvView.startAnimation(animation)
            }
            R.id.btn_rotate -> {

            }
            R.id.btn_trans -> {

            }
            else -> {
                LogUtil.i(TAG, "tvView clicked ......")
            }
        }
    }
}
