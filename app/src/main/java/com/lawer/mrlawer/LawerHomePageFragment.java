package com.lawer.mrlawer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.util.QuestionUtil;

import java.util.List;

public class LawerHomePageFragment extends Fragment {


    private Button mChangeQuestionSetBtn, mLogoutBtn, mRegisterBtn, mLoginBtn;
    private TextView mSkilledTv;
    private Account mAccount = AccountManager.getCurAccount();
    private CurAccountUpdateReceiver mCurAccountUpdateReceiver;

    public static final String KEY_REQUEST_TYPE = "request_type";
    public static final int REQUEST_TYPE_LOGIN = 1;
    public static final int REQUEST_TYPE_REGISTER = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurAccountUpdateReceiver = new CurAccountUpdateReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mCurAccountUpdateReceiver, new IntentFilter
                (AccountManager.ACTION_CUR_ACCOUNT_UPDATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lawer_homepage_fragment, container, false);
        mChangeQuestionSetBtn = (Button) view.findViewById(R.id.change_question_set);
        mSkilledTv = (TextView) view.findViewById(R.id.skilled_area);
        mLogoutBtn = (Button) view.findViewById(R.id.logout);
        mRegisterBtn = (Button) view.findViewById(R.id.register);
        mLoginBtn = (Button) view.findViewById(R.id.login);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Intent intent = new Intent(getActivity(), AccountActivity.class);
        mChangeQuestionSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PickQuestionSetActivity.class));
            }
        });
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.updateCurAccount(getActivity(), new Account());
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQUEST_TYPE, REQUEST_TYPE_REGISTER);
                startActivity(intent);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQUEST_TYPE, REQUEST_TYPE_LOGIN);
                startActivity(intent);
            }
        });
        checkLoginStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> personalList = QuestionUtil.makeString(getActivity(), mAccount.getFamiliarArea(), QuestionUtil
                .TYPE_PERSONAL);
        List<String> companyList = QuestionUtil.makeString(getActivity(), mAccount.getFamiliarArea(), QuestionUtil
                .TYPE_COMPANY);
        String showString = "";
        for (String string : personalList) {
            showString += string;
            showString += " ";
        }

        for (String string : companyList) {
            showString += string;
            showString += " ";
        }
        mSkilledTv.setText(showString);

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mCurAccountUpdateReceiver);
        super.onDestroy();
    }

    private class CurAccountUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkLoginStatus();
        }
    }

    private void checkLoginStatus() {
        mAccount = AccountManager.getCurAccount();
        if (mAccount.isValid()) {
            mRegisterBtn.setVisibility(View.GONE);
            mLoginBtn.setText("已登陆，用户名为" + mAccount.getUserName());
            mLoginBtn.setEnabled(false);
            mLogoutBtn.setVisibility(View.VISIBLE);
        } else {
            mRegisterBtn.setVisibility(View.VISIBLE);
            mLoginBtn.setText(R.string.login);
            mLoginBtn.setEnabled(true);
            mLogoutBtn.setVisibility(View.GONE);
        }
    }
}
