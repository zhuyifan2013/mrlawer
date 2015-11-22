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
import android.widget.Spinner;
import android.widget.TextView;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.network.RongyunRequest;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserHomePageFragment extends Fragment {

    private Button mRegisterBtn, mLoginBtn, mLogoutBtn, mConListBtn, mNextBtn;
    private Spinner mQuesPersonalSp, mQuesCompanySp;
    private CurAccountUpdateReceiver mCurAccountUpdateReceiver;
    private TextView mWelcomeTv;

    public static final String KEY_REQUEST_TYPE = "request_type";
    public static final int REQUEST_TYPE_LOGIN = 1;
    public static final int REQUEST_TYPE_REGISTER = 2;

    private Account mAccount = AccountManager.getCurAccount();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurAccountUpdateReceiver = new CurAccountUpdateReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mCurAccountUpdateReceiver, new IntentFilter
                (AccountManager.ACTION_CUR_ACCOUNT_UPDATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        mWelcomeTv = (TextView) view.findViewById(R.id.welcome_tv);
        mRegisterBtn = (Button) view.findViewById(R.id.register);
        mLoginBtn = (Button) view.findViewById(R.id.login);
        mLogoutBtn = (Button) view.findViewById(R.id.logout);
        mConListBtn = (Button) view.findViewById(R.id.conversation_list);
        mNextBtn = (Button) view.findViewById(R.id.next);
        mQuesPersonalSp = (Spinner) view.findViewById(R.id.sp_personal);
        mQuesCompanySp = (Spinner) view.findViewById(R.id.sp_company);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Intent intent = new Intent(getActivity(), AccountActivity.class);
        switch (mAccount.getUserType()) {
            case Account.USER_TYPE_LAWER:
                mWelcomeTv.setText(getString(R.string.welcome, getString(R.string.user_type_lawyer)));
                break;
            case Account.USER_TYPE_CONSULTANT:
                mWelcomeTv.setText(getString(R.string.welcome, getString(R.string.user_type_consultant)));
                break;
        }
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
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO problem
                AccountManager.updateCurAccount(getActivity(), new Account());
            }
        });

        mConListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongyunRequest.connect(getActivity(), mAccount.getToken(), ConversationActivity.class);
            }
        });
        checkLoginStatus();

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditQuestionActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        RongyunRequest.connect(getActivity(), "v+sCbFFHLsSsWemtEK6YPN6R9AnKgY3idi59vy4MQiNAKTZ" +
                "+uYjoaXg0tpiFUaLUX0qCHxqAeYq0g4p+fFz0Dg==");
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
        if (mAccount.isValid()) {
            mRegisterBtn.setVisibility(View.GONE);
            mLoginBtn.setText("已登陆，用户名为" + mAccount.getUsername());
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
