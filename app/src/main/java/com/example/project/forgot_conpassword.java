package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.LoginRequest;
import com.example.project.ApiService.pojo.ResetRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_conpassword extends AppCompatActivity {

    Button b;
    EditText newPass,connPass;
    boolean passwordVisible;
    TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_forgot_conpassword);

        b = findViewById(R.id.button);

        newPass = findViewById(R.id.newpassword);
        connPass = findViewById(R.id.conpassword);

        tv_back = findViewById(R.id.login);

        newPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= newPass.getRight() - newPass.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = newPass.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable Image
                            newPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
                            //for hide password
                            newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable Image
                            newPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
                            //for show password
                            newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        newPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        connPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= connPass.getRight() - connPass.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = connPass.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable Image
                            connPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
                            //for hide password
                            connPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable Image
                            connPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
                            //for show password
                            connPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        connPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

    }



    public void next(View view) {
        if (!connPass.equals("") && !newPass.equals("")) {
        if(newPass.getText().toString().equals(connPass.getText().toString())) {

            Bundle bundle = getIntent().getExtras();

            String userName = bundle.getString("username");
            String cpass = newPass.getText().toString().trim();
            Log.d("name",userName);
            Log.d("pass",cpass);




                Call<ResetRequest> resetRequestCall = APIclient.getUserService().userReset(userName,cpass);

                resetRequestCall.enqueue(new Callback<ResetRequest>() {
                    @Override
                    public void onResponse(Call<ResetRequest> call, Response<ResetRequest> response) {
                        Log.e("success_response", "" + call.request().url());
                        Log.e("success_response", "" + response.message());
                        Log.e("success_response", "" + response.body());
                        Log.e("success_response", "" + response.code());
                        Log.e("success_response", "" + response.isSuccessful());
                        Log.e("success_response", "" + response.body());

                        ResetRequest apiResponse = response.body();
                        Log.e("success_msg", "" + response.body().getMsg());


                        if (response.body().getMsg().equals("Password Updated")) {
                            Toast.makeText(forgot_conpassword.this, "Password Successfully Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgot_conpassword.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            newPass.setError("Enter Valid Password");
                            connPass.setError("Enter Valid Password");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetRequest> call, Throwable t) {
                        Log.e("fail_response", "" + call.request().url());
                        Log.e("fail_response", "" + t.toString());
                        Log.e("fail_response", "" + t.getMessage());

                    }
                });
            }
        else{
            connPass.setError("Password Does Not Match");
        }
        }

        else {
            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        Intent intent = new Intent(forgot_conpassword.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}