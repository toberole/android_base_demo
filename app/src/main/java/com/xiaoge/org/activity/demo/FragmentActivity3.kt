package com.xiaoge.org.activity.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xiaoge.org.R
import com.xiaoge.org.fragment.Fragment3
import kotlinx.android.synthetic.main.activity_fragment3.*

/*
1、show/hideFragment只是改变fragment根View的visibility，最多带上个动画效果，
另外只有本身是hidden的fragment，调用show才起作用，否则没用的，
fragment.onHiddenChanged会被触发；其次不会有生命周期callback触发，
当然了这些操作的前提是已经被add了的fragment；

2、addFragment的时候，不管加不加入回退栈都一样，
经历的生命周期如下：onAttach、onCreate、onCreateView、onActivityCreate、onStart、onResume；

3、removeFragment的时候，
经历的生命周期如下：onPause、onStop、onDestroyView，如果不加回退栈还会继续走
onDestroy、onDetach；remove的时候不仅从mAdded中移除fragment，也从mActive中移除了；

4、attach/detachFragment的前提都是已经add了的fragment，
其生命周期回调不受回退栈影响。attach的时候onCreateView、onActivityCreate、onStart、onResume会被调用；
detach的时候onPause、onStop、onDestroyView会被调用，onDestroy、onDetach不会被调用；
对应的fragment只是从mAdded中移除了；

5、remove、detachFragment的时候，
当FragmentManagerImpl.makeInactive()被调用的话，
fragment就变成了一个空壳，里面绝大部分字段都会被置空，
注意只是系统内部自己管理的字段，假如你在自己的fragment子类中引入了新的字段，
当你重用这些类的对象时要自己处理这种情况（即系统不会reset你自己造的字段

 */
class FragmentActivity3 : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment3)

        btn_op1.setOnClickListener(this)
        btn_op2.setOnClickListener(this)
        btn_op3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_op1 -> {
                op1();
            }
            R.id.btn_op2 -> {
                op2();
            }
            R.id.btn_op3 -> {
                op3();
            }
        }
    }

    /**
     * f3->f4->f5
     */
    private fun op1() {
        var f3 = Fragment3()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_container, f3, "f3")
                .commit()
    }

    private fun op2() {

    }

    private fun op3() {

    }
}
