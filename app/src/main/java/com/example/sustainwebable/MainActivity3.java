package com.example.sustainwebable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.anychart.core.annotations.Line;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    private DatabaseReference usersDB;
    private FirebaseAuth mAuth;
    String UId;
    ArrayList<ActualDataModel> actualDataModels = new ArrayList<>();

    // display list of actual (not normalized) values
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mAuth= FirebaseAuth.getInstance();
        UId=mAuth.getUid();
        usersDB= FirebaseDatabase.getInstance("https://sustainwebable-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(UId).child("links");

        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        // retrieve data from intent
        Intent intent = getIntent();
        float bytes = intent.getFloatExtra("Bytes",0);
        String url = getIntent().getStringExtra("url");
        double grid = getIntent().getDoubleExtra("Grid",0);
        double renewable = getIntent().getDoubleExtra("Renewable",0);
        boolean green = getIntent().getBooleanExtra("Green",false);
        double cleanerthan = getIntent().getDoubleExtra("CleanerThan",0);
        double energy = getIntent().getDoubleExtra("Energy",0);
        ActualDataModel[] actualDataModels = new ActualDataModel[] {
                new ActualDataModel(bytes, url, grid, renewable, green, cleanerthan, energy)
        };
        ActualDataAdapter actualDataAdapter = new ActualDataAdapter(actualDataModels, MainActivity3.this);
        recyclerView3.setAdapter(actualDataAdapter);





    }


}