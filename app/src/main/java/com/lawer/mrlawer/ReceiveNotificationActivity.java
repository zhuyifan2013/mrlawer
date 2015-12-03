package com.lawer.mrlawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.rong.imkit.RongIM;

/**
 * Created by yifan on 15-12-2.
 */
public class ReceiveNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Log.i("yifan", "get intent, test is : " + bundle.getString("test"));
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startConversationList(this);
        }
        finish();
    }
}
