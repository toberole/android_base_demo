package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.xiaoge.org.Constant;
import com.xiaoge.org.R;

import java.io.File;

public class ShareActivity extends AppCompatActivity {
    public static final String TAG = ShareActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ActivityCompat.requestPermissions(this, Constant.PS, 110);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_share)
    void share() {
        Log.i(TAG, "share ...");
        Intent shareIntent = new Intent();
        File file = new File(Constant.TEMP_DIR, "wangxiaochuan.mp3");
        String type = "*/*";
        // String type = "audio/mp3";
        shareIntent.addCategory(Intent.CATEGORY_DEFAULT);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileUri = FileProvider.getUriForFile(ShareActivity.this, getString(R.string.file_provider_authority), file);
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(fileUri, type);
        } else {
            shareIntent.setDataAndType(Uri.fromFile(file), type);
        }

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I would like to share this with you...");
        startActivity(Intent.createChooser(shareIntent, getTitle()));
    }
}
