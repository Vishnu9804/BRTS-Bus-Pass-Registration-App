package com.example.project;

import static android.app.Activity.RESULT_OK;

//import static com.example.project.nav_drawer.nav_doc;
//import static com.example.project.nav_drawer.nav_slot;

import static com.example.project.nav_drawer.nav_doc;
import static com.example.project.nav_drawer.navigationView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.ApiService.APIclient;
import com.example.project.ApiService.pojo.BonafideRequest;
import com.example.project.ApiService.pojo.DisabilityRequest;
import com.example.project.ApiService.pojo.ResetRequest;
import com.example.project.ApiService.pojo.UploadRequest;
import com.example.project.Id.Id;
import com.google.android.material.navigation.NavigationView;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.android.material
        .snackbar
        .Snackbar;

public class Fragment_updoc extends Fragment implements PickiTCallbacks {

    private int PICK_PDF_REQUEST = 1;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";

    PickiT pickiT;
    Button buttonChoose1, buttonChoose2, buttonChoose3, buttonChoose4, btn_doc, btne;
    //            Button buttonChoose5;
    Button buttonUpload;

    int fileType = 0;

    int count = 0;

    File adhar_file, ele_file, pic_file, sign_file;
    //    File bono_file;
    File user_doc;

    int temp = 0;

    int doc_temp = 0;

    int flag = 0,flag2 = 0,flag3 = 0,flag4 = 0,flag5 = 0;

    CoordinatorLayout layout;

//    NavigationView navigationView;
//    DrawerLayout drawer;
    //    File temp = new File("/dat
//    a/user/0/com.example.project/cache/WDPM Mid result _T222.pdf");
    TextView adhar, ele, passpic, sign, tv_doc,document;
//    MenuItem nav_slot,nav_doc;
//    NavigationView nv;
    //    TextView bonofide;
    ActivityResultLauncher<Intent> resultLauncher;
//    ActivityResultLauncher<Intent> resultLauncher5;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_updoc, container, false);

        layout = root.findViewById(R.id.cordlayout);

        tv_doc = root.findViewById(R.id.tv_doc);
        btn_doc = root.findViewById(R.id.btn_doc);
        btne = root.findViewById(R.id.btne);

//        navigationView = root.findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(getContext());

//        nv= root.findViewById(R.id.nav_view);
//        Menu menuNav=nv.getMenu();
//        nav_slot = menuNav.findItem(R.id.nav_sbook);
//        nav_doc = menuNav.findItem(R.id.nav_updoc);

//        drawer=root.findViewById(R.id.drawer_layout);
//        navigationView=root.findViewById(R.id.nav_view);



//        tv_doc.setVisibility(View.VISIBLE);
//        btn_doc.setVisibility(View.VISIBLE);
//        btne.setVisibility(View.VISIBLE);

        document = root.findViewById(R.id.document);
        Log.d("category",Id.category);
        if (Id.category.equals("Student")) {
            tv_doc.setVisibility(View.VISIBLE);
            btn_doc.setVisibility(View.VISIBLE);
            btne.setVisibility(View.VISIBLE);
            document.setText("Bonafide");
        }else if (Id.category.equals("Handicap")){
            tv_doc.setVisibility(View.VISIBLE);
            btn_doc.setVisibility(View.VISIBLE);
            btne.setVisibility(View.VISIBLE);
            document.setText("Disability\nCerificate");
        }else{
            tv_doc.setVisibility(View.INVISIBLE);
            btn_doc.setVisibility(View.INVISIBLE);
            btne.setVisibility(View.INVISIBLE);
            document.setText("");
        }
        buttonChoose1 = root.findViewById(R.id.choose);
        buttonChoose2 = root.findViewById(R.id.choose2);
        buttonChoose3 = root.findViewById(R.id.choose3);
        buttonChoose4 = root.findViewById(R.id.choose4);
//        buttonChoose5 = root.findViewById(R.id.choose5);
        pickiT = new PickiT(getActivity(), this, getActivity());


        adhar = root.findViewById(R.id.adhar);
        ele = root.findViewById(R.id.ele);
        passpic = root.findViewById(R.id.passpic);
        sign = root.findViewById(R.id.signfile);
//        bonofide = root.findViewById(R.id.bonofide);

