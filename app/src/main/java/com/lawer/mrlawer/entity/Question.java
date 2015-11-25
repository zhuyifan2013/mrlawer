package com.lawer.mrlawer.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yifan on 15-11-8.
 */
public class Question {

    public static final String PARAM_QUESTIONID_INFO = "questionInfo";
    public static final String PARAM_QUESTIONID = "questionID";
    public static final String PARAM_USERID = "userID";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_TIMESTART = "timeStart";
    public static final String PARAM_TIMEEND = "timeEnd";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_URL = "url";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_LAWERID = "lawerID";
    public static final String PARAM_STAR = "star";

    int mQuestionID;
    int mUserID;
    int mQuestionType;
    long mTimeStart;
    long mTimeEnd;
    String mText;
    String mUrl = "";
    int mStatus;
    int mLawerID;
    int mStar;

    public int getQuestionID() {
        return mQuestionID;
    }

    public void setQuestionID(int questionID) {
        mQuestionID = questionID;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public int getQuestionType() {
        return mQuestionType;
    }

    public void setQuestionType(int questionType) {
        mQuestionType = questionType;
    }

    public long getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(long timeStart) {
        mTimeStart = timeStart;
    }

    public long getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getLawerID() {
        return mLawerID;
    }

    public void setLawerID(int lawerID) {
        mLawerID = lawerID;
    }

    public int getStar() {
        return mStar;
    }

    public void setStar(int star) {
        mStar = star;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(PARAM_QUESTIONID, this.mQuestionID);
            jsonObject.put(PARAM_USERID, this.mUserID);
            jsonObject.put(PARAM_TYPE, this.mQuestionType);
            jsonObject.put(PARAM_TIMESTART, this.mTimeStart);
            jsonObject.put(PARAM_TIMEEND, this.mTimeEnd);
            jsonObject.put(PARAM_TEXT, this.mText);
            jsonObject.put(PARAM_URL, this.mUrl);
            jsonObject.put(PARAM_STATUS, this.mStatus);
            jsonObject.put(PARAM_LAWERID, this.mLawerID);
            jsonObject.put(PARAM_STAR, this.mStar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

