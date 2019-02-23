package com.example.karen.quizbattle;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {

    private String testName;
    private int testDurationTime;
    private List<Question> questionList;
    private boolean isCheched;

    public Test(String testName, List<Question> questionList,int testDurationTime) {
        this.testName = testName;
        this.questionList = questionList;
        this.testDurationTime = testDurationTime;
    }

    public String getTestName() {
        return testName;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setCheched(boolean cheched) {
        isCheched = cheched;
    }

    public int getTestDurationTime() {
        return testDurationTime;
    }
}
