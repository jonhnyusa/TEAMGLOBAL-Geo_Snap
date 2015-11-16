package edu.ltu.ase.projec2.teamglobal_geo_snap;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Jonhny on 10/19/2015.
 */

public class GEOSNAP_DatabaseHandler extends SQLiteOpenHelper {
    // Database Initialization
    private static final String DATABASE_NAME = "GeoSnapDatabase";
    private static final int DATABASE_VERSION = 1;

    // Tables

    private static final String TABLE_USERS = "users";

    // Columns

    // User Table Columns
    private static final String USER_ID = "ID";
    private static final String USER_USERID = "USERID";
    private static final String USER_NAME = "NAME";
    private static final String USER_PASSWORD = "NAME";
    private static final String USER_EMAIL = "NAME";
    private static final String USER_GENDER = "NAME";
    private static final String USER_BIRTHDAY = "NAME";
    private static final String USER_DESCRIPTION = "NAME";

    private static GEOSNAP_DatabaseHandler sInstance;


    // Reference from  (See this article for more information: http://bit.ly/6LRzfx)// Avoid leak an Activity's context.
    public static synchronized GEOSNAP_DatabaseHandler getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new GEOSNAP_DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     GEOSNAP_DatabaseHandler Constructor
     */
    private GEOSNAP_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Initial Configuration of database
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //db.setForeignKeyConstraintsEnabled(true);
    }

    // Database created for the FIRST time.
    // When database already exists on disk with the same DATABASE_NAME ommiteed the called to this method
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                USER_ID + " INTEGER PRIMARY KEY NOT NULL," +
                USER_USERID +              " TEXT NOT NULL," +
                USER_PASSWORD +           " TEXT NOT NULL" +
                USER_NAME +              " TEXT NOT NULL," +
                USER_EMAIL +                       " TEXT" +
                USER_GENDER +                     " TEXT," +
                USER_BIRTHDAY +                    " REAL" +
                USER_DESCRIPTION +                 " TEXT" +
                ")";

         db.execSQL(CREATE_USERS_TABLE);
    }

    // Called when the database needs to be upgraded.(If necessary)
    // Uodate the version of the database in the device if the version encountered is different from the one current
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Delete tables
             db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }




    /* Insert or update a user in the database Ref from (https://code.google.com/p/android/issues/detail?id=13045)
    */
    public int findUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        int count = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USER_USERID, user.UserID);
            values.put(USER_PASSWORD, user.Password);


            // Get the primary key of the user we just updated
            String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",USER_ID, TABLE_USERS, USER_USERID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.UserID)});
                try {
                    if (cursor.moveToFirst()) {
                        count=cursor.getCount();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }

        } catch (Exception e) {
            Log.d("Find User:", "Error while trying to find userid");
        } finally {
            db.endTransaction();
        }
        return count;
    }

    /* Insert or update a user in the database Ref from (https://code.google.com/p/android/issues/detail?id=13045)
    */
    public long addorUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USER_USERID, user.UserID);
            values.put(USER_NAME, user.Name);
            values.put(USER_PASSWORD, user.Password);
            values.put(USER_EMAIL, user.Email);
            values.put(USER_GENDER, user.Gender);
            values.put(USER_BIRTHDAY, user.Birthday);
            values.put(USER_DESCRIPTION, user.Description);

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USERS, values, USER_USERID + "= ?", new String[]{user.UserID});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        USER_ID, TABLE_USERS, USER_USERID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.UserID)});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("Insert or Update:", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }


    // Update the user's profile picture url
    public int updateUserProfilePicture(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.Name);
        values.put(USER_PASSWORD, user.Password);
        values.put(USER_EMAIL, user.Email);
        values.put(USER_GENDER, user.Gender);
        values.put(USER_BIRTHDAY, user.Birthday);
        values.put(USER_DESCRIPTION, user.Description);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_USERS, values, USER_USERID + " = ?",
                new String[] { String.valueOf(user.UserID) });
    }

    // Delete all tables from the database
    public void deleteAllPostsAndUsers() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {


            db.delete(TABLE_USERS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Delete all records:", "Error while trying to delete all records");
        } finally {
            db.endTransaction();
        }
    }
}