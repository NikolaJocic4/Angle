package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView starredHeadlinesTextView;
    private TextView aboutUsTextView;
    private Button editProfileButton;
    private ImageView newsImageView;
    private ImageView postImageView;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Retrieve userID from extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //connecting variables to their layour views
        usernameTextView = findViewById(R.id.example_user);
        emailTextView = findViewById(R.id.example_email);
        starredHeadlinesTextView = findViewById(R.id.starred_headlines);
        aboutUsTextView = findViewById(R.id.about_us);
        editProfileButton = findViewById(R.id.edit_profile);
        newsImageView = findViewById(R.id.news);
        postImageView = findViewById(R.id.post);

        //opening database
        PostManager userManager = new PostManager(profile.this);
        SQLiteDatabase db = userManager.connectToDb();
        //retrieving user information
        User user = userManager.getUser(db, userID);

        //Set username and email in the TextViews
        usernameTextView.setText(user.username);
        emailTextView.setText(user.email);

        // OnClickListener for starred headlines
        starredHeadlinesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, starred.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // OnClickListener for about us
        aboutUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, aboutus.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // OnClickListener for edit profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, editprofile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // OnClickListener for news ImageView
        newsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // OnClickListener for post ImageView
        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, makeapost.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
