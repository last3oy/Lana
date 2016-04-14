package kmutt.senior.pet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        pictureProfile = (CircleImageView) findViewById(R.id.pictureProfile);

        tvName.setText("Name: "+mProfile.getDogName());
        pictureProfile.setImageBitmap(DbBitmapUtility.getImage(mProfile.getPicture()));
    }
}
