package com.example.kovalchyk_at.secondapplication;

/**
 * Created by Kovalchyk_at on 11.05.2017.
 */

class Question {

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }
}
