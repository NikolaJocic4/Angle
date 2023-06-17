package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class postreview extends AppCompatActivity {

    private TextView headlineTextView;
    private TextView bodyTextView;
    private Button unstarButton;
    private int userID;

    private PostManager postManager;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postreview);

        //connecting variables to layout views
        headlineTextView = findViewById(R.id.headline);
        bodyTextView = findViewById(R.id.body);
        unstarButton = findViewById(R.id.bt_unstar);
        TextView xTextView = findViewById(R.id.x);
        ImageView newsImage = findViewById(R.id.news);
        ImageView postImage = findViewById(R.id.post);
        ImageView profileImage = findViewById(R.id.profile);

        //opening database
        postManager = new PostManager(postreview.this);
        db = postManager.connectToDb();

        //Fetching extras
        Bundle extras = getIntent().getExtras();
        headlineTextView.setText(extras.getString("title"));
        userID = extras.getInt("userID");
        bodyTextView.setText(extras.getString("body"));

        unstarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Assign the post to the user
                postManager.removeStarredPost(db, userID, extras.getInt("_id"));

                //Start the Homepage activity
                Intent intent = new Intent(postreview.this, starred.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //on click listener for the 'X' image
        xTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(postreview.this, starred.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //on click listener for the news image, leads to homepage activity
        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(postreview.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //post image on click listener goes to post activity
        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(postreview.this, makeapost.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //profile image on click listener goes to profile activity
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(postreview.this, profile.class);
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
