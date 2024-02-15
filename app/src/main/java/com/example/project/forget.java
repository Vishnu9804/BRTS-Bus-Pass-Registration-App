package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.LoginRequest;
import com.example.project.ApiService.pojo.VerifyforgetRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forget extends AppCompatActivity {
    TextView textView;
    Button btn;
    TextView username;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_forget);
        textView = (TextView)findViewById(R.id.login);
        btn = (Button)findViewById(R.id.button);

        username = findViewById(R.id.username);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forget.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(forget.this,forgot_otp.class));
//                finish();
//            }
//        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(forget.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void forget(View view) {
        String userName = username.getText().toString().trim();

        if(!userName.equals("")) {

            Call<VerifyforgetRequest> verifyforgetRequestCall = APIclient.getUserService().userVerify(userName);

            verifyforgetRequestCall.enqueue(new Callback<VerifyforgetRequest>() {
                @Override
                public void onResponse(Call<VerifyforgetRequest> call, Response<VerifyforgetRequest> response) {
                    Log.e("success_response", "" + call.request().url());
                    Log.e("success_response", "" + response.message());
                    Log.e("success_response", "" + response.body());
                    Log.e("success_response", "" + response.code());
                    Log.e("success_response", "" + response.isSuccessful());
                    Log.e("success_response", "" + response.body());

                    VerifyforgetRequest apiResponse = response.body();
                    Log.e("success_msg", "" + response.body().getMsg());


                    if (response.body().getMsg().equals("User Registered")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("username",username.getText().toString());
                        Intent intent = new Intent(forget.this, forgot_otp.class);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    } else {
                        username.setError("Enter Valid Username");
                    }
                }

                @Override
                public void onFailure(Call<VerifyforgetRequest> call, Throwable t) {
                    Log.e("fail_response", "" + call.request().url());
                    Log.e("fail_response", "" + t.toString());
                    Log.e("fail_response", "" + t.getMessage());

                }
            });
        }
        else{
            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
    }
    }
