package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class editprofile extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnUpdateProfile;
    private int userID;
    private ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        //attaching variables to layout views
        etUsername = findViewById(R.id.edit_username);
        etEmail = findViewById(R.id.edit_email);
        etPassword = findViewById(R.id.edit_password);
        btnUpdateProfile = findViewById(R.id.edit_profile);
        arrowBack = findViewById(R.id.arrow);

        //Retrieve the userID from extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //listener for submitting new credentials
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //collecting all the data from the edit text boxes
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Update the user details
                PostManager userManager = new PostManager(editprofile.this);
                SQLiteDatabase db = userManager.connectToDb();
                userManager.editUser(db, new User(userID, username, email, password));

                Intent intent = new Intent(editprofile.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

                //display message to let the user know the profile credentials were updated
                Toast.makeText(editprofile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        //arrow button listener, goes back to profile activity
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editprofile.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}