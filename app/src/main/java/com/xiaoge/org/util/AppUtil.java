package com.xiaoge.org.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class AppUtil {
    private static final String TAG = AppUtil.class.getSimpleName();

    private List<MessageReceiver> receivers = new ArrayList<>();

    private volatile Context app;
    private volatile Handler msgController;
    private volatile Handler mainUIThreadHandler;

    public Context getAppContext() {
        return app;
    }

    public void postMessage(Message msg) {
        if (null != msgController && msg != null) {
            postDelayedMessage(msg, 0);
        }
    }

    public void postDelayedMessage(Message msg, long delayMillis) {
        if (msg != null && delayMillis <= 0) {
            delayMillis = 0;
        }
        msgController.sendMessageDelayed(msg, delayMillis);
    }

    public void postMessage(int messageType) {
        LogUtil.i(TAG, "messageType: " + messageType);
        if (null != msgController) {
            postDelayedMessage(messageType, 0);
        }
    }

    public void postDelayedMessage(int messageType, long delayMillis) {
        if (null != msgController) {
            if (delayMillis <= 0) {
                delayMillis = 0;
            }
            msgController.sendMessageDelayed(msgController.obtainMessage(messageType), delayMillis);
        }
    }

    public void post(Runnable runnable) {
        if (null != msgController) {
            postDelayed(runnable, 0);
        }
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        if (null != msgController) {
            if (delayMillis <= 0) {
                delayMillis = 0;
            }
            msgController.postDelayed(runnable, delayMillis);
        }
    }

    public void removeMessage(int messageType) {
        if (null != msgController) {
            msgController.removeMessages(messageType);
        }
    }

    public void runOnUiThread(Runnable runnable) {
        if (mainUIThreadHandler != null) {
            mainUIThreadHandler.post(runnable);
        }
    }

    public void init(Context context) {
        if (app == null && context != null) {
            synchronized (AppUtil.class) {
                if (app == null) {
                    app = context.getApplicationContext();
                    mainUIThreadHandler = new Handler(Looper.getMainLooper());
                    new MsgControllerThread().start();
                }
            }
        }
    }

    public synchronized void destroy() {
        app = null;
        mainUIThreadHandler = null;

        if (null != msgController) {
            msgController.getLooper().quit();
            msgController = null;
        }
        receivers.removeAll(receivers);
    }

    public void toast(final Spanned msg) {
        if (!TextUtils.isEmpty(msg)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast t = Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT);
                    t.setText(msg);
                    t.show();
                }
            });
        }
    }

    public void toast(final Spanned msg, int gravity) {
        if (!TextUtils.isEmpty(msg)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast t = Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT);
                    t.setText(msg);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            });
        }
    }

    public void toast(final String msg, int gravity) {
        if (!TextUtils.isEmpty(msg)) {
            toast(new SpannableString(msg), gravity);
        }
    }

    public void toast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            toast(new SpannableString(msg));
        }
    }

    public void removeMessage(int messageType, Object object) {
        if (null != msgController) {
            msgController.removeMessages(messageType);
            msgController.removeMessages(messageType, object);
        }
    }

    public synchronized void registerMessageReceiver(MessageReceiver receiver) {
        if (null != receiver && !receivers.contains(receiver)) {
            receivers.add(receiver);
        }
    }

    public synchronized void unRegisterMessageReceiver(MessageReceiver receiver) {
        if (null != receiver) {
            receivers.remove(receiver);
        }
    }

    private synchronized void dispatchAppMessage(Message msg) {
        for (MessageReceiver receiver : receivers) {
            receiver.onReceivedMsg(msg);
        }
    }

    public Message obtainMessage() {
        return msgController.obtainMessage();
    }

    public void execute(Runnable task) {
        execute(task, false);
    }

    public void execute(Runnable runnable, boolean isLinear) {
        AppExecutor.getInstance().execute(runnable, isLinear);
    }

    public Executor getExecutor() {
        return AppExecutor.getInstance().getExecutor();
    }

    public Executor getLinearExecutor() {
        return AppExecutor.getInstance().getLinearExecutor();
    }

    public Looper getLooper() {
        if (msgController != null) {
            return msgController.getLooper();
        }

        return null;
    }

    public Looper getMainLooper() {
        if (mainUIThreadHandler != null) {
            return mainUIThreadHandler.getLooper();
        }

        return null;
    }

    private class MsgControllerThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            msgController = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    dispatchAppMessage(msg);
                }
            };
            Looper.loop();
        }
    }

    public int dip2px(float dpValue) {
        float scale = app.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public String getFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return format.format(new Date());
    }

    public static interface MessageReceiver {
        void onReceivedMsg(Message msg);
    }

    public Handler getMsgController() {
        return msgController;
    }

    public static AppUtil getInstance() {
        return APPUtilHolder.instance;
    }

    private AppUtil() {
    }

    private static class APPUtilHolder {
        public static AppUtil instance = new AppUtil();
    }
}