package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaoge.org.R;
import com.xiaoge.org.views.M_View;

public class TestViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        M_View mView = findViewById(R.id.testView);
        mView.setOnClickListener(v -> {
            Toast.makeText(TestViewActivity.this, "Lambda表达式", Toast.LENGTH_SHORT).show();
        });
    }
}
