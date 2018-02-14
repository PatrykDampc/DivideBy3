package com.example.patryk.divideby3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RandomNumber rn = new RandomNumber();

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(rn.getRanNumString());


    }
}
