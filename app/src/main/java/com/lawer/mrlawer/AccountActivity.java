package com.lawer.mrlawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lawer.mrlawer.util.UiUtil;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int requestType = (int)intent.getExtras().get(UserHomePageFragment.KEY_REQUEST_TYPE);
        switch (requestType) {
            case UserHomePageFragment.REQUEST_TYPE_LOGIN:
                UiUtil.replaceFragment(this, new LoginFragment());
                break;
            case UserHomePageFragment.REQUEST_TYPE_REGISTER:
                UiUtil.replaceFragment(this, new RegisterFragment());
                break;
        }
    }
}
