package com.example.karen.quizbattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserMakeTestActivity extends AppCompatActivity {

    private EditText testName;
    private NumberPicker testDurationTimePicker;
    private Button creatQuestionButton;
    private Button makeTestBtn;
    private Test test;
    private List<Question> addedQuestions;
    private RecyclerView questionsRecyclerView;
    private QuestionsRecyclerViewAdapter questionsRecyclerViewAdapter;
    private final static int REQUEST_CODE = 1002;
    private FrameLayout makeTestActivityLook;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_test);

        testDurationTimePicker = findViewById(R.id.makedTestDurationPicker);
        testDurationTimePicker.setMinValue(0);
        testDurationTimePicker.setMaxValue(100);
        testName = findViewById(R.id.testName);
        makeTestBtn = findViewById(R.id.userMakeTestButton);
        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        makeTestActivityLook = findViewById(R.id.makeTestLayoutLook);
        makeTestActivityLook.setBackgroundResource(R.drawable.main_background_img);
        addedQuestions = new ArrayList<>();
        creatQuestionButton = findViewById(R.id.addQuestionButton);

        resultIntent = new Intent();

        creatQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserMakeTestActivity.this,ActivityForAddQuestion.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        makeTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String testNameText = testName.getText().toString();
                int makedTestDuration = testDurationTimePicker.getValue();
                if(!TextUtils.isEmpty(testNameText)){

                    if(addedQuestions.size() > 0){

                        if(makedTestDuration > 0){

                            test = new Test(testNameText,addedQuestions, makedTestDuration);

                            File file = new File(UserMakeTestActivity.this.getFilesDir(),"test"  + Math.random() + ".tst");

                            Gson gson = new Gson();

                            String question = gson.toJson(test);

                            try {
                                FileOutputStream outputStream = new FileOutputStream(file);

                                outputStream.write(question.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(UserMakeTestActivity.this,"your Test is maked",Toast.LENGTH_SHORT).show();

                            resultIntent.putExtra("resultTest",test);
                            setResult(RESULT_OK,resultIntent);
                            finish();

                        }else{
                            Toast.makeText(UserMakeTestActivity.this,"Please choose a test duration",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(UserMakeTestActivity.this,"you Test question count must be bigger than 0",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UserMakeTestActivity.this,"Something is wrong with your test name",Toast.LENGTH_SHORT).show();

                }
                }
        });

        questionsRecyclerViewAdapter = new QuestionsRecyclerViewAdapter(addedQuestions);

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        questionsRecyclerView.setLayoutManager(manager);

        questionsRecyclerView.setAdapter(questionsRecyclerViewAdapter);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Question resultQuestion = (Question) data.getSerializableExtra("createdQuestion");
                addedQuestions.add(resultQuestion);
                questionsRecyclerViewAdapter.notifyDataSetChanged();
            }
        }

    }


}
