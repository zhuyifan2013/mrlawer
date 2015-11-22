package com.lawer.mrlawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.network.BasicResponse;
import com.lawer.mrlawer.network.RequestManager;
import com.lawer.mrlawer.network.ResultCode;
import com.lawer.mrlawer.util.Coder;
import com.lawer.mrlawer.util.UiUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class RegisterFragment extends Fragment {

    private Button mFillFinishBtn;
    private EditText mUsernameEt, mPasswordEt, mConfirmPwdEt;
    private TextView mAgeTv;
    private String mUsername, mPassword;
    private Spinner mProvinceSp, mCitySp, mGenderSp;
    private int mGender, mProvince, mCity, mAge;

    private Account mAccount = AccountManager.getCurAccount();

    private static final int MSG_REGISTER_SUCCESS = 0;
    private static final int MSG_REGISTER_FAILURE = 1;
    private static final int MSG_PASSWORD_NOT_SAME = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_SUCCESS:
                    UiUtil.showToast(getActivity(), R.string.register_success);
                    AccountManager.updateCurAccount(getActivity(), mAccount);
                    getActivity().finish();
                    break;
                case MSG_REGISTER_FAILURE:
                    UiUtil.showToast(getActivity(), R.string.register_failure);
                    break;
                case MSG_PASSWORD_NOT_SAME:
                    UiUtil.showToast(getActivity(), R.string.password_not_same);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = null;
        switch (mAccount.getUserType()) {
            case Account.USER_TYPE_LAWER:
                view = inflater.inflate(R.layout.lawer_register_fragment, container, false);
                break;
            case Account.USER_TYPE_CONSULTANT:
                view = inflater.inflate(R.layout.user_register_fragment, container, false);
                break;
        }
        mFillFinishBtn = (Button) view.findViewById(R.id.fill_finish);
        mFillFinishBtn.setEnabled(false);
        mUsernameEt = (EditText) view.findViewById(R.id.username);
        mPasswordEt = (EditText) view.findViewById(R.id.password);
        mConfirmPwdEt = (EditText) view.findViewById(R.id.confirm_password);
        mGenderSp = (Spinner) view.findViewById(R.id.gender);
        mGenderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mGender = Account.GENDER_MALE;
                        break;
                    case 1:
                        mGender = Account.GENDER_FEMALE;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = Account.GENDER_MALE;
            }
        });
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList
                (getResources().getStringArray(R.array.city_beijing))));
        mProvinceSp = (Spinner) view.findViewById(R.id.province);
        mCitySp = (Spinner) view.findViewById(R.id.city);
        mCitySp.setAdapter(arrayAdapter);
        mProvinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case Account.PROVINCE_BEIJING:
                        mProvince = Account.PROVINCE_BEIJING;
                        arrayAdapter.clear();
                        arrayAdapter.addAll(getResources().getStringArray(R.array.city_beijing));
                        break;
                    case Account.PROVINCE_HENAN:
                        mProvince = Account.PROVINCE_HENAN;
                        arrayAdapter.clear();
                        arrayAdapter.addAll(getResources().getStringArray(R.array.city_henan));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (mProvince) {
                    case Account.PROVINCE_BEIJING:
                        mCity = Account.CITY_BEIJING;
                        break;
                    case Account.PROVINCE_HENAN:
                        switch (position) {
                            case 0:
                                mCity = Account.CITY_ZHENGZHOU;
                                break;
                            case 1:
                                mCity = Account.CITY_NANYANG;
                                break;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCity = Account.CITY_BEIJING;
            }
        });

        mAgeTv = (TextView) view.findViewById(R.id.age);
        mAgeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = inflater.inflate(R.layout.age_number_picker, null);
                final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.age_picker);
                numberPicker.setMaxValue(100);
                numberPicker.setMinValue(16);
                builder.setTitle("设置年龄");
                builder.setView(view);
                builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAge = numberPicker.getValue();
                        mAgeTv.setText(Integer.toString(mAge));
                    }
                });
                builder.create();
                builder.show();
            }
        });

        mPasswordEt.addTextChangedListener(mEditTextWatcher);
        mUsernameEt.addTextChangedListener(mEditTextWatcher);
        mConfirmPwdEt.addTextChangedListener(mEditTextWatcher);
        mFillFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mUsernameEt.getText().toString();
                mPassword = mPasswordEt.getText().toString();
                if (!TextUtils.equals(mPassword, mConfirmPwdEt.getText())) {
                    mHandler.obtainMessage(MSG_PASSWORD_NOT_SAME).sendToTarget();
                    return;
                }
                mAccount.setUsername(mUsername);
                mAccount.setPassword(Coder.encodePassword(mUsername, mPassword));
                mAccount.setGender(mGender);
                mAccount.setCityCode(mCity);
                mAccount.setAge(mAge);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BasicResponse response = RequestManager.register(mAccount);
                        if(response == null) {
                            mHandler.obtainMessage(MSG_REGISTER_FAILURE).sendToTarget();
                            return;
                        }
                        try {
                            mAccount.fillSelf(response.getData().getString(Constants.KEY_ACCOUNT_INFO));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (response.getResultCode() == ResultCode.RESULT_OK) {
                            mHandler.obtainMessage(MSG_REGISTER_SUCCESS).sendToTarget();
                        } else {
                            mHandler.obtainMessage(MSG_REGISTER_FAILURE).sendToTarget();
                        }
                    }
                }).start();
            }
        });
        return view;
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
            if (!TextUtils.isEmpty(mUsernameEt.getText()) && !TextUtils.isEmpty(mPasswordEt
                    .getText()) && !TextUtils.isEmpty(mConfirmPwdEt.getText())) {
                mFillFinishBtn.setEnabled(true);
            }
        }
    };
}


