package com.example.karen.quizbattle;

import java.io.Serializable;

public class Option implements Serializable{

    private String optionText;
    private int optionNumber;
    private boolean isThisIsARightOption;
    private boolean isChecked;

    public Option(String optionText,int optionNumber) {
        this.optionText = optionText;
        this.optionNumber = optionNumber;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setThisIsARightOption(boolean thisIsARightOption) {
        isThisIsARightOption = thisIsARightOption;
    }

    public boolean isThisIsARightOption() {
        return isThisIsARightOption;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

}
