package com.example.karen.quizbattle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowTestResult extends AppCompatActivity {

    TextView showTestResult;
    TextView testNameInResult;
    Button goToMainBtn;
    Intent intent;
    Test canceledTest;
    ViewPager optionsLookViewPager;
    List<Option> canceledTestOptionsList;
    List<Question> canceledTestQuestionList;
    LinearLayout resultViewPagerLook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test_result);

        canceledTestOptionsList = new ArrayList<>();
        canceledTestQuestionList = new ArrayList<>();

        showTestResult = findViewById(R.id.testResultLook);
        goToMainBtn = findViewById(R.id.goToMainBtn);
        testNameInResult = findViewById(R.id.testNameInResult);

        resultViewPagerLook = findViewById(R.id.resultViewPagerLook);

        intent = getIntent();

        String testName = intent.getStringExtra("completeTestName");
        int rightAnswersCount = intent.getIntExtra("rightAnswerCount",0);
        int questionsCount = intent.getIntExtra("testQuestionsCount",0);
        canceledTest = (Test) intent.getSerializableExtra("completedTest");
        showTestResult.setText(rightAnswersCount + "/" + questionsCount);

        testNameInResult.setText(testName);

        canceledTestQuestionList = canceledTest.getQuestionList();


        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        for (Question q:canceledTestQuestionList
             ) {
            View page = inflater.inflate(R.layout.reslut_view_pager_item, null);
            TextView textView = (TextView) page.findViewById(R.id.completedQuestionName);
            textView.setBackgroundResource(R.drawable.text_view_custom_design);
            textView.setTextSize(30);
            textView.setMinHeight(50);

            LinearLayout linearLayout = (LinearLayout) page.findViewById(R.id.completedTestOptionsLook);
            textView.setText(q.getQuestionText());
            for (Option o:q.getOptions()
                 ) {
                TextView optionView = new TextView(ShowTestResult.this);
                textView.setTextSize(30);
                textView.setMinHeight(50);
                optionView.setBackgroundResource(R.drawable.text_view_custom_design);
                optionView.setText(o.getOptionText());
                if (o.isChecked() && o.isThisIsARightOption()){
                    optionView.setBackgroundColor(Color.GREEN);
                }else if(o.isChecked()){
                    optionView.setBackgroundColor(Color.RED);
                }else if(o.isThisIsARightOption()){
                    optionView.setBackgroundColor(Color.GREEN);
                }
                linearLayout.addView(optionView);
            }
            pages.add(page);
        }

        ShowResultViewPagerAdapter pagerAdapter = new ShowResultViewPagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);


        resultViewPagerLook.addView(viewPager);



        goToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent(ShowTestResult.this,QuizBattleMain.class);
                startActivity(goIntent);
            }
        });

    }
}
