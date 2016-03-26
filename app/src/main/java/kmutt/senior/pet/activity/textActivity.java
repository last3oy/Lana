package kmutt.senior.pet.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import kmutt.senior.pet.R;


public class textActivity extends AppCompatActivity {
    EditText textmsg;

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Log.wtf("test","ok");
                File myInternalFile;
                String myData = "";
                String filename = "textdog.txt";
                String filepath = "Device storage";
                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
                myInternalFile = new File(directory, filename);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(myInternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                        Log.wtf("test",myData);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }




}
