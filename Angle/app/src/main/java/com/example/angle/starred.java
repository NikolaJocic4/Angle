package com.example.angle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class starred extends AppCompatActivity {

    private ImageView arrowBack;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starred);

        //connecting variables to their layout views
        ListView lv=findViewById(R.id.lv);
        arrowBack = findViewById(R.id.arrow);

        //getting extras
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");

        //opening database
        PostManager postManager=new PostManager(starred.this);
        SQLiteDatabase db = postManager.connectToDb();

        //setting up the cursor adapter
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(starred.this,
                R.layout.postslistlayout,postManager.getAssignedPosts(db, userID),
                new String[]{"title"},new  int[]{R.id.post_headline});
        lv.setAdapter(simpleCursorAdapter);

        //adding the item listener for the list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor)lv.getItemAtPosition(position);
                Intent intent=new Intent(starred.this,postreview.class);
                intent.putExtra("_id",cursor.getInt(cursor.getColumnIndex("_id")));
                intent.putExtra("title",cursor.getString(cursor.getColumnIndex("title")));
                intent.putExtra("body",cursor.getString(cursor.getColumnIndex("body")));
                intent.putExtra("category",cursor.getString(cursor.getColumnIndex("category")));
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //arrow-button on-click-listener, leads back to the profile activity
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(starred.this, profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}

