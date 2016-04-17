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
    private static final String DATABASE_NAME = "mydataaa";
    private static final String TABLE_MEMBER = "pulaa";

    public dataActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

// TODO Auto-generated constructor stub

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MEMBER +
                "(Name TEXT(20)NOT NULL," +
                "Date TEXT(20) NOT NULL," +
                "Time TEXT(20) NOT NULL ," +
                "Pulse INTEGER(20) NOT NULL ," +
                "PRIMARY KEY (Date, Time))");

        Log.d("CREATE TABLE", "Create Table Successfully.");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long InsertData(String strName, String strDate, String strTIME, Integer intpulse) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data



            ContentValues Val = new ContentValues();
            Val.put("Name", strName);
            Val.put("Date", strDate);
            Val.put("Time", strTIME);
            Val.put("Pulse", intpulse);


            long rows = db.insert(TABLE_MEMBER, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }
    }
    public List<Getdata> SelectAllData(String sss) {
        // TODO Auto-generated method stub

        try {
            List<Getdata> MemberList = new ArrayList<Getdata>();

            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Read Data

            Cursor cursor;
            //Log.d("sad",sss);
            String strSQL = "SELECT  * FROM " + TABLE_MEMBER + " WHERE Date = '"+ sss+"'";


            cursor = db.rawQuery(strSQL, null);

            //Log.d("sad",""+cursor.moveToFirst());
            //Log.d("sad",strSQL);
           // int count = cursor.getCount();

            /*String[] Time = new String[count];
            int[] Pulse = new int[count];
            for (int m = 0; m < count; m++) {
                cursor.moveToNext();
                Time[m] = cursor.getString(0);

                Pulse[m] = cursor.getInt(1);
            }*/
            if(cursor != null)
            {
                //Log.d("sad","sad");

                //Log.d("sad",""+cursor.moveToFirst());
                if (cursor.moveToFirst()) {
                    do {
                        Getdata cMember = new Getdata();
                        cMember.sName(cursor.getString(0));
                        cMember.sDate(cursor.getString(1));
                        cMember.sTime(cursor.getString(2));
                        cMember.sPulse(cursor.getInt(3));
                        Log.d("sad",""+MemberList);
                        MemberList.add(cMember);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

            //Log.d("sad",""+cursor.isFirst());
            db.close();
            return MemberList;
        }
         catch (Exception e) {
                return null;
            }

    }
}
