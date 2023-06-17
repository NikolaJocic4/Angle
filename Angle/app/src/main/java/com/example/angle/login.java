package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    private PostManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Initialize database manager
        userManager = new PostManager(this);
        SQLiteDatabase db = userManager.connectToDb();

        //connect variables to layout views
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login);
        registerTextView = findViewById(R.id.register_text);

        //Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //Check if username and password exist in the database
                if (checkCredentials(db, username, password)) {

                    int userID= userManager.getUserID(db, username, password);

                    // Valid credentials, navigate to homepage
                    Intent intent = new Intent(login.this, homepage.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    finish(); // Optional: Finish the login activity to prevent going back
                } else {
                    // Invalid credentials, show a toast message
                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for register text
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }

    //method to check whether the input data is correct/storred inside the database
    private boolean checkCredentials(SQLiteDatabase db, String username, String password) {
        String[] columns = {"_id"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}

