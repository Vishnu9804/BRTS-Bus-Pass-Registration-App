package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.ProfileRequest;
import com.example.project.Id.Id;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_profile extends AppCompatActivity {

    TextView u_uname,u_fname,u_lname,u_pnum,u_email,u_gender,u_dob,u_city,u_address,u_landmark,u_pincode,u_category,u_s_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_edit_profile);

        u_uname = (TextView) findViewById(R.id.tv_uname);
        u_fname = (TextView) findViewById(R.id.u_fname);
        u_lname = (TextView) findViewById(R.id.u_lname);
        u_pnum = (TextView) findViewById(R.id.u_pnum);
        u_email = (TextView) findViewById(R.id.u_email);
        u_gender = (TextView) findViewById(R.id.u_gender);
        u_dob = (TextView) findViewById(R.id.u_dob);
        u_city = (TextView) findViewById(R.id.u_city);
        u_address = (TextView) findViewById(R.id.u_address);
        u_landmark = (TextView) findViewById(R.id.u_landmark);
        u_pincode = (TextView) findViewById(R.id.u_pincode);
        u_category = (TextView) findViewById(R.id.u_category);
        u_s_date = (TextView) findViewById(R.id.u_s_date);

        String id = Id.u_id;

        Call<ProfileRequest> profileResponseCall = APIclient.getUserService().userProfile(id);

        profileResponseCall.enqueue(new Callback<ProfileRequest>() {
            @Override
            public void onResponse(Call<ProfileRequest> call, Response<ProfileRequest> response) {
                Log.e("success_response", "" + call.request().url());
                Log.e("success_response", "" + response.message());
                Log.e("success_response", "" + response.body());
                Log.e("success_response", "" + response.code());
                Log.e("success_response", "" + response.isSuccessful());
                Log.e("success_response", "" + response.body());

                ProfileRequest apiResponse = response.body();
                Log.e("success_msg", "" + response.body().getMsg());


                if (response.body().getMsg().equals("Displayed Successfully")) {

                    String uname = apiResponse.getU_username();
                    String fname = apiResponse.getU_firstname();
                    String lname = apiResponse.getU_lastname();
                    String pnum = apiResponse.getU_phonenumber();
                    String email = apiResponse.getU_email();
                    String gender = apiResponse.getU_gender();
                    String dob = apiResponse.getU_dob();
                    String city = apiResponse.getU_city();
                    String address = apiResponse.getU_address();
                    String landmark = apiResponse.getU_landmark();
                    String pin = apiResponse.getU_pin();
                    String category = apiResponse.getU_category();
                    String date = apiResponse.getU_timeslotdate() + "\n" +  apiResponse.getU_timeslottime();
                    String name = fname + " " + lname;
                    Log.d("n",name);
                    Log.d("n",category);

//                    u_uname,u_fname,u_lname,u_pnum,u_email,u_gender,u_dob,u_city,u_address,u_landmark,u_pincode,u_category;

                    u_uname.setText(""+uname);
                    u_fname.setText(""+fname);
                    u_lname.setText(""+lname);
                    u_pnum.setText(""+pnum);
                    u_email.setText(""+email);
                    u_gender.setText(""+gender);
                    u_dob.setText(""+dob);
                    u_city.setText(""+city);
                    u_address.setText(""+address);
                    u_landmark.setText(""+landmark);
                    u_pincode.setText(""+pin);
                    u_category.setText(""+category);
                    u_s_date.setText(""+date);


                } else {
//                    u_uname.setError("Enter Valid");
//                    tv_category.setError("Enter Valid");
                }
            }

            @Override
            public void onFailure(Call<ProfileRequest> call, Throwable t) {
                Log.e("fail_response", "" + call.request().url());
                Log.e("fail_response", "" + t.toString());
                Log.e("fail_response", "" + t.getMessage());

            }
        });
    }


    public void back(View view) {
        Intent intent = new Intent(edit_profile.this,nav_drawer.class);
        startActivity(intent);
        finish();
    }
}