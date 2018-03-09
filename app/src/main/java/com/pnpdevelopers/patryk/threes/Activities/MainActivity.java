package com.pnpdevelopers.patryk.threes.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.Conditions;
import com.pnpdevelopers.patryk.threes.model.LevelFactory;
import com.pnpdevelopers.patryk.threes.model.RandomArrayFactory;
import com.pnpdevelopers.patryk.threes.util.CustomCountDownTimer;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;
import com.pnpdevelopers.patryk.threes.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.pnpdevelopers.patryk.threes.util.PreferenceManager.HIGH_SCORE_KEY;
import static com.pnpdevelopers.patryk.threes.util.PreferenceManager.MUSIC_KEY;
import static com.pnpdevelopers.patryk.threes.util.PreferenceManager.PREFERENCES_KEY;

public class MainActivity extends AppCompatActivity {
    //Views
    private ConstraintLayout layout;
    private TextSwitcher textSwitcher;
    private ObjectAnimator animation;
    private ProgressBar regresBar, progressBar;
    private TextView scoreView, highScoreView, nextLevel;
    //regular variables
    private Vibrator vibe;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private CustomCountDownTimer loop;
    private MediaPlayer mediaPlayer2;
    private int[] currentArray;
    private RandomArrayFactory array1, array2, array3, array4, array5, array6;
    private int progressStatus, progressScope = 13, level = 1, highScore, inLevelIterator = 0, scoreCount = 0, time = 2500, number;
    private boolean gameleft;
    private Animation in, scale;
    private List<LevelFactory> levels;
    private LevelFactory currentLevel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameleft = false;

        Animation in = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_top);
        in.reset();
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        layout = findViewById(R.id.mainActivityLayoutID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);
        highScoreView = findViewById(R.id.highScoreTextViewID);
        regresBar = findViewById(R.id.regresBar);
        progressBar = findViewById(R.id.progressBarID);
        nextLevel = findViewById(R.id.nextLevelID);

        prefs = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(time);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE_KEY, 0);
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2 = MediaPlayer.create(this, R.raw.bensound_funkysuspense);
        mediaPlayer2.setLooping(true);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));
        nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
        progressBar.setMax(progressScope);
        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);
        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onClick() {
                super.onClick();
                if (Conditions.succesCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                loop.cancel();
                loop.onFinish();
            }
        });

        scoreView.clearAnimation();
        highScoreView.clearAnimation();
        progressBar.clearAnimation();
        nextLevel.clearAnimation();
        regresBar.clearAnimation();;
        regresBar.startAnimation(scale);
        scoreView.startAnimation(in);
        highScoreView.startAnimation(in);
        progressBar.startAnimation(in);
        nextLevel.startAnimation(in);

        levels = new ArrayList<>();
        levels.add(new LevelFactory(3,100, 0, 13));
        levels.add(new LevelFactory(101,200, 14, 43));
        levels.add(new LevelFactory(201,310, 44, 100));
        levels.add(new LevelFactory(396,720, 101, 206));
        levels.add(new LevelFactory(721,999, 206, 419));
        levels.add(new LevelFactory(1000,1310, 420, 2000));




//        array1 = new RandomArrayFactory(3,100);
//        array2 = new RandomArrayFactory(101,200);
//        array3 = new RandomArrayFactory(201,310);
//        array4 = new RandomArrayFactory(396,720);
//        array5 = new RandomArrayFactory(721,999);
//        array5 = new RandomArrayFactory(1000,1310);
//        array6 = new RandomArrayFactory(1396,2000);

        loop = gameLoop(time).start();
        mediaPlayer2.start();
        if(prefs.getBoolean(MUSIC_KEY, true)){
            mediaPlayer2.setVolume(1,1);
        } else {
            mediaPlayer2.setVolume(0,0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameleft){
            startActivity(gameStop());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        loop.cancel();
        mediaPlayer2.stop();
        gameleft = true;
    }

    public LevelFactory findLevel(List<LevelFactory> levels, int currentScore ) {
        for (LevelFactory level : levels) {
            if (currentScore >= level.getScopeFrom()) {
                return level;
            }
        }
        throw new RuntimeException("gówno");
    }

    public CustomCountDownTimer gameLoop(int time){
        return new CustomCountDownTimer(time, 50000) {
            @Override
            public void onTick(long millisUntilFinished) {

               currentLevel = findLevel(levels, scoreCount);
               currentArray = currentLevel.getRandomArrayFactory().getNumbertab();
               progressScope = currentLevel.getProgressScope();





                //setting array depending on score count. more score = more difficult numbers array
//                switch (scoreCount) {
//                    case 0:
//                        progressScope = 13;
//                        currentArray = array1.getNumbertab();
//                        break;
//                    case 13:
//                        progressScope = 31;
//                        currentArray = array2.getNumbertab();
//                        break;
//                    case 44:
//                        progressScope = 56;
//                        currentArray = array3.getNumbertab();
//                        break;
//                    case 100:
//                        progressScope = 106;
//                        currentArray = array4.getNumbertab();
//                        break;
//                    case 206:
//                        progressScope = 214;
//                        currentArray = array5.getNumbertab();
//                        break;
//                    case 420:
//                        progressScope = currentArray.length;
//                        currentArray = array6.getNumbertab();
//                        break;
//                }
                //circle time animation
                animation.start();
                number = currentArray[inLevelIterator];
                textSwitcher.setText(String.valueOf(number));
                //progress bar logic
                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
                    level++;
                    nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
                    progressStatus = 0;
                    inLevelIterator = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }
            }
            @Override
            public void onFinish() {
                if (!Conditions.succesCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
        };
    }

    public void success() {
        loop.cancel();
        regresBar.clearAnimation();
        loop.start();
        scoreCount++;
        scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
        if (scoreCount == highScore && scoreCount != 0) {
            Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.new_record), Toast.LENGTH_SHORT).show();
        }
        if (scoreCount > highScore) {
            editor.putInt(HIGH_SCORE_KEY, scoreCount);
            editor.commit();
            highScoreView.setText(MainActivity.this.getText(R.string.high_score) +" "+ String.valueOf(scoreCount));
        }
        vibe.vibrate(40);
        progressBar.setProgress(++progressStatus);
        inLevelIterator++;
    }

    public void fail(){
        startActivity(gameStop().putExtra("numberKey", number));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(gameStop());
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        super.onBackPressed();
    }

    public Intent gameStop(){
        loop.cancel();
        mediaPlayer2.stop();
        layout.setOnTouchListener(null);
        return new Intent(MainActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(scoreCount));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.fullScreenIfHasFocus(hasFocus, this);
    }

}