        buttonUpload = root.findViewById(R.id.btnupload);


        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doc_temp==0)
                {
                    Snackbar snackbar
                            = Snackbar
                            .make(
                                    layout,
                                    "Upload all Documents First!!!!",
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
                else {
                    if (Id.category.equals("Student")) {
                        File doc_path = new File(user_doc.getPath());
                        RequestBody requestFile5 = RequestBody.create(MediaType.parse("application/pdf"), doc_path);
                        MultipartBody.Part multipartDoc = MultipartBody.Part.createFormData("bonafide", doc_path.getName(), requestFile5);
                        Log.e("doc", "" + doc_path);

                        String id = Id.u_id;
                        Log.d("blahid", id);

                        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
                        Call<BonafideRequest> bonafideRequestCall = APIclient.getUserService().userBonafide(multipartDoc, requestId);

                        bonafideRequestCall.enqueue(new Callback<BonafideRequest>() {
                            @Override
                            public void onResponse(Call<BonafideRequest> call, Response<BonafideRequest> response) {
                                Log.e("success_response", "" + call.request().url());
                                Log.e("success_response", "" + response.message());
                                Log.e("success_response", "" + response.body().toString());
                                Log.e("success_response", "" + response.code());
                                Log.e("success_response", "" + response.isSuccessful());
                                Log.e("success_response", "" + response.body());

                                BonafideRequest apiResponse = response.body();
                                Log.e("success_msg", "" + response.body().getMsg());


                                if (response.body().getMsg().equals("File Inserted Successfully")) {
                                    Log.d("Uploaded", "Inserted Successfully");
                                } else if (response.body().getMsg().equals("File Updated Successfully")) {
                                    Log.d("Uploaded", "Upload Successfully");
                                }
                                else if (response.body().getMsg().equals("Bonafide Size exceeded")) {
                                    Snackbar snackbar
                                            = Snackbar
                                            .make(
                                                    layout,
                                                    "Bonafide Size exceeded should be less than 2MB",
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
                                else {
                                    Snackbar snackbar
                                            = Snackbar
                                            .make(
                                                    layout,
                                                    "Error Uploading the Bonafide!!!",
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
                            public void onFailure(Call<BonafideRequest> call, Throwable t) {
                                Log.e("fail_response", "" + call.request().url());
                                Log.e("fail_response", "" + t.toString());
                                Log.e("fail_response", "" + t.getMessage());

                            }
                        });
                    } else if (Id.category.equals("Handicap")) {
                        File doc_path = new File(user_doc.getPath());
                        RequestBody requestFile5 = RequestBody.create(MediaType.parse("application/pdf"), doc_path);
                        MultipartBody.Part multipartDoc = MultipartBody.Part.createFormData("disability", doc_path.getName(), requestFile5);
                        Log.e("doc", "" + doc_path);

                        String id = Id.u_id;
                        Log.d("blahid", id);

                        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
                        Call<DisabilityRequest> disabilityRequestCall = APIclient.getUserService().userDisability(multipartDoc, requestId);

                        disabilityRequestCall.enqueue(new Callback<DisabilityRequest>() {
                            @Override
                            public void onResponse(Call<DisabilityRequest> call, Response<DisabilityRequest> response) {
                                Log.e("success_response", "" + call.request().url());
                                Log.e("success_response", "" + response.message());
                                Log.e("success_response", "" + response.body().toString());
                                Log.e("success_response", "" + response.code());
                                Log.e("success_response", "" + response.isSuccessful());
                                Log.e("success_response", "" + response.body());

                                DisabilityRequest apiResponse = response.body();
                                Log.e("success_msg", "" + response.body().getMsg());


                                if (response.body().getMsg().equals("File Inserted Successfully")) {
                                    Log.d("Uploaded", "Inserted Successfully");
                                } else if (response.body().getMsg().equals("File Updated Successfully")) {
                                    Log.d("Uploaded", "Upload Successfully");
                                }
                                else if (response.body().getMsg().equals("Disability Size exceeded")) {
                                    Snackbar snackbar
                                            = Snackbar
                                            .make(
                                                    layout,
                                                    "Disability Certificate Size exceeded should be less than 2MB",
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
                                else {
                                    Snackbar snackbar
                                            = Snackbar
                                            .make(
                                                    layout,
                                                    "Error Uploading the Disability Certificate!!!",
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
                            public void onFailure(Call<DisabilityRequest> call, Throwable t) {
                                Log.e("fail_response", "" + call.request().url());
                                Log.e("fail_response", "" + t.toString());
                                Log.e("fail_response", "" + t.getMessage());

                            }
                        });
                    }

                }
            }
        });



        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count == 0){
                    Snackbar snackbar
                            = Snackbar
                            .make(
                                    layout,
                                    "Upload all Documents First!!!!",
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
                } else if (count >= 4) {

                    File adhar_path = new File(adhar_file.getPath());
                    RequestBody requestFile1 = RequestBody.create(MediaType.parse("application/pdf"), adhar_path);
                    MultipartBody.Part multipartAadhar = MultipartBody.Part.createFormData("aadharcard", adhar_path.getName(), requestFile1);
                    Log.e("adhar", "" + adhar_path);


                    File ele_path = new File(ele_file.getPath());
                    RequestBody requestFile2 = RequestBody.create(MediaType.parse("application/pdf"), ele_path);
                    MultipartBody.Part multipartEle = MultipartBody.Part.createFormData("electricitybill", ele_path.getName(), requestFile2);
                    Log.e("electricity bill", "" + ele_path);


                    File pic_path = new File(pic_file.getPath());
                    RequestBody requestFile3 = RequestBody.create(MediaType.parse("application/pdf"), pic_path);
                    MultipartBody.Part multipartPic = MultipartBody.Part.createFormData("photo", pic_path.getName(), requestFile3);
                    Log.e("photo", "" + pic_path);


                    File sign_path = new File(sign_file.getPath());
                    RequestBody requestFile4 = RequestBody.create(MediaType.parse("application/pdf"), sign_path);
                    MultipartBody.Part multipartSign = MultipartBody.Part.createFormData("signature", sign_path.getName(), requestFile4);
                    Log.e("sign", "" + sign_path);

//                File bono_path = new File(bono_file.getPath());
//                RequestBody requestFile5 = RequestBody.create(MediaType.parse("multipart/form-data"), bono_file.getPath());
//                MultipartBody.Part multipartBody5 =MultipartBody.Part.createFormData("file",bono_file.getName(),requestFile5);
//                Log.e("bonofide",""+bono_path);
//                Log.e("bono name",""+bono_file.getName());


                    String id = Id.u_id;
//                RequestBody requestFile5 = RequestBody.create(MediaType.parse("multipart/form-data"), temp.getPath());
//                MultipartBody.Part multiTemp =MultipartBody.Part.createFormData("temp",temp.getName(),requestFile5);
                    Log.d("id", id);
                    RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
                    Call<UploadRequest> uploadRequestCall = APIclient.getUserService().userUpload(multipartAadhar, multipartPic, multipartEle, multipartSign, requestId);
//                Call<UploadRequest> uploadRequestCall = APIclient.getUserService().userUpload(multiTemp,requestId);

                    uploadRequestCall.enqueue(new Callback<UploadRequest>() {
                        @Override
                        public void onResponse(Call<UploadRequest> call, Response<UploadRequest> response) {
                            Log.e("success_response", "" + call.request().url());
                            Log.e("success_response", "" + response.message());
                            Log.e("success_response", "" + response.body().toString());
                            Log.e("success_response", "" + response.code());
                            Log.e("success_response", "" + response.isSuccessful());
                            Log.e("success_response", "" + response.body());

                            UploadRequest apiResponse = response.body();
                            Log.e("success_msg", "" + response.body().getMsg());

                            if (apiResponse.getU_doc_success().equals("1")) {
                                MainActivity.u_doc_success = true;
                            } else if (apiResponse.getU_doc_success().equals("0")) {
                                MainActivity.u_doc_success = false;
                            }


                            if (response.body().getMsg().equals("File Inserted Successfully")) {
                                Log.d("Uploaded", "Inserted Successfully");
//                            MainActivity.u_doc_success = true;


                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment_status myFragment = new Fragment_status();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
//                            navigationView.setCheckedItem(R.id.nav_status);
                                Toast.makeText(getContext(), "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                nav_doc.setEnabled(false);
                                navigationView.setCheckedItem(R.id.nav_status);

                            } else if (response.body().getMsg().equals("File Updated Successfully")) {
                                Log.d("Uploaded", "Upload Successfully");
//                            MainActivity.u_doc_success = true;
//                            nav_doc.setEnabled(false);
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment_status myFragment = new Fragment_status();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
//                            navigationView.setCheckedItem(R.id.nav_status);
                                nav_doc.setEnabled(false);
                                navigationView.setCheckedItem(R.id.nav_status);

                                Toast.makeText(getContext(), "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else if (response.body().getMsg().equals("Photo Size exceeded")) {
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Photo Size exceeded should be less than 2MB",
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
                            else if (response.body().getMsg().equals("Aadharcard Size exceeded")) {
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Aadharcard Size exceeded should be less than 2MB",
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
                            else if (response.body().getMsg().equals("Signature Size exceeded")) {
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Signature Size exceeded should be less than 2MB",
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
                            else if (response.body().getMsg().equals("Electricity Bill Size exceeded")) {
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Electricity Bill Size exceeded should be less than 2MB",
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
                            else {
                                Snackbar snackbar
                                        = Snackbar
                                        .make(
                                                layout,
                                                "Error Uploading the files!!!",
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
                        public void onFailure(Call<UploadRequest> call, Throwable t) {
                            Log.e("fail_response", "" + call.request().url());
                            Log.e("fail_response", "" + t.toString());
                            Log.e("fail_response", "" + t.getMessage());

                        }
                    });
                }

            }
        });

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // Initialize result data
                Intent data = result.getData();

                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

            }
        });


        adhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                Log.d("hi12", "hi");

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // When permission is granted
                    // Create method
                    count++;
                    selectPDF();
                    flag = 1;
                }
            }
        });

        ele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // When permission is granted
                    // Create method
                    count++;
                    selectPDF2();
                    flag2 = 1;
                }
            }
        });

        passpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // When permission is granted
                    // Create method
                    count++;
                    selectPDF3();
                    flag3 = 1;
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // When permission is granted
                    // Create method
                    count++;
                    selectPDF4();
                    flag4 = 1;
                }
            }
        });

        tv_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // When permission is granted
                    // Create method
                    doc_temp++;
                    selectPDF5();
                    flag5 = 1;
                }
            }
        });


        buttonChoose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("1", "1");
                if(flag == 1) {
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    String mimeType = myMime.getMimeTypeFromExtension("pdf");
                    Uri adharURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", adhar_file);
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    newIntent.setDataAndType(adharURI, mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    flag = 0;
                    startActivity(newIntent);
                }
                else
                {
                    Toast.makeText(getContext(), "Select PDF to View", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonChoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag2 == 1) {
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    String mimeType = myMime.getMimeTypeFromExtension("pdf");
                    Uri eleURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", ele_file);
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    newIntent.setDataAndType(eleURI, mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    flag2 = 0;
                    startActivity(newIntent);
                }
                else
                {
                    Toast.makeText(getContext(), "Select PDF to View", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonChoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag3 == 1) {
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    String mimeType = myMime.getMimeTypeFromExtension("pdf");
                    Uri eleURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", pic_file);
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    newIntent.setDataAndType(eleURI, mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    flag3 = 0;
                    startActivity(newIntent);
                }
                else
                {
                    Toast.makeText(getContext(), "Select PDF to View", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonChoose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag4 == 1) {
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    String mimeType = myMime.getMimeTypeFromExtension("pdf");
                    Uri eleURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", sign_file);
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    newIntent.setDataAndType(eleURI, mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    flag4 = 0;
                    startActivity(newIntent);
                }
                else
                {
                    Toast.makeText(getContext(), "Select PDF to View", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag5 == 1) {
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    String mimeType = myMime.getMimeTypeFromExtension("pdf");
                    Uri eleURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", user_doc);
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    newIntent.setDataAndType(eleURI, mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    flag5 = 0;
                    startActivity(newIntent);
                } else {
                    Toast.makeText(getContext(), "Select PDF to View", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void selectPDF() {
        // Initialize intent
        fileType = 1;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);

    }

    private void selectPDF2() {
        // Initialize intent
        fileType = 2;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }

    private void selectPDF3() {
        // Initialize intent
        fileType = 3;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
//        intent.setType("application/pdf");
        intent.setType("application/pdf");

        // Launch intent
        resultLauncher.launch(intent);
    }

    private void selectPDF4() {
        // Initialize intent
        fileType = 4;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
//        intent.setType("image/jpeg");
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }

    private void selectPDF5() {
        // Initialize intent
        fileType = 5;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
//        intent.setType("image/jpeg");
        intent.setType("application/pdf");

        // Launch intent
        resultLauncher.launch(intent);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
        } else {
            // When permission is denied
            // Display toast
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {
    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {


//      Intent intent = ShareCompat.IntentBuilder.from(getActivity()).setType("*/*").setSubject("").setStream(path).setChooserTitle("rohan").createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//      startActivity(intent);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType = myMime.getMimeTypeFromExtension("pdf");

        if (fileType == 1) {
            adhar_file = new File(path);
            adhar.setText(adhar_file.getName());
        }
        if (fileType == 2) {
            ele_file = new File(path);
            ele.setText(ele_file.getName());
        }
        if (fileType == 3) {
            pic_file = new File(path);
            passpic.setText(pic_file.getName());
        }
        if (fileType == 4) {
            sign_file = new File(path);
            sign.setText(sign_file.getName());
        }
        if (fileType == 5) {
            user_doc = new File(path);
            tv_doc.setText(user_doc.getName());
        }

//        Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", +);
//              Intent intent = ShareCompat.IntentBuilder.from(getActivity()).setType("*/*").setSubject("").setStream(photoURI).setChooserTitle("rohan").createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//      startActivity(intent);
        //        Intent newIntent = new Intent(Intent.ACTION_VIEW);
//        newIntent.setDataAndType(photoURI, mimeType);
//        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(newIntent);

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {
        Log.e("waahhhhh>>>", "kemmmm6ooo : " + paths.size());
        Log.e("waahhhhh>>>", "kemmmm6ooo : " + wasSuccessful);
        Log.e("waahhhhh>>>", "kemmmm6ooo : " + Reason);

    }

}
