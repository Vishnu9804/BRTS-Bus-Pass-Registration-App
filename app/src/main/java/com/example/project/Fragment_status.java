package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.DisplayRequest;
import com.example.project.Id.Id;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_status extends Fragment {

    ImageView up,dv,sb,ov,bpe,d;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_status, container, false);

        up = root.findViewById(R.id.u_updoc);
        dv = root.findViewById(R.id.u_adminverification);
        sb = root.findViewById(R.id.u_sbook);
        ov = root.findViewById(R.id.u_offlineverification);
        bpe = root.findViewById(R.id.u_eligibility);
        d = root.findViewById(R.id.u_delievery);

        // Inflate the layout for this fragment
        if(MainActivity.u_doc_success == true){
            up.setImageResource(R.drawable.checked);
        }else{
            up.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else{
            dv.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.u_slot_success == true){
            sb.setImageResource(R.drawable.checked);
        }else{
            sb.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.eligible == true){
            ov.setImageResource(R.drawable.checked);
            bpe.setImageResource(R.drawable.checked);
            d.setImageResource(R.drawable.checked);
        }else{
            ov.setImageResource(R.drawable.cancel);
            bpe.setImageResource(R.drawable.cancel);
            d.setImageResource(R.drawable.cancel);
        }

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

        String id = Id.u_id;

        Call<DisplayRequest> displayResponseCall = APIclient.getUserService().userDisplay(id);

        displayResponseCall.enqueue(new Callback<DisplayRequest>() {
            @Override
            public void onResponse(Call<DisplayRequest> call, Response<DisplayRequest> response) {
                Log.e("success_response", "" + call.request().url());
                Log.e("success_response", "" + response.message());
                Log.e("success_response", "" + response.body());
                Log.e("success_response", "" + response.code());
                Log.e("success_response", "" + response.isSuccessful());
                Log.e("success_response", "" + response.body());

                DisplayRequest apiResponse = response.body();
                Log.e("success_msg", "" + response.body().getMsg());


                if (response.body().getMsg().equals("Displayed Successfully")) {
//
//                    String fname = apiResponse.getU_firstname();
//                    String lname = apiResponse.getU_lastname();
//                    String name = fname + " " + lname;
//                    Log.d("n",name);
//                    String category = apiResponse.getU_category();
//                    Log.d("n",category);
//                    tv_name.setText(""+name);
//                    tv_category.setText(""+category);

                    if(apiResponse.getU_doc_status().equals("0")){
                        MainActivity.status_slot = false;
                    } else if(apiResponse.getU_doc_status().equals("1")){
                        MainActivity.status_slot = true;
                    }

                    if(apiResponse.getU_doc_success().equals("1")){
                        MainActivity.u_doc_success = true;
                    }else if(apiResponse.getU_doc_success().equals("0")) {
                        MainActivity.u_doc_success = false;
                    }

                    if(apiResponse.getU_slot_success().equals("1")){
                        MainActivity.u_slot_success = true;
                    }else if(apiResponse.getU_slot_success().equals("0")) {
                        MainActivity.u_slot_success = false;
                    }

                    if(apiResponse.getEligible().equals("1")){
                        MainActivity.eligible = true;
                    }else if(apiResponse.getEligible().equals("0")) {
                        MainActivity.eligible = false;
                    }
//                        Log.d("Status", String.valueOf(status));
//                    nav_slot.setEnabled(false);


                } else {
//                    tv_name.setError("Enter Valid");
//                    tv_category.setError("Enter Valid");
                }
            }

            @Override
            public void onFailure(Call<DisplayRequest> call, Throwable t) {
                Log.e("fail_response", "" + call.request().url());
                Log.e("fail_response", "" + t.toString());
                Log.e("fail_response", "" + t.getMessage());

            }
        });

        if(MainActivity.u_doc_success == true){
            up.setImageResource(R.drawable.checked);
        }else{
            up.setImageResource(R.drawable.checked);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else{
            dv.setImageResource(R.drawable.checked);
        }

        if(MainActivity.u_slot_success == true){
            sb.setImageResource(R.drawable.checked);
        }else{
            sb.setImageResource(R.drawable.checked);
        }

        if(MainActivity.eligible == true){
            ov.setImageResource(R.drawable.checked);
            bpe.setImageResource(R.drawable.checked);
            d.setImageResource(R.drawable.checked);
        }else{
            ov.setImageResource(R.drawable.cancel);
            bpe.setImageResource(R.drawable.cancel);
            d.setImageResource(R.drawable.cancel);
        }
    }
}