package com.example.sustainwebable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends BaseActivity {
    private TextView CleanerThan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
          int bytes = getIntent().getIntExtra("Bytes",0);
          String url = getIntent().getStringExtra("url");
          double grid = getIntent().getDoubleExtra("Grid",0);
          double renewable = getIntent().getDoubleExtra("Renewable",0);
          boolean green = getIntent().getBooleanExtra("Green",false);
          double cleanerthan = getIntent().getDoubleExtra("CleanerThan",0);

          CleanerThan = findViewById(R.id.greenerthanhere);
          CleanerThan.setText(String.valueOf(cleanerthan));

    }
}