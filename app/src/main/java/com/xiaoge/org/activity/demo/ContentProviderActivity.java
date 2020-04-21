package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add)
    void btn_add() {
        // uri格式：content://authority/路径
        // authority必须与需要访问的 ContentProvider配置的authority匹配
        Uri uri = Uri.parse("content://com.xiaoge.org/test_add");
        ContentProviderActivity.this.getContentResolver().query(uri, null, null, null, null);
    }

    @OnClick(R.id.btn_delete)
    void btn_delete() {
        Uri uri = Uri.parse("content://com.xiaoge.org/test_delete");
        ContentProviderActivity.this.getContentResolver().delete(uri, null, null);
    }

    @OnClick(R.id.btn_modify)
    void btn_modify() {
//        Uri uri = Uri.parse("content://com.xiaoge.org/test_update");
//        ContentProviderActivity.this.getContentResolver().update(uri, null, null, null);

        test_modify_xxx();
    }

    @OnClick(R.id.btn_read)
    void btn_read() {
//        LogUtil.i("TestContentProvider", "ContentProviderActivity#btn_read Thread name: " + Thread.currentThread().getName());
//        Uri uri = Uri.parse("content://com.xiaoge.org/test_query");
//        ContentValues values = new ContentValues();
//        ContentProviderActivity.this.getContentResolver().insert(uri, values);
        test_insert_xxx();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.i("TestContentProvider", "ContentProviderActivity#btn_read Thread name: " + Thread.currentThread().getName());
//                Uri uri = Uri.parse("content://com.xiaoge.org/test_query");
//                ContentValues values = new ContentValues();
//                ContentProviderActivity.this.getContentResolver().insert(uri, values);
//            }
//        }).start();
    }

    @OnClick(R.id.btn_call)
    void btn_call() {
        Uri uri = Uri.parse("content://com.xiaoge.org/test_call");
        String method = "test";
        String arg = "hello call";
        Bundle extras = new Bundle();
        extras.putString("par", "btn_call");
        ContentProviderActivity.this.getContentResolver().call(uri, method, arg, extras);

    }

    private void test_insert_xxx() {
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.i("TestContentProvider", "ContentProviderActivity#btn_read Thread name: " + Thread.currentThread().getName());
                    Uri uri = Uri.parse("content://com.xiaoge.org/test_query");
                    ContentValues values = new ContentValues();
                    ContentProviderActivity.this.getContentResolver().insert(uri, values);
                }
            }).start();
        }
    }

    private void test_modify_xxx() {
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.i("TestContentProvider", "ContentProviderActivity#btn_read Thread name: " + Thread.currentThread().getName());
                    Uri uri = Uri.parse("content://com.xiaoge.org/test_update");
                    ContentValues values = new ContentValues();
                    ContentProviderActivity.this.getContentResolver().update(uri, values, null, null);
                }
            }).start();
        }
    }
}
