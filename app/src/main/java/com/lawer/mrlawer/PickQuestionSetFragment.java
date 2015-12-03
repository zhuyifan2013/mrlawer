package com.lawer.mrlawer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.lawer.mrlawer.entity.Account;
import com.lawer.mrlawer.network.BasicResponse;
import com.lawer.mrlawer.network.RequestManager;
import com.lawer.mrlawer.network.ResultCode;
import com.lawer.mrlawer.util.QuestionUtil;
import com.lawer.mrlawer.util.UiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickQuestionSetFragment extends BaseFragment {

    private GridLayout mPersonLayout, mCompanyLayout;
    private Button mFinishBtn;
    private List<QuestionTextView> mTextviewList;
    private int mQuestionSet;
    private Account mCuAccount = AccountManager.getCurAccount();
    private static final int MSG_SUCCESS = 0;
    private static final int MSG_FAILURE = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(mProgrssDialog.isShowing()) {
                mProgrssDialog.dismiss();
            }
            switch (msg.what) {
                case MSG_SUCCESS:
                    UiUtil.showToast(getActivity(), "更新成功");
                    AccountManager.updateCurAccount(getActivity(), mCuAccount);
                    getActivity().finish();
                    break;
                case MSG_FAILURE:
                    UiUtil.showToast(getActivity(), "更新失败，请重试");
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextviewList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pick_question_set_fragment, container, false);
        mPersonLayout = (GridLayout) view.findViewById(R.id.personal_question_set);
        mCompanyLayout = (GridLayout) view.findViewById(R.id.company_question_set);
        mFinishBtn = (Button) view.findViewById(R.id.finish);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgrssDialog.show();
                for (QuestionTextView textView : mTextviewList) {
                    if (textView.isChecked()) {
                        mQuestionSet += textView.getQuestionValue();
                        mCuAccount.setFamiliarArea(mQuestionSet);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BasicResponse basicResponse = RequestManager.updateAccountInfo(mCuAccount);
                                if(basicResponse.getResultCode() == ResultCode.RESULT_OK) {
                                    mHandler.obtainMessage(MSG_SUCCESS).sendToTarget();
                                } else {
                                    mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
                                }
                            }
                        }).start();
                    }
                }
            }
        });
        for (Map.Entry<Integer, Integer> entry : QuestionUtil.sQuestionResMap.entrySet()) {
            int key = entry.getKey();
            QuestionTextView textView = new QuestionTextView(getActivity());
            textView.setQuestionValue(entry.getKey());
            textView.setQuestionResId(entry.getValue());
            if ((key & 0x000FFF) != 0) {
                mPersonLayout.addView(textView);
            } else {
                mCompanyLayout.addView(textView);
            }
            mTextviewList.add(textView);
        }
    }

}
