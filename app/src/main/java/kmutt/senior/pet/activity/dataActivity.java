package kmutt.senior.pet.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class dataActivity extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase";
    private static final String TABLE_MEMBER = "pulse";

    public dataActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

// TODO Auto-generated constructor stub

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMBER +

                "(Time TEXT(10) PRIMARY KEY," +

                " Pulse INTEGER)");

        Log.d("CREATE TABLE", "Create Table Successfully.");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long InsertData(String strTIME, Integer intpulse) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data



            ContentValues Val = new ContentValues();
            Val.put("Time", strTIME);
            Val.put("Pulse", intpulse);


            long rows = db.insert(TABLE_MEMBER, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }
    }
    public List<Getdata> SelectAllData() {
        // TODO Auto-generated method stub

        try {
            List<Getdata> MemberList = new ArrayList<Getdata>();

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + TABLE_MEMBER;
            Cursor cursor = db.rawQuery(strSQL, null);

            int count = cursor.getCount();

            /*String[] Time = new String[count];
            int[] Pulse = new int[count];
            for (int m = 0; m < count; m++) {
                cursor.moveToNext();
                Time[m] = cursor.getString(0);

                Pulse[m] = cursor.getInt(1);
            }*/
            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    do {
                        Getdata cMember = new Getdata();
                        cMember.sTime(cursor.getString(0));
                        cMember.sPulse(cursor.getInt(1));

                        MemberList.add(cMember);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MemberList;
        }
         catch (Exception e) {
            return null;
        }

    }
}
