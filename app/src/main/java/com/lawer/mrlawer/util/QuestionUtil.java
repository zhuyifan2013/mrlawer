package com.lawer.mrlawer.util;

import android.content.Context;

import com.lawer.mrlawer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionUtil {

    public static final int TYPE_PERSONAL = 0;
    public static final int TYPE_COMPANY = 1;

    public static HashMap<Integer, Integer> sQuestionResMap = new HashMap<>();

    static {
        sQuestionResMap.put(0x000001, R.string.question_000001);
        sQuestionResMap.put(0x000002, R.string.question_000002);
        sQuestionResMap.put(0x000004, R.string.question_000004);
        sQuestionResMap.put(0x000008, R.string.question_000008);
        sQuestionResMap.put(0x000010, R.string.question_000010);
        sQuestionResMap.put(0x000020, R.string.question_000020);
        sQuestionResMap.put(0x000040, R.string.question_000040);
        sQuestionResMap.put(0x000FFF, R.string.question_000FFF);
        sQuestionResMap.put(0x001000, R.string.question_001000);
        sQuestionResMap.put(0x002000, R.string.question_002000);
        sQuestionResMap.put(0x004000, R.string.question_004000);
        sQuestionResMap.put(0x008000, R.string.question_008000);
        sQuestionResMap.put(0x010000, R.string.question_010000);
        sQuestionResMap.put(0x020000, R.string.question_020000);
        sQuestionResMap.put(0x040000, R.string.question_040000);
        sQuestionResMap.put(0x080000, R.string.question_080000);
        sQuestionResMap.put(0xFFF000, R.string.question_FFF000);
    }

    public static List<String> makeString(Context context, int questionSet, int type) {
        List<String> questionList = new ArrayList<>();
        switch (type) {
            case TYPE_PERSONAL:
                if ((questionSet & 0x000001) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000001)));
                }
                if ((questionSet & 0x000002) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000002)));
                }
                if ((questionSet & 0x000004) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000004)));
                }
                if ((questionSet & 0x000008) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000008)));
                }
                if ((questionSet & 0x000010) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000010)));
                }
                if ((questionSet & 0x000020) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000020)));
                }
                if ((questionSet & 0x000040) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x000040)));
                }
                break;
            case TYPE_COMPANY:
                if ((questionSet & 0x001000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x001000)));
                }
                if ((questionSet & 0x002000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x002000)));
                }
                if ((questionSet & 0x004000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x004000)));
                }
                if ((questionSet & 0x008000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x008000)));
                }
                if ((questionSet & 0x010000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x010000)));
                }
                if ((questionSet & 0x020000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x020000)));
                }
                if ((questionSet & 0x040000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x040000)));
                }
                if ((questionSet & 0x080000) != 0) {
                    questionList.add(context.getString(sQuestionResMap.get(0x080000)));
                }
                break;
        }
        return questionList;
    }
}
