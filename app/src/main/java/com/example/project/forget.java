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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.LoginRequest;
import com.example.project.ApiService.pojo.VerifyforgetRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.ktx.Firebase;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forget extends AppCompatActivity {
    TextView textView;
    Button btn;
    TextView phone;

    CoordinatorLayout layout;

    FirebaseAuth auth;

//    String email = "jashpastagia3@gmail.com";
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

        phone = findViewById(R.id.phone);

        layout = findViewById(R.id.cordlayout);

        auth = FirebaseAuth.getInstance();
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget(view);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(forget.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void forget(View view) {
        String Phone = phone.getText().toString().trim();

        if(!Phone.equals("")) {

//            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(forget.this, "Check Your Email", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(forget.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + phone.getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    forget.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                            Toast.makeText(forget.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar snackbar
                                    = Snackbar
                                    .make(
                                            layout,
                                            e.getMessage(),
                                            Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction(
                                    "DISMISS",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            snackbar.dismiss();
                                        }
                                    });

                            snackbar.show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);



            Call<VerifyforgetRequest> verifyforgetRequestCall = APIclient.getUserService().userVerify(phone.getText().toString());

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
                        bundle.putString("phone",phone.getText().toString());
                        bundle.putString("backendotp",s);
                        Intent intent = new Intent(forget.this, forgot_otp.class);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    } else {
                        phone.setError("Enter Valid Phone Number");
                        Snackbar snackbar
                                = Snackbar
                                .make(
                                        layout,
                                        "Enter Valid Phone Number",
                                        Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(
                                "DISMISS",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        snackbar.dismiss();
                                    }
                                });

                        snackbar.show();
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
                    });
        }
        else{
//            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
            Snackbar snackbar
                    = Snackbar
                    .make(
                            layout,
                            "Fields Cannot Be Empty",
                            Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(
                    "DISMISS",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            snackbar.dismiss();
                        }
                    });

            snackbar.show();
        }
    }
    }
