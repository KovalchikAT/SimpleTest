package com.example.kovalchyk_at.secondapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mShowQuestionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mShowQuestionTextView = (TextView) findViewById(R.id.show_question);
        mShowQuestionTextView.setText("\n\nПитання на яке ви не знали відповіді:\n"+getIntent().getStringExtra(String.valueOf(R.string.EXTRA_QUESTION))+
                "\n\n\nПравильна відповідь: \n"+ getIntent().getBooleanExtra(String.valueOf(R.string.EXTRA_QUESTION_ANSWER),false));
        mShowQuestionTextView.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.show_question:{
                Toast.makeText(this, R.string.cheat, Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        }
    }
}
