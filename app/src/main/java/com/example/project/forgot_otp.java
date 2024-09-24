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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.ktx.Firebase;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class forgot_otp extends AppCompatActivity {

    Button b;
    int otp = 1234;
    OtpTextView tv;

    TextView resend;
    String backend_otp;
    String phone;

    CoordinatorLayout layout;

    @SuppressLint("MissingInflatedId")
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

        resend = findViewById(R.id.resend);

        layout = findViewById(R.id.cordlayout);


//        backend_otp =

//        auth.setLanguageCode("fr");

//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + phone,
                        60,
                        TimeUnit.SECONDS,
                        forgot_otp.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                Toast.makeText(forgot_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                            }
                        });
            }
        });


    }


    public void reset(View view) {
        Bundle bundle = getIntent().getExtras();
        backend_otp = bundle.getString("backendotp");
//        if(tv.getOTP() == backend_otp) {
            if (bundle != null) {

//                Log.d("name", userName);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backend_otp,tv.getOTP());
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()) {
                                            phone = bundle.getString("phone");
                                            bundle.putString("phone", phone);
                                            Intent intent = new Intent(forgot_otp.this, forgot_conpassword.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }else {

                                        }
                                    }
                                });
                                } else {
                Snackbar snackbar
                        = Snackbar
                        .make(
                                layout,
                                "cannot fetch",
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
//                Toast.makeText(this, "cannot fetch", Toast.LENGTH_SHORT).show();
            }


        }
//        else
//            Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
//        }
    }
