package com.lawer.mrlawer.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Account implements Parcelable {

    /**
     * 性别
     */
    public static final int GENDER_FEMALE = 0;
    public static final int GENDER_MALE = 1;

    /**
     * 省份,只是与spinner保持一致
     */
    public static final int PROVINCE_BEIJING = 0; //北京
    public static final int PROVINCE_HENAN = 1; //河南

    /**
     * 城市
     */
    public static final int CITY_BEIJING = 0; //北京
    public static final int CITY_ZHENGZHOU = 1; //郑州
    public static final int CITY_NANYANG = 2; //南阳

    /**
     * 用户类型
     */
    public static final int USER_TYPE_DEFAULT = -1; //未设置
    public static final int USER_TYPE_LAWER = 1; //法科生
    public static final int USER_TYPE_CONSULTANT = 2; //普通用户

    public static final String PARAM_USERID = "userId";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_NICKNAME = "nickname";
    public static final String PARAM_GENDER = "gender";
    public static final String PARAM_CITY = "city";
    public static final String PARAM_AGE = "age";
    public static final String PARAM_COLLEGE = "college";
    public static final String PARAM_EDUCATION = "education";
    public static final String PARAM_USER_TYPE = "userType";

    private int mUserId;
    private String mToken;
    private String mUsername;
    private String mPassword;
    private int mCityCode;
    private String mNickName;
    private int mGender;
    private int mAge;
    private int mUserType;
    private String mCollege;
    private String mEducation;

    public String getEducation() {
        return mEducation;
    }

    public void setEducation(String education) {
        mEducation = education;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int userType) {
        mUserType = userType;
    }

    public String getCollege() {
        return mCollege;
    }

    public void setCollege(String college) {
        mCollege = college;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        this.mGender = gender;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        this.mAge = age;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public int getId() {
        return mUserId;
    }

    public void setId(int mId) {
        this.mUserId = mId;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public int getCityCode() {
        return mCityCode;
    }

    public void setCityCode(int cityCode) {
        this.mCityCode = cityCode;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(mUsername) && !TextUtils.isEmpty(mPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mUserId);
        dest.writeString(this.mToken);
        dest.writeString(this.mUsername);
        dest.writeString(this.mPassword);
        dest.writeInt(this.mCityCode);
        dest.writeString(this.mNickName);
        dest.writeInt(this.mGender);
        dest.writeInt(this.mAge);
        dest.writeInt(this.mUserType);
        dest.writeString(this.mCollege);
        dest.writeString(this.mEducation);
    }

    public Account() {
    }

    protected Account(Parcel in) {
        this.mUserId = in.readInt();
        this.mToken = in.readString();
        this.mUsername = in.readString();
        this.mPassword = in.readString();
        this.mCityCode = in.readInt();
        this.mNickName = in.readString();
        this.mGender = in.readInt();
        this.mAge = in.readInt();
        this.mUserType = in.readInt();
        this.mCollege = in.readString();
        this.mEducation = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public void fillSelf(String rawJson) {
        try {
            JSONObject jsonObject = new JSONObject(rawJson);
            this.setId(jsonObject.getInt(PARAM_USERID));
            this.setToken(jsonObject.getString(PARAM_TOKEN));
            this.setUsername(jsonObject.getString(PARAM_USERNAME));
            this.setPassword(jsonObject.getString(PARAM_PASSWORD));
            this.setmNickName(jsonObject.getString(PARAM_NICKNAME));
            this.setGender(jsonObject.getInt(PARAM_GENDER));
            this.setCityCode(jsonObject.getInt(PARAM_USERID));
            this.setAge(jsonObject.getInt(PARAM_USERID));
            this.setUserType(jsonObject.getInt(PARAM_USER_TYPE));
            this.setCollege(jsonObject.getString(PARAM_COLLEGE));
            this.setEducation(jsonObject.getString(PARAM_EDUCATION));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
