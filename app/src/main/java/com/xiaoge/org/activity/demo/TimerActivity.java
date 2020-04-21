package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.xiaoge.org.R;

public class TimerActivity extends AppCompatActivity {
    public static final String TAG = TimerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_AlarmManager)
    void btn_AlarmManager() {
        //创建一个PendingIntent
        Intent intent = new Intent(TimerActivity.this, WorkerService.class);
        PendingIntent sender = PendingIntent.getService(TimerActivity.this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //获取AlarmManager对象
        AlarmManager alarm = (AlarmManager) TimerActivity.this.getSystemService(Context.ALARM_SERVICE);
        //执行定时任务
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                , 1000 * 5, sender);
    }
}
