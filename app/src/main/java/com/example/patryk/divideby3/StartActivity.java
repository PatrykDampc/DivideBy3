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
    SharedPreferences prefs;
    TextView highScoreViewStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        highScoreViewStart = findViewById(R.id.highScoreTextViewStartActivityID);
        highScoreViewStart.setText("High Score: " + String.valueOf(prefs.getInt(HIGH_SCORE, 0)));
        startButton = (Button) findViewById(R.id.playButtonID);

        startButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
