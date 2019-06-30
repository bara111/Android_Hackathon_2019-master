package com.example.android_hackathon_2019;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marozzi.roundbutton.RoundButton;


public class Authentication extends AppCompatActivity {
    private RoundButton mSignIn, mSignUp;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mEmail, mPassword;
    private ImageView mImage;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    private DatabaseReference newPostRef, postsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mSignIn = findViewById(R.id.Sign_in);
        mSignUp = findViewById(R.id.Sign_up);
        mImage=findViewById(R.id.image);
        postsRef = myRef.child("posts");
        mAuth = FirebaseAuth.getInstance();



// We can also chain the two calls together
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sign_in();
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }

    public void RegisterUser() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter correct email", Toast.LENGTH_SHORT);
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT);
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(null, "createUserWithEmail:success");
                            mUser = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(null, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Authentication.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void Sign_in() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter correct email", Toast.LENGTH_SHORT);
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(null, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(Authentication.this, MainActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(null, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Authentication.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }



    }


}
