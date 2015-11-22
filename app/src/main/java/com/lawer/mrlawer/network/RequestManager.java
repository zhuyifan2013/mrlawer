package com.lawer.mrlawer.network;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.entity.Question;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RequestManager {

    protected static final String URL_SERVER = "http://10.235.118.119:8080/";
    private static final String URL_REGISTER = "register";
    private static final String URL_LOGIN = "login";
    private static final String URL_SEND_QUESTION = "sendQuestion";

    public static BasicResponse register(Account account) {
        HttpPost httpPost = new HttpPost(URL_SERVER + URL_REGISTER);
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_USERNAME, account.getUsername()));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_PASSWORD, account.getPassword()));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_GENDER, Integer.toString(account.getGender())));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_CITY, Integer.toString(account.getCityCode())));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_AGE, Integer.toString(account.getAge())));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_USER_TYPE, Integer.toString(account.getUserType())));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_COLLEGE, account.getCollege()));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_EDUCATION, account.getEducation()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return BasicResponse.makeResponse(realSendRequest(httpPost));
    }

    public static BasicResponse login(Account account) {
        HttpPost httpPost = new HttpPost(URL_SERVER + URL_LOGIN);
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_USERNAME, account.getUsername()));
        nameValuePairList.add(new BasicNameValuePair(Account.PARAM_PASSWORD, account.getPassword()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return BasicResponse.makeResponse(realSendRequest(httpPost));
    }

    public static BasicResponse sendQuestion(Question question) {
        HttpPost httpPost = new HttpPost(URL_SERVER + URL_SEND_QUESTION);
        try {
            httpPost.setEntity(new StringEntity(question.toJson().toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return BasicResponse.makeResponse(realSendRequest(httpPost));
    }

    public static HttpResponse realSendRequest(HttpPost httpPost) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
}