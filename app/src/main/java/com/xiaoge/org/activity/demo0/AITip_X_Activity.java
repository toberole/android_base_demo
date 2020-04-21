package com.xiaoge.org.activity.demo0;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.views.AITipView_X;

public class AITip_X_Activity extends AppCompatActivity implements View.OnClickListener {

    private AITipView_X aiTipView;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aitip_x);

        aiTipView = findViewById(R.id.ai_tip);
        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                aiTipView.start();
                break;
            default:
                break;
        }
    }
}
