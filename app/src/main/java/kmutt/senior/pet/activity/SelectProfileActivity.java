package kmutt.senior.pet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kmutt.senior.pet.R;
import kmutt.senior.pet.adapter.DogProfileAdapter;
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.service.DatabaseHelper;

public class SelectProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lvProfile;
    ArrayList<DogProfile> allProfile;
    DogProfileAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        initInstances();


    }

    private void initInstances() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select your dogs");

        db = new DatabaseHelper(this);

        lvProfile = (ListView) findViewById(R.id.lvProfile);
        allProfile = db.getListSelectProfile();

        mAdapter = new DogProfileAdapter(this,allProfile);

        lvProfile.setAdapter(mAdapter);

        lvProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                Log.i("SELCETPROFILE", "" + mAdapter.getIdProfile(position));
                Bundle bundle = new Bundle();
                bundle.putInt("idProfile",mAdapter.getIdProfile(position));
                intent.putExtra("idBundle", bundle);
                startActivity(intent);

            }
        });

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
