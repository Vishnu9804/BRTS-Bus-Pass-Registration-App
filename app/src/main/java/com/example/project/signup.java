package com.example.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.RegisterRequest;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    // UI Elements
    private TextView textView, tv_doc;
    private Button btn, dateButton, btn_doc;
    private EditText password1, password2, etuName, etEmail, first, last, phone, landmark, address, pincode;
    private RadioGroup gender;
    private CoordinatorLayout layout;
    private DatePickerDialog datePickerDialog;

    // Constants
    private static final String URL = "http://172.30.224.1/ap/Api.php";
    private static final String REQUIRED_FIELD_MSG = "Required";
    private static final String PASSWORD_MISMATCH_MSG = "Password Does Not Match";
    private static final String SIGNUP_SUCCESS_MSG = "Signup Successfully";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        initUI(); // Initialize UI elements
        initDatePicker(); // Initialize DatePicker
    }

    // Method to initialize UI components
    private void initUI() {
        textView = findViewById(R.id.login);
        btn = findViewById(R.id.button);
        dateButton = findViewById(R.id.date);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        etuName = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        first = findViewById(R.id.txtfirstname);
        last = findViewById(R.id.txtlastname);
        phone = findViewById(R.id.phonenum);
        landmark = findViewById(R.id.txtlandmark);
        address = findViewById(R.id.txtaddress);
        pincode = findViewById(R.id.txtpin);
        gender = findViewById(R.id.RadioGrp);
        layout = findViewById(R.id.cordlayout);

        dateButton.setText(getTodaysDate());

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        setupPasswordVisibility(password1);
        setupPasswordVisibility(password2);
    }

    // Setup password visibility toggle
    private void setupPasswordVisibility(EditText passwordField) {
        passwordField.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= passwordField.getRight() - passwordField.getCompoundDrawables()[Right].getBounds().width()) {
                    togglePasswordVisibility(passwordField);
                    return true;
                }
            }
            return false;
        });
    }

    // Toggle password visibility
    private void togglePasswordVisibility(EditText passwordField) {
        int selection = passwordField.getSelectionEnd();
        if (passwordField.getTransformationMethod() instanceof PasswordTransformationMethod) {
            passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
            passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
            passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        passwordField.setSelection(selection);
    }

    // Method to get today's date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        return makeDateString(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }

    // Initialize the DatePicker dialog
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, dateSetListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    // Create formatted date string
    private String makeDateString(int day, int month, int year) {
        return year + " " + getMonthFormat(month) + " " + day;
    }

    // Format month to string
    private String getMonthFormat(int month) {
        switch (month) {
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
            default: return "JAN";
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        finish();
    }

    // Method to save user data
    public void save(View view) {
        if (validateInputs()) {
            String userName = etuName.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String firstname = first.getText().toString().trim();
            String lastname = last.getText().toString().trim();
            String phonenumber = phone.getText().toString().trim();
            String date = dateButton.getText().toString().trim();
            String u_landmark = landmark.getText().toString().trim();
            String u_address = address.getText().toString().trim();
            String u_city = city.getSelectedItem().toString().trim();
            String u_pincode = pincode.getText().toString().trim();
            String u_category = category.getSelectedItem().toString().trim();
            String u_gender = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();

            registerUser(userName, password, firstname, lastname, phonenumber, email, u_gender, date, u_city, u_address, u_landmark, u_pincode, u_category);
        }
    }

    // Validate input fields
    private boolean validateInputs() {
        boolean isValid = true;

        if (isEmpty(etuName)) { setError(etuName, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(password1)) { setError(password1, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(password2)) { setError(password2, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(etEmail)) { setError(etEmail, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(first)) { setError(first, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(last)) { setError(last, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(phone)) { setError(phone, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(landmark)) { setError(landmark, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(address)) { setError(address, REQUIRED_FIELD_MSG); isValid = false; }
        if (isEmpty(pincode)) { setError(pincode, REQUIRED_FIELD_MSG); isValid = false; }

        if (!password1.getText().toString().trim().equals(password2.getText().toString().trim())) {
            password2.setError(PASSWORD_MISMATCH_MSG);
            isValid = false;
        }

        return isValid;
    }

    // Check if EditText is empty
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    // Set error message to EditText
    private void setError(EditText editText, String message) {
        editText.setError(message);
    }

    // Register user through API
    private void registerUser(String userName, String password, String firstname, String lastname, String phonenumber,
                              String email, String gender, String date, String city, String address,
                              String landmark, String pincode, String category) {
        RegisterRequest registerRequest = new RegisterRequest(userName, password, firstname, lastname,
                phonenumber, email, gender, date, city, address, landmark, pincode, category);

        Call<Void> call = APIclient.getInstance().getApi().registerUser(URL, registerRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, SIGNUP_SUCCESS_MSG, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                } else {
                    Snackbar.make(layout, "Registration Failed", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("SignupActivity", "onFailure: " + t.getMessage());
                Snackbar.make(layout, "Network error, please try again", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
