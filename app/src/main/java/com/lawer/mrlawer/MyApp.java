package com.lawer.mrlawer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.notification.PushNotificationMessage;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.initAccount(this);
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            RongIM.setOnReceiveMessageListener(new MyReceivePushMessageListener());
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private class MyReceivePushMessageListener implements RongIMClient.OnReceiveMessageListener {

        @Override
        public boolean onReceived(Message message, int i) {
            Log.i("yifan", "receive push : " + message.getSenderUserId() + "," + message.getObjectName() + "," + message.getContent().toString());
            return false;
        }
    }

}
