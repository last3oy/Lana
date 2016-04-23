package kmutt.senior.pet.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
        db.execSQL(CREATE_TABLE_BPM);

        //Insert default values
        db.execSQL("INSERT INTO " +
                TABLE_GENDER + "(" +
                KEY_GENDER + ") VALUES(" +
                "'Male'),('Female')");
        db.execSQL("INSERT INTO " +
                TABLE_SIZE + "(" +
                KEY_SIZE + ") VALUES(" +
                "'Small'),('Medium'),('Large')");
        db.execSQL("INSERT INTO " +
                        TABLE_BPM + "(" +
                        KEY_DATE + "," + KEY_ID_DOG + "," + KEY_BPM + ") VALUES" +
                        "(" + "'2016-04-01 09:00:00','1','160')" +
                        ",(" + "'2016-04-01 10:00:00','1','170')" +
                        ",(" + "'2016-04-01 11:00:00','1','140')" +
                        ",(" + "'2016-04-01 12:00:00','1','155')" +
                        ",(" + "'2016-04-01 13:00:00','1','178')" +
                        ",(" + "'2016-04-01 14:00:00','1','156')" +
                        ",(" + "'2016-04-01 15:00:00','1','167')" +
                        ",(" + "'2016-04-01 16:00:00','1','189')" +
                        ",(" + "'2016-04-01 17:00:00','1','134')" +
                        ",(" + "'2016-04-03 09:00:00','1','156')" +
                        ",(" + "'2016-04-03 10:00:00','1','165')" +
                        ",(" + "'2016-04-03 11:00:00','1','166')" +
                        ",(" + "'2016-04-03 12:00:00','1','168')" +
                        ",(" + "'2016-04-03 13:00:00','1','177')" +
                        ",(" + "'2016-04-03 14:00:00','1','186')" +
                        ",(" + "'2016-04-06 09:00:00','1','164')" +
                        ",(" + "'2016-04-06 10:00:00','2','155')"
        );


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

    public ArrayList<DogProfileDTO> getbpm(int id, String datee) throws ParseException {
        ArrayList<DogProfileDTO> Profiles = new ArrayList<DogProfileDTO>();
        String selectQuery = "SELECT * FROM " + TABLE_BPM + " WHERE "+KEY_ID_DOG + " = " + id +" and "+KEY_DATE+ " LIKE '%"+datee+"%'";
        String startTime,n = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {
            do {
                DogProfileDTO mDogProfile = new DogProfileDTO();
                startTime = mCursor.getString(mCursor.getColumnIndex(KEY_DATE));
                StringTokenizer tk = new StringTokenizer(startTime);
                String date = tk.nextToken();
                String time = tk.nextToken();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                time = sdf.format(time);
                mDogProfile.setdate(time);
                mDogProfile.setBpm(mCursor.getInt(mCursor.getColumnIndex(KEY_BPM)));
                Profiles.add(mDogProfile);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Profiles;
    }

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
