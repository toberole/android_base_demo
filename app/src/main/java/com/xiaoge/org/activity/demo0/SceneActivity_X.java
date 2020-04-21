package com.xiaoge.org.activity.demo0;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.views.SceneView;

public class SceneActivity_X extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SceneActivity_X.class.getSimpleName();
    private SceneView scene_vv;
    private Button btn_xxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_x);

        scene_vv = findViewById(R.id.scene_vv);
        btn_xxx = findViewById(R.id.btn_xxx);

        btn_xxx.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xxx:
                scene_vv.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
