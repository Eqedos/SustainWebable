package com.example.sustainwebable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Radar;
import com.anychart.core.axes.Linear;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LabelsOverlapMode;
import com.anychart.enums.Orientation;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends BaseActivity {
    private TextView CleanerThan;
    private DatabaseReference usersDB;
    private FirebaseAuth mAuth;
    String UId;
    ArrayList<MainActivity.WebsiteCarbonResponse> websiteCarbonResponses;

    Button button;

    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth= FirebaseAuth.getInstance();
        UId=mAuth.getUid();
        usersDB= FirebaseDatabase.getInstance("https://sustainwebable-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(UId).child("links");
        RadarChart radarChart = findViewById(R.id.radarChart);
          float bytes = getIntent().getIntExtra("Bytes",0);
          String url = getIntent().getStringExtra("url");
          double grid = getIntent().getDoubleExtra("Grid",0);
          double renewable = getIntent().getDoubleExtra("Renewable",0);
          boolean green = getIntent().getBooleanExtra("Green",false);
          double cleanerthan = getIntent().getDoubleExtra("CleanerThan",0);
          double energy = getIntent().getDoubleExtra("Energy",0);
          ArrayList<RadarEntry> visitorsForFirstWebsite = new ArrayList<>();
          visitorsForFirstWebsite.add(new RadarEntry(bytes/1000000));
          visitorsForFirstWebsite.add(new RadarEntry((float) (grid)));
          visitorsForFirstWebsite.add(new RadarEntry((float) (renewable)));
          visitorsForFirstWebsite.add(new RadarEntry((float) cleanerthan));
          visitorsForFirstWebsite.add(new RadarEntry((float) (energy*100)));
        RadarDataSet radarDataSet = new RadarDataSet(visitorsForFirstWebsite, url);

        radarDataSet.setColor(Color.GREEN);
        radarDataSet.setLineWidth(1f);
        radarDataSet.setValueTextColor(Color.BLACK);
        ArrayList<RadarEntry> visitorsForSecondWebsite = new ArrayList<>();
        visitorsForSecondWebsite.add(new RadarEntry(525000/1000000));
        visitorsForSecondWebsite.add(new RadarEntry((float) (0.25)));
        visitorsForSecondWebsite.add(new RadarEntry((float) (0.25)));
        visitorsForSecondWebsite.add(new RadarEntry((float) 0.5));
        visitorsForFirstWebsite.add(new RadarEntry((float) (0.2)));

        RadarDataSet radarDataSet1 = new RadarDataSet(visitorsForSecondWebsite,"Average Website");
        radarDataSet1.setColor(Color.RED);
        radarDataSet1.setLineWidth(1f);
        radarDataSet1.setValueTextColor(Color.BLACK);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.addDataSet(radarDataSet1);
        String[] labels = {"bytes","Grid CO2","Renewable CO2","Cleaner Than","Energy Transferred"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.getDescription().setText("For more information about each criteria, refer to the text below");
        radarChart.setData(radarData);


        // button to main activity 3 to show actual data via recycler view
       button = findViewById(R.id.ButtonToActualData);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
               intent.putExtra("Bytes", bytes);
               intent.putExtra("Grid",grid);
               intent.putExtra("Renewable",renewable);
               intent.putExtra("url",url);
               intent.putExtra("Green",green);
               intent.putExtra("CleanerThan",cleanerthan);
               intent.putExtra("Energy",energy);
               startActivity(intent);
           }
       });

       button2 = findViewById(R.id.buttonToSuggestions);
       button2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent2 = new Intent(MainActivity2.this, MainActivity4.class);
               startActivity(intent2);
           }
       });
    }
}