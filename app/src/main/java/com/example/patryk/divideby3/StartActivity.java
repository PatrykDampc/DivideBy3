package com.example.patryk.divideby3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final String PREFERENCES = "Prefs" ;
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY" ;
    private Button startButton;
    private TextView highScore;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.startGameButtonID);
        highScore = (TextView) findViewById(R.id.highScoreTextViewID);
        startButton.setOnClickListener(this);
       // prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
       // highScore.setText(prefs.getInt(HIGH_SCORE, 0));




    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
