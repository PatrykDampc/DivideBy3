package com.pnpdevelopers.patryk.threes.Activities;

import android.content.Context;
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
import com.pnpdevelopers.patryk.threes.function.GameMusicIndicator;
import com.pnpdevelopers.patryk.threes.function.HighScore;
import com.pnpdevelopers.patryk.threes.function.LostMessagePrinter;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;
import com.pnpdevelopers.patryk.threes.util.Utils;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button startButton, tutorialButton;
    private TextView highScoreViewStart, scoreViewStart, numberViewStart, copyryghtView;
    private ImageView musicView, logoView;
    private AdView adView;
    private int lostNumber;
    private String score;
    private Animation stampAnimation, inFromTop, inFromBottom;
    private Context context = this;

    private PreferenceManager preferenceManager;
    private GameMusic gameMusic;
    private GameMusicIndicator gameMusicIndicator;
    private HighScore highScore;
    private LostMessagePrinter lostMessagePrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setUpViews();
        setUpAnimations();
        setUpTouchListeners();

        preferenceManager = new PreferenceManager(context);
        gameMusic = new GameMusic(context, preferenceManager);
        gameMusicIndicator = new GameMusicIndicator(preferenceManager, musicView);
        highScore = new HighScore(context,preferenceManager);
        lostMessagePrinter = new LostMessagePrinter(context);

        Intent intent = getIntent();
        lostNumber = intent.getIntExtra("numberKey", 0);
        score = intent.getStringExtra("scoreKey");

        scoreViewStart.setText(this.getString(R.string.your_score) + " " + score);
        highScoreViewStart.setText(this.getString(R.string.high_score) +  String.valueOf(highScore.getHighScore()));

        gameMusic.setUpMusic(R.raw.bensound_thejazzpiano, false);
        gameMusicIndicator.setUpMusicIndicator();
        lostMessagePrinter.print(startButton,numberViewStart,scoreViewStart, score,lostNumber);

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
        copyryghtView.startAnimation(inFromBottom);
        musicView.startAnimation(inFromBottom);
        highScoreViewStart.startAnimation(inFromTop);
        logoView.startAnimation(inFromTop);
        numberViewStart.startAnimation(stampAnimation);

    }

    private void setUpViews() {
        copyryghtView = findViewById(R.id.copyrightViewID);
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
    }

    public void onMusicViewClick(View view){
        gameMusic.musicMuteSwitch(gameMusicIndicator);
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
                startActivity(new Intent(this, MainActivity.class));
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
        Utils.fullScreenIfHasFocus(hasFocus, this);
    }

}
