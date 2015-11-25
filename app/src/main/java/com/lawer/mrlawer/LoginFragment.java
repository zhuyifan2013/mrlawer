package com.lawer.mrlawer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.network.BasicResponse;
import com.lawer.mrlawer.network.RequestManager;
import com.lawer.mrlawer.network.ResultCode;
import com.lawer.mrlawer.util.Coder;
import com.lawer.mrlawer.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private EditText mUsernameEt, mPasswordEt;
    private Button mFinishBtn;
    private Account mAccount;

    private static final int MSG_LOGIN_SUCCESS = 0;
    private static final int MSG_LOGIN_FAILURE = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOGIN_SUCCESS:
                    UiUtil.showToast(getActivity(), R.string.login_success);
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        mAccount.fillSelf(jsonObject.getString(Constants.KEY_ACCOUNT_INFO));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AccountManager.updateCurAccount(getActivity(), mAccount);
                    getActivity().finish();
                    break;
                case MSG_LOGIN_FAILURE:
                    switch (msg.arg1) {
                        case ResultCode.RESULT_NO_ACCOUNT:
                            UiUtil.showToast(getActivity(), R.string.login_failure_no_account);
                            break;
                        case ResultCode.RESULT_WRONG_PASSWORD:
                            UiUtil.showToast(getActivity(), R.string.password_wrong);
                            break;
                        default:
                            UiUtil.showToast(getActivity(), R.string.login_failure);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccount = new Account();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        mUsernameEt = (EditText) view.findViewById(R.id.username);
        mPasswordEt = (EditText) view.findViewById(R.id.password);
        mFinishBtn = (Button) view.findViewById(R.id.fill_finish);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mUsernameEt.addTextChangedListener(mEditTextWatcher);
        mPasswordEt.addTextChangedListener(mEditTextWatcher);
        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAccount.setUserName(mUsernameEt.getText().toString());
                mAccount.setPassword(Coder.encodePassword(mUsernameEt.getText().toString(), mPasswordEt.getText()
                        .toString()));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BasicResponse response = RequestManager.login(mAccount);
                        if (response.getResultCode() == ResultCode.RESULT_OK) {
                            mHandler.obtainMessage(MSG_LOGIN_SUCCESS, response.getData()).sendToTarget();
                        } else {
                            mHandler.obtainMessage(MSG_LOGIN_FAILURE, response.getResultCode(), 0)
                                    .sendToTarget();
                        }
                    }
                }).start();
            }
        });
    }

    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(mUsernameEt.getText()) && !TextUtils.isEmpty(mPasswordEt.getText())) {
                mFinishBtn.setEnabled(true);
            }
        }
    };
}
