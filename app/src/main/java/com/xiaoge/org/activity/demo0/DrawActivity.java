package com.xiaoge.org.activity.demo0;


import android.os.Bundle;

import com.xiaoge.org.R;
import com.xiaoge.org.views.DrawView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DrawActivity extends Activity implements View.OnClickListener {
    static int i = 0, j = 0;
    private DrawView dv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        getFilesDir();

        initView();
    }

    private void initView() {
        //获取自定义的绘图视图
        dv = findViewById(R.id.drawView1);
        dv.paint.setXfermode(null);        //取消擦除效果
        dv.paint.setStrokeWidth(5);        //初始化画笔的宽度
        dv.paint.setColor(Color.RED);    //设置画笔的颜色为红色
        dv.paint.setStrokeWidth(5);    //设置笔触的宽度为1像素

        //  dv.clear();        //橡皮檫
        // dv.back();        //返回上一步
        // dv.nothing();        //清屏

        Button clear = findViewById(R.id.btn_clear);
        Button back = findViewById(R.id.btn_back);
        Button eraser = findViewById(R.id.btn_eraser);

        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        eraser.setOnClickListener(this);

    }

    // 创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = new MenuInflater(this);//实例化一个MenuInflater对象
        inflator.inflate(R.menu.toolsmenu, menu);    //解析菜单文件
        return super.onCreateOptionsMenu(menu);
    }

    // 当菜单项被选择时，作出相应的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                j = 0;
                dv.save(i, j);
                i++;    //保存绘画到APP目录
                break;
            }
            case R.id.save_camera: {
                j = 1;
                dv.save(i, j);
                i++;
                if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))//若是获取权限失败，提示手动获取
                    Toast.makeText(DrawActivity.this, "获取权限失败，请手动授权", Toast.LENGTH_SHORT).show();
                break;
            }//保存绘画到相册

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                dv.clear();
                break;
            case R.id.btn_back:
                dv.back();
                break;
            case R.id.btn_eraser:
                dv.eraser();
                break;
            default:
                break;
        }
    }
}