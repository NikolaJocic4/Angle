package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class register extends AppCompatActivity {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //connecting variables to their layout views
        EditText emailEditText = findViewById(R.id.register_email);
        EditText usernameEditText = findViewById(R.id.register_username);
        EditText passwordEditText = findViewById(R.id.register_password);
        Button register = findViewById(R.id.register);
        ImageView arrowImageView = findViewById(R.id.arrow);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //collecting input information from text boxes
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //opening database
                PostManager userManager = new PostManager(register.this);
                SQLiteDatabase db = userManager.connectToDb();
                userManager.addUser(db, new User(1, username, email, password));

                //storing the user id into the variable to be passed as extras
                userID =  userManager.getUserID(db, username, email);

                Intent intent = new Intent(register.this, homepage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //arrow image on click listener leads back to login activity
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });

    }
}
