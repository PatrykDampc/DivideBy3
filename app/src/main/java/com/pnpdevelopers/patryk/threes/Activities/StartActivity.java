package com.pnpdevelopers.patryk.threes.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.function.GameConditions;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.HighScore;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener {
    @BindView(R.id.playButtonID) Button startButton;
    @BindView(R.id.tutorialButtonID) Button tutorialButton;
    @BindView(R.id.copyrightViewID) TextView copyrightView;
    @BindView(R.id.logoViewID) ImageView logoView;
    @BindView(R.id.musicButtonID) ImageView musicView;
    @BindView(R.id.highScoreTextViewStartActivityID) TextView highScoreViewStart;
    @BindView(R.id.startAcvityScoreViewID) TextView scoreViewStart;
    @BindView(R.id.startActivityNumberViewID) TextView numberViewStart;

    private Animation stampAnimation, inFromTop, inFromBottom;
    private AdView adView;

    private PreferenceManager preferenceManager = new PreferenceManager();
    private GameMusic gameMusic = new GameMusic();
    private HighScore  highScore = new HighScore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameMusic.setUpMusic(R.raw.bensound_thejazzpiano, false);
        setUpViews();
        setUpAnimations();
        setUpTouchListeners();
        Intent intent = getIntent();
        printInfo(intent);
    }

    private void setUpTouchListeners() {
        startButton.setOnClickListener(this);
        tutorialButton.setOnClickListener(this);
    }

    private void setUpAnimations() {
        stampAnimation = AnimationUtils.loadAnimation(this, R.anim.stamp);
        stampAnimation.reset();
        inFromTop = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_top);
        inFromTop.reset();
        inFromBottom = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_bottom);
        inFromBottom.reset();
        scoreViewStart.startAnimation(inFromBottom);
        startButton.startAnimation(inFromBottom);
        tutorialButton.startAnimation(inFromBottom);
        copyrightView.startAnimation(inFromBottom);
        musicView.startAnimation(inFromBottom);
        highScoreViewStart.startAnimation(inFromTop);
        logoView.startAnimation(inFromTop);
        numberViewStart.startAnimation(stampAnimation);

    }

    private void setUpViews() {
        ButterKnife.bind(this);
        numberViewStart.setTypeface(Typeface.createFromAsset(getAssets(),"Depressionist_3_Revisited_2010.ttf"));
        setUpMusicIndicator();
    }

    private void setUpMusicIndicator() {
        if(gameMusic.isMusicOn()){
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
        } else {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
        }
    }

    public void onMusicViewClick(View view){
        gameMusic.musicMuteSwitch();
        setUpMusicIndicator();
    }

    private void printInfo(Intent intent){
        String score = intent.getStringExtra("scoreKey");
        int lostNumber = intent.getIntExtra("numberKey",0);
        scoreViewStart.setText(getString(R.string.your_score) + " " + score);
        highScoreViewStart.setText(getString(R.string.high_score) +  String.valueOf(highScore.getHighScore()));
        if(score == null){
            scoreViewStart.setVisibility(View.GONE);
        }
        if( lostNumber == 0){
            numberViewStart.setVisibility(View.GONE);
        } else if(GameConditions.isDivisibleByThree(lostNumber)){
            int result = lostNumber/3;
            numberViewStart.setText(getString(R.string.your_lost) +" "+ String.valueOf(lostNumber) +" ÷ 3 = "+ result);
            startButton.setText(getString(R.string.tryagain));
        }  else if (GameConditions.containsThree(lostNumber)){
            numberViewStart.setText(getString(R.string.your_lost) +" "+ String.valueOf(lostNumber) +" "+ getString(R.string.contains));
            startButton.setText(getString(R.string.tryagain));
        }  else {
            numberViewStart.setText(getString(R.string.your_lost) + " " + String.valueOf(lostNumber) + "...");
            startButton.setText(getString(R.string.tryagain));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameMusic.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
       gameMusic.startMusicFromRandom();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButtonID:
                onPause();
                startActivity(new Intent(this, GameActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.tutorialButtonID:
                onPause();
                preferenceManager.getEditor().putBoolean(PreferenceManager.IS_FIRST_TIME_LAUNCH, true).apply();
                startActivity(new Intent(this, TutorialActivity.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                break;
            default:
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
    }


}
