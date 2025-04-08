package com.dstrube.bardboard;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.transition.Visibility;
import android.widget.Button;

public class ButtonRow {
    private String buttonAText,  buttonBText, buttonCText;
    private int height, width;

    public ButtonRow(String buttonAText, String buttonBText, String buttonCText, int height, int width){
        this.buttonAText = buttonAText;
        this.buttonBText = buttonBText;
        this.buttonCText = buttonCText;
        this.height = height;
        this.width = width;
    }

    public String getButtonAText(){
        return buttonAText;
    }
    public String getButtonBText(){
        return buttonBText;
    }
    public String getButtonCText(){
        return buttonCText;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
}
