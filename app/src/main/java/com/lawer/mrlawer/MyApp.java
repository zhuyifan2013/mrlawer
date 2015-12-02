package com.lawer.mrlawer;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.lawer.mrlawer.network.RongyunRequest;

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
            RongIM.setOnReceivePushMessageListener(new MyReceivePushMessageListener());
        }
        RongyunRequest.connect(this, "GzG6OMtczLGCM9+dxIm4g96R9AnKgY3idi59vy4MQiMZUFkLW97kUukSaUbMa3NEHFzYOjXInBm0g4p" +
                "+fFz0Dg==");
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

    private class MyReceivePushMessageListener implements RongIMClient.OnReceivePushMessageListener {

//        @Override
//        public boolean onReceived(Message message, int i) {
//            Log.i("yifan", "receive push : " + message.getSenderUserId() + "," + message.getObjectName() + "," +
//                    message.getContent().toString());
//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(MyApp.this)
//                            .setSmallIcon(R.drawable.ic_launcher)
//                            .setContentTitle("My notification")
//                            .setContentText("你有新消息了");
//            // Creates an explicit intent for an Activity in your app
//            Intent resultIntent = new Intent(MyApp.this, ConversationActivity.class);
//
//            // The stack builder object will contain an artificial back stack for the
//            // started Activity.
//            // This ensures that navigating backward from the Activity leads out of
//            // your application to the Home screen.
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyApp.this);
//            // Adds the back stack for the Intent (but not the Intent itself)
//            stackBuilder.addParentStack(ResultActivity.class);
//            // Adds the Intent that starts the Activity to the top of the stack
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent =
//                    stackBuilder.getPendingIntent(
//                            0,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    );
//            mBuilder.setContentIntent(resultPendingIntent);
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            // mId allows you to update the notification later on.
//            mNotificationManager.notify(1, mBuilder.build());
//            return false;
//        }

        @Override
        public boolean onReceivePushMessage(PushNotificationMessage pushNotificationMessage) {
            Log.i("yifan", "receive push : " + pushNotificationMessage.getSenderUserId() + "," + pushNotificationMessage.getObjectName() + "," +
                    pushNotificationMessage.getContent().toString());
            return false;
        }
    }

}
