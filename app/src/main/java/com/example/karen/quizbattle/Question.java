package com.example.karen.quizbattle;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{

    private String questionText;
    private int questionOptionsCount;
    private List<Option> options;
    private boolean isready;
    private int questionId;
    private int questionsCount = 0;

    public Question(String questionText, int questionOptionsCount, List<Option> options) {
        this.questionText = questionText;
        this.questionOptionsCount = questionOptionsCount;
        this.options = options;
        questionsCount++;
        this.questionId = questionsCount;
    }

    public void addOption(Option option){
        options.add(option);
        questionOptionsCount++;
        if(questionOptionsCount > 1){
            isready = true;
        }
    }

    public boolean isready() {
        return isready;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<Option> getOptions() {
        return options;
    }
}
