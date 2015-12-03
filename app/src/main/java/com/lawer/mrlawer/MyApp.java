package com.lawer.mrlawer;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.lawer.mrlawer.network.RongyunRequest;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class MyApp extends Application {

    private CurAccountUpdateReceiver mCurAccountUpdateReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.initAccount(this);
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化l
             */
            RongIM.init(this);
            RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        }
        mCurAccountUpdateReceiver = new CurAccountUpdateReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mCurAccountUpdateReceiver, new IntentFilter
                (AccountManager.ACTION_CUR_ACCOUNT_UPDATE));
    }

    @Override
    public void onTerminate() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mCurAccountUpdateReceiver);
        super.onTerminate();
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


    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        @Override
        public boolean onReceived(Message message, int i) {
            Log.i("yifan", "receive push : " + message.getSenderUserId() + "," + message.getObjectName() + "," +
                    message.getContent().toString());
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MyApp.this)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("My notification")
                            .setContentText("啦啦啦啦");
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(MyApp.this, ReceiveNotificationActivity.class);
            resultIntent.putExtra("test", "1");
            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyApp.this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(ReceiveNotificationActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(1, mBuilder.build());
            return true;
        }
    }

    private class CurAccountUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("yifan", "receive broadcast and token is : " +AccountManager.getCurAccount().getToken() );
           // RongyunRequest.connect(context.getApplicationContext(), AccountManager.getCurAccount().getToken());
        }
    }

}
