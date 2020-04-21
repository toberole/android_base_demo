package com.xiaoge.org.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.xiaoge.org.R;

public class NavigationActivity extends AppCompatActivity {

    private ConstraintLayout cl_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        cl_container = findViewById(R.id.cl_container);
    }

    @Override
    /**
     * onSupportNavigateUp()方法的重写，
     * 意味着Activity将它的 back键点击事件的委托出去，
     * 如果当前并非栈中顶部的Fragment,
     * 那么点击back键，返回上一个Fragment。
     */
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp();
    }
}
