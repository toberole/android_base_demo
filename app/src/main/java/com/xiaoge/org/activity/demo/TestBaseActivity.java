package com.xiaoge.org.activity.demo;

import android.widget.Button;

import com.cat.zeus.annotation.ZeusAPTBindView;
import com.cat.zeus.annotation.ZeusOnClick;
import com.cat.zeus.annotation.ZeusPage;
import com.cat.zeus.page.ZeusActivity;
import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

@ZeusPage(title = "abc", layout = R.layout.activity_test_base, hideLeftIV = true)
public class TestBaseActivity extends ZeusActivity {
    @ZeusAPTBindView(R.id.btn_xxxx)
    Button btn_xxxx;

    @Override
    protected void initViews() {
        super.initViews();
        LogUtil.i("TestBaseActivity-xxxx", String.valueOf(btn_xxxx));
    }

    @ZeusOnClick(R.id.btn_xxxx)
    void btn_xxxx(){
        LogUtil.i("TestBaseActivity-xxxx", "onclicked");
    }
}
