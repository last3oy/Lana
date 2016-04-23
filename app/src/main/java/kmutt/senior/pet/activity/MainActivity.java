package kmutt.senior.pet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kmutt.senior.pet.R;
import kmutt.senior.pet.model.DogProfileDTO;
import kmutt.senior.pet.service.DatabaseHelper;
import kmutt.senior.pet.view.MyMarkerView;


public class MainActivity extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    private int fragSelectProfile;
    private String strDate,d;
    private LineChart mChart,mChart1,mChart2;
    private final int newSelectCode = 14;
    TextView mResultTextView, TextView;
    private List<DogProfileDTO> MebmerList;
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
    DatabaseHelper DB;
    int flag = 0;
    AlertDialog alertDialog;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
        DB = new DatabaseHelper(this);


        mChart = (LineChart) findViewById(R.id.chart1);
        mChart1 = (LineChart) findViewById(R.id.chart2);

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
        //date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(c.getTime());
        //graph
        Button button = (Button) findViewById(R.id.btnSelectDate);
        try {
            MebmerList = DB.getbpm(profile.getId(),strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(MainActivity.this);
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });
        if (MebmerList == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else {

            // add data
            setgraph();
            setData(45, 100);

        }
        try {
            graphmonth();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private void setgraph() {
        mChart.setDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(true);

        XAxis x = mChart.getXAxis();
        //x.setAvoidFirstLastClipping(true);
        x.setTextColor(Color.rgb(37, 52, 65));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.rgb(56, 77, 95));
        //x.setDrawAxisLine(true);

        YAxis y = mChart.getAxisLeft();

        y.setTextColor(Color.rgb(37, 52, 65));
        y.setStartAtZero(false);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.rgb(56, 77, 95));
        mChart.getAxisRight().setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        mChart.getLegend().setEnabled(true);

        mChart.animateX(2500);

        // dont forget to refresh the drawing

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        mChart.notifyDataSetChanged();
        mChart.invalidate();

    }

    private void setData(int count, float range) {
        mChart.setBackgroundColor(Color.TRANSPARENT);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> vals1 = new ArrayList<Entry>();
        int i = 0;
        int sum = 0;
        float avg = 0;
        for (DogProfileDTO getdata : MebmerList) {
            // Log.wtf("LookX",""+getdata.gName());
            // Log.wtf("LookX",""+getdata.gDate());
            // Log.wtf("LookX",""+getdata.gTime());
            //   Log.wtf("LookY",""+getdata.gPulse());

            sum += getdata.getBpm();
            xVals.add(getdata.getDatetime());
            vals1.add(new Entry(getdata.getBpm(), i));
            i++;
        }
        if (sum != 0) {
            avg = sum / i;

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("No data On date Your select");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        TextView = (TextView) findViewById(R.id.textView);
        TextView.setText("Avg Pulse :" + avg + " BPM");
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "");
        set1.setColor(Color.rgb(56, 77, 95));
        set1.setLineWidth(1f);
        set1.setCircleColor(Color.rgb(37, 52, 65));
        set1.setCircleRadius(5f);
        set1.setFillColor(Color.rgb(37, 52, 65));
        set1.setDrawCubic(true);
        set1.setDrawValues(false);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.rgb(37, 52, 65));


        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        data.setValueTextSize(9f);
        //data.setDrawValues(true);
        mChart.notifyDataSetChanged();

        // set data
        mChart.setData(data);

    }

    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Date o;
        String n = null;
        mResultTextView = (TextView) findViewById(R.id.textView1);
        mResultTextView.setText(getString(R.string.calendar_date_picker_result_values, year, monthOfYear + 1, dayOfMonth));
        d = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

        Log.d("sad", "" + d);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        try {
            o = input.parse(d);                 // parse input
            n = output.format(o);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.d("sad", "" + d);
        Log.d("sad", n);
        DB = new DatabaseHelper(this);
        try {
            MebmerList = DB.getbpm(profile.getId(),n);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (MebmerList != null) {
            setData(45, 100);
            mChart.invalidate();
        } else {
        }


    }

    private void graphmonth() throws ParseException {

        mChart1.setDescription("");

        // enable touch gestures
        mChart1.setTouchEnabled(true);

        // enable scaling and dragging
        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart1.setPinchZoom(true);

        mChart1.setDrawGridBackground(true);

        XAxis x = mChart1.getXAxis();
        //x.setAvoidFirstLastClipping(true);
        x.setTextColor(Color.rgb(37, 52, 65));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.rgb(56, 77, 95));
        //x.setDrawAxisLine(true);

        YAxis y = mChart1.getAxisLeft();

        y.setTextColor(Color.rgb(37, 52, 65));
        y.setStartAtZero(false);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.rgb(56, 77, 95));
        mChart1.getAxisRight().setEnabled(false);
        Legend l = mChart1.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        mChart1.getLegend().setEnabled(true);

        mChart1.animateX(2500);

        // dont forget to refresh the drawing

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart1.setMarkerView(mv);

        mChart1.notifyDataSetChanged();
        mChart1.invalidate();
        mChart1.setBackgroundColor(Color.TRANSPARENT);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> vals1 = new ArrayList<Entry>();
        int i = 0;

        for (DogProfileDTO getdata : MebmerList) {
            // Log.wtf("LookX",""+getdata.gName());
            // Log.wtf("LookX",""+getdata.gDate());
            // Log.wtf("LookX",""+getdata.gTime());
            //   Log.wtf("LookY",""+getdata.gPulse());

            xVals.add(getdata.getDatetime());
            vals1.add(new Entry(getdata.getBpm(), i));
            i++;
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "");
        set1.setColor(Color.rgb(56, 77, 95));
        set1.setLineWidth(1f);
        set1.setCircleColor(Color.rgb(37, 52, 65));
        set1.setCircleRadius(5f);
        set1.setFillColor(Color.rgb(37, 52, 65));
        set1.setDrawCubic(true);
        set1.setDrawValues(false);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.rgb(37, 52, 65));


        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        data.setValueTextSize(9f);
        //data.setDrawValues(true);
        mChart1.notifyDataSetChanged();

        // set data
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("-MM-");
        strDate = sdf.format(c.getTime());
        Log.d("sad",""+strDate);
        DB = new DatabaseHelper(this);
        MebmerList = DB.getbpm(profile.getId(),strDate);
    }
}
