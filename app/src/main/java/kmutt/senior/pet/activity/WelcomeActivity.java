package kmutt.senior.pet.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import kmutt.senior.pet.R;


public class WelcomeActivity extends AppCompatActivity {

    Button btnPair;
    Button btnDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initInstances();

    }

    private void initInstances() {
        btnDog = (Button) findViewById(R.id.btnDog);
        btnPair = (Button) findViewById(R.id.btnPair);


        btnDog.setOnClickListener(buttonClicked);
        btnPair.setOnClickListener(buttonClicked);
    }

    View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnDog) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (v == btnPair) {
                Intent intent = new Intent(WelcomeActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }

        }
    };


}
