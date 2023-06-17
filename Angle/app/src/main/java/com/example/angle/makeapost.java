

package com.example.angle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class makeapost extends AppCompatActivity {

    private EditText edtTitle, edtBody;
    private Spinner spinnerCategory;
    private Button btnPost;
    private ImageView imgNews, imgProfile;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeapost);

        //connecting variables with layout views
        edtTitle = findViewById(R.id.write_headline);
        edtBody = findViewById(R.id.write_story);
        spinnerCategory = findViewById(R.id.category);
        btnPost = findViewById(R.id.bt_post);
        imgNews = findViewById(R.id.news);
        imgProfile = findViewById(R.id.profile);

        //getting extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //connecting to database
        PostManager postManager = new PostManager(this);
        SQLiteDatabase db = postManager.connectToDb();

        // Set onclick listener for Post button
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting information from the fields
                String title = edtTitle.getText().toString();
                String body = edtBody.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                // Add post to the database
                postManager.addPost(db, new Post(1, title, body, category));

                Intent intent = new Intent(makeapost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //Set onclick listener for 'x' text, leads back to homepage
        findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(makeapost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //Set onclick listener for news image, leads to homepage
        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(makeapost.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //Set onclick listener for profile image, leads to profile
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(makeapost.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
