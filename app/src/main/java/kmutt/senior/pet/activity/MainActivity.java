/**
 * Create by BPY @ 24/4/16
 * @ copyright KMUTT
 * sponsor by Dr.Parivate
 *
 */
package kmutt.senior.pet.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerDialogFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.model.BpmValue;
import kmutt.senior.pet.model.DogNameId;
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.util.DatabaseHelper;
import kmutt.senior.pet.util.DbBitmapUtility;
import kmutt.senior.pet.view.CustomMarkerView;


public class MainActivity extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener, ExpirationPickerDialogFragment.ExpirationPickerDialogHandler {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private DatabaseHelper db;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigation;
    private ArrayList<DogNameId> allNameProfile;
    private ImageButton ibProfile;
    private View headerView;
    private CircleImageView civPicture;
    private Spinner spinner;
    private DogProfile profile;
    private TextView tvName;
    private TextView tvBreed;
    private int flag = 0;
    private AlertDialog alertDialog;
    private String currentDate;
    private String currentMonth;
    private ArrayList<BpmValue> listValue;
    private BarChart mBarChart;
    private LineChart mLineChart;
    private CustomMarkerView markerView;
    private TextView tvDate;
    private TextView tvMonth;
    private TextView tvBpm;
    private Button btnDate;
    private Button btnMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
    }

    @Override
    protected void onStart() {
        super.onStart();
        allNameProfile = db.getNameProfile();
        if (allNameProfile.size() > 1) {
            ibProfile.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            profile = db.getDogProfile(allNameProfile.get(0).getId());
            changeProfile();
        } else if (allNameProfile.size() == 1) {
            profile = db.getDogProfile(allNameProfile.get(0).getId());
            changeProfile();
            Log.w("onStart", "set profile = 1");
        } else if (allNameProfile.isEmpty())
            showEmptyProfileDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (allNameProfile.size() >= 1) {
            listValue = db.getbpm(profile.getId(), currentDate);
            setDataDailyChart(listValue);
            listValue = db.getbpm(profile.getId(), currentMonth);
            setDataMonthChart(listValue);
            mBarChart.invalidate();
            mLineChart.invalidate();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        allNameProfile.clear();
        tvMonth.setText(tvMonth.getText().toString().replaceAll("No data",""));
        Log.i("MainActivity", "Stop");
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigation))
            drawerLayout.closeDrawer(navigation);
        else super.onBackPressed();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCanceledOnTouchOutside(false);

        markerView = new CustomMarkerView(this, R.layout.custom_marker_view);
        mBarChart = (BarChart) findViewById(R.id.dailyBarChart);
        mLineChart = (LineChart) findViewById(R.id.monthlyLineChart);

        initDailyChart();
        initMonthlyChart();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation = (NavigationView) findViewById(R.id.navigation);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvBpm = (TextView) findViewById(R.id.tvBpm);
        btnDate = (Button) findViewById(R.id.btnSelectDate);
        btnMonth = (Button) findViewById(R.id.btnSelectMonth);

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


        headerView = navigation.getHeaderView(0);
        ibProfile = (ImageButton) headerView.findViewById(R.id.ibProfile);
        spinner = (Spinner) headerView.findViewById(R.id.spinner);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        tvBreed = (TextView) headerView.findViewById(R.id.tvBreed);
        civPicture = (CircleImageView) headerView.findViewById(R.id.circle_image);


        ibProfile.setOnClickListener(imagebuttonProfileClicked);
        navigation.setItemIconTintList(null);

        navigation.setNavigationItemSelectedListener(navigationViewClicked);

        btnDate.setOnClickListener(btnDateClicked);
        btnMonth.setOnClickListener(btnMonthClicked);

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = mSimpleDateFormat.format(mCalendar.getTime());
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
        currentMonth = mSimpleDateFormat.format(mCalendar.getTime());

        tvDate.setText("Date: " + currentDate);
        tvMonth.setText("Month: " + currentMonth);


    }

    private void initDailyChart() {
        mBarChart.setDescription("");

        // enable touch gestures
        mBarChart.setTouchEnabled(true);

        // enable scaling and dragging
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mBarChart.fitScreen();
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(true);

        XAxis x = mBarChart.getXAxis();
        //x.setAvoidFirstLastClipping(true);
        x.setTextColor(Color.rgb(37, 52, 65));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.rgb(56, 77, 95));
        //x.setDrawAxisLine(true);

        YAxis y = mBarChart.getAxisLeft();

        y.setTextColor(Color.rgb(37, 52, 65));
        y.setStartAtZero(false);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.rgb(56, 77, 95));
        mBarChart.getAxisRight().setEnabled(false);

        mBarChart.getLegend().setEnabled(false);

        mBarChart.animateX(2500);

        // dont forget to refresh the drawing

        // set the marker to the chart
        mBarChart.setMarkerView(markerView);

        mBarChart.notifyDataSetChanged();
        mBarChart.invalidate();
        mBarChart.setBackgroundColor(Color.TRANSPARENT);

    }

    private void initMonthlyChart() {

        // set data

        mLineChart.setDescription("");
        mLineChart.fitScreen();
        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.fitScreen();
        mLineChart.setPinchZoom(false);
        mLineChart.setDrawGridBackground(true);

        XAxis x = mLineChart.getXAxis();
        //x.setAvoidFirstLastClipping(true);
        x.setTextColor(Color.rgb(37, 52, 65));
        //  x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.rgb(56, 77, 95));
        //x.setDrawAxisLine(true);

        YAxis y = mLineChart.getAxisLeft();

        y.setTextColor(Color.rgb(37, 52, 65));
        y.setStartAtZero(false);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.rgb(56, 77, 95));
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setDrawLabels(false);
        mLineChart.animateX(2500);

        // dont forget to refresh the drawing


        // set the marker to the chart
        mLineChart.setMarkerView(markerView);

        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
        mLineChart.setBackgroundColor(Color.TRANSPARENT);

    }

    private void setDataMonthChart(ArrayList<BpmValue> values) {
        if (!values.isEmpty()) {
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<Entry> vals1 = new ArrayList<Entry>();
            int i = 0;


            for (BpmValue mBpmValue : values) {
                xVals.add(mBpmValue.getDate());
                vals1.add(new Entry(mBpmValue.getBpm(), i));
                i++;
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(vals1, "");
            set1.setColor(Color.rgb(56, 77, 95));
            set1.setLineWidth(2f);
            set1.setCircleColor(Color.rgb(37, 52, 65));
            set1.setCircleRadius(5f);
            set1.setFillColor(Color.rgb(37, 52, 65));
            set1.setDrawCubic(true);
            set1.setDrawValues(false);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.rgb(37, 52, 65));


            set1.setDrawHorizontalHighlightIndicator(false);


            // create a data object with the datasets
            LineData data = new LineData(xVals, set1);

            data.setValueTextSize(9f);
            //data.setDrawValues(true);
            mLineChart.notifyDataSetChanged();
            mLineChart.setData(data);
            mLineChart.invalidate();
        } else {
            tvMonth.setText(tvMonth.getText().toString() + " No data");
            mLineChart.clear();
            mLineChart.invalidate();
        }
    }

    private void setDataDailyChart(ArrayList<BpmValue> values) {
        if (!values.isEmpty()) {
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<BarEntry> yValsNormal = new ArrayList<BarEntry>();
            ArrayList<BarEntry> yValsAbnormal = new ArrayList<BarEntry>();
            int i = 0;
            int sum = 0;
            float avg = 0;
            for (BpmValue mBpmValue : values) {
                sum += mBpmValue.getBpm();
                xVals.add(mBpmValue.getDate());
                if (mBpmValue.getBpm() >= 180 || mBpmValue.getBpm() <= 70) {
                    yValsAbnormal.add(new BarEntry(mBpmValue.getBpm(), i));
                } else {
                    yValsNormal.add(new BarEntry(mBpmValue.getBpm(), i));
                }
                i++;
            }
            avg = sum / i;
            tvBpm.setText("Avg Pulse :" + avg + " BPM");


            // create a dataset and give it a type
            BarDataSet set1 = new BarDataSet(yValsNormal, "");
            set1.setColor(Color.rgb(56, 77, 95));
            set1.setBarSpacePercent(90f);
            set1.setDrawValues(false);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.rgb(37, 52, 65));

            BarDataSet set2 = new BarDataSet(yValsAbnormal, "");
            set2.setColor(Color.RED);
            set2.setBarSpacePercent(90f);
            set2.setDrawValues(false);
            //set1.setDrawHorizontalHighlightIndicator(false);


            // create a data object with the datasets
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            BarData data = new BarData(xVals, dataSets);

            data.setValueTextSize(9f);
            //data.setDrawValues(true);
            mBarChart.notifyDataSetChanged();

            // set data
            mBarChart.setData(data);

            mBarChart.invalidate();

        } else {
            tvBpm.setText("can't not find average value");
            mBarChart.clear();
            mBarChart.invalidate();
        }


    }

    private void showEmptyProfileDialog() {
        alertDialog.setMessage("You haven't dog profile pls add.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        alertDialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, InputPetProfileActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }



    private void changeProfile() {
        civPicture.setImageBitmap(DbBitmapUtility.getImage(profile.getPicture()));
        tvName.setText(profile.getName());
        tvBreed.setText(profile.getBreed());
    }

    private void changMenuNavigation() {
        navigation.getMenu().clear();
        navigation.inflateMenu(R.menu.navigation_drawer_items);
    }

    private void createListChangeProfile() {
        navigation.getMenu().clear();
        for (DogNameId mDogNameId : allNameProfile) {
            navigation.getMenu().add(R.integer.group_id_profile,
                    mDogNameId.getId(),
                    Menu.NONE,
                    mDogNameId.getName())
                    .setIcon(R.drawable.ic_pets_black_24dp);
        }
    }

    /**
     * Listenner
     */

    View.OnClickListener imagebuttonProfileClicked = new View.OnClickListener() {
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
    };

    NavigationView.OnNavigationItemSelectedListener navigationViewClicked = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            int id = menuItem.getItemId();
            int groupId = menuItem.getGroupId();
            Intent intent;
            if (groupId == R.integer.group_id_profile) {
                profile = db.getDogProfile(id);
                drawerLayout.closeDrawer(navigation);
                changeProfile();
                initDailyChart();
                initMonthlyChart();
                listValue = db.getbpm(profile.getId(), currentDate);
                setDataDailyChart(listValue);
                listValue = db.getbpm(profile.getId(), currentMonth);
                setDataMonthChart(listValue);



            } else {
                switch (id) {
                    case R.id.navItem1:
                        intent = new Intent(MainActivity.this, BluetoothActivity.class);
                        intent.putExtra("flag", 0);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigation);
                        break;
                    case R.id.navItem2:
                        intent = new Intent(MainActivity.this, SelectProfileActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigation);
                        break;
                    case R.id.navItem3:
                        intent = new Intent(MainActivity.this, InputPetProfileActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigation);
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                        break;
                    case R.id.navItem4:
                        intent = new Intent(MainActivity.this, HealthdataActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navItem5:
                        intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel://0898665958"));
                        startActivity(intent);
                        break;
                    case R.id.navdrawer_item_settings:
                        intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
            return false;
        }
    };
    View.OnClickListener btnDateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setOnDateSetListener(MainActivity.this);
            cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
        }
    };

    View.OnClickListener btnMonthClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ExpirationPickerBuilder epb = new ExpirationPickerBuilder()
                    .setFragmentManager(getSupportFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment);
            epb.show();
        }
    };

    public void onDialogExpirationSet(int reference, int year, int monthOfYear) {
        tvMonth.setText("Month: " + getString(R.string.expiration_picker_result_value, String.format("%02d", monthOfYear), year));
        currentMonth = year + "-" + String.format("%02d", monthOfYear);
        listValue = db.getbpm(profile.getId(), currentMonth);
        initMonthlyChart();
        setDataMonthChart(listValue);


    }

    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Date mDate;
        String d;

        tvDate.setText("Date: " + getString(R.string.calendar_date_picker_result_values, year, monthOfYear + 1, dayOfMonth));
        d = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

        try {
            mDate = input.parse(d);
            currentDate = output.format(mDate);
        } catch (ParseException e) {
            Toast.makeText(MainActivity.this, "No Date to Select", Toast.LENGTH_SHORT).show();
        }
        initDailyChart();
        listValue = db.getbpm(profile.getId(),currentDate);
        setDataDailyChart(listValue);



    }
}