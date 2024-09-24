package com.example.project;

import static com.example.project.nav_drawer.nav_doc;
import static com.example.project.nav_drawer.nav_pass;
import static com.example.project.nav_drawer.nav_slot;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.DisplayRequest;
import com.example.project.Id.Id;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_status extends Fragment {

    ImageView up,dv,sb,ov,bpe,d;
    TextView remark;
//    MenuItem nav_slot,nav_doc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
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

        remark = root.findViewById(R.id.remark);

//        NavigationView nv= root.findViewById(R.id.nav_view);
//        Menu menuNav=nv.getMenu();
//        nav_slot = menuNav.findItem(R.id.nav_sbook);
//        nav_doc = menuNav.findItem(R.id.nav_updoc);
//
//        if (MainActivity.status_slot == true) {
//            nav_slot.setEnabled(true);
//        }else {
//            nav_slot.setEnabled(false);
//        }
//
//        if (MainActivity.u_doc_success == false) {
//            nav_doc.setEnabled(true);
//        }else {
//            nav_doc.setEnabled(false);
//        }


        // Inflate the layout for this fragment
        if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
            nav_slot.setEnabled(true);
        }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
            nav_slot.setEnabled(false);
        }
        else {
            nav_slot.setEnabled(false);
        }

        if(MainActivity.bpass == true){
            nav_pass.setEnabled(true);
        }else {
            nav_pass.setEnabled(false);
        }

        if(MainActivity.u_doc_success == true){

            up.setImageResource(R.drawable.checked);
        }else{
            up.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else if(MainActivity.status_slot == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
            dv.setImageResource(R.drawable.cancel);
        }

//        Log.d("abcd" ,MainActivity.v_remark);

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
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == true && MainActivity.eligible == false){
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.eligible == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.u_slot_success == true){
            remark.setText("");
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
                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();


                    if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
                        nav_slot.setEnabled(true);
                    }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
                        nav_slot.setEnabled(false);
                    }
                    else {
                        nav_slot.setEnabled(false);
                    }

                    if (MainActivity.u_doc_success == false) {
                        nav_doc.setEnabled(true);
                    }else {
                        nav_doc.setEnabled(false);
                    }

                    if(MainActivity.bpass == true){
                        nav_pass.setEnabled(true);
                    }else {
                        nav_pass.setEnabled(false);
                    }

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

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
            up.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else if(MainActivity.status_slot == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
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
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
            nav_slot.setEnabled(true);
        }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
            nav_slot.setEnabled(false);
        }
        else {
            nav_slot.setEnabled(false);
        }

        if (MainActivity.u_doc_success == false) {
            nav_doc.setEnabled(true);
        }else {
            nav_doc.setEnabled(false);
        }

        if(MainActivity.bpass == true){
            nav_pass.setEnabled(true);
        }else {
            nav_pass.setEnabled(false);
        }

        if(MainActivity.status_slot == true && MainActivity.eligible == false){
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.eligible == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.u_slot_success == true){
            remark.setText("");
        }

    }


    @Override
    public void onResume() {
        super.onResume();

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

                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

                    if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
                        nav_slot.setEnabled(true);
                    }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
                        nav_slot.setEnabled(false);
                    }
                    else {
                        nav_slot.setEnabled(false);
                    }

                    if (MainActivity.u_doc_success == false) {
                        nav_doc.setEnabled(true);
                    }else {
                        nav_doc.setEnabled(false);
                    }

                    if(MainActivity.bpass == true){
                        nav_pass.setEnabled(true);
                    }else {
                        nav_pass.setEnabled(false);
                    }

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
            up.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else if(MainActivity.status_slot == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
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
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
            nav_slot.setEnabled(true);
        }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
            nav_slot.setEnabled(false);
        }
        else {
            nav_slot.setEnabled(false);
        }

        if (MainActivity.u_doc_success == false) {
            nav_doc.setEnabled(true);
        }else {
            nav_doc.setEnabled(false);
        }

        if(MainActivity.bpass == true){
            nav_pass.setEnabled(true);
        }else {
            nav_pass.setEnabled(false);
        }

        if(MainActivity.status_slot == true && MainActivity.eligible == false){
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.eligible == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.u_slot_success == true){
            remark.setText("");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

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

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();
                    if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
                        nav_slot.setEnabled(true);
                    }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
                        nav_slot.setEnabled(false);
                    }
                    else {
                        nav_slot.setEnabled(false);
                    }

                    if (MainActivity.u_doc_success == false) {
                        nav_doc.setEnabled(true);
                    }else {
                        nav_doc.setEnabled(false);
                    }

                    if(MainActivity.bpass == true){
                        nav_pass.setEnabled(true);
                    }else {
                        nav_pass.setEnabled(false);
                    }

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
            up.setImageResource(R.drawable.cancel);
        }

        if(MainActivity.status_slot == true){
            dv.setImageResource(R.drawable.checked);
        }else if(MainActivity.status_slot == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
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
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if (MainActivity.status_slot == true && MainActivity.u_slot_success == false) {
            nav_slot.setEnabled(true);
        }else if(MainActivity.status_slot == true && MainActivity.u_slot_success == true){
            nav_slot.setEnabled(false);
        }
        else {
            nav_slot.setEnabled(false);
        }

        if (MainActivity.u_doc_success == false) {
            nav_doc.setEnabled(true);
        }else {
            nav_doc.setEnabled(false);
        }

        if(MainActivity.bpass == true){
            nav_pass.setEnabled(true);
        }else {
            nav_pass.setEnabled(false);
        }
        if(MainActivity.status_slot == true && MainActivity.eligible == false){
            if(MainActivity.v_remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.v_remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.eligible == false){
            if(MainActivity.remark.equals("0")){
                remark.setText("");
            }else{
                remark.setText("Remark :- " + MainActivity.remark);
                remark.setTextColor(Color.RED);
            }
        }

        if(MainActivity.status_slot == false && MainActivity.u_slot_success == true){
            remark.setText("");
        }

    }
}