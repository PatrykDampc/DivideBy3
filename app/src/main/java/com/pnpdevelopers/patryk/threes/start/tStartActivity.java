package com.pnpdevelopers.patryk.threes.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.pnpdevelopers.patryk.threes.R;

import butterknife.BindView;


public class tStartActivity extends AppCompatActivity implements StartContract.View{
    @BindView(R.id.playButtonID) Button startButton;
    @BindView(R.id.tutorialButtonID) Button tutorialButton;
    @BindView(R.id.musicButtonID) ImageView musicView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

    ////        View methods        ////
    @Override
    public void startGame() {

    }

    @Override
    public void showTutorial() {

    }

    @Override
    public void muteMusic() {

    }

    @Override
    public void showScore() {

    }

    @Override
    public void showRecord() {

    }
}
