package kmutt.senior.pet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.model.DogProfileDTO;
import kmutt.senior.pet.service.DatabaseHelper;
import kmutt.senior.pet.service.DbBitmapUtility;


public class MainActivity extends AppCompatActivity {
    private int fragSelectProfile;
    private final int newSelectCode = 14;
    DatabaseHelper db;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    ArrayList<String> allNameProfile;
    ImageButton ibProfile;
    View headerView;
    Spinner spinner;
    DogProfileDTO profile;
    TextView tvName;
    TextView tvBreed;
    int flag = 0;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();


    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragSelectProfile = 0;

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("You haven't dog profile pls add.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, InputPetProfileActivity.class);
                        startActivityForResult(intent, newSelectCode);
                    }
                });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation = (NavigationView) findViewById(R.id.navigation);


        drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.hello_world,
                R.string.hello_world) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                changMenuNavigation();
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        db = new DatabaseHelper(this);


        headerView = navigation.getHeaderView(0);
        ibProfile = (ImageButton) headerView.findViewById(R.id.ibProfile);
        spinner = (Spinner) headerView.findViewById(R.id.spinner);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        tvBreed = (TextView) headerView.findViewById(R.id.tvBreed);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    createListChangeProfile();
                    flag = 1;
                } else {
                    changMenuNavigation();
                    flag = 0;
                }
            }
        });
        navigation.setItemIconTintList(null);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                int groupId = menuItem.getGroupId();
                Intent intent;
                if (groupId == R.integer.group_id_profile) {
                    Toast.makeText(getBaseContext(), "id = " + id, Toast.LENGTH_LONG).show();
                    profile = db.getDogProfile(id);
                    Log.w("onitemclick", "set profile = " + profile.getName());
                    Log.w("onitemclick", "set profile = " + profile.getBreed());
                    Log.w("onitemclick", "set profile = " + id);
                    drawerLayout.closeDrawer(navigation);
                    tvName.setText(profile.getName());
                    tvBreed.setText(profile.getBreed());


                } else {
                    switch (id) {
                        case R.id.navItem1:
                            intent = new Intent(MainActivity.this, BluetoothActivity.class);
                            startActivity(intent);
                            drawerLayout.closeDrawer(navigation);
                            break;
                        case R.id.navItem2:
                            intent = new Intent(MainActivity.this, SelectProfileActivity.class);
                            startActivityForResult(intent, newSelectCode);
                            drawerLayout.closeDrawer(navigation);
                            break;
                        case R.id.navItem3:
                            intent = new Intent(MainActivity.this, InputPetProfileActivity.class);
                            startActivity(intent);
                            drawerLayout.closeDrawer(navigation);
                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                            break;
                    }
                }
                return false;
            }
        });


    }

    private void changMenuNavigation() {
        navigation.getMenu().clear();
        navigation.inflateMenu(R.menu.navigation_drawer_items);
    }

    @Override
    protected void onStart() {
        super.onStart();
        allNameProfile = db.getNameProfile();
        if (!allNameProfile.isEmpty() && allNameProfile.size() > 1) {
            ibProfile.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            Log.w("onStart", "visible");

        } else if (allNameProfile.size() == 1) {
            profile = db.getDogProfile(1);
            drawerLayout.closeDrawer(navigation);
            tvName.setText(profile.getName());
            tvBreed.setText(profile.getBreed());
            Log.w("onStart", "set profile = 1");
        } else if (allNameProfile.isEmpty())
            alertDialog.show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        allNameProfile.clear();
        Log.i("MainActivity", "Stop");
    }

    private void createListChangeProfile() {
        navigation.getMenu().clear();
        int id = 1;
        for (String mDogName : allNameProfile) {
            navigation.getMenu().add(R.integer.group_id_profile,
                    id,
                    Menu.NONE,
                    mDogName)
                    .setIcon(R.drawable.ic_pets_black_24dp);
            id++;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigation))
            drawerLayout.closeDrawer(navigation);
        else super.onBackPressed();
    }


}
