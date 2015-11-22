package com.lawer.mrlawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.lawer.mrlawer.util.QuestionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickQuestionSetFragment extends Fragment {

    private GridLayout mPersonLayout, mCompanyLayout;
    private Button mFinishBtn;
    private List<QuestionTextView> mTextviewList;
    private int mQuestionSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextviewList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.pick_question_set_fragment, container, false);
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
                for(QuestionTextView textView : mTextviewList) {
                    if(textView.isChecked()) {
                        mQuestionSet += textView.getQuestionValue();
                    }
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        for(Map.Entry<Integer,Integer> entry : QuestionUtil.sQuestionResMap.entrySet()) {
            int key = entry.getKey();
            QuestionTextView textView = new QuestionTextView(getActivity());
            textView.setQuestionValue(entry.getKey());
            textView.setQuestionResId(entry.getValue());
            if((key & 0x000FFF) != 0) {
                mPersonLayout.addView(textView);
            } else {
                mCompanyLayout.addView(textView);
            }
            mTextviewList.add(textView);
        }
    }
}
