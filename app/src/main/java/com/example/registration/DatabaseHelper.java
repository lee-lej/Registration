package com.example.registration;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Manage.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHOTO = "user_photo";
    private static final String COLUMN_USER_SURNAME = "user_surname";
    //   private static final String COLUMN_USER_USER = "user_user";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_ADRESS = "user_adress";
    private static final String COLUMN_USER_CEL = "user_cel";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+  COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_SURNAME + " TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT, "+ COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_ADRESS +
            " TEXT, " + COLUMN_USER_CEL   + " TEXT " + ")";





    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_SURNAME, user.getSurname());
        //   values.put(COLUMN_USER_USER, user.getUser());

        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_ADRESS, user.getAdress());
        values.put(COLUMN_USER_CEL, user.getCel());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }





    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SURNAME,
                //  COLUMN_USER_USER,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_EMAIL,
                COLUMN_USER_ADRESS,
                COLUMN_USER_CEL
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query( TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));

                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSurname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SURNAME)));
                //     user.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USER)));

                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setAdress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADRESS)));
                user.setCel(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CEL)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }




    @SuppressLint("Range")
    public List<User> getUserProfil(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SURNAME,
                //    COLUMN_USER_USER,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_EMAIL,
                COLUMN_USER_ADRESS,
                COLUMN_USER_CEL
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query( TABLE_USER, //Table to query
                columns,    //columns to return
                "user_email=?",        //columns for the WHERE clause
                new String[]{email},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));

                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSurname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SURNAME)));
                //     user.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USER)));

                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setAdress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADRESS)));
                user.setCel(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CEL)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    //method to updateUser
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_SURNAME, user.getSurname());
        //   values.put(COLUMN_USER_USER, user.getUser());

        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_ADRESS, user.getAdress());
        values.put(COLUMN_USER_CEL, user.getCel());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }



    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }



    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,
                null
        );                    //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'indritsaveta@ascaldera.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
