package com.lawer.mrlawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.lawer.mrlawer.entity.Question;
import com.lawer.mrlawer.network.RequestManager;
import com.lawer.mrlawer.util.QuestionUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class EditQuestionFragment extends Fragment {

    private Button mNextBtn;
    private EditText mQuestionEt;
    private GridLayout mPersonLayout, mCompanyLayout;
    private List<QuestionTextView> mTextviewList;
    private QuestionTextView mCheckedQuestionTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_question_fragment, container, false);
        mNextBtn = (Button) view.findViewById(R.id.next);
        mPersonLayout = (GridLayout) view.findViewById(R.id.personal_question_set);
        mCompanyLayout = (GridLayout) view.findViewById(R.id.company_question_set);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Question question = new Question();
                question.setUserID(AccountManager.getCurAccount().getId());
                question.setQuestionType(mCheckedQuestionTv.getQuestionValue());
                question.setText(mQuestionEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestManager.sendQuestion(question);
                    }
                }).start();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private View.OnClickListener mQuestionTypeTvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            QuestionTextView textView = (QuestionTextView) v;
            if (textView.isChecked()) {
                textView.toggleCheckedStatus();
            } else {
                mCheckedQuestionTv.toggleCheckedStatus();
                mCheckedQuestionTv = textView;
            }
        }
    };
}
