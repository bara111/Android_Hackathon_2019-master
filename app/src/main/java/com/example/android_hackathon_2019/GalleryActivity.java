package com.example.android_hackathon_2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecycleViewImageRecordAdapter mAdapter;
    private TextView task2;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ImageRecord> mImageList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_activity);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mImageList = new ArrayList<>();
        task2 = findViewById(R.id.task);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        Log.v("bara_","debug");

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseReference.child("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Log.v("bara_","bara_");
                        ImageRecord post = postSnapShot.getValue(ImageRecord.class);
                        mImageList.add(post);


                    }
                    mAdapter = new RecycleViewImageRecordAdapter(GalleryActivity.this,mImageList);
                    mRecyclerView.setAdapter(mAdapter);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
