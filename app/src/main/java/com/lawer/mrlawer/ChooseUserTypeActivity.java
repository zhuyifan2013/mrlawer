package com.lawer.mrlawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.util.SysUtil;

public class ChooseUserTypeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_user_type_activity);
        Button consultBtn = (Button) findViewById(R.id.consultant);
        Button lawyer = (Button) findViewById(R.id.lawyer);
        consultBtn.setOnClickListener(this);
        lawyer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lawyer:
                AccountManager.getCurAccount().setUserType(Account.USER_TYPE_LAWER);
                SysUtil.putInt(this, Account.PARAM_USER_TYPE, Account.USER_TYPE_LAWER);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.consultant:
                AccountManager.getCurAccount().setUserType(Account.USER_TYPE_CONSULTANT);
                SysUtil.putInt(this, Account.PARAM_USER_TYPE, Account.USER_TYPE_CONSULTANT);
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        finish();
    }
}
