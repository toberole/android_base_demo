package com.xiaoge.org.activity.demo;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class IntentServiceTest extends IntentService {
    public IntentServiceTest(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
