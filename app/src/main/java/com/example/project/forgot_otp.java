package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.aabhasjindal.otptextview.OtpTextView;

public class forgot_otp extends AppCompatActivity {

    Button b;
    int otp = 1234;
    OtpTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_forgot_otp);

        b = findViewById(R.id.button);
        tv = findViewById(R.id.otp_view);

    }


    public void reset(View view) {
        Bundle bundle = getIntent().getExtras();
        if(Integer.parseInt(tv.getOTP()) == otp) {
            if (bundle != null) {
                String userName = bundle.getString("username");
                bundle.putString("username", userName);
                Intent intent = new Intent(forgot_otp.this, forgot_conpassword.class);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("name", userName);
                finish();
            } else {
                Toast.makeText(this, "cannot fetch", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
        }
    }
}