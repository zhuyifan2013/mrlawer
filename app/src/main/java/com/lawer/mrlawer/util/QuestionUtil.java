package com.lawer.mrlawer.util;

import com.lawer.mrlawer.R;

import java.util.HashMap;

public class QuestionUtil {

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
}
