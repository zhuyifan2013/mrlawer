package com.lawer.mrlawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class QuestionTextView extends TextView {

    private int mQuestionValue;
    private int mQuestionResId;
    private boolean mIsChecked;

    public boolean isChecked() {
        return mIsChecked;
    }

    public int getQuestionValue() {
        return mQuestionValue;
    }

    public void setQuestionValue(int questionValue) {
        mQuestionValue = questionValue;
    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
        this.setText(questionResId);
    }

    public QuestionTextView(Context context) {
        super(context);
        this.setOnClickListener(mClickListener);
    }

    public QuestionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuestionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleCheckedStatus();
        }
    };

    public void toggleCheckedStatus() {
        mIsChecked = !mIsChecked;
        if (mIsChecked) {
            QuestionTextView.this.setTextColor(getResources().getColor(R.color.green));
        } else {
            QuestionTextView.this.setTextColor(getResources().getColor(R.color.dark));
        }
    }
}
