package com.lawer.mrlawer.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lawer.mrlawer.MyApp;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class RongyunRequest {

    /**
     *
     * @param context activity context
     * @param token token from Rongyun
     */
    public static void connect( final Activity context, String token , final Class objClass) {
        if (context.getApplicationInfo().packageName.equals(MyApp.getCurProcessName(context))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
                    context.startActivity(new Intent(context, objClass));
                    context.finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    /**
     *
     * @param context activity context
     * @param token token from Rongyun
     */
    public static void connect( final Context context, String token) {
        if (context.getApplicationInfo().packageName.equals(MyApp.getCurProcessName(context))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("yifan", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("yifan", "--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d(" yifan", "--onError" + errorCode);
                }
            });
        }
    }
}
