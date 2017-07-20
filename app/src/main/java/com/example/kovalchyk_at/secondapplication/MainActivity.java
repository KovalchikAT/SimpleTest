package com.example.kovalchyk_at.secondapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //константи
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";

    //глобальні змінні активності
    private int mScore = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mShowAnswerButton;
    private static TextView mQuestionTextView;
    private TextView mScoreView;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.questions_text, true),
            new Question(R.string.questions_text1, true),
            new Question(R.string.questions_text2, false),
            new Question(R.string.questions_text3,true)
    };

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, mScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mScore = savedInstanceState.getInt(KEY_SCORE, 0);
        }

        mScoreView = (TextView)findViewById(R.id.scoreView);
        mQuestionTextView = (TextView)findViewById(R.id.textView2);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        updateQuestion();

        mNextButton.setOnClickListener(this);
        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mShowAnswerButton.setOnClickListener(this);
        mNextButton.setClickable(false);
    }

    public void onClick(View v){
        mNextButton.setClickable(false);
        try {
            switch (v.getId()) {
                case R.id.true_button: checkAnswer(true); break;
                case R.id.false_button: checkAnswer(false); break;
                case R.id.next_button: {
                    mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                    updateQuestion();
                    mTrueButton.setClickable(true);
                    mFalseButton.setClickable(true);

                    int cx = mShowAnswerButton.getWidth()/2;
                    int cy = mShowAnswerButton.getHeight()/2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.VISIBLE);
                        }
                    });
                    anim.start();

                } break;
                case R.id.show_answer_button:{
                    Intent i = newIntent(MainActivity.this, SecondActivity.class, mQuestionBank[mCurrentIndex]);
                    startActivity(i);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  Intent newIntent(Context packageContext, Class secondActivityClass, Question q){
        Intent i = new Intent(packageContext, secondActivityClass);
        i.putExtra(String.valueOf(R.string.EXTRA_QUESTION_ANSWER), q.isAnswerTrue());
        i.putExtra(String.valueOf(R.string.EXTRA_QUESTION), mQuestionTextView.getText().toString());
        return i;
    }

    @SuppressLint("SetTextI18n")
    private void updateQuestion() {
        int question =mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mScoreView.setText(""+mScore);

    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer(boolean b) throws InterruptedException {
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == b){
            mScoreView.setText(""+(++mScore));
            mQuestionTextView.setText(R.string.correct);
            mQuestionTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateQuestion();
                }
            }, 800);
        }else{
            mQuestionTextView.setText(R.string.incorrect);
            mQuestionTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateQuestion();
                }
            }, 800);
        }
        mNextButton.setClickable(true);
        mTrueButton.setClickable(false);
        mFalseButton.setClickable(false);

        int cx = mShowAnswerButton.getWidth()/2;
        int cy = mShowAnswerButton.getHeight()/2;
        float radius = mShowAnswerButton.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();

    }
}


