package com.example.project.ApiService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIclient {

    private static Retrofit retrofit = null;



    private static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.5/api/")  //Change server URL
                .client(okHttpClient)
                .build();

        return retrofit;
    }
        public static APIinterface getUserService(){
            APIinterface apiInterface = getRetrofit().create(APIinterface.class);
            return apiInterface;
        }
}