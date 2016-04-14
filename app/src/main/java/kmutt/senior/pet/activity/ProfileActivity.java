package kmutt.senior.pet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.service.DbBitmapUtility;

/**
 * Created by last3oy on 14/04/2016.
 */
public class ProfileActivity extends AppCompatActivity {
    DogProfile mProfile;
    TextView tvName;
    TextView tvBreed;
    TextView tvSize;
    TextView tvAge;
    CircleImageView pictureProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initInstances();
    }

    private void initInstances() {
        Intent intent = getIntent();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mProfile = intent.getParcelableExtra("Profile");
        getSupportActionBar().setTitle(mProfile.getDogName());
        tvName = (TextView) findViewById(R.id.tvName);
        tvBreed = (TextView) findViewById(R.id.tvBreed);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvAge = (TextView) findViewById(R.id.tvAge);
        pictureProfile = (CircleImageView) findViewById(R.id.pictureProfile);

        tvName.setText("Name: " + mProfile.getDogName());
        tvBreed.setText("Breed: "+mProfile.getBreed());
        tvSize.setText("Size "+mProfile.getSize());
        tvAge.setText("Age "+String.valueOf(mProfile.getAge())+" year");
        pictureProfile.setImageBitmap(DbBitmapUtility.getImage(mProfile.getPicture()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
