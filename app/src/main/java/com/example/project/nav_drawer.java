package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.DisplayRequest;
import com.example.project.Id.Id;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nav_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    TextView tv_name,tv_category;
    static MenuItem nav_slot,nav_doc,nav_pass;
    static NavigationView navigationView;


    //    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    TextView nani;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

//        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
//        editor = sharedPreferences.edit();

//        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
//        Boolean logedIn = sp.getBoolean("flag",false);

//        if(logedIn==false) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }

        NavigationView nv= findViewById(R.id.nav_view);
        Menu menuNav=nv.getMenu();
        nav_slot = menuNav.findItem(R.id.nav_sbook);
        nav_doc = menuNav.findItem(R.id.nav_updoc);
        nav_pass = menuNav.findItem(R.id.nav_buspass);

        NavigationView n = (NavigationView) findViewById(R.id.nav_view);
        View headerView = n.getHeaderView(0);
//        item = (MenuItem) headerView.findViewById(R.id.nav_sbook);

//        TextView navUsername = (TextView) headerView.findViewById(R.id.nani);
//        navUsername.setText("Your Text Here");
//        nani = (TextView) findViewById(R.id.nani);

        String id = Id.u_id;

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_status()).commit();
            navigationView.setCheckedItem(R.id.nav_status);
        }
        tv_name = headerView.findViewById(R.id.tv_name);
        tv_category = headerView.findViewById(R.id.tv_category);
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

                    Id.fname = apiResponse.getU_firstname();
                    Id.lname = apiResponse.getU_lastname();
                    Id.name = Id.fname + " " + Id.lname;
//                    Log.d("n",name);
                    Id.category = apiResponse.getU_category();
                    Log.d("n",Id.category);
                    tv_name.setText(""+Id.name);
                    tv_category.setText(""+Id.category);

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

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();

                    MainActivity.slotdate = apiResponse.getU_timeslotdate();
                    MainActivity.slottime = apiResponse.getU_timeslottime();

                } else {
                    tv_name.setError("Enter Valid");
                    tv_category.setError("Enter Valid");
                }
            }

            @Override
            public void onFailure(Call<DisplayRequest> call, Throwable t) {
                Log.e("fail_response", "" + call.request().url());
                Log.e("fail_response", "" + t.toString());
                Log.e("fail_response", "" + t.getMessage());

            }
        });
//        header();
    }


    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item)
    {
////        MenuItem m = m.findItem(R.id.nav_sbook);
//       if(MainActivity.status == true){
//           Log.d("t","true");
////            m.setEnabled(true);
//           menu.findItem(R.id.nav_sbook);
//       } else if (MainActivity.status == false) {
//           Log.d("f","false");
//           m.setEnabled(false);
//       }


        switch (item.getItemId())
        {
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Sure Want To Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(nav_drawer.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                navigationView.setCheckedItem(R.id.nav_status);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putBoolean("flag", false);
//                editor.apply();
                break;

//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new fragment_logout()).commit();

            case R.id.nav_updoc:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_updoc()).commit();
                break;
            case R.id.nav_status:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_status()).commit();
                break;
            case R.id.nav_sbook:
//                item.setEnabled(false);
                Log.d("Status", String.valueOf(MainActivity.status_slot));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_sbook()).commit();
//                if (MainActivity.status_slot == false) {
//                    Toast.makeText(this, "Please Wait Till Your Document Gets Verifed", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_contact()).commit();
                break;
            case R.id.nav_buspass:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_buspass()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
////        MenuItem item = menu.findItem(R.id.nav_sbook);
////        Menu item = menu.findItem(R.menu.drawer_menu);
////        MenuItem
//        Log.d("item",""+item);
//        Log.d("Status", String.valueOf(MainActivity.status));
//
////        item.setTitle("abc");
//
//      /*  if (MainActivity.status == true) {
//            item.setEnabled(false);
//        }*/ /*else {
//            item.setEnabled(false);
//        }*/
////        return true;
//        return super.onPrepareOptionsMenu(menu);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
////        MenuInflater inflater = this.getMenuInflater();
////        inflater.inflate(R.menu.drawer_menu, menu);
////        return super.onCreateOptionsMenu(menu);
//
//    }
    public boolean onPrepareOptionsMenu(final Menu menu) {

        super.onPrepareOptionsMenu(menu);

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


        return super.onPrepareOptionsMenu(menu);
    }


