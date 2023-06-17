package com.example.angle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.angle.User;

public class PostManager extends SQLiteOpenHelper {
    public PostManager(@Nullable Context context) {
        super(context, "UsersDB", null, 1);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("Sqlinfo", "oncreate called");
        db.execSQL("CREATE TABLE IF NOT EXISTS User (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " username TEXT, email TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Post (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title TEXT, body TEXT, category TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS UserPost (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " userid INTEGER, postid INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Open the database
    public SQLiteDatabase connectToDb(){
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            Log.v("Sqlinfo", "opened");
            return sqLiteDatabase;
        } catch(Exception e){
            Log.v("Sqlinfo", "error");
        }
        return sqLiteDatabase;
    }

    //query to add user entry to the User table
    public void addUser(SQLiteDatabase sqLiteDatabase, User user) {
        ContentValues userValues = new ContentValues();
        userValues.put("username", user.username);
        userValues.put("email", user.email);
        userValues.put("password", user.password);

        sqLiteDatabase.insert("User", null, userValues);
    }

    //query to update user credentials based on user input
    public void editUser(SQLiteDatabase sqLiteDatabase, User user) {
        ContentValues userValues = new ContentValues();
        userValues.put("username", user.username);
        userValues.put("email", user.email);
        userValues.put("password", user.password);

        sqLiteDatabase.update("User", userValues, "_id=" + user.id, null);
    }

    //query to add post to Post table
    public void addPost(SQLiteDatabase sqLiteDatabase, Post post) {
        ContentValues postValues = new ContentValues();
        postValues.put("title", post.title);
        postValues.put("body", post.body);
        postValues.put("category", post.category);

        sqLiteDatabase.insert("Post", null, postValues);
    }

    //query to connect the favorite post to the user, add entry to UserPost
    public void assignPost(SQLiteDatabase sqLiteDatabase, int userId, int postId) {
        ContentValues assignmentValues = new ContentValues();
        assignmentValues.put("userid", userId);
        assignmentValues.put("postid", postId);

        sqLiteDatabase.insert("UserPost", null, assignmentValues);
    }

    //query to get all the favorite posts from one specific user based on the user's id
    public Cursor getAssignedPosts(SQLiteDatabase sqLiteDatabase, int userId) {
        String rawQuery = "SELECT _id, title, body, category FROM Post " +
                "WHERE _id IN (SELECT postid FROM UserPost WHERE userid = " + userId + ")";
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.v("seeposts", cursor.getString(cursor.getColumnIndex("title")));
            cursor.moveToNext();
        }
        return cursor;
    }

    //query to get the all the posts in the cursor
    public Cursor getPosts(SQLiteDatabase sqLiteDatabase) {
        String rawQuery = "SELECT * FROM Post" ;
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.v("seeposts", cursor.getString(cursor.getColumnIndex("title")));
            cursor.moveToNext();
        }
        return cursor;
    }

    //query to get user credentials based on user id
    public User getUser(SQLiteDatabase sqLiteDatabase, int userId) {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM User WHERE _id = ?",
                new String[] { String.valueOf(userId) }
        );

        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            // Create and return a User object
            return new User(userId, username, email, password);
        }

        return null;
    }

    //query to get the user id, based on username and password input
    public int getUserID(SQLiteDatabase sqLiteDatabase, String username, String password) {
        String[] columns = { "_id" };
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = sqLiteDatabase.query("User", columns, selection, selectionArgs, null, null, null);

        int userID= -1;

        if (cursor.moveToFirst()) {
            userID =  cursor.getInt(cursor.getColumnIndex("_id"));
        }

        cursor.close();

        return userID;
    }

    //query to get the cursor of posts based on the category selected from the spinner
    public Cursor getPostsByCategory(SQLiteDatabase sqLiteDatabase, String category) {
        return sqLiteDatabase.query("Post", null, "category = ?", new String[]{category}, null, null, null);
    }

    //query to remove the favorite post from the entry of the UserPost table
    public void removeStarredPost(SQLiteDatabase sqLiteDatabase, int userId, int postId) {
        sqLiteDatabase.delete("UserPost", "userid = ? AND postid = ?", new String[]{String.valueOf(userId), String.valueOf(postId)});
    }


}
