package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.views.AITipView;

public class AITipActivity extends AppCompatActivity implements View.OnClickListener {

    private AITipView aiTipView;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aitip);

        aiTipView = findViewById(R.id.ai_tip);
        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                aiTipView.start1();
                break;
            default:
                break;
        }
    }
}
