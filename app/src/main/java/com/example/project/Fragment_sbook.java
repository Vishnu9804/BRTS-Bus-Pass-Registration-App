package com.example.project;


import static com.example.project.nav_drawer.navigationView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.SlotRequest;
import com.example.project.ApiService.pojo.UploadRequest;
import com.example.project.Id.Id;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_sbook extends Fragment {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button btn_date,btn_submit;
    TextView full;
    RadioGroup rg;

    RadioButton selectedRadioButton;

    MenuItem nav_slot;
    boolean flag=true;
    int tday,tmonth,tyear;

    CoordinatorLayout layout;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sbook, container, false);

        full = root.findViewById(R.id.slot_full);

        rg = root.findViewById(R.id.rg);

        btn_submit = root.findViewById(R.id.submit);
        btn_date = root.findViewById(R.id.date);

        layout = root.findViewById(R.id.cordlayout);


//        NavigationView nv= root.findViewById(R.id.nav_view);
//        Menu menuNav=nv.getMenu();
//        nav_slot = menuNav.findItem(R.id.nav_sbook);

        btn_date.setText(getTodaysDate());
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
                dialog.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==true) {

                    String id = Id.u_id;
                    Log.d("tu", id);
                    String date = btn_date.getText().toString().trim();
                    Log.d("tu", date);
                    int selectedRadioButtonId = rg.getCheckedRadioButtonId();
                    selectedRadioButton = root.findViewById(selectedRadioButtonId);
                    String time = selectedRadioButton.getText().toString();
                    Log.d("tu", time);

                    Call<SlotRequest> slotRequestCall = APIclient.getUserService().userSlot(id, date, time);

                    slotRequestCall.enqueue(new Callback<SlotRequest>() {
                        @Override
                        public void onResponse(Call<SlotRequest> call, Response<SlotRequest> response) {
                            Log.e("success_response", "" + call.request().url());
                            Log.e("success_response", "" + response.message());
                            Log.e("success_response", "" + response.body().toString());
                            Log.e("success_response", "" + response.code());
                            Log.e("success_response", "" + response.isSuccessful());
                            Log.e("success_response", "" + response.body());

                            SlotRequest apiResponse = response.body();
                            Log.e("success_msg", "" + response.body().getMsg());

                            if(apiResponse.getU_slot_success().equals("1")){
                                MainActivity.u_slot_success = true;
                            }else if(apiResponse.getU_slot_success().equals("0")) {
                                MainActivity.u_slot_success = false;
                            }

                            if (response.body().getMsg().equals("Slot Booked Successfully")) {
                                Log.d("Booked", "Slot Booked");
                                nav_drawer.nav_slot.setEnabled(false);
                                navigationView.setCheckedItem(R.id.nav_status);
                                Toast.makeText(getContext(), "Slot Booked Successfully", Toast.LENGTH_SHORT).show();
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                Fragment_status myFragment = new Fragment_status();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                                flag = true;
                                full.setVisibility(View.INVISIBLE);
                                full.setText("");
                            } else if (response.body().getMsg().equals("Slot Is Full")) {
                                flag = false;
                                full.setText("**SLOT YOU ARE TO BOOK IS FULL, PLEASE TRY ANOTHER SLOT !!");
                                full.setVisibility(View.VISIBLE);
                            } else {
//                                btn_submit.setError("Slot Not Booked");
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Slot Not Booked",
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
                        public void onFailure(Call<SlotRequest> call, Throwable t) {
                            Log.e("fail_response", "" + call.request().url());
                            Log.e("fail_response", "" + t.toString());
                            Log.e("fail_response", "" + t.getMessage());

                        }
                    });
                }
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = makeDateString(day, month, year);
                Date d1 = new Date(year,month,day);
                Date d2 = new Date(tyear,tmonth,tday);

                if(d1.after(d2)){
                    flag = true;
                    btn_date.setText(date);
                    full.setVisibility(View.INVISIBLE);
                    full.setText("");
                }
                else{
                    flag = false;
                    full.setText("**Date You Selected Is Already Passed. \n Please Select Date After Current Date!!");
                    full.setVisibility(View.VISIBLE);
                }
//                if((day>tday) && (month>tmonth-1) && (year>tyear-1)){
//                    flag = true;
//                    btn_date.setText(date);
//                    full.setVisibility(View.INVISIBLE);
//                    full.setText("");
//                }
//                else{
//                    flag = false;
//                    full.setText("**Date You Selected Is Already Passed");
//                    full.setVisibility(View.VISIBLE);
//                }
            }

            private String makeDateString(int day, int month, int year) {
                return year + "-" + month + "-" + day;
            }

            private String getmonthFormat(int month) {
                if (month == 1)
                    return "JAN";
                if (month == 2)
                    return "FAB";
                if (month == 3)
                    return "MAR";
                if (month == 4)
                    return "APR";
                if (month == 5)
                    return "MAY";
                if (month == 6)
                    return "JUN";
                if (month == 7)
                    return "JUL";
                if (month == 8)
                    return "AUG";
                if (month == 9)
                    return "SEP";
                if (month == 10)
                    return "OCT";
                if (month == 11)
                    return "NOV";
                if (month == 12)
                    return "DEC";
                return null;
            }
        };
        return root;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        tyear = cal.get(Calendar.YEAR);
        tmonth = cal.get(Calendar.MONTH);
        tmonth = tmonth + 1;
        tday = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(tday, tmonth, tyear);
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


//    public void submit(View view) {
//        String id = Id.u_id;
//        Log.d("ID",id);
//        String date = btn_date.getText().toString().trim();
//        String time = selectedRadioButton.getText().toString();
//
//
//        Call<SlotRequest> slotRequestCall = APIclient.getUserService().userSlot(date, time, id);
//
//        slotRequestCall.enqueue(new Callback<SlotRequest>() {
//            @Override
//            public void onResponse(Call<SlotRequest> call, Response<SlotRequest> response) {
//                Log.e("success_response", "" + call.request().url());
//                Log.e("success_response", "" + response.message());
//                Log.e("success_response", "" + response.body().toString());
//                Log.e("success_response", "" + response.code());
//                Log.e("success_response", "" + response.isSuccessful());
//                Log.e("success_response", "" + response.body());
//
//                SlotRequest apiResponse = response.body();
//                Log.e("success_msg", "" + response.body().getMsg());
//
//
//                if (response.body().getMsg().equals("Slot Booked Successfully")) {
//                    Log.d("Booked", "Slot Booked");
//                } else {
//                    btn_submit.setError("Slot Not Booked");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SlotRequest> call, Throwable t) {
//                Log.e("fail_response", "" + call.request().url());
//                Log.e("fail_response", "" + t.toString());
//                Log.e("fail_response", "" + t.getMessage());
//
//            }
//        });
//
//    }




}

