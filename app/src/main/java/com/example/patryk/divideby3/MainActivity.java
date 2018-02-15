package com.example.patryk.divideby3;

import android.graphics.Color;
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
    private boolean started = false;
    private Handler handler = new Handler();
    private RandomNumber rn;
    private int i = 0;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> randHandle;

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
        start();
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

    }

    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            rn = new RandomNumber();
            Log.d("RN: ", rn.getRanNumString());
//            textSwitcher.setText(rn.getRanNumString());
        }
    };
//    public void stop(){
//        started = false;
//        handler.removeCallbacks(runnable);
//    }
//
//
    public void start(){
        Log.d("Start: ","OK");
        randHandle = scheduler.scheduleWithFixedDelay(runnable,0,2, TimeUnit.SECONDS);
    }
    public void stop(){
        Log.d("Stop: ","OK");
        randHandle.cancel(true);
    }

    @Override
    public void onClick(View v) {
        if(rn.isDivisibleByThree()){
            success();
        }else fail();
    }

    public void success(){
        //punkt+1
        stop();
        start();
    }

    public void fail(){
        stop();
    }

}