package kmutt.senior.pet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import kmutt.senior.pet.R;


public class textActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        TextView  mText = (TextView)findViewById(R.id.randomTextView);
        try {
            InputStream is = getAssets().open(String.valueOf(R.raw.text));

            int size = is.available();
            // create buffer for IO
            byte[] buffer = new byte[size];
            // get data to buffer
            is.read(buffer);
            // close stream
            is.close();
            // set result to TextView
            mText.setText(is.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String path = ".../res/raw/text.txt";
        File file = new File(path);
        ArrayList<String> myArr = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                myArr.add(line);
            }
            br.close();
            file = null;

            final ListView lisView1 = (ListView) findViewById(R.id.listView1);
            String[] myData = {};
            myData = myArr.toArray(new String[myArr.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myData);
            lisView1.setAdapter(adapter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

    }


}
