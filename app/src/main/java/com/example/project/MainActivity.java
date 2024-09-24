package com.example.project;

import static com.example.project.R.menu.drawer_menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.APIinterface;
import com.example.project.ApiService.pojo.LoginRequest;
import com.example.project.Id.Id;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    EditText password;
    boolean passwordVisible;
    private EditText u_username, u_password;
    CoordinatorLayout layout;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginBtn);
        layout = findViewById(R.id.cordlayout);
        textView1 = findViewById(R.id.signup);
        textView2 = findViewById(R.id.forget);
        password = findViewById(R.id.password);

        u_username = findViewById(R.id.username);
        u_password = findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> login());

        textView1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, signup.class);
            startActivity(intent);
            finish();
        });

        textView2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, forget.class);
            startActivity(intent);
            finish();
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            // set drawable Image for hidden password
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // set drawable Image for visible password
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void login() {
        String userName = u_username.getText().toString().trim();
        String userPassword = u_password.getText().toString().trim();

        if (userName.equals("Vishnu.9804") && userPassword.equals("1234")) {
            Intent intent = new Intent(MainActivity.this, nav_drawer.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            u_username.setError("Enter Valid Username");
            u_password.setError("Enter Valid Password");
            Snackbar snackbar = Snackbar.make(layout, "Invalid Username or Password", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("DISMISS", view -> snackbar.dismiss());
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> MainActivity.super.onBackPressed())
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
