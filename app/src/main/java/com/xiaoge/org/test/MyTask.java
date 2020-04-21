package com.xiaoge.org.test;

import android.os.AsyncTask;

import com.xiaoge.org.util.LogUtil;

public class MyTask extends AsyncTask<Object, Integer, String> {
    public static final String TAG = MyTask.class.getSimpleName();

    public MyTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        LogUtil.i(TAG, "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        LogUtil.i(TAG, "onProgressUpdate " + values + " Thread.name: " + Thread.currentThread().getName());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        LogUtil.i(TAG, "onPostExecute " + s);
    }

    @Override
    protected String doInBackground(Object... objects) {
        LogUtil.i(TAG, "doInBackground");
        for (Object o : objects) {
            LogUtil.i(TAG, "doInBackground " + o);
        }
        LogUtil.i(TAG, "doInBackground");
        publishProgress(100);
        return "res doInBackground";
    }
}
