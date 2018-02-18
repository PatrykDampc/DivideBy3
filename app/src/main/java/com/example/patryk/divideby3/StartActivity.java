package com.example.patryk.divideby3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.patryk.divideby3.MainActivity.HIGH_SCORE;
import static com.example.patryk.divideby3.MainActivity.PREFERENCES;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button startButton;
    private SharedPreferences prefs;
    private TextView highScoreViewStart;
    private TextView scoreViewStart;
    private TextView numberViewStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        highScoreViewStart = findViewById(R.id.highScoreTextViewStartActivityID);
        scoreViewStart = findViewById(R.id.startAcvityScoreViewID);
        numberViewStart = findViewById(R.id.startActivityNumberViewID);
        startButton = (Button) findViewById(R.id.playButtonID);

        prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        highScoreViewStart.setText(this.getString(R.string.high_score) +" "+ String.valueOf(prefs.getInt(HIGH_SCORE, 0)));

        Intent intent = getIntent();
        int number = intent.getIntExtra("numberKey", 0);
        String score = intent.getStringExtra("scoreKey");

        if( number == 0 && score == null){
            numberViewStart.setVisibility(View.GONE);
            scoreViewStart.setVisibility(View.GONE);
            } else if(Utils.isDivisibleByThree(number)){
                int result = number/3;
                numberViewStart.setText(this.getString(R.string.your_lost) +" "+ String.valueOf(number) +" ÷ 3 = "+ result);
                startButton.setText(this.getString(R.string.tryagain));
            }  else if (String.valueOf(number).contains("3")){
                numberViewStart.setText(this.getString(R.string.your_lost) +" "+ String.valueOf(number) +" "+ this.getString(R.string.contains));
                startButton.setText(this.getString(R.string.tryagain));
            }  else {
                numberViewStart.setText(this.getString(R.string.your_lost) +" "+ String.valueOf(number) + "...");
                startButton.setText(this.getString(R.string.tryagain));
            }

        startButton.setOnClickListener(this);
        scoreViewStart.setText(this.getString(R.string.your_score) +" "+ score);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
