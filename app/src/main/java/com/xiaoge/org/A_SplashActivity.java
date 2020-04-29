package com.xiaoge.org;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;

public class A_SplashActivity extends AppCompatActivity {
    private void startNextPage() {
        Intent intent = new Intent(A_SplashActivity.this, TestIndex.clazzs[TestIndex.test_page]);
        // binder 传递数据有大小限制
        // E/Binder: Unreasonably large binder buffer
//        byte[] data = new byte[1024 * 1024];
//        intent.putExtra("hhhh", data);
        startActivity(intent);
    }

    private void startNextPage1() {
        // Intent intent = new Intent(A_SplashActivity.this, TestIndex.clazzs[TestIndex.test_page]);
        Intent intent = new Intent();
        intent.setAction("com.sogou.test");
        intent.addCategory("com.sogou.xxx");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this, Constant.PS, 1000);
    }

    @OnClick(R.id.btn_next)
    void next() {
        startNextPage();
    }
}
