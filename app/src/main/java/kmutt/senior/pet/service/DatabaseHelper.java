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
import kmutt.senior.pet.model.DogProfileDTO;

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
    private static final String TABLE_GENDER = "gender";
    private static final String TABLE_SIZE = "size";

    //Column Name ID
    private static final String KEY_ID = "id";

    //PROFILE Table Column Name
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID_GENDER = "idgender";
    private static final String KEY_BREED = "breed";
    private static final String KEY_ID_SIZE = "idsize";
    private static final String KEY_AGE = "age";

    //GENDER Table Column Name
    private static final String KEY_GENDER = "gender";

    //SIZE Table Column Name
    private static final String KEY_SIZE = "size";

    //BPM Table Column Name
    private static final String KEY_DATE = "date";
    private static final String KEY_ID_DOG = "iddog";
    private static final String KEY_BPM = "bpm";


    //PROFILE Table Create
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE " +
            TABLE_PROFILE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_PICTURE + " BLOB," +
            KEY_NAME + " TEXT," +
            KEY_ID_GENDER + " INTEGER," +
            KEY_BREED + " TEXT," +
            KEY_ID_SIZE + " INT," +
            KEY_AGE + " INTEGER" + ")";

    //GENDER Table Create
    private static final String CREATE_TABLE_GENDER = "CREATE TABLE " +
            TABLE_GENDER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_GENDER + " TEXT" + ")";

    //SIZE Table Create
    private static final String CREATE_TABLE_SIZE = "CREATE TABLE " +
            TABLE_SIZE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_SIZE + " TEXT" + ")";


    //BPM Table Create
    private static final String CREATE_TABLE_BPM = "CREATE TABLE "
            + TABLE_BPM + "(" +
            KEY_DATE + " DATETIME PRIMARY KEY," +
            KEY_ID_DOG + " INTEGER," +
            KEY_BPM + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_GENDER);
        db.execSQL(CREATE_TABLE_SIZE);
        //db.execSQL(CREATE_TABLE_BPM);

        //Insert default values
        db.execSQL("INSERT INTO " +
                TABLE_GENDER + "(" +
                KEY_GENDER + ") VALUES(" +
                "'Male'),('Female')");
        db.execSQL("INSERT INTO " +
                TABLE_SIZE + "(" +
                KEY_SIZE + ") VALUES(" +
                "'Small'),('Medium'),('Large')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BPM);

        onCreate(db);
    }

    //insert----
    public long createProfile(DogProfile dogProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTURE, dogProfile.getPicture());
        values.put(KEY_NAME, dogProfile.getDogName());
        values.put(KEY_ID_GENDER, dogProfile.getIdDogGender());
        values.put(KEY_BREED, dogProfile.getBreed());
        values.put(KEY_ID_SIZE, dogProfile.getIdSize());
        values.put(KEY_AGE, dogProfile.getAge());

        long profile_id = db.insert(TABLE_PROFILE, null, values);
        Log.i("Insert", "" + profile_id);
        db.close();
        return profile_id;

    }


    //select----

  /*  public ArrayList<DogProfile> getProfile(int id) {
        ArrayList<DogProfile> Profiles = new ArrayList<DogProfile>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE + " WHERE = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {
            do {
                DogProfile mDogProfile = new DogProfile();
                mDogProfile.setDogId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfile.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfile.setDogName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfile.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));
                mDogProfile.setIdSize(mCursor.getInt(mCursor.getColumnIndex(KEY_ID_SIZE)));
                mDogProfile.setAge(mCursor.getInt(mCursor.getColumnIndex(KEY_AGE)));

                Profiles.add(mDogProfile);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Profiles;
    }*/

    public ArrayList<DogProfile> getListSelectProfile() {
        ArrayList<DogProfile> Profiles = new ArrayList<DogProfile>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {
            do {
                DogProfile mDogProfile = new DogProfile();
                mDogProfile.setDogId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfile.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfile.setDogName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfile.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));

                Profiles.add(mDogProfile);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Profiles;
    }

    public ArrayList<String> getNameProfile() {
        ArrayList<String> mName = new ArrayList<String>();
        String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);
        String name;
        if (mCursor.moveToFirst()) {
            do {

                name = mCursor.getString(mCursor.getColumnIndex(KEY_NAME));

                mName.add(name);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return mName;
    }


    public DogProfileDTO getDogProfile(int id) {
        DogProfileDTO mDogProfileDTO = new DogProfileDTO();
        String selectQuery =
                "SELECT " + TABLE_PROFILE + "." + KEY_ID + "," + TABLE_PROFILE + "." + KEY_PICTURE + "," + TABLE_PROFILE + "." + KEY_NAME + ","
                        + TABLE_GENDER + "." + KEY_GENDER + "," + TABLE_PROFILE + "." + KEY_BREED + "," + TABLE_SIZE + "." + KEY_SIZE + ","
                        + TABLE_PROFILE + "." + KEY_AGE + "" +
                        " FROM " + TABLE_PROFILE + "," + TABLE_GENDER + "," + TABLE_SIZE +
                        " WHERE " + TABLE_PROFILE + "." + KEY_ID_GENDER + " = " + TABLE_GENDER + "." + KEY_ID + " AND " +
                        TABLE_PROFILE + "." + KEY_ID_SIZE + " = " + TABLE_SIZE + "." + KEY_ID + " AND " +
                        TABLE_PROFILE + "." + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {
            do {
                mDogProfileDTO.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfileDTO.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfileDTO.setName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfileDTO.setGender(mCursor.getString(mCursor.getColumnIndex(KEY_GENDER)));
                mDogProfileDTO.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));
                mDogProfileDTO.setSize(mCursor.getString(mCursor.getColumnIndex(KEY_SIZE)));
                mDogProfileDTO.setAge(mCursor.getInt(mCursor.getColumnIndex(KEY_AGE)));

            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return mDogProfileDTO;
    }


}
