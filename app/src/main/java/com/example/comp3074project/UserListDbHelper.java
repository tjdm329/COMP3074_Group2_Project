package com.example.comp3074project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RestaurantsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "userList";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_TAGS = "tags";
    private static final String COLUMN_DESC = "description";


    public UserListDbHelper(
            @Nullable Context context
    ) {
        //CURSOR factory is when you are using your own custom cursor factory
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(200) NOT NULL," +
                COLUMN_ADDRESS + " VARCHAR(200)," +
                COLUMN_PHONE + " VARCHAR(200)," +
                COLUMN_RATING + " REAL," +
                COLUMN_TAGS + " VARCHAR(200)," +
                COLUMN_DESC + " VARCHAR(200))"
                ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade is needed for the migration
        /* we just, for this demo, drop the table and recreate it */
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    /* CRUD METHODS */

    boolean addToUserList(Restaurant r) {
        // we need a writeable instance of db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //we need content value instance to write into DB
        ContentValues cv = new ContentValues();
        //we are passing the table names as keys to the CV content value
        cv.put(COLUMN_NAME, r.name);
        cv.put(COLUMN_ADDRESS, r.address);
        cv.put(COLUMN_PHONE, r.phone);
        cv.put(COLUMN_RATING, r.rating);
        cv.put(COLUMN_TAGS, r.tags);
        cv.put(COLUMN_DESC, r.description);

        //the insert method in sqlite returns the number of rows affected (inserted)
        // if the transaction is successful we get -1
        return sqLiteDatabase.insert(TABLE_NAME, null , cv)  != -1 ;
    }

    //get users list of restaurants
    public List<Restaurant> getUserList(){
        List<Restaurant> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //Query all rows
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow("tags"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                Restaurant r = new Restaurant(id, name, address, phone, rating, tags, description);
                userList.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return userList;
    }

    //update
    boolean updateUserRestaurant(Restaurant r) {
        //writeable instance
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, r.getName());
        cv.put(COLUMN_ADDRESS, r.getAddress());
        cv.put(COLUMN_PHONE, r.getPhone());
        cv.put(COLUMN_RATING, r.getRating());
        cv.put(COLUMN_TAGS, r.getTags());
        cv.put(COLUMN_DESC, r.getDescription());

        //the update method returns the number of rows affected
        return sqLiteDatabase.update(
                TABLE_NAME,
                cv,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(r.getId())}
        ) > 0 ;
    }

    //delete
    boolean deleteUserEntry(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //returns the number of rows affected
        return sqLiteDatabase.delete(
                TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        ) > 0 ;
    }

    boolean deleteAllUserEntries() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(
                TABLE_NAME,
                null,
                null
        ) > 0 ;
    }
}
