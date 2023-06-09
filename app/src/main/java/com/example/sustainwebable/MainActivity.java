package com.example.sustainwebable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;



public class MainActivity extends BaseActivity implements RecyclerViewInterface {
    private FirebaseAuth mAuth;
    private EditText urlhere;
    private RecyclerView recyclerView;
    private WebsiteCarbonAdapter websiteCarbonAdapter;
    private Button submit;
    private Button logout;
    private DatabaseReference usersDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlhere=findViewById(R.id.totalurls);
        submit=findViewById(R.id.checkURL);
        mAuth=FirebaseAuth.getInstance();
        String UId= mAuth.getUid();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<WebsiteCarbonResponse> websiteCarbonResponses = new ArrayList<WebsiteCarbonResponse>();
        websiteCarbonAdapter = new WebsiteCarbonAdapter(getDataSetweb(),MainActivity.this,this);
        recyclerView.setAdapter(websiteCarbonAdapter);
        usersDB= FirebaseDatabase.getInstance("https://sustainwebable-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(UId).child("links");
        WebsiteCarbonAPI api = new WebsiteCarbonAPI();
        logout=findViewById(R.id.Logout);
        getlinkdetails();


        // check url button
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
                            usersDB.child(key).setValue(websiteCarbonResponse);
                            Log.d("WebsiteCarbonAPI", "API response: " + websiteCarbonResponse.getStatistics().getCo2().getGrid().getGrams());
                        } else {
                            Log.e("WebsiteCarbonAPI", "API request failed with status code " + response.code());
                        }
                    }

                });
            }
        });
    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            logoutUser();
        }
    });
    }



    public void getlinkdetails(){
        usersDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    WebsiteCarbonResponse websiteCarbonResponse = new WebsiteCarbonResponse();
                    if (snapshot.child("bytes").exists()){
                        websiteCarbonResponse.setUrl(snapshot.child("url").getValue().toString());
                        websiteCarbonResponse.setBytes(Integer.parseInt(snapshot.child("bytes").getValue().toString()));
                        websiteCarbonResponse.setCleanerThan(Double.parseDouble(snapshot.child("cleanerThan").getValue().toString()));
                        websiteCarbonResponse.setGreen(Boolean.parseBoolean(snapshot.child("green").getValue().toString()));
                        websiteCarbonResponse.getStatistics().getCo2().getRenewable().setGrams(Double.parseDouble(snapshot.child("statistics").child("co2").child("renewable").child("grams").getValue().toString()));
                        websiteCarbonResponse.getStatistics().getCo2().getGrid().setGrams(Double.parseDouble(snapshot.child("statistics").child("co2").child("grid").child("grams").getValue().toString()));
                        websiteCarbonResponse.getStatistics().getCo2().getRenewable().setLitres(Double.parseDouble(snapshot.child("statistics").child("co2").child("renewable").child("litres").getValue().toString()));
                        websiteCarbonResponse.getStatistics().getCo2().getGrid().setLitres(Double.parseDouble(snapshot.child("statistics").child("co2").child("grid").child("litres").getValue().toString()));
                        websiteCarbonResponse.getStatistics().setEnergy(Double.parseDouble(snapshot.child("statistics").child("energy").getValue().toString()));
                        websiteCarbonResponses.add(websiteCarbonResponse);
                        websiteCarbonAdapter.notifyDataSetChanged();
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
    public void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginRegPage.class);
        startActivity(intent);
        finish();
    }
    // click cardview
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("Bytes",websiteCarbonResponses.get(position).getBytes());
        intent.putExtra("Grid",websiteCarbonResponses.get(position).getStatistics().getCo2().getGrid().getGrams());
        intent.putExtra("Renewable",websiteCarbonResponses.get(position).getStatistics().getCo2().getRenewable().getGrams());
        intent.putExtra("url",websiteCarbonResponses.get(position).getUrl());
        intent.putExtra("Green",websiteCarbonResponses.get(position).isGreen());
        intent.putExtra("CleanerThan",websiteCarbonResponses.get(position).getCleanerThan());
        intent.putExtra("Energy",websiteCarbonResponses.get(position).getStatistics().getEnergy());
        startActivity(intent);


    }

    public class WebsiteCarbonResponse implements Serializable {
        private String url;
        private boolean green;
        private int bytes;
        private double cleanerThan;
        private Statistics statistics=new Statistics();

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

    public class Statistics implements Serializable {
        private double adjustedBytes;
        private double energy;
        private CO2 co2 = new CO2();

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

    public class CO2 implements Serializable {
        private Grid grid = new Grid();
        private Renewable renewable = new Renewable();

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

    public class Grid implements Serializable{
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

    public class Renewable implements Serializable {
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
    private ArrayList<WebsiteCarbonResponse> websiteCarbonResponses= new ArrayList<WebsiteCarbonResponse>();
    private List<WebsiteCarbonResponse> getDataSetweb() {
        return websiteCarbonResponses;
    }


}








