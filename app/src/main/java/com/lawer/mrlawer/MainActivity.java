package com.lawer.mrlawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.util.SysUtil;
import com.lawer.mrlawer.util.UiUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int userType = SysUtil.getInt(this, Account.PARAM_USER_TYPE, -1);
        switch (userType) {
            case Account.USER_TYPE_DEFAULT:
                startActivity(new Intent(this, ChooseUserTypeActivity.class));
                return;
            case Account.USER_TYPE_LAWER:
                UiUtil.replaceFragment(this, new LawerHomePageFragment());
                return;
            case Account.USER_TYPE_CONSULTANT:
                UiUtil.replaceFragment(this, new UserHomePageFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
