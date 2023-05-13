package com.example.sustainwebable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth= FirebaseAuth.getInstance();
        UId=mAuth.getUid();
        usersDB= FirebaseDatabase.getInstance("https://sustainwebable-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(UId).child("links");
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progressBar));
        Cartesian barChart = AnyChart.bar();
        barChart.animation(true);
        barChart.padding(10d, 20d, 5d, 20d);
        barChart.yScale().stackMode(ScaleStackMode.VALUE);
        barChart.yAxis(0).labels().format(
                "function() {\n" +
                        "    return Math.abs(this.value).toLocaleString();\n" +
                        "  }");
        barChart.yAxis(0d).title("Your website vs. the average");
        barChart.xAxis(0d).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

        Linear xAxis1 = barChart.xAxis(1d);
        xAxis1.enabled(true);
        xAxis1.orientation(Orientation.RIGHT);
        xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

        barChart.title("How your app compares to the average.");
        barChart.tooltip()
                .title(false)
                .separator(false)
                .displayMode(TooltipDisplayMode.SEPARATED)
                .positionMode(TooltipPositionMode.POINT)
                .useHtml(true)
                .fontSize(12d)
                .offsetX(5d)
                .offsetY(0d)
                .format(
                        "function() {\n" +
                                "      return '<span style=\"color: #D9D9D9\">$</span>' + Math.abs(this.value).toLocaleString();\n" +
                                "    }");

        barChart.interactivity().hoverMode(HoverMode.BY_X);


          int bytes = getIntent().getIntExtra("Bytes",0);
          String url = getIntent().getStringExtra("url");
          double grid = getIntent().getDoubleExtra("Grid",0);
          double renewable = getIntent().getDoubleExtra("Renewable",0);
          boolean green = getIntent().getBooleanExtra("Green",false);
          double cleanerthan = getIntent().getDoubleExtra("CleanerThan",0);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("Bytes",bytes/545000,-1));
        seriesData.add(new CustomDataEntry("Grid CO2",grid/0.5,-1));
        seriesData.add(new CustomDataEntry("Renewable CO2",renewable/0.5,-1));
        seriesData.add(new CustomDataEntry("Cleaner Than", cleanerthan,-0.5));
        Set set = Set.instantiate();
        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

        Bar series1 = barChart.bar(series1Data);
        series1.name("Average App")
                .color("Red");
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER);

        Bar series2 = barChart.bar(series2Data);
        series2.name("Your App").color("Green");
        series2.tooltip()
                .position("left")
                .anchor(Anchor.RIGHT_CENTER);
        barChart.legend().enabled(true);
        barChart.legend().inverted(true);
        barChart.legend().fontSize(13d);
        barChart.legend().padding(0d, 0d, 20d, 0d);
        anyChartView.setBackgroundColor("black");
        anyChartView.setChart(barChart);

    }
    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}