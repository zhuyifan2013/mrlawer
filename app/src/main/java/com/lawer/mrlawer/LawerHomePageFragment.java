package com.lawer.mrlawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.util.QuestionUtil;

import java.util.List;

public class LawerHomePageFragment extends Fragment {


    private Button mChangeQuestionSetBtn, mLogoutBtn;
    private TextView mSkilledTv;
    private Account mAccount = AccountManager.getCurAccount();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lawer_homepage_fragment, container, false);
        mChangeQuestionSetBtn = (Button) view.findViewById(R.id.change_question_set);
        mSkilledTv = (TextView) view.findViewById(R.id.skilled_area);
        mLogoutBtn = (Button) view.findViewById(R.id.logout);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}
