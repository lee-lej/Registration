package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    ProfileUserFargment profileUserFragment;
    FragmentManager fragmentManager;
    TextView txtUser;
    String email;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferences=getSharedPreferences("Login", Context.MODE_PRIVATE);
        //Intent intent = getIntent();
        email=sharedPreferences.getString("Email","test@gmail.com");


        profileUserFragment = new ProfileUserFargment();
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        profileUserFragment.setArguments(bundle);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,profileUserFragment).commit();


    }




}
