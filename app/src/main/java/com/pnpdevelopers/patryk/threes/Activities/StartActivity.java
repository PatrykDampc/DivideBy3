package com.pnpdevelopers.patryk.threes.Activities;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.HighScore;
import com.pnpdevelopers.patryk.threes.function.LostMessagePrinter;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button startButton, tutorialButton;
    private TextView highScoreViewStart, scoreViewStart, numberViewStart, copyrightView;
    private ImageView musicView, logoView;
    private Animation stampAnimation, inFromTop, inFromBottom;
    private AdView adView;

    private PreferenceManager preferenceManager = new PreferenceManager();
    private GameMusic gameMusic = new GameMusic(preferenceManager);
    private HighScore  highScore = new HighScore(preferenceManager);
    private LostMessagePrinter  lostMessagePrinter = new LostMessagePrinter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setUpViews();
        setUpAnimations();
        setUpTouchListeners();



        Intent intent = getIntent();
        scoreViewStart.setText(this.getString(R.string.your_score) + " " + intent.getStringExtra("scoreKey"));
        highScoreViewStart.setText(this.getString(R.string.high_score) +  String.valueOf(highScore.getHighScore()));
        gameMusic.setUpMusic(R.raw.bensound_thejazzpiano, false);

        lostMessagePrinter.print(startButton,numberViewStart,scoreViewStart,
                intent.getStringExtra("scoreKey"),
                intent.getIntExtra("numberKey", 0));

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
        copyrightView = findViewById(R.id.copyrightViewID);
        logoView = findViewById(R.id.logoViewID);
        highScoreViewStart = findViewById(R.id.highScoreTextViewStartActivityID);
        scoreViewStart = findViewById(R.id.startAcvityScoreViewID);
        numberViewStart = findViewById(R.id.startActivityNumberViewID);
        musicView = findViewById(R.id.musicButtonID);
        startButton = findViewById(R.id.playButtonID);
        tutorialButton = findViewById(R.id.tutorialButtonID);

        numberViewStart.getPaint().setShader(new LinearGradient(10,0,0,numberViewStart.getLineHeight(),
                getResources().getColor(R.color.colorAccentLostMessageGradient),
                getResources().getColor(R.color.colorAccent),
                Shader.TileMode.REPEAT));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/SPETETRIAL.ttf");
        numberViewStart.setTypeface(type);

        if(preferenceManager.isMusicOn()){
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
        } else {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
        }

    }

    public void onMusicViewClick(View view){
        gameMusic.musicMuteSwitch();
        if(preferenceManager.isMusicOn()) {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
        } else {
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
        }
        preferenceManager.getEditor().apply();
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
