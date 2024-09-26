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
import com.example.project.ApiService.pojo.BonafideRequest;
import com.example.project.ApiService.pojo.RegisterRequest;
import com.example.project.Id.Id;
import com.google.android.material.snackbar.Snackbar;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class signup extends AppCompatActivity {
//implements PickiTCallbacks
//    private int PICK_PDF_REQUEST = 1;
//    private static final int BUFFER_SIZE = 1024 * 2;
//    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";

//    PickiT pickiT;

    //    File user_doc;
    TextView textView,tv_doc;
    Button btn,dateButton,btn_doc;
    DatePickerDialog datePickerDialog;
    EditText password1, password2;
    EditText first,last,phone,landmark,address,pincode;
    RadioGroup gender;
    RadioButton selectedRadioButton;
    Spinner city,category;
    boolean passwordVisible;
    int fileType = 0;
    //    ActivityResultLauncher<Intent> resultLauncher;
    CoordinatorLayout layout;



    private EditText etuName, etEmail;
    private TextView tvStatus;
    private Button btnRegister;
    private String URL = "http://172.30.224.1/ap/Api.php";
    private String username, email, password, confirm_password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_signup);
        initDatePicker();

        tv_doc = (TextView) findViewById(R.id.tv_doc);
        btn_doc = (Button) findViewById(R.id.btn_doc);

        textView = (TextView) findViewById(R.id.login);
        btn = (Button) findViewById(R.id.button);
        dateButton=(Button) findViewById(R.id.date);
        dateButton.setText(getTodaysDate());
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);

        etuName = (EditText) findViewById(R.id.username);
        etEmail = (EditText) findViewById(R.id.email);
        first = (EditText) findViewById(R.id.txtfirstname);
        last = (EditText) findViewById(R.id.txtlastname);
        phone = (EditText) findViewById(R.id.phonenum);
        landmark = (EditText) findViewById(R.id.txtlandmark);
        address = (EditText) findViewById(R.id.txtaddress);
        pincode = (EditText) findViewById(R.id.txtpin);
//        datePickerDialog = (DatePickerDialog) findViewById(R.id.);
        city=(Spinner) findViewById(R.id.spinnercity);
        category=(Spinner) findViewById(R.id.spinnercategory);

        gender=(RadioGroup) findViewById(R.id.RadioGrp);

        layout = findViewById(R.id.cordlayout);


        btnRegister = findViewById(R.id.button);
//        pickiT = new PickiT(this, this, this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this,MainActivity.class));
                finish();
            }
        });*/
        password1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password1.getRight() - password1.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password1.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable Image
                            password1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
                            //for hide password
                            password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable Image
                            password1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
                            //for show password
                            password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password1.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        password2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password2.getRight() - password2.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password2.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable Image
                            password2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff, 0);
                            //for hide password
                            password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable Image
                            password2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityon, 0);
                            //for show password
                            password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password2.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

//        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                // Initialize result data
//                Intent data = result.getData();
//
//                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
//
//            }
//        });


//        btn_doc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // check condition
//                if (ActivityCompat.checkSelfPermission(signup.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    // When permission is not granted
//                    // Result permission
//                    ActivityCompat.requestPermissions((Activity) signup.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//                } else {
//                    // When permission is granted
//                    // Create method
//                    selectPDF();
//                }
//            }
//        });
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    //    private void selectPDF() {
//// Initialize intent
//        fileType = 1;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        // set type
//        intent.setType("application/pdf");
//        // Launch intent
//        resultLauncher.launch(intent);
//    }
//
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year) {
        return      year+" "+ getMonthFormat(month)+" "+ day;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(signup.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    public void save(View view) {
//        username = etuName.getText().toString().trim();
//        email = etEmail.getText().toString().trim();
//        password = etPass1.getText().toString().trim();
//        confirm_password = etPass2.getText().toString().trim();
//
//        if (!password.equals(confirm_password)) {
//            Toast.makeText(this, "Password Mismatched", Toast.LENGTH_SHORT).show();
//        } else if (!username.equals("") && !email.equals("") && !password.equals("")) {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
//                    if (response.equals("success")) {
//                        Intent intent = new Intent(signup.this, forget.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (response.equals("failure")) {
//                        Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("u_username", username);
//                    data.put("u_email", email);
//                    data.put("u_password", password);
//                    return data;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            requestQueue.add(stringRequest);
//        } else {
//            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void save(View view){


        String userName = etuName.getText().toString().trim();
        String password = password1.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String firstname = first.getText().toString().trim();
        String lastname = last.getText().toString().trim();
        String phonenumber = phone.getText().toString().trim();
//        int id = gender.getCheckedRadioButtonId();
        int selectedRadioButtonId = gender.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedRadioButtonId);
//        String u_gender = ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();
//        Log.d("Radio",""+u_gender);
        String date = dateButton.getText().toString().trim();
        String u_landmark = landmark.getText().toString().trim();
        String u_address = address.getText().toString().trim();
        String u_city = city.getSelectedItem().toString().trim();
        String u_pincode = pincode.getText().toString().trim();
        String u_category = category.getSelectedItem().toString().trim();

        if (!userName.equals("") && !password.equals("") && !password2.getText().toString().equals("") && !email.equals("") && !firstname.equals("") && !lastname.equals("") && !phonenumber.equals("") && !u_landmark.equals("") && !u_address.equals("") && !u_pincode.equals("") && !gender.equals("") && !dateButton.getText().equals(getTodaysDate())) {
            String u_gender=selectedRadioButton.getText().toString();

            if (password1.getText().toString().equals(password2.getText().toString())) {

                Call<RegisterRequest> registerRequestCall = APIclient.getUserService().userRegister(userName,password,firstname,lastname,phonenumber,email,u_gender,date,u_city,u_address,u_landmark,u_pincode,u_category);

                registerRequestCall.enqueue(new Callback<RegisterRequest>() {
                    @Override
                    public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                        Log.e("success_response",""+call.request().url());
                        Log.e("success_response",""+response.message());
                        Log.e("success_response",""+response.body());
                        Log.e("success_response",""+response.code());
                        Log.e("success_response",""+response.isSuccessful());
                        Log.e("success_response",""+response.body());

                        RegisterRequest apiResponse = response.body();
                        Log.e("success_msg",""+response.body().getMsg());


                        Toast.makeText(signup.this, "Signup Successfully", Toast.LENGTH_SHORT).show();

//                if(response.body().getMsg().equals("User Login Successfully")){
                        Intent intent=new Intent(signup.this,MainActivity.class);
                        startActivity(intent);
                        finish();
//                }
//                else {
//                    u_username.setError("Enter Valid Username");
//                    u_password.setError("Enter Valid Password");
//                }
                    }

                    @Override
                    public void onFailure(Call<RegisterRequest> call, Throwable t) {
                        Log.e("fail_response",""+call.request().url());
                        Log.e("fail_response",""+t.toString());
                        Log.e("fail_response",""+t.getMessage());

                    }
                });

            } else {
                password2.setError("Password Does Not Match");
                Snackbar snackbar
                        = Snackbar
                        .make(
                                layout,
                                "Password Does Not Match",
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
        else {
//            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
            etuName.setError("Required");
            password1.setError("Required");
            password2.setError("Required");
            etEmail.setError("Required");
            first.setError("Required");
            last.setError("Required");
            phone.setError("Required");
            landmark.setError("Required");
            address.setError("Required");
            pincode.setError("Required");

            Snackbar snackbar
                    = Snackbar
                    .make(
                            layout,
                            "Required Fields",
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


    //    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // check condition
//        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // When permission is granted
//            // Call method
//            selectPDF();
//        } else {
//            // When permission is denied
//            // Display toast
//            Toast.makeText(signup.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    @Override
//    public void PickiTonUriReturned() {
//
//    }
//
//    @Override
//    public void PickiTonStartListener() {
//
//    }
//
//    @Override
//    public void PickiTonProgressUpdate(int progress) {
//    }
//
//    @Override
//    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
//
//
////      Intent intent = ShareCompat.IntentBuilder.from(getActivity()).setType("*/*").setSubject("").setStream(path).setChooserTitle("rohan").createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////      startActivity(intent);
//
//        MimeTypeMap myMime = MimeTypeMap.getSingleton();
//        String mimeType = myMime.getMimeTypeFromExtension("pdf");
//
//        if (fileType == 1) {
//            user_doc = new File(path);
//            tv_doc.setText(user_doc.getName());
//        }
////        if (fileType == 2) {
//////            user_doc = new File(path);
//////            tv_doc.setText(user_doc.getName());
////        }
//
//
//
////        Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", adhar_file);
////              Intent intent = ShareCompat.IntentBuilder.from(getActivity()).setType("*/*").setSubject("").setStream(photoURI).setChooserTitle("rohan").createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////      startActivity(intent);
//        //        Intent newIntent = new Intent(Intent.ACTION_VIEW);
////        newIntent.setDataAndType(photoURI, mimeType);
////        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////        startActivity(newIntent);
//
//    }
//
//    @Override
//    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {
//        Log.e("waahhhhh>>>", "kemmmm6ooo : " + paths.size());
//        Log.e("waahhhhh>>>", "kemmmm6ooo : " + wasSuccessful);
//        Log.e("waahhhhh>>>", "kemmmm6ooo : " + Reason);
//
//    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

//    public void btne(View view) {
//
////        String id = Id.u_id;
//
//        String id = "4";
//
//
//
//
//        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
//        Call<BonafideRequest> bonafideRequestCall = APIclient.getUserService().userBonafide(multipartDoc, requestId);
//
//        bonafideRequestCall.enqueue(new Callback<BonafideRequest>() {
//            @Override
//            public void onResponse(Call<BonafideRequest> call, Response<BonafideRequest> response) {
//                Log.e("success_response", "" + call.request().url());
//                Log.e("success_response", "" + response.message());
//                Log.e("success_response", "" + response.body());
//                Log.e("success_response", "" + response.code());
//                Log.e("success_response", "" + response.isSuccessful());
//                Log.e("success_response", "" + response.body());
//
//                BonafideRequest apiResponse = response.body();
//                Log.e("success_msg", "" + response.body().getMsg());
//
//
////                if(response.body().getMsg().equals("User Login Successfully")){
//                if (response.body().getMsg().equals("File Inserted Successfully")) {
//                    Log.d("Uploaded", "Upload Success");
//                } else if (response.body().getMsg().equals("File Updated Successfully")) {
//                    Log.d("Uploaded", "Upload Success");
//                } else {
//                    btn_doc.setError("File Not Uploaded");
//                }
//            }
////                }
////                else {
////                    u_username.setError("Enter Valid Username");
////                    u_password.setError("Enter Valid Password");
////                }
//
//
//            @Override
//            public void onFailure(Call<BonafideRequest> call, Throwable t) {
//                Log.e("fail_response", "" + call.request().url());
//                Log.e("fail_response", "" + t.toString());
//                Log.e("fail_response", "" + t.getMessage());
//
//            }
//        });
//    }
}

