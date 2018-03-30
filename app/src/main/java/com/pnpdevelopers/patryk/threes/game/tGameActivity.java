package com.pnpdevelopers.patryk.threes.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pnpdevelopers.patryk.threes.R;

public class tGameActivity extends AppCompatActivity implements GameContract.View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showNumber() {
        //set textswitcher
    }

    @Override
    public void increaseProgress() {
        //set progressbar
    }

    @Override
    public void nextLevel() {
        //zero progressbar
    }

    @Override
    public void backToStartActivity() {

    }
}
