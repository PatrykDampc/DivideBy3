package com.example.patryk.divideby3.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patryk.divideby3.R;
import com.example.patryk.divideby3.util.Utils;

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

        prefs = getSharedPreferences(MainActivity.PREFERENCES, MODE_PRIVATE);
        highScoreViewStart.setText(this.getString(R.string.high_score) +" "+ String.valueOf(prefs.getInt(MainActivity.HIGH_SCORE, 0)));

        Intent intent = getIntent();
        int number = intent.getIntExtra("numberKey", 0);
        String score = intent.getStringExtra("scoreKey");

        Utils.printLostMessage(number, score, numberViewStart, scoreViewStart, startButton, this);
        scoreViewStart.setText(this.getString(R.string.your_score) +" "+ score);
        startButton.setOnClickListener(this);

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
        Utils.ifHasFocus(hasFocus, this);
    }

}
