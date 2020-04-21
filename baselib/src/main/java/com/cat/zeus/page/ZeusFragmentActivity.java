package com.cat.zeus.page;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sogou.speech.base_lib.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ZeusFragmentActivity extends FragmentActivity {

    private ImageView iv_left;
    private TextView tv_text;

    private FrameLayout fl_container;

    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_zeus);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fl_container = findViewById(R.id.fl_container);

        iv_left = findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_text = findViewById(R.id.tv_text);
        String title = setTitle();
        if (TextUtils.isEmpty(title)) {
            tv_text.setText(this.getClass().getSimpleName());
        } else {
            tv_text.setText(title);
        }

        initViews();
        initData();
    }

    protected String setTitle() {
        return "";
    }

    protected void initData() {

    }

    protected void initViews() {

    }

    public void addFragment(Fragment fragment, String tag) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            tag = fragment.getClass().getSimpleName();
        }
        ft.add(R.id.fl_container, fragment, tag);
        ft.commit();
    }

    public void addFragment(Fragment fragment) {
        addFragment(fragment, fragment.getClass().getSimpleName());
    }
}
