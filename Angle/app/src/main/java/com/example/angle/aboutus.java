package com.example.angle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class aboutus extends AppCompatActivity {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        //connecting all the variagbles to their respective layout views
        ImageView newsImageView = findViewById(R.id.news);
        ImageView postImageView = findViewById(R.id.post);
        ImageView profileImageView = findViewById(R.id.profile);

        //getting extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //on click listener for news image that leads to homepage activity
        newsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aboutus.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //on click listener for the post image that leads to makeapost activity
        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aboutus.this, makeapost.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //on click listener for profile image that leads to profile activity
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aboutus.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
