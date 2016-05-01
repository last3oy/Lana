package kmutt.senior.pet.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;

import kmutt.senior.pet.model.BpmValue;
import kmutt.senior.pet.model.DogNameId;
import kmutt.senior.pet.model.DogProfileInput;
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
            KEY_DATE + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME'))," +
            KEY_ID_DOG + " INTEGER," +
            KEY_BPM + " INTEGER," +
            "PRIMARY KEY (" + KEY_DATE + "," + KEY_ID_DOG + "))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_GENDER);
        db.execSQL(CREATE_TABLE_SIZE);
        db.execSQL(CREATE_TABLE_BPM);

        //Insert default values
        db.execSQL("INSERT INTO " +
                TABLE_GENDER + "(" +
                KEY_GENDER + ") VALUES(" +
                "'Male'),('Female')");
        db.execSQL("INSERT INTO " +
                TABLE_SIZE + "(" +
                KEY_SIZE + ") VALUES(" +
                "'Small (<13 Kg)'),('Medium-Large (13-40 Kg)'),('X-Large (>40 Kg)')");

        db.execSQL("INSERT INTO " +
                TABLE_BPM + "(" +
                KEY_ID_DOG + "," +
                KEY_BPM + ") VALUES" +
                "(1,124)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BPM);

        onCreate(db);
    }

    /**
     * Insert
     */
    public long createProfile(DogProfileInput dogProfileInput) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTURE, dogProfileInput.getPicture());
        values.put(KEY_NAME, dogProfileInput.getDogName());
        values.put(KEY_ID_GENDER, dogProfileInput.getIdDogGender());
        values.put(KEY_BREED, dogProfileInput.getBreed());
        values.put(KEY_ID_SIZE, dogProfileInput.getIdSize());
        values.put(KEY_AGE, dogProfileInput.getAge());

        long profile_id = db.insert(TABLE_PROFILE, null, values);
        Log.i("Insert", "" + profile_id);
        db.close();
        return profile_id;

    }

    public long createBpmvalue(int dogId, int value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_DOG, dogId);
        values.put(KEY_BPM, value);


        long profile_id = db.insert(TABLE_BPM, null, values);
        db.close();
        return profile_id;
    }


    /**
     * Select
     */


    public ArrayList<BpmValue> getbpm(int id, String date) {
        ArrayList<BpmValue> values = new ArrayList<BpmValue>();
        String selectQuery = "SELECT * FROM " +
                TABLE_BPM + " WHERE " +
                KEY_ID_DOG + " = " + id + " and " +
                KEY_DATE + " LIKE '%" + date + "%'";
        String startTime;
        String time;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {
            do {
                BpmValue mBpmValue = new BpmValue();
                startTime = mCursor.getString(mCursor.getColumnIndex(KEY_DATE));
                StringTokenizer tk = new StringTokenizer(startTime);
                tk.nextToken();

                time = tk.nextToken();

                mBpmValue.setDate(time);
                mBpmValue.setBpm(mCursor.getInt(mCursor.getColumnIndex(KEY_BPM)));
                values.add(mBpmValue);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return values;
    }

    public ArrayList<DogProfileInput> getListSelectProfile() {
        ArrayList<DogProfileInput> Profiles = new ArrayList<DogProfileInput>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);
        DogProfileInput mDogProfileInput;

        if (mCursor.moveToFirst()) {
            do {
                mDogProfileInput = new DogProfileInput();
                mDogProfileInput.setDogId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfileInput.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfileInput.setDogName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfileInput.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));

                Profiles.add(mDogProfileInput);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Profiles;
    }

    public ArrayList<DogNameId> getNameProfile() {
        ArrayList<DogNameId> mNameId = new ArrayList<DogNameId>();
        String selectQuery = "SELECT " + KEY_NAME + "," + KEY_ID + " FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);
        DogNameId mDogNameId;
        if (mCursor.moveToFirst()) {
            do {
                mDogNameId = new DogNameId();
                mDogNameId.setName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogNameId.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));

                mNameId.add(mDogNameId);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return mNameId;
    }


    public DogProfile getDogProfile(int id) {
        DogProfile mDogProfile = new DogProfile();
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
                mDogProfile.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                mDogProfile.setPicture(mCursor.getBlob(mCursor.getColumnIndex(KEY_PICTURE)));
                mDogProfile.setName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mDogProfile.setGender(mCursor.getString(mCursor.getColumnIndex(KEY_GENDER)));
                mDogProfile.setBreed(mCursor.getString(mCursor.getColumnIndex(KEY_BREED)));
                mDogProfile.setSize(mCursor.getString(mCursor.getColumnIndex(KEY_SIZE)));
                mDogProfile.setAge(mCursor.getInt(mCursor.getColumnIndex(KEY_AGE)));

            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return mDogProfile;
    }

    /**
     * Delete
     */
    public int deleteProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String deleteQuery = KEY_ID + " = " + id;
        return db.delete(TABLE_PROFILE, deleteQuery, null);
    }

    /**
     * Update
     */
    public int updateProfile(DogProfileInput dogProfileInput) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTURE, dogProfileInput.getPicture());
        values.put(KEY_NAME, dogProfileInput.getDogName());
        values.put(KEY_ID_GENDER, dogProfileInput.getIdDogGender());
        values.put(KEY_BREED, dogProfileInput.getBreed());
        values.put(KEY_ID_SIZE, dogProfileInput.getIdSize());
        values.put(KEY_AGE, dogProfileInput.getAge());

        int profile_id = db.update(TABLE_PROFILE, values, KEY_ID + "=" + dogProfileInput.getDogId(), null);

        db.close();
        return profile_id;


    }
}
