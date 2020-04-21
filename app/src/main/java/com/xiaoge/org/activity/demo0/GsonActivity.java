package com.xiaoge.org.activity.demo0;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.xiaoge.org.R;
import com.xiaoge.org.bean.Person;

import java.io.StringWriter;

public class GsonActivity extends AppCompatActivity {
    public static final String TAG = GsonActivity.class.getSimpleName();

    @OnClick(R.id.btn_test)
    void btn_test() {
        test1();
    }

    private void test1() {
        Gson gson = new Gson();
        Person p = new Person();
        p.age = 11;
        p.name = "hello";
        String ps = gson.toJson(p);
        Log.i(TAG, "toJson: " + ps);

        try {
            StringWriter sw = new StringWriter();
            JsonWriter writer = new JsonWriter(sw);
            writer.beginObject()
                    .name("age")
                    .value(22)
                    .name("name")
                    .value("xiaoming")
                    .endObject();

            Log.i(TAG, "JsonWriter: " + sw.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StringWriter sw = new StringWriter();
            JsonWriter writer = new JsonWriter(sw);
            writer.beginObject()
                    .name("address")
                    .value("china")
                    .name("name")
                    .value("zhangsan")
                    .endObject();

            Log.i(TAG, "JsonWriter: " + sw.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        ButterKnife.bind(this);
    }
}
