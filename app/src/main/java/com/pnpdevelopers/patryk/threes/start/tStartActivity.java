package com.pnpdevelopers.patryk.threes.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pnpdevelopers.patryk.threes.Activities.GameActivity;
import com.pnpdevelopers.patryk.threes.Activities.TutorialActivity;
import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.GameMusicIndicator;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class tStartActivity extends AppCompatActivity implements StartContract.View{
    @BindView(R.id.playButtonID) Button startButton;
    @BindView(R.id.tutorialButtonID) Button tutorialButton;
    @BindView(R.id.musicButtonID) ImageView musicView;
    Context context;
    StartPresenter startPresenter;
    GameMusic gameMusic;
    PreferenceManager preferenceManager;
    GameMusicIndicator gameMusicIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        context = this;
        startPresenter = new StartPresenter(this);
        preferenceManager = new PreferenceManager(context);
        gameMusicIndicator = new GameMusicIndicator(preferenceManager,musicView);
        gameMusic = new GameMusic(context,preferenceManager);

        gameMusic.setUpMusic(R.raw.bensound_thejazzpiano,true);




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
        startActivity(new Intent(this, GameActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void showTutorial() {
        startActivity(new Intent(this, TutorialActivity.class));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void muteUnmuteMusic() {
        gameMusicIndicator.musicIndicatorSwitch();
        gameMusic.musicMuteSwitch(gameMusicIndicator);
    }

    @Override
    public void mute() {
        gameMusic.muteMusic();
    }

    @Override
    public void unMute() {
        gameMusic.unMuteMusic();
    }

    @Override
    public void showScore() {

    }

    @Override
    public void showRecord() {

    }


}
