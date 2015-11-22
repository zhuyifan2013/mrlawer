package com.lawer.mrlawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lawer.mrlawer.entity.Question;
import com.lawer.mrlawer.network.RequestManager;

import java.io.UnsupportedEncodingException;

public class EditQuestionFragment extends Fragment {

    private Button mNextBtn;
    private EditText mQuestionEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_question_fragment, container, false);
        mNextBtn = (Button) view.findViewById(R.id.next);
        mQuestionEt = (EditText) view.findViewById(R.id.question_content);
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
                try {
                    question.setText(new String(mQuestionEt.getText().toString().getBytes(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestManager.sendQuestion(question);
                    }
                }).start();
            }
        });
    }
}
