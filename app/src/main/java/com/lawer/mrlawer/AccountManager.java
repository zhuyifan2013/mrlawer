package com.lawer.mrlawer;


import android.content.Context;
import android.content.Intent;
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
        mCurAccount.setId(SysUtil.getInt(context, Account.PARAM_USERID, -1));
        mCurAccount.setToken(SysUtil.getString(context, Account.PARAM_TOKEN, ""));
        mCurAccount.setUserName(SysUtil.getString(context, Account.PARAM_USERNAME, ""));
        mCurAccount.setPassword(SysUtil.getString(context, Account.PARAM_PASSWORD, ""));
        mCurAccount.setCityCode(SysUtil.getInt(context, Account.PARAM_CITY, -1));
        mCurAccount.setmNickName(SysUtil.getString(context, Account.PARAM_NICKNAME, ""));
        mCurAccount.setGender(SysUtil.getInt(context, Account.PARAM_GENDER, -1));
        mCurAccount.setAge(SysUtil.getInt(context, Account.PARAM_AGE, -1));
        mCurAccount.setUserType(SysUtil.getInt(context, Account.PARAM_USER_TYPE, -1));
        mCurAccount.setCollege(SysUtil.getString(context, Account.PARAM_COLLEGE, ""));
        mCurAccount.setEducation(SysUtil.getString(context, Account.PARAM_EDUCATION, ""));
        mCurAccount.setFamiliarArea(SysUtil.getInt(context, Account.PARAM_FAMILIAR_AREA, -1));
    }

    public static void updateCurAccount(Context context, Account account) {
        mCurAccount = account;
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_CUR_ACCOUNT_UPDATE));
        SysUtil.putInt(context, Account.PARAM_USERID, account.getId());
        SysUtil.putString(context, Account.PARAM_TOKEN, account.getToken());
        SysUtil.putString(context, Account.PARAM_USERNAME, account.getUserName());
        SysUtil.putString(context, Account.PARAM_PASSWORD, account.getPassword());
        SysUtil.putInt(context, Account.PARAM_CITY, account.getCityCode());
        SysUtil.putString(context, Account.PARAM_NICKNAME, account.getmNickName());
        SysUtil.putInt(context, Account.PARAM_GENDER, account.getGender());
        SysUtil.putInt(context, Account.PARAM_AGE, account.getAge());
        SysUtil.putInt(context, Account.PARAM_USER_TYPE, account.getUserType());
        SysUtil.putString(context, Account.PARAM_COLLEGE, account.getCollege());
        SysUtil.putString(context, Account.PARAM_EDUCATION, account.getEducation());
        SysUtil.putInt(context, Account.PARAM_FAMILIAR_AREA, account.getFamiliarArea());
    }

    public static Account getCurAccount() {
        return mCurAccount;
    }

}
