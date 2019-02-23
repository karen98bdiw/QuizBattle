package com.example.karen.quizbattle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityForAddQuestion extends AppCompatActivity {

    private EditText inAddQuestionText;
    private EditText inAddOptionText;
    private Button addOptionButton;
    private Button createQuestionButton;
    private LinearLayout addedOptionsLook;
    private List<Option> inaddOptionsList;
    private int optionsCount;
    private int questionCount;
    private Question inAddQuestion;
    private Intent intent;
    FrameLayout activityforAddquestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_add_question);

        activityforAddquestion = findViewById(R.id.addQuestionActivityLook);
        activityforAddquestion.setBackgroundResource(R.drawable.main_background_img);

        intent = new Intent();

        inaddOptionsList = new ArrayList<>();


        inAddOptionText = findViewById(R.id.InAddOptionText);
        inAddQuestionText = findViewById(R.id.InAddQuestionText);
        addOptionButton = findViewById(R.id.addOptionButton);
        createQuestionButton = findViewById(R.id.creatQuestionButton);
        addedOptionsLook = findViewById(R.id.addedOptionsLook);
        optionsCount = 0;

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentOptionText = inAddOptionText.getText().toString();

                if(!TextUtils.isEmpty(currentOptionText)){

                    optionsCount++;

                    final Option option = new Option(currentOptionText,optionsCount);
                    inaddOptionsList.add(option);


                    final TextView textView = new TextView(ActivityForAddQuestion.this);
                    textView.setText(option.getOptionNumber() + ")" + option.getOptionText());
                    textView.setMinHeight(50);
                    textView.setBackgroundResource(R.drawable.text_view_custom_design);
                    addedOptionsLook.addView(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textView.setBackgroundColor(Color.BLUE);
                            option.setThisIsARightOption(true);
                            Toast.makeText(ActivityForAddQuestion.this,"You select this option as a right",Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(ActivityForAddQuestion.this,"Options is Added",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(ActivityForAddQuestion.this,"Someting is Wrong With Your Option",Toast.LENGTH_SHORT).show();
                    }

            }
        });

        createQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inaddQuestionText = inAddQuestionText.getText().toString();

                if(!TextUtils.isEmpty(inaddQuestionText)){

                    if(optionsCount > 1){

                        inAddQuestion = new Question(inaddQuestionText,optionsCount,inaddOptionsList);

                        intent.putExtra("createdQuestion",inAddQuestion);
                        setResult(RESULT_OK,intent);

                        Toast.makeText(ActivityForAddQuestion.this,"You Question is created",Toast.LENGTH_SHORT).show();

                        finish();

                    }else{

                        Toast.makeText(ActivityForAddQuestion.this,"Options count Must be more than 1",Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(ActivityForAddQuestion.this,"Something is wrong with your question text",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
