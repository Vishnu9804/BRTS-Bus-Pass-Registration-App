package com.example.project.ApiService;

import com.example.project.ApiService.pojo.BonafideRequest;
import com.example.project.ApiService.pojo.DisabilityRequest;
import com.example.project.ApiService.pojo.DisplayRequest;
import com.example.project.ApiService.pojo.LoginRequest;
import com.example.project.ApiService.pojo.ProfileRequest;
import com.example.project.ApiService.pojo.RegisterRequest;
import com.example.project.ApiService.pojo.ResetRequest;
import com.example.project.ApiService.pojo.SlotRequest;
import com.example.project.ApiService.pojo.UploadRequest;
import com.example.project.ApiService.pojo.VerifyforgetRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIinterface {

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_login")
    Call<LoginRequest> userLogin(
            @Field("u_data[u_username]") String username,
            @Field("u_data[u_password]") String password
    );

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_reg")
    Call<RegisterRequest> userRegister(
            @Field("u_data[u_username]") String username,
            @Field("u_data[u_password]") String password,
            @Field("u_data[u_firstname]") String firstname,
            @Field("u_data[u_lastname]") String lastname,
            @Field("u_data[u_phonenumber]") String phonenumber,
            @Field("u_data[u_email]") String email,
            @Field("u_data[u_gender]") String gender,
            @Field("u_data[u_dob]") String dob,
            @Field("u_data[u_city]") String city,
            @Field("u_data[u_address]") String address,
            @Field("u_data[u_landmark]") String landmark,
            @Field("u_data[u_pin]") String pin,
            @Field("u_data[u_category]") String category
    );
    //    @Query("u_data[u_password]") String password,

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_verify")
    Call<VerifyforgetRequest> userVerify(
            @Field("u_data[u_phonenumber]") String phonenumber

    );

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_reset")
    Call<ResetRequest> userReset(
            @Field("u_data[u_phonenumber]") String phonenumber,
            @Field("u_data[u_password]") String password
    );

    @Multipart
    @POST("/api/Api.php?v_api=user_doc")
    Call<UploadRequest> userUpload(
            @Part  MultipartBody.Part aadharcard,
            @Part  MultipartBody.Part photo,
            @Part  MultipartBody.Part electricitybill,
            @Part  MultipartBody.Part signature,
            @Part("u_data[u_id]") RequestBody id
    );

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_slot")
    Call<SlotRequest> userSlot(
            @Field("u_data[u_id]") String id,
            @Field("u_data[u_timeslotdate]") String timeslotdate,
            @Field("u_data[u_timeslottime]") String timeslottime

    );

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_display")
    Call<DisplayRequest> userDisplay(
            @Field("u_data[u_id]") String id
    );

    @FormUrlEncoded
    @POST("/api/Api.php?v_api=user_profile")
    Call<ProfileRequest> userProfile(
            @Field("u_data[u_id]") String id
    );

//    @Multipart
//    @POST("/api/Api.php?v_api=user_bonafide")
//    Call<BonafideRequest> userBonafide(
//            @Part  MultipartBody.Part bonafide,
//            @Part("u_data[u_id]") RequestBody id
//    );

    @Multipart
    @POST("/api/Api.php?v_api=user_bonafide")
    Call<BonafideRequest> userBonafide(
            @Part  MultipartBody.Part bonafide,
            @Part("u_data[u_id]") RequestBody id
    );

    @Multipart
    @POST("/api/Api.php?v_api=user_disability")
    Call<DisabilityRequest> userDisability(
            @Part  MultipartBody.Part disabilitydisability,
            @Part("u_data[u_id]") RequestBody id
    );
}
