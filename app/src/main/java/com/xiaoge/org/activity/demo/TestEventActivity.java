package com.xiaoge.org.activity.demo;

import android.util.Log;
import android.widget.Button;

import com.cat.zeus.annotation.ZeusBindView;
import com.cat.zeus.annotation.ZeusOnClick;
import com.cat.zeus.page.ZeusActivity;
import com.xiaoge.org.R;
import com.xiaoge.org.event.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestEventActivity extends ZeusActivity {
    public static final String TAG = TestEventActivity.class.getSimpleName();

    @ZeusBindView
    Button btn_post;

    @Override
    protected int setLayout() {
        return R.layout.activity_test_event;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
    }

    @ZeusOnClick(R.id.btn_post)
    void post() {
        TestEvent testEvent = new TestEvent();
        testEvent.msg = "hello event bus";
        EventBus.getDefault().post(testEvent);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void receiveEvent(TestEvent testEvent) {
        Log.i(TAG, "receiveEvent: " + testEvent.msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
