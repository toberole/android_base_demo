package com.xiaoge.org.activity.demo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.os.HandlerThread;

import com.xiaoge.org.R;
import com.xiaoge.org.jni.Bean;
import com.xiaoge.org.jni.Data;
import com.xiaoge.org.jni.Gender;
import com.xiaoge.org.jni.Test;
import com.xiaoge.org.jni.Test1;
import com.xiaoge.org.util.LogUtil;

import java.util.concurrent.TimeUnit;

public class JNIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);

        HandlerThread handlerThread;
    }

    @OnClick(R.id.btn_test)
    void btn_test() {
        Test test = new Test();
        test.test((byte) 0, (char) 1, (short) 1, 1, 1f, 1, "", null);
    }

    @OnClick(R.id.btn_test1)
    void btn_test1() {
        Test test = new Test();
        byte b = 0;
        test.test1(b);
    }

    @OnClick(R.id.btn_test2)
    void btn_test2() {
        Test test = new Test();
        byte b = 0;
        test.test1(b);
    }

    @OnClick(R.id.btn_test3)
    void btn_test3() {
        Test1.sysHello("hello");
    }

    @OnClick(R.id.data_test)
    void data_test() {
        Data data = new Data();
        data.test();
    }

    @OnClick(R.id.data_test1)
    void data_test1() {
        Data.test1();
    }

    @OnClick(R.id.data_test2)
    void data_test2() {
        byte b = 0;
        char ch = 'a';
        short s = 1;
        int i = 2;
        long l = 3;
        float f = 1.1f;
        double d = 2.2;
        boolean bl = true;

        Data.test2(b, ch, s, i, l, f, d, bl);

        Data.test2(b, ch, s, i, l, f, d, !bl);
    }

    @OnClick(R.id.data_test3)
    void data_test3() {
        Bean bean = new Bean();
        bean.tag = "hello bean";
        Data.test3(bean);
    }

    @OnClick(R.id.data_test4)
    void data_test4() {
        // String s = "hello test4 中国";
        String s = "hello test4 china";
        Data.test4(s);
        LogUtil.i("jni-log", "data_test4: " + s);
    }

    @OnClick(R.id.data_test5)
    void data_test5() {
        int arr[] = new int[]{0, 1, 2};
        Data.test5(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            LogUtil.i("jni-log", "jni 处理后 arr[" + i + "] = " + arr[i]);
        }
    }

    @OnClick(R.id.data_test5_1)
    void data_test5_1() {
        String[] strs = {"hello", "world"};
        Data.test5_1(strs);
    }

    @OnClick(R.id.data_test5_2)
    void data_test5_2() {
        byte[] bs = {0, 1, 2};
        Data.test5_2(bs);
    }

    @OnClick(R.id.data_test5_3)
    void data_test5_3() {
        Data.test5_3("hello 中国".toCharArray());
    }

    Data.Callback callback = new Data.Callback() {
        @Override
        public void onError(int errorcode, String errormsg) {
            LogUtil.i("jni-log", "onError errorcode: " + errorcode + " errormsg: " + errormsg);
        }

        @Override
        public void onCallback_Short(int id, short[] data) {
            LogUtil.i("jni-log", "onCallback_Short id: " + id + " data len: " + data.length);
            for (int i = 0; i < data.length; i++) {
                LogUtil.i("jni-log", "onCallback_Short data: " + data[i]);
            }
        }

        @Override
        public void onCallback_byte(int id, byte[] data) {
            LogUtil.i("jni-log", "onCallback_byte id: " + id + " data len: " + data.length);
            for (int i = 0; i < data.length; i++) {
                LogUtil.i("jni-log", "onCallback_byte data: " + data[i]);
            }
        }
    };

    @OnClick(R.id.data_test5_4)
    void data_test5_4() {
        Data.test5_4(callback);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.data_test5_4_1)
    void data_test5_4_1() {
        Data.test5_4_1(callback);
    }

    @OnClick(R.id.data_test5_5)
    void data_test5_5() {
        Data.test5_5(Gender.Man);
    }

    @OnClick(R.id.data_test6)
    void data_test6() {
        byte b = Data.test6();
        LogUtil.i("jni-log", "data_test6 b: " + b);
    }

    @OnClick(R.id.data_test7)
    void data_test7() {
        char ch = Data.test7();
        LogUtil.i("jni-log", "data_test7 ch: " + ch);
    }

    @OnClick(R.id.data_test8)
    void data_test8() {
        int i = Data.test8();
        LogUtil.i("jni-log", "data_test8 i: " + i);
    }

    @OnClick(R.id.data_test9)
    void data_test9() {
        String s = Data.test9();
        LogUtil.i("jni-log", "data_test8 str: " + s);
    }

    @OnClick(R.id.data_test10)
    void data_test10() {
        int arr[] = Data.test10();
        for (int i = 0; i < arr.length; i++) {
            LogUtil.i("jni-log", "data_test10 data: " + arr[i]);
        }
    }

    @OnClick(R.id.data_test11)
    void data_test11() {
        Bean bean = Data.test11();
        LogUtil.i("jni-log", "data_test11 bean: " + String.valueOf(bean));
    }

    @OnClick(R.id.data_test12)
    void data_test12() {
        Bean bean = Data.test12();
        LogUtil.i("jni-log", "data_test12 bean: " + String.valueOf(bean));
    }

    @OnClick(R.id.data_test13)
    void data_test13() {
        Data.test13();
    }

    @OnClick(R.id.data_test14)
    void data_test14() {
        Data.test14();
    }

    @OnClick(R.id.data_test14_1)
    void data_test14_1() {
        Data.test14_1();
    }

    @OnClick(R.id.test_fork)
    void test_fork() {
        Data.test_fork();
    }
    @OnClick(R.id.native_load_so)
    void native_load_so() {
        Data.native_load_so();
    }




}
