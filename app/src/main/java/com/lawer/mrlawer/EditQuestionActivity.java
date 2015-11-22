package com.lawer.mrlawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lawer.mrlawer.util.UiUtil;

public class EditQuestionActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtil.replaceFragment(this, new EditQuestionFragment());
    }
}
