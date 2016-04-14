package kmutt.senior.pet.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import kmutt.senior.pet.model.DogProfile;

/**
 * Created by last3oy on 11/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //DB Name
    private static final String DATABASE_NAME = "lanaDB";

    //DB Version
    private static final int DATABASE_VERSION = 1;

    //Table Name
    private static final String TABLE_BPM = "bpm";
    private static final String TABLE_PROFILE = "profile";

    //PROFILE Table Column Name
    private static final String KEY_ID = "id";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_NAME = "name";
    private static final String KEY_BREED = "breed";
    private static final String KEY_SIZE = "size";
    private static final String KEY_AGE = "age";

    //BPM Table Column Name
    private static final String KEY_DATE = "date";
    private static final String KEY_BPM = "bpm";


    //PROFILE Table Create
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_PICTURE + " BLOB," +
            KEY_NAME + " TEXT," +
            KEY_BREED + " TEXT," +
            KEY_SIZE + " TEXT," +
            KEY_AGE + " INTEGER" + ")";

    //BPM Table Create
    private static final String CREATE_TABLE_BPM = "CREATE TABLE "
            + TABLE_BPM + "(" +
            KEY_DATE + " DATETIME PRIMARY KEY," +
            KEY_BPM + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILE);
        //db.execSQL(CREATE_TABLE_BPM);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BPM);
        onCreate(db);
    }

    //insert----
    public long createProfile(DogProfile dogProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTURE, dogProfile.getPicture());
        values.put(KEY_NAME, dogProfile.getDogName());
        values.put(KEY_BREED, dogProfile.getBreed());
        values.put(KEY_SIZE, dogProfile.getSize());
        values.put(KEY_AGE, dogProfile.getAge());

        long profile_id = db.insert(TABLE_PROFILE, null, values);
        Log.i("TEST", "" + profile_id);
        return profile_id;

    }


    //select----

    public ArrayList<DogProfile> getAllProfile() {
        ArrayList<DogProfile> Profiles = new ArrayList<DogProfile>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()){
            do {
                DogProfile mDogProfile = new DogProfile();
                mDogProfile.setDogId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfile.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfile.setDogName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfile.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));
                mDogProfile.setSize(mCursor.getString(mCursor.getColumnIndex(KEY_SIZE)));
                mDogProfile.setAge(mCursor.getInt(mCursor.getColumnIndex(KEY_AGE)));

                Profiles.add(mDogProfile);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Profiles;
    }




}
