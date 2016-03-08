package kmutt.senior.pet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import kmutt.senior.pet.R;

public class WelcomeActivity extends AppCompatActivity {

    /*private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /*CircleImageView t = (CircleImageView)findViewById(R.id.picture);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IntroActivity.this,
                        "The favorite list would appear on clicking this icon",
                        Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        MaterialBetterSpinner  textView = (MaterialBetterSpinner)
                findViewById(R.id.spinner);

        textView.setAdapter(adapter);*/
    }




}
