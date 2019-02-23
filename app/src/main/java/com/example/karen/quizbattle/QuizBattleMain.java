package com.example.karen.quizbattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizBattleMain extends AppCompatActivity {

    private Button makeTestBtn;
    private Button takeTestBtn;
    private static final int REQUEST_CODE1 = 1003;
    private List<Test> makedTest;
    private RecyclerView testRecyclerView;
    private TestRecyclerViewAdapter testRecyclerViewAdapter;
    private FrameLayout mainLayoutLook;
    private WorkTask workTask;
    private Intent goToTakeTestIntent;
    private Test checkedTest;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_battle_main);
        workTask = new WorkTask();

        workTask.execute();

        mainLayoutLook = findViewById(R.id.mainLayoutLook);
        mainLayoutLook.setBackgroundResource(R.drawable.main_background_img);

        testRecyclerView = findViewById(R.id.testRecyclerView);
        makedTest = new ArrayList<>();
        makeTestBtn = findViewById(R.id.makeTest);
        takeTestBtn = findViewById(R.id.takeTest);

        makeTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizBattleMain.this,UserMakeTestActivity.class);
                startActivityForResult(intent,REQUEST_CODE1);
            }
        });

        testRecyclerViewAdapter = new TestRecyclerViewAdapter(makedTest);

        testRecyclerViewAdapter.setCheckedTestListener(new TestRecyclerViewAdapter.CheckedTestListener() {
            @Override
            public void oncheckedTestLister(Test test) {
                checkedTest = test;
               goToTakeTestIntent  = new Intent(QuizBattleMain.this,TakeTestActivity.class);
                goToTakeTestIntent.putExtra("checkedTest",test);

            }
        });

        takeTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizBattleMain.this,"coming soon...",Toast.LENGTH_SHORT).show();
                if(checkedTest != null) {
                    startActivity(goToTakeTestIntent);
                }else{
                    Toast.makeText(QuizBattleMain.this,"Please check a test",Toast.LENGTH_SHORT).show();
                }

            }
        });



        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        testRecyclerView.setLayoutManager(manager);



        testRecyclerView.setAdapter(testRecyclerViewAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE1){
            if(resultCode == RESULT_OK){

                Test resultTest = (Test) data.getSerializableExtra("resultTest");
                makedTest.add(resultTest);
                testRecyclerViewAdapter.notifyDataSetChanged();
            }
        }

    }

    private  class WorkTask extends android.os.AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Gson gson = new Gson();
            File dir = getFilesDir();
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".tst");
                }
            });
            for (File f : files) {
                try {
                    FileInputStream fis = new FileInputStream(f);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    Test test = gson.fromJson(sb.toString(),Test.class);
                    makedTest.add(test);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            testRecyclerViewAdapter.notifyDataSetChanged();
        }

    }
}
