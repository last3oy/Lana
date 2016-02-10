package kmutt.senior.pet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import kmutt.senior.pet.R;

public class graphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        GraphView graph = (GraphView)findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(0.25, 5),
                new DataPoint(0.55, 3),
                new DataPoint(0.75, 2),
                new DataPoint(1.25, 6),
                new DataPoint(1.50, 2),
                new DataPoint(1.75, 1.5),
                new DataPoint(2, 4)
        });
        graph.addSeries(series);

        // titles
        graph.setTitle("Chart Title");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Vertical Axis");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Horizontal Axis");

        // optional styles
        //graph.setTitleTextSize(40);
        //graph.setTitleColor(Color.BLUE);
        //graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(40);
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLUE);
        //graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(40);
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.BLUE);

    }

}
