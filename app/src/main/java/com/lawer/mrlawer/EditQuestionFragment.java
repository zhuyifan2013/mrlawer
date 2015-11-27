package com.lawer.mrlawer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.lawer.mrlawer.entity.Question;
import com.lawer.mrlawer.network.BasicResponse;
import com.lawer.mrlawer.network.RequestManager;
import com.lawer.mrlawer.network.ResultCode;
import com.lawer.mrlawer.util.QuestionUtil;
import com.lawer.mrlawer.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditQuestionFragment extends Fragment {

    private Button mNextBtn;
    private EditText mQuestionEt;
    private GridLayout mPersonLayout, mCompanyLayout;
    private List<QuestionTextView> mTextviewList;
    private QuestionTextView mCheckedQuestionTv;
    private Question mQuestion;


    private static final int MSG_SEND_SUCCESS = 0;
    private static final int MSG_SEND_FAILURE = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEND_SUCCESS:
                    UiUtil.showToast(getActivity(), "发送成功");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    mQuestion.fillFromJson(jsonObject.toString());
                    getActivity().finish();
                    break;
                case MSG_SEND_FAILURE:
                    UiUtil.showToast(getActivity(), "发送失败，请重试");
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextviewList = new ArrayList<>();
        mQuestion = new Question();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_question_fragment, container, false);
        mNextBtn = (Button) view.findViewById(R.id.next);
        mPersonLayout = (GridLayout) view.findViewById(R.id.personal_question_set);
        mCompanyLayout = (GridLayout) view.findViewById(R.id.company_question_set);
        mQuestionEt = (EditText) view.findViewById(R.id.question_content);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion.setUserID(AccountManager.getCurAccount().getId());
                mQuestion.setQuestionType(mCheckedQuestionTv.getQuestionValue());
                mQuestion.setText(mQuestionEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BasicResponse response = RequestManager.sendQuestion(mQuestion);
                        if (response.getResultCode() == ResultCode.RESULT_OK) {
                            mHandler.obtainMessage(MSG_SEND_SUCCESS, response.getData()).sendToTarget();
                        } else {
                            mHandler.obtainMessage(MSG_SEND_FAILURE, response).sendToTarget();
                        }
                    }
                }).start();
            }
        });
        for (Map.Entry<Integer, Integer> entry : QuestionUtil.sQuestionResMap.entrySet()) {
            int key = entry.getKey();
            QuestionTextView textView = new QuestionTextView(getActivity());
            textView.setQuestionValue(entry.getKey());
            textView.setQuestionResId(entry.getValue());
            textView.setOnClickListener(mQuestionTypeTvListener);
            if ((key & 0x000FFF) != 0) {
                mPersonLayout.addView(textView);
            } else {
                mCompanyLayout.addView(textView);
            }
            mTextviewList.add(textView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private View.OnClickListener mQuestionTypeTvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            QuestionTextView textView = (QuestionTextView) v;
            if (textView.isChecked()) {
                textView.toggleCheckedStatus();
            } else {
                if (mCheckedQuestionTv != null) {
                    mCheckedQuestionTv.toggleCheckedStatus();
                }
                mCheckedQuestionTv = textView;
                mCheckedQuestionTv.toggleCheckedStatus();
            }
        }
    };
}
