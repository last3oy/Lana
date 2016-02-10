package kmutt.senior.pet.activity;

import android.content.Intent;
import android.content.res.Configuration;
<<<<<<< HEAD
import android.os.Bundle;
=======
import android.support.v4.app.FragmentManager;
>>>>>>> 1828992e2100eae7a61d225554f48b028236d80c
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import kmutt.senior.pet.R;
import kmutt.senior.pet.fragment.FragmentTemplate;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, FragmentTemplate.newInstance())
                    .commit();
        }
        final Button btn1 = (Button) findViewById(R.id.button);
        // Perform action on click
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
                Intent newActivity = new Intent(MainActivity.this,graphActivity.class);
=======
                Intent newActivity = new Intent(MainActivity.this,bluetoothActivity.class);
>>>>>>> 1828992e2100eae7a61d225554f48b028236d80c
                startActivity(newActivity);
            }
        });
    }

    private void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this
                , drawerLayout
                , R.string.hello_world
                , R.string.hello_world
        );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

/*

    @Override
    protected void onResume() {
        super.onResume();
        MainBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainBus.getInstance().unregister(this);
    }

    @Subscribe
    public void busEventReceived(BusEventDessert event){
        DessertListManager.getInstance().setSelectedDao(event.getDao());

        // TODO : Check Mobile/Tablet


        FrameLayout moreInfoFrameLayout = (FrameLayout) findViewById(R.id.moreContainer);
        if(moreInfoFrameLayout == null){
            //mobile
            Intent intent = new Intent(MainActivity.this,MoreInfoActivity.class);
            startActivity(intent);
        }else {
            //tablet
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, MoreInfoFragment.newInstance())
                    .commit();
        }

        //ทำให้เป็น paselable แล้วส่ง extra ไป
    }*/
}
