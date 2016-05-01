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
import kmutt.senior.pet.model.DogProfileInput;
import kmutt.senior.pet.util.DatabaseHelper;

public class SelectProfileActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ListView lvProfile;
    private ArrayList<DogProfileInput> allProfile;
    private DogProfileAdapter mAdapter;

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


    }

    @Override
    protected void onStart() {
        super.onStart();
        allProfile = db.getListSelectProfile();
        if (!allProfile.isEmpty()) {
            mAdapter = new DogProfileAdapter(this, allProfile);

            lvProfile.invalidate();
            lvProfile.setAdapter(mAdapter);

            lvProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    Log.i("SELCETPROFILE", "" + mAdapter.getIdProfile(position));
                    Bundle bundle = new Bundle();
                    bundle.putInt("idProfile", mAdapter.getIdProfile(position));
                    intent.putExtra("idBundle", bundle);
                    startActivity(intent);

                }
            });
        } else finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        allProfile.clear();
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
