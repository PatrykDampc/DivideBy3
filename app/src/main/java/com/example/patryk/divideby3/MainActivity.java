package com.example.patryk.divideby3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton button;
    TextSwitcher textSwitcher;
    TextView scoreView;
    private RandomNumber rn;
    CountDownTimer cd;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bumButtonID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);

        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
        button.setOnClickListener(this);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(MainActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(30);
                myText.setTextColor(Color.RED);
                return myText;
            }
        });

        cd = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
             onTaskCreated();
            }

            @Override
            public void onFinish() {
                if(!rn.isDivisibleByThree()) {
                   i++;
                    start();
                } else {
                    rn.setDivisibleByThree(false);
                    Toast.makeText(MainActivity.this, "gówno gówno", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }


    public void onTaskCreated(){
        rn = new RandomNumber();
        textSwitcher.setText(rn.getRanNumString());
        scoreView.setText(String.valueOf(i));

    }



    @Override
    public void onClick(View v) {
        if(rn.isDivisibleByThree()) {
            Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
            i = i + 2;
            cd.cancel();
            cd.start();
        }else {
            cd.cancel();
            Toast.makeText(MainActivity.this, "gówno", Toast.LENGTH_SHORT).show();
        }
    }



}