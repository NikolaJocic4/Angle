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

public class enterpost extends AppCompatActivity {

    private TextView headlineTextView;
    private TextView bodyTextView;
    private Button addToStarredButton;
    private int userID;
    private PostManager postManager;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        //connecting variables to layout views
        headlineTextView = findViewById(R.id.headline);
        bodyTextView = findViewById(R.id.body);
        addToStarredButton = findViewById(R.id.bt_star);
        ImageView profileImage = findViewById(R.id.profile);
        ImageView postImage = findViewById(R.id.post);
        ImageView newsImage = findViewById(R.id.news);
        TextView xTextView = findViewById(R.id.x);

        //opening database
        postManager = new PostManager(enterpost.this);
        db = postManager.connectToDb();

        // Get the headline and userID from the previous activity's extras
        Bundle extras = getIntent().getExtras();
        headlineTextView.setText(extras.getString("title"));
        userID = extras.getInt("userID");
        bodyTextView.setText(extras.getString("body"));
        addToStarredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the postID based on the headline

                // Assign the post to the user
                postManager.assignPost(db, userID, extras.getInt("_id"));

                Intent intent = new Intent(enterpost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //'x' image on click listener leads back to homepage activity
        xTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(enterpost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //news image on click listener leads to homepage activity
        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(enterpost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //post image on click listener leads to makeapost activity
        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(enterpost.this, makeapost.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //profile image on click listener leads to profile activity
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(enterpost.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        postManager.close();
    }
}