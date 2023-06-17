package com.example.angle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class homepage extends AppCompatActivity {

    private int userID;
    private String selectedCategory = "All";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //setting up the gps variables
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Handle location updates here
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //Do something with the latitude and longitude values
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        //connecting variables to layout views
        ListView lv=findViewById(R.id.lv);
        Spinner categorySpinner = findViewById(R.id.sort);
        ImageView postImageView = findViewById(R.id.post);
        ImageView profileImageView = findViewById(R.id.profile);

        //getting extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //opening database
        PostManager postManager=new PostManager(homepage.this);
        SQLiteDatabase db = postManager.connectToDb();

        //populating the cursor adapter
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(homepage.this,
                R.layout.postslistlayout,postManager.getPosts(db),
                new String[]{"title"},new  int[]{R.id.post_headline});
        lv.setAdapter(simpleCursorAdapter);

        //setting up the item on click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor)lv.getItemAtPosition(position);
                Intent intent=new Intent(homepage.this,enterpost.class);
                intent.putExtra("_id",cursor.getInt(cursor.getColumnIndex("_id")));
                intent.putExtra("title",cursor.getString(cursor.getColumnIndex("title")));
                intent.putExtra("body",cursor.getString(cursor.getColumnIndex("body")));
                intent.putExtra("category",cursor.getString(cursor.getColumnIndex("category")));
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //on selected item click listener for spinner
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categorySpinner.getSelectedItem().toString();
                refreshListView(selectedCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //post image listener goes the post activity
        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, makeapost.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //profile image listener goes the profile activity
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    //methods for the gps sensor
    private void refreshListView(String selectedCategory) {
        PostManager postManager = new PostManager(homepage.this);
        SQLiteDatabase db = postManager.connectToDb();
        Cursor cursor;
        if (selectedCategory.equals("All")) {
            cursor = postManager.getPosts(db);
        } else {
            cursor = postManager.getPostsByCategory(db, selectedCategory);
        }
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(homepage.this,
                R.layout.postslistlayout, cursor,
                new String[]{"title"}, new int[]{R.id.post_headline});
        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(simpleCursorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check if the user granted the location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Request location updates every 1 second and 10 meters of distance change
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
        } else {
            //Request the location permission from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted, start requesting location updates
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

}

