package com.example.patryk.divideby3;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton button;
    TextSwitcher textSwitcher;
    private RandomNumber rn;
    CountDownTimer cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bumButtonID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
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
        start();


    }



    public void start(){
        cd = new CountDownTimer(Long.MAX_VALUE,5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                rn = new RandomNumber();
                textSwitcher.setText(rn.getRanNumString());
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
    public void stop(){
        cd.cancel();
    }

    @Override
    public void onClick(View v) {
        if(rn.isDivisibleByThree()){
            success();
        }else fail();
    }

    public void success(){
        //punkt+1
        Log.d("Success: ", "ok" );
        stop();
        start();
    }

    public void fail(){
        Log.d("Fail: ", "ok" );
        stop();
    }

}