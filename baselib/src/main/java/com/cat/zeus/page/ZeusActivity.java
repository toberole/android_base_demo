package com.cat.zeus.page;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cat.zeus.annotation.ZeusBindView;
import com.cat.zeus.annotation.ZeusOnClick;
import com.cat.zeus.utils.APT;
import com.sogou.speech.base_lib.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ZeusActivity extends Activity {
    public static final String TAG = ZeusActivity.class.getSimpleName();

    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_text;

    private int titleBarLayout;
    protected View titleBarView;

    private RelativeLayout rl_titleBar_container;

    private FrameLayout fl_titleBar_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeus);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fl_titleBar_container = findViewById(R.id.fl_titleBar_container);
        boolean hideTitleBar = APT.isHideTitleBar(ZeusActivity.this.getClass());
        if (hideTitleBar) {
            fl_titleBar_container.setVisibility(View.GONE);
        } else {
            rl_titleBar_container = findViewById(R.id.rl_titleBar_container);
            titleBarLayout = APT.getTitleBarLayoutByAnnotation(this.getClass());
            if (0 == titleBarLayout) {
                if ((titleBarLayout = setTitleBarLayout()) != 0) {
                    rl_titleBar_container.setVisibility(View.INVISIBLE);
                    titleBarView = View.inflate(this, titleBarLayout, fl_titleBar_container);
                } else {
                    iv_left = findViewById(R.id.iv_left);
                    iv_right = findViewById(R.id.iv_right);
                    tv_text = findViewById(R.id.tv_text);
                }
            } else {
                titleBarView = View.inflate(this, titleBarLayout, fl_titleBar_container);
            }
        }

        FrameLayout fl_container = findViewById(R.id.fl_container);
        int layout = APT.getLayoutByAnnotation(this.getClass());
        if (0 != layout) {
            View.inflate(this, layout, fl_container);
        } else {
            View.inflate(this, setLayout(), fl_container);
        }

        if (iv_left != null) {
            if (APT.isHideLeftIV(ZeusActivity.this.getClass())) {
                iv_left.setVisibility(View.INVISIBLE);
            } else {
                View.OnClickListener l = setIvLeftClickListener();
                if (l != null) {
                    iv_left.setOnClickListener(l);
                } else {
                    iv_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        }

        if (iv_right != null) {
            View.OnClickListener l = setIvRightClickListener();
            if (l != null) {
                iv_right.setOnClickListener(l);
            }
        }

        if (tv_text != null) {
            String title = setTitleText();
            if (TextUtils.isEmpty(title)) {
                //解析注解
                title = APT.getTitleTextByAnnotation(this.getClass());
                if (TextUtils.isEmpty(title)) {
                    title = getAppLabel();
                }
            }
            tv_text.setText(title);
        }

        init();
        initViews();
        initData();
    }

    public View getTitleBarView() {
        return titleBarView;
    }

    protected int setLayout() {
        return 0;
    }

    protected String setTitleText() {
        return null;
    }

    protected View.OnClickListener setIvRightClickListener() {
        return null;
    }

    protected View.OnClickListener setIvLeftClickListener() {
        return null;
    }

    public int setTitleBarLayout() {
        return 0;
    }

    protected void initData() {

    }

    protected void initViews() {
        Field[] fields = ZeusActivity.this.getClass().getDeclaredFields();
        Method method;
        try {
            method = ZeusActivity.this.getClass().getMethod("findViewById", int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Map<Integer, View> views = new HashMap<>();

        if (fields != null && fields.length > 0) {
            for (Field f : fields) {
                ZeusBindView zeusBindView = f.getAnnotation(ZeusBindView.class);
                if (null != zeusBindView) {
                    int id = zeusBindView.value();
                    if (0 != id) {
                        try {
                            View v = (View) method.invoke(ZeusActivity.this, id);
                            if (v != null) {
                                views.put(id, v);
                                f.setAccessible(true);
                                f.set(ZeusActivity.this, v);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        Method[] methods = ZeusActivity.this.getClass().getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method m : methods) {
                ZeusOnClick zeusOnClick = m.getAnnotation(ZeusOnClick.class);
                if (null != zeusOnClick) {
                    int id = zeusOnClick.value();
                    if (0 != id) {
                        try {
                            m.setAccessible(true);
                            View v = views.get(id);
                            if (v == null) {
                                v = (View) method.invoke(ZeusActivity.this, id);
                            }
                            if (v != null) {
                                v.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            m.invoke(ZeusActivity.this);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        views.clear();
    }

    protected void init() {

    }

    private String getAppLabel() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void toast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ZeusActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void startPage(final Class<? extends Activity> clazz) {
        if (null != clazz) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ZeusActivity.this, clazz);
                    ZeusActivity.this.startActivity(intent);
                }
            });
        }
    }
}