//    @Override
//    public boolean onPrepareOptionsMenu (Menu menu) {
//        if (true)
//            menu.getItem(1).setEnabled(true);
//        else
//            menu.getItem(1).setEnabled(false);
//        return true;
//    }


    @Override
    protected void onStart() {
        super.onStart();
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
    }

    @Override
    protected void onPause() {
        super.onPause();

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

                    Id. fname = apiResponse.getU_firstname();
                    Id. lname = apiResponse.getU_lastname();
                    Id.name = Id.fname + " " + Id.lname;
//                    Log.d("n",name);
                    Id.category = apiResponse.getU_category();
//                    Log.d("n",category);
                    tv_name.setText(""+Id.name);
                    tv_category.setText(""+Id.category);


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
                    }else if(apiResponse.getU_doc_success().equals("0")) {
                        MainActivity.u_slot_success = false;
                    }

                    if(apiResponse.getEligible().equals("1")){
                        MainActivity.eligible = true;
                    }else if(apiResponse.getU_doc_success().equals("0")) {
                        MainActivity.eligible = false;
                    }
//                        Log.d("Status", String.valueOf(status));
//                    nav_slot.setEnabled(false);

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();

                    MainActivity.slotdate = apiResponse.getU_timeslotdate();
                    MainActivity.slottime = apiResponse.getU_timeslottime();

                } else {
                    tv_name.setError("Enter Valid");
                    tv_category.setError("Enter Valid");
                }
            }

            @Override
            public void onFailure(Call<DisplayRequest> call, Throwable t) {
                Log.e("fail_response", "" + call.request().url());
                Log.e("fail_response", "" + t.toString());
                Log.e("fail_response", "" + t.getMessage());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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

                    Id.fname = apiResponse.getU_firstname();
                    Id.lname = apiResponse.getU_lastname();
                    Id.name = Id.fname + " " + Id.lname;
//                    Log.d("n",name);
                    Id.category = apiResponse.getU_category();
//                    Log.d("n",category);
                    tv_name.setText(""+Id.name);
                    tv_category.setText(""+Id.category);


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
                    }else if(apiResponse.getU_doc_success().equals("0")) {
                        MainActivity.u_slot_success = false;
                    }

                    if(apiResponse.getEligible().equals("1")){
                        MainActivity.eligible = true;
                    }else if(apiResponse.getU_doc_success().equals("0")) {
                        MainActivity.eligible = false;
                    }
//                        Log.d("Status", String.valueOf(status));
//                    nav_slot.setEnabled(false);

                    if(apiResponse.getU_create().equals("1")){
                        MainActivity.bpass = true;
                    }else if(apiResponse.getU_create().equals("0")) {
                        MainActivity.bpass = false;
                    }

                    MainActivity.remark = apiResponse.getRemark().toString();
                    MainActivity.v_remark = apiResponse.getV_remark().toString();

                    MainActivity.slotdate = apiResponse.getU_timeslotdate();
                    MainActivity.slottime = apiResponse.getU_timeslottime();

                } else {
                    tv_name.setError("Enter Valid");
                    tv_category.setError("Enter Valid");
                }
            }

            @Override
            public void onFailure(Call<DisplayRequest> call, Throwable t) {
                Log.e("fail_response", "" + call.request().url());
                Log.e("fail_response", "" + t.toString());
                Log.e("fail_response", "" + t.getMessage());

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are You Sure Want To Exit? \n(You Will Be Loged out)")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
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
    }

    public void edit(View view) {
        Intent intent = new Intent(this,edit_profile.class);
        startActivity(intent);
        finish();
    }
}