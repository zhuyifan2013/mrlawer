package com.lawer.mrlawer.network;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class BasicResponse {

    public static final String PARAM_RESULT_CODE = "result_code";

    private int mResultCode;
    private JSONObject mData;

    private BasicResponse() {
        mResultCode = ResultCode.RESULT_DEFAULT;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        this.mResultCode = resultCode;
    }

    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject mData) {
        this.mData = mData;
    }

    public static BasicResponse makeResponse(HttpResponse httpResponse) {
        BasicResponse basicResponse = new BasicResponse();
        if (httpResponse != null) {

            try {
                String rawJson = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = new JSONObject(rawJson);
                basicResponse.setResultCode(jsonObject.getInt(PARAM_RESULT_CODE));
                jsonObject.remove(PARAM_RESULT_CODE);
                basicResponse.setData(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return basicResponse;
    }
}
