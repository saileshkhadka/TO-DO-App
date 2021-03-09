package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {  //This is activity life cycle when the app first start 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Thread background = new Thread() {
//            public void run() {
//
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(3*1000);
//
//                    // After 5 seconds redirect to another intent
////                    Intent i=new Intent(getBaseContext(),WelcomeActivity.class);
////                    startActivity(i);
////
////                    //Remove activity
////                    finish();
//
//                } catch (Exception e) {
//
//                }
//                finally {
//                    Intent i=new Intent(SplashActivity.this,WelcomeActivity.class);
//                    startActivity(i);
//
//                    //Remove activity
//                    finish();
//                }
//            }
//        };
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);


    }
}