package kmutt.senior.pet.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

import java.util.ArrayList;
import java.util.List;

import kmutt.senior.pet.R;

public class graphActivity extends ActionBarActivity {


    private LineChart mChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
       //         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar;



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChart = (LineChart) findViewById(R.id.chart1);


        mChart.setBackgroundColor(Color.TRANSPARENT);

        // no description text
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
        x.setTextColor(Color.rgb(37,52,65));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
       x.setAxisLineColor(Color.rgb(56,77,95));
        //x.setDrawAxisLine(true);

        YAxis y = mChart.getAxisLeft();

        y.setTextColor(Color.rgb(37,52,65));
        y.setStartAtZero(false);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.rgb(56,77,95));
        

        mChart.getAxisRight().setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        mChart.invalidate();
        // add data
        setData(45,100);

        mChart.getLegend().setEnabled(true);

        mChart.animateX(2500);

        // dont forget to refresh the drawing
        mChart.invalidate();
        MyMarkerView mv = new MyMarkerView(this,R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);
    }
    private void setData(int count, float range) {
        dataActivity myDb = new dataActivity(this);
        List<Getdata> MebmerList = myDb.SelectAllData();

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> vals1 = new ArrayList<Entry>();
        int i = 0;
        for (Getdata getdata : MebmerList) {
           // Log.wtf("LookX",""+getdata.gName());
           // Log.wtf("LookX",""+getdata.gDate());
           // Log.wtf("LookX",""+getdata.gTime());
         //   Log.wtf("LookY",""+getdata.gPulse());
            xVals.add(getdata.gTime());
            vals1.add(new Entry(getdata.gPulse(),i));
            i++;

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1,"" );
        set1.setColor(Color.rgb(56,77,95));
        set1.setLineWidth(1f);
        set1.setCircleColor(Color.rgb(37,52,65));
        set1.setCircleRadius(5f);
        set1.setFillColor(Color.rgb(37,52,65));
        set1.setDrawCubic(true);
        set1.setDrawValues(false);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.rgb(37,52,65));


        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(xVals , set1);

        data.setValueTextSize(9f);
        //data.setDrawValues(true);



        // set data
        mChart.setData(data);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}