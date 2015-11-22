package com.lawer.mrlawer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.util.SysUtil;

public class AccountManager {

    public static final String ACTION_CUR_ACCOUNT_UPDATE = "cur_account_update";

    private static Account mCurAccount = new Account();

    /**
     * 检查SharedPreference里有没有登录信息
     */
    public static void initAccount(Context context) {
        mCurAccount.setUserType(SysUtil.getInt(context, Account.PARAM_USER_TYPE, -1));
        mCurAccount.setUsername(SysUtil.getString(context, Account.PARAM_USERNAME, null));
        mCurAccount.setPassword(SysUtil.getString(context, Account.PARAM_PASSWORD, null));
        //TODO 获取更多账户信息 通过数据库和联网
    }

    public static void updateCurAccount(Context context, Account account) {
        mCurAccount = account;
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_CUR_ACCOUNT_UPDATE));
        SysUtil.putInt(context, Account.PARAM_USER_TYPE, account.getUserType());
        SysUtil.putString(context, Account.PARAM_USERNAME, account.getUsername());
        SysUtil.putString(context, Account.PARAM_PASSWORD, account.getPassword());
    }

    public static Account getCurAccount() {
        return mCurAccount;
    }

}
