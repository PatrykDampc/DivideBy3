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
    TextView scoreViewStart;
    TextView numberViewStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        highScoreViewStart = findViewById(R.id.highScoreTextViewStartActivityID);
        highScoreViewStart.setText(this.getString(R.string.high_score) +" "+ String.valueOf(prefs.getInt(HIGH_SCORE, 0)));
        scoreViewStart = findViewById(R.id.startAcvityScoreViewID);
        numberViewStart = findViewById(R.id.startActivityNumberViewID);
        startButton = (Button) findViewById(R.id.playButtonID);

        Intent intent = getIntent();
        int number = intent.getIntExtra("numberKey", 0);
        String score = intent.getStringExtra("scoreKey");

        if( number == 0 && score == null){
            numberViewStart.setVisibility(View.GONE);
            scoreViewStart.setVisibility(View.GONE);
        }
        if(Numbers.isDivisibleByThree(number)){
           int result = number/3;
           numberViewStart.setText("YOU LOST!\n" + String.valueOf(number) +" ÷ 3 = "+ result);
        }  else if (String.valueOf(number).contains("3")){
            numberViewStart.setText("YOU LOST!\n" + String.valueOf(number) + " CONTAINS \"3\" DIGIT!");
        }  else {
            numberViewStart.setText("YOU LOST!\n" + String.valueOf(number) + "...");
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
}
