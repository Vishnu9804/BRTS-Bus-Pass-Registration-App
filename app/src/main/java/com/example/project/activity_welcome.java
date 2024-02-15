package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.project.ApiService.pojo.BonafideRequest;

public class activity_welcome extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
//                Boolean logedIn = sp.getBoolean("flag",false);
//
//                if(logedIn==true) {
//                    Intent logedinIntent = new Intent(getApplicationContext(), nav_drawer.class);
//                    startActivity(logedinIntent);
//                }
//                else{
                SharedPreferences sp = getSharedPreferences("first_time",MODE_PRIVATE);
                Boolean check = sp.getBoolean("flag",true);

//                SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
//                Boolean lg = sp.getBoolean("login",false);

                Intent intent;

                if(check){
                    intent = new Intent(activity_welcome.this, walkthrough1.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent logedinIntent;
//                    if(lg==true){
//                    logedinIntent = new Intent(getApplicationContext(), nav_drawer.class);
//                    }
//                    else{
                        logedinIntent = new Intent(getApplicationContext(),MainActivity.class);
//                    }
                    startActivity(logedinIntent);
                    finish();
//                }

//                }

            }
            }
        },SPLASH_TIME_OUT);
    }

//        void share() {
//        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("flag", true);
//        editor.apply();
//    }
}