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

public class MainActivity extends  {

    TextView textView1;
    TextView textView2;
    EditText password;

    boolean passwordVisible;
    private EditText u_username,u_password;
    public static boolean status_slot;
    public static boolean u_doc_success;

    public static boolean u_slot_success;

    public static boolean eligible;

    public static boolean bpass;

    public static String remark,v_remark,slotdate,slottime;

    MenuItem item;
    private String e_username, e_password;

    CoordinatorLayout layout;
//    private String URL = "http://172.30.224.1/ap/Api.php";

    APIinterface apIinterface;

    //    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
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
        textView1 = (TextView)findViewById(R.id.signup);
        textView2 = (TextView)findViewById(R.id.forget);
        password = (EditText)findViewById(R.id.password);

        e_username = e_password = ""; // empty string
        u_username = findViewById(R.id.username);
        u_password = findViewById(R.id.password);

//        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
//        editor = sharedPreferences.edit();
        loginBtn.setOnClickListener(v -> {
            login(v);
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
                finish();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,forget.class);
                startActivity(intent);
                finish();
            }
        });

        password.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if(passwordVisible){
                            //set drawable Image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityoff,0);
                            //for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else{
                            //set drawable Image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityon,0);
                            //for show password
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

//    public void login(View view)
//    {
//        e_username = u_username.getText().toString().trim();
//        e_password = u_password.getText().toString().trim();
//
//        if(!e_username.equals("") && !e_password.equals("")) {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    if (response.equals("success")) {
//                        Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, signup.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (response.equals("failure")) {
//                        Toast.makeText(MainActivity.this, "Invalid Login Username And Password", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("u_username", e_username);
//                    data.put("u_password", e_password);
//                    return data;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            requestQueue.add(stringRequest);
//        }else{
//            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
//        }
//    }

    //    public void login(View view) {
//        public String sendPostRequest(String requestURL, HashMap<String , String> postDataParams){
//            URL url;
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//        }
//
//    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


//    public void login(View view) {
//        Intent intent=new Intent(MainActivity.this,nav_drawer.class);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//    MenuInflater inflater = this.getMenuInflater();
//    inflater.inflate(R.menu.drawer_menu, menu);
//    return super.onCreateOptionsMenu(menu);
//
//    }
//
//    public boolean onPrepareOptionsMenu(final Menu menu) {
//
//        item = menu.findItem(R.id.nav_sbook);
//        Log.i("", "* onPrepareOptionsMenu *" + item);
////        menu.getItem(1).setEnabled(false);
////        Log.i("", "* getActionView *" + menuFolder.getActionView());
//        super.onPrepareOptionsMenu(menu);
//        (menu.findItem(R.id.nav_sbook)).setEnabled(false);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    public void login(View view) {

        Log.e("login called", "");

        String userName = u_username.getText().toString().trim();
        String password = u_password.getText().toString().trim();
        if(!userName.equals("") && !password.equals("")) {

            Call<LoginRequest> loginResponseCall = APIclient.getUserService().userLogin(userName, password);

            loginResponseCall.enqueue(new Callback<LoginRequest>() {
                @Override
                public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                    Log.e("success_response", "" + call.request().url());
                    Log.e("success_response", "" + response.message());
                    Log.e("success_response", "" + response.body());
                    Log.e("success_response", "" + response.code());
                    Log.e("success_response", "" + response.isSuccessful());
                    Log.e("success_response", "" + response.body());

                    LoginRequest apiResponse = response.body();
                    Log.e("success_msg", "" + response.body().getMsg());


                    if (response.body().getMsg().equals("User Login Successfully")) {

//                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//
//                        editor.putString("username",userName);
//                        editor.putString("id",apiResponse.getU_id());
////                        Id.u_id = apiResponse.getU_id();
//                        Boolean logedIn = pref.getBoolean("flag",false);

//                        if(logedIn == true){
//                        }
//                        else{



//                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean("flag", true);
//                        editor.apply();
//                        Boolean logedIn = pref.getBoolean("flag",true);
//                        editor.putBoolean("flag",true);
//                        editor.apply();
//
//                        SharedPreferences pref5 = getSharedPreferences("key", MODE_PRIVATE);
//                        Boolean b = pref5.getBoolean("flag",false);
//
//                        if(b==true){
//                            SharedPreferences fetch = getSharedPreferences("id", MODE_PRIVATE);
//                            String idstr = fetch.getString("value","-1");
//                            Id.u_id = idstr;

//                        }else if(b==false){
//
//                            SharedPreferences pref1 = getSharedPreferences("id", MODE_PRIVATE);
//                            SharedPreferences.Editor editor1 = pref1.edit();
//                            editor1.putString("value",apiResponse.getU_id().toString().trim());
//                            editor1.apply();
                        Id.u_id = apiResponse.getU_id();

//                        }

//                        }
//                        share();
//                        editor.putBoolean("flag", true);
//                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, nav_drawer.class);
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        if(apiResponse.getU_doc_status().equals("0")){
                            status_slot = false;
                        } else if(apiResponse.getU_doc_status().equals("1")){
                            status_slot = true;
                        }

                        if(apiResponse.getU_doc_success().equals("1")){
                            u_doc_success = true;
                        }else if(apiResponse.getU_doc_success().equals("0")) {
                            u_doc_success = false;
                        }

                        if(apiResponse.getU_slot_success().equals("1")){
                            u_slot_success = true;
                        }else if(apiResponse.getU_doc_success().equals("0")) {
                            u_slot_success = false;
                        }

                        if(apiResponse.getEligible().equals("1")){
                            eligible = true;
                        }else if(apiResponse.getU_doc_success().equals("0")) {
                            eligible = false;
                        }

                        if(apiResponse.getU_create().equals("1")){
                            bpass = true;
                        }else if(apiResponse.getU_create().equals("0")) {
                            bpass = false;
                        }

                        remark = apiResponse.getRemark().toString();
                        v_remark = apiResponse.getV_remark().toString();
                        slotdate = apiResponse.getU_timeslotdate();
                        slottime = apiResponse.getU_timeslottime();

                        finish();

                    } else {
                        u_username.setError("Enter Valid Username");
                        u_password.setError("Enter Valid Password");
                        Snackbar snackbar
                                = Snackbar
                                .make(
                                        layout,
                                        "Enter Valid Username Or Password",
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
                public void onFailure(Call<LoginRequest> call, Throwable t) {
                    Log.e("fail_response", "" + call.request().url());
                    Log.e("fail_response", "" + t.toString());
                    Log.e("fail_response", "" + t.getMessage());

                }
            });
        }
        else{
//                        Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
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


//    void share() {
//        SharedPreferences pref = getSharedPreferences("id", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("value",)
//        editor.apply();
//    }
}

