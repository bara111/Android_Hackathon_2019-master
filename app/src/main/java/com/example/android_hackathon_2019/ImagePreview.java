package com.example.android_hackathon_2019;


import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.marozzi.roundbutton.RoundButton;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.FileNotFoundException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ImagePreview extends AppCompatActivity {

    private ImageView imageView;
    public static String IMAGE = "IMAGE";
    private Bitmap bitmap;
    private RoundButton start_processing;
    TextView tv_photo_advice;
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    String advice_photo_text = "Make sure photo is <font color='#EE0000'>Sharp, Centered</font> and free of hair or other obstructing objects.";
    final String ID = "1111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        imageView = findViewById(R.id.img);
        tv_photo_advice = findViewById(R.id.tv_photo_advice);
        tv_photo_advice.setText(Html.fromHtml(advice_photo_text));


        //setting the bitmap from camera
        if (getIntent() != null) {
            byte[] bytes = getIntent().getByteArrayExtra(IMAGE);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        }


        start_processing = findViewById(R.id.start_processing);
        findViewById(R.id.retake_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        start_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading_bar).setVisibility(View.VISIBLE);
                start_processing.setClickable(false);
                uploadPhoto();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GalleryActivity.class));
    }

    private void uploadPhoto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        File file = new File(this.getCacheDir(), id + ".png");

        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("xx", os.toString());

        final DjangoApi postApi = retrofit.create(DjangoApi.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), file);
        final MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", file.getName(), requestBody);


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Call<RequestBody> call = postApi.uploadFile(multiPartBody);

                call.enqueue(new Callback<RequestBody>() {
                    @Override
                    public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {

                        Log.e("good", "good" + response.code());

                    }

                    @Override
                    public void onFailure(Call<RequestBody> call, Throwable t) {

                        Log.e("fail", "fail" + t.getMessage());
                    }
                });
                return null;
            }
        };

        asyncTask.execute();


    }


}