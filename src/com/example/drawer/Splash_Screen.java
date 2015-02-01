package com.example.drawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash_Screen extends Activity {

    public static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent launchPianoActivity = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(launchPianoActivity);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}