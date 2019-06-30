package com.example.android_hackathon_2019;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DjangoApi {

    String DJANGO_SITE = "";



    @Multipart
    @POST("upload/")
    Call<RequestBody>  uploadFile(@Part MultipartBody.Part file);



}
