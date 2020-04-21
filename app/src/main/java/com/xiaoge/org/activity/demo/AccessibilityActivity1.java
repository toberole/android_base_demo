package com.xiaoge.org.activity.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaoge.org.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AccessibilityActivity1 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = AccessibilityActivity1.class.getSimpleName();

    private AppCompatButton btn_accessibility;

    private View.AccessibilityDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        Log.i(TAG, TAG + "***** onCreate");

        btn_accessibility = findViewById(R.id.btn_accessibility);
        btn_accessibility.setOnClickListener(this);
        delegate = new MyAccessibilityDelegate();
        btn_accessibility.setAccessibilityDelegate(delegate);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, TAG + "***** onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, TAG + "***** onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, TAG + "***** onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, TAG + "***** onStop");
    }

    private class MyAccessibilityDelegate extends View.AccessibilityDelegate {
        @Override
        public void sendAccessibilityEvent(View host, int eventType) {
            super.sendAccessibilityEvent(host, eventType);
            Log.i(TAG, "sendAccessibilityEvent");
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            Log.i(TAG, "performAccessibilityAction");
            return super.performAccessibilityAction(host, action, args);
        }
    }

    private void test() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(null, null);
        fragmentTransaction.commit();
    }
}
