package kmutt.senior.pet.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import kmutt.senior.pet.R;

public class graphActivity extends ActionBarActivity {


    private LineChart mChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);


        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 20, 0, 0);
        mChart.setBackgroundColor(Color.WHITE);

        // no description text
        mChart.setDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);



        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();

        y.setLabelCount(6, false);
        y.setStartAtZero(false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLUE);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(45, 100);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);
    }
    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((1990 +i) + "");
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            vals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(true);
        //set1.setCubicIntensity(0.2f);
        //set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(1f);
        set1.setCircleRadius(5f);
        set1.setCircleColor(Color.rgb(240, 238, 70));
        set1.setDrawValues(true);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.BLUE);
        set1.setFillColor(Color.rgb(240, 238, 70));


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
        data.setDrawValues(false);

        // set data
        mChart.setData(data);

    }
}