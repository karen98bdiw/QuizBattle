package com.example.karen.quizbattle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TakeTestActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    private int timerStartValue;
    private int checkedOptionsCount = 0;
    private TextView lastChechedoption;
    private FrameLayout answeringTestAllLook;
    private TextView answeringTestName;
    private TextView answeringTestTime;
    private TextView answeringQuestionText;
    private Button answerQuestionButton;
    private Button skipQuestionButton;
    private Button endTestBtn;
    private LinearLayout answeringQuestionOptionsLook;
    private Intent intent;
    private Test intentResultTest;
    private int curentQuestionNumber;
    private TextView textView;
    private LinearLayout linearLayout;
    private List<TextView> curentQuestionOptionsList;
    private boolean isRightOptionChecked;
    private int chechkedRightOptionsCount;
    private int skipedQuestionsCount;
    private List<Question> skipedQuestions;
    private int[] answerToSkipedQuestionNumber;
    private int iterationForSkipQuestion;
    private boolean isTestEnded;


    List<Option> choosenOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);

        intent = getIntent();


        skipedQuestions = new ArrayList<>();
        skipedQuestionsCount = 0;
        iterationForSkipQuestion = 0;
        curentQuestionOptionsList = new ArrayList<>();
        chechkedRightOptionsCount = 0;
        answerToSkipedQuestionNumber = new int[10];
        curentQuestionNumber = 0;
        answeringTestAllLook = findViewById(R.id.answeringTestAllLook);
        answerQuestionButton = findViewById(R.id.answerQuestionBtn);
        skipQuestionButton = findViewById(R.id.skipQuestionBtn);
        skipedQuestionsCount = 0;
        endTestBtn = findViewById(R.id.endTestBtn);
        answeringTestName = findViewById(R.id.answeringTestNameLook);
        answeringTestTime = findViewById(R.id.answeringTestTimingLook);
        answeringQuestionText = findViewById(R.id.answeringTestCurrentQuestionText);
        answeringQuestionOptionsLook = findViewById(R.id.answeringQuestionOptionsLookInTest);

        intentResultTest = (Test) intent.getSerializableExtra("checkedTest");

        answeringTestName.setText(intentResultTest.getTestName());

        timerStartValue = intentResultTest.getTestDurationTime();

        answeringTestTime.setText(":" + timerStartValue);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerStartValue--;
                        answeringTestTime.setText(":" + timerStartValue);
                        if(timerStartValue == 0){
                            timer.cancel();
                            isTestEnded = true;
                            Toast.makeText(TakeTestActivity.this,"Your time is end",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TakeTestActivity.this,ShowTestResult.class);
                            intent.putExtra("completeTestName",intentResultTest.getTestName());
                            intent.putExtra("completedTest",intentResultTest);
                            intent.putExtra("rightAnswerCount",chechkedRightOptionsCount);
                            intent.putExtra("testQuestionsCount",intentResultTest.getQuestionList().size());
                            startActivity(intent);
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask,1,1000);

        drowOptions(intentResultTest.getQuestionList().get(curentQuestionNumber));

        answerQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                curentQuestionNumber++;
                if(curentQuestionNumber == intentResultTest.getQuestionList().size()){
                    if(isRightOptionChecked){
                        chechkedRightOptionsCount++;
                        Toast.makeText(TakeTestActivity.this, "right" + chechkedRightOptionsCount, Toast.LENGTH_SHORT).show();
                    }
                    answerQuestionButton.setEnabled(false);
                }else {
                    if(isRightOptionChecked){
                        chechkedRightOptionsCount++;
                        Toast.makeText(TakeTestActivity.this, "right" + chechkedRightOptionsCount, Toast.LENGTH_SHORT).show();
                        }
                        isRightOptionChecked = false;
                    drowOptions(intentResultTest.getQuestionList().get(curentQuestionNumber));

                }
            }
        });

        skipQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skipedQuestions.add(intentResultTest.getQuestionList().get(curentQuestionNumber));

                  answeringQuestionOptionsLook.removeView(linearLayout);
                  intentResultTest.getQuestionList().add(intentResultTest.getQuestionList().remove(curentQuestionNumber));


                drowOptions(intentResultTest.getQuestionList().get(curentQuestionNumber));

            }
        });

        endTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTestEnded = true;
                timer.cancel();
                        Log.e("hey1","curent answers count is" + chechkedRightOptionsCount);
                        Toast.makeText(TakeTestActivity.this,"You end the test",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(TakeTestActivity.this,ShowTestResult.class);
                        intent.putExtra("completeTestName",intentResultTest.getTestName());
                        intent.putExtra("completedTest",intentResultTest);
                        intent.putExtra("rightAnswerCount",chechkedRightOptionsCount);
                        intent.putExtra("testQuestionsCount",intentResultTest.getQuestionList().size());

                        startActivity(intent);
                    }


        });

    }

    public void drowOptions(Question question){

        answeringQuestionText.setText(question.getQuestionText());

        final List<Option> options = question.getOptions();

        if(curentQuestionNumber > 0){
            answeringQuestionOptionsLook.removeView(linearLayout);
        }

        linearLayout = new LinearLayout(TakeTestActivity.this);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (final Option o:options
             ) {

//              if(curentQuestionOptionsList.size() > 0){
//                  for (TextView t:curentQuestionOptionsList
//                       ) {
//                      textView.setVisibility(View.GONE);
//                  }
//              }

              textView = new TextView(TakeTestActivity.this);
              curentQuestionOptionsList.add(textView);
              textView.setText(o.getOptionText());
              textView.setBackgroundResource(R.drawable.text_view_custom_design);
              textView.setTextSize(30);
              textView.setMinHeight(50);
              linearLayout.addView(textView);


              final TextView curenttextView = textView;

              curenttextView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      for(Option b:options){
                          if(b.isChecked()){
                              b.setChecked(false);
                          }
                      }

                      o.setChecked(true);

                      curenttextView.setBackgroundColor(Color.BLUE);
                      if (o.isThisIsARightOption()){
                          Log.e("hey","right answer");
                          isRightOptionChecked = true;
                      }else{
                          Log.e("hey","wrong answer");
                          isRightOptionChecked = false;
                          }

                      if(checkedOptionsCount == 0){
                          lastChechedoption = curenttextView;
                          Log.e("last","heyif");
                      }else if(lastChechedoption.equals(curenttextView)){
                          return;
                      }else{
                          lastChechedoption.setBackgroundResource(R.drawable.text_view_custom_design);
                          lastChechedoption = curenttextView;
                          Log.e("last","heyelse");
                      }

                      checkedOptionsCount++;
                  }
              });

        }

        answeringQuestionOptionsLook.addView(linearLayout);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isTestEnded){
        timer.cancel();
        }
    }
}
