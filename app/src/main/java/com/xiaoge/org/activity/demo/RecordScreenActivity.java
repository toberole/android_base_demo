package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaoge.org.R;

import java.io.File;
import java.io.IOException;

public class RecordScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_screen);

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) this.
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager != null) {
            Intent intent = mediaProjectionManager.createScreenCaptureIntent();
            PackageManager packageManager = RecordScreenActivity.this.getPackageManager();
            if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                RecordScreenActivity.this.startActivityForResult(intent, REQUEST_CODE);
            } else {
                Toast.makeText(RecordScreenActivity.this, "can_not_record", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
