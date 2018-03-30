package com.pnpdevelopers.patryk.threes.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class tStartActivity extends AppCompatActivity implements StartContract.View{
    @BindView(R.id.playButtonID) Button startButton;
    @BindView(R.id.tutorialButtonID) Button tutorialButton;
    @BindView(R.id.musicButtonID) ImageView musicView;
    StartPresenter startPresenter;
    GameMusic gameMusic;
    boolean isMuted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startPresenter = new StartPresenter(this);
        ButterKnife.bind(this);
        isMuted = false;


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPresenter.startGameButtonClick();
            }
        });

        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPresenter.tutorialButtonClick();
            }
        });

        musicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPresenter.musicButtonClick();
            }
        });


    }

    ////        View methods        ////
    @Override
    public void startGame() {

    }

    @Override
    public void showTutorial() {

    }

    @Override
    public void muteUnmuteMusic() {
        if(isMuted) {
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
            startPresenter.unMute();
        }
        else {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
            startPresenter.mute();
        }
    }

    @Override
    public void showScore() {

    }

    @Override
    public void showRecord() {

    }
}
