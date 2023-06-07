package com.example.mekaproj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**This is the very first starting screen for our app*/
public class SplashActivity extends AppCompatActivity {

    @Override
    /**Here we create splash screen.*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
        @Override
        /**Here we run the splash screen for 4000 millis (4 seconds)*/
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 4000);

    }
}
