package com.example.angle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//THIS PAGE IS ONLY SERVED AS AN INTRO PAGE, IT HAS A TIME THAT WILL LEAD TO THE NEXT ACTIVITY
public class MainActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use a Handler to post a delayed Runnable
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the HomeActivity
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        },
                DELAY_TIME);
    }
}