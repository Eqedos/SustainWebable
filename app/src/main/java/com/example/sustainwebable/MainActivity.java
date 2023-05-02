package com.example.sustainwebable;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText urlhere;
    private TextView datahere;
    private RecyclerView recyclerView;
    private WebsiteCarbonAdapter websiteCarbonAdapter;
    private Button submit;
    private DatabaseReference usersDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlhere=findViewById(R.id.totalurls);
        submit=findViewById(R.id.checkURL);
        datahere=findViewById(R.id.datahere);
        mAuth=FirebaseAuth.getInstance();
        String UId= mAuth.getUid();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        websiteCarbonAdapter = new WebsiteCarbonAdapter(new ArrayList<WebsiteCarbonResponse>(),MainActivity.this);
        recyclerView.setAdapter(websiteCarbonAdapter);
        usersDB= FirebaseDatabase.getInstance("https://sustainwebable-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(UId).child("links");
        WebsiteCarbonAPI api = new WebsiteCarbonAPI();
        getlinkdetails();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.getCarbonData(urlhere.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("WebsiteCarbonAPI", "API request failed: " + e.getMessage(), e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseBody = response.body().string();
                            WebsiteCarbonResponse websiteCarbonResponse = new Gson().fromJson(responseBody, WebsiteCarbonResponse.class);
                            double grams = websiteCarbonResponse.getStatistics().getCo2().getGrid().getGrams();
                            double roundedGrams = Math.round(grams * 100.0) / 100.0;
                            String key = usersDB.push().getKey();
                            datahere.setText(String.valueOf(roundedGrams));
                            usersDB.child(key).setValue(websiteCarbonResponse);
                            Log.d("WebsiteCarbonAPI", "API response: " + websiteCarbonResponse.getStatistics().getCo2().getGrid().getGrams());
                        } else {
                            Log.e("WebsiteCarbonAPI", "API request failed with status code " + response.code());
                        }
                    }

                });
            }
        });

    }

    private void getlinkdetails(){
        usersDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    WebsiteCarbonResponse websiteCarbonResponse = new WebsiteCarbonResponse();

                    if (snapshot.child("bytes").exists()){
                        Toast.makeText(MainActivity.this,String.valueOf(snapshot.child("bytes").getValue()),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "dont work", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class WebsiteCarbonResponse {
        private String url;
        private boolean green;
        private int bytes;
        private double cleanerThan;
        private Statistics statistics;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isGreen() {
            return green;
        }

        public void setGreen(boolean green) {
            this.green = green;
        }

        public int getBytes() {
            return bytes;
        }

        public void setBytes(int bytes) {
            this.bytes = bytes;
        }

        public double getCleanerThan() {
            return cleanerThan;
        }

        public void setCleanerThan(double cleanerThan) {
            this.cleanerThan = cleanerThan;
        }

        public Statistics getStatistics() {
            return statistics;
        }

        public void setStatistics(Statistics statistics) {
            this.statistics = statistics;
        }
// getters and setters
    }

    public class Statistics {
        private double adjustedBytes;
        private double energy;
        private CO2 co2;

        public double getAdjustedBytes() {
            return adjustedBytes;
        }

        public void setAdjustedBytes(double adjustedBytes) {
            this.adjustedBytes = adjustedBytes;
        }

        public double getEnergy() {
            return energy;
        }

        public void setEnergy(double energy) {
            this.energy = energy;
        }

        public CO2 getCo2() {
            return co2;
        }

        public void setCo2(CO2 co2) {
            this.co2 = co2;
        }
// getters and setters
    }

    public class CO2 {
        private Grid grid;
        private Renewable renewable;

        public Grid getGrid() {
            return grid;
        }

        public void setGrid(Grid grid) {
            this.grid = grid;
        }

        public Renewable getRenewable() {
            return renewable;
        }

        public void setRenewable(Renewable renewable) {
            this.renewable = renewable;
        }
// getters and setters
    }

    public class Grid {
        private double grams;
        private double litres;

        public double getGrams() {
            return grams;
        }

        public void setGrams(double grams) {
            this.grams = grams;
        }

        public double getLitres() {
            return litres;
        }

        public void setLitres(double litres) {
            this.litres = litres;
        }
// getters and setters
    }

    public class Renewable {
        private double grams;
        private double litres;

        // getters and setters

        public double getGrams() {
            return grams;
        }

        public void setGrams(double grams) {
            this.grams = grams;
        }

        public double getLitres() {
            return litres;
        }

        public void setLitres(double litres) {
            this.litres = litres;
        }
    }

}








