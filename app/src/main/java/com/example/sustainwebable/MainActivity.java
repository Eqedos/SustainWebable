package com.example.sustainwebable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebsiteCarbonAPI api = new WebsiteCarbonAPI();
        api.getCarbonData("https://www.google.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("WebsiteCarbonAPI", "API request failed: " + e.getMessage(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    WebsiteCarbonResponse websiteCarbonResponse = new Gson().fromJson(responseBody, WebsiteCarbonResponse.class);
                    Log.d("WebsiteCarbonAPI", "API response: " + websiteCarbonResponse.getUrl());
                } else {
                    Log.e("WebsiteCarbonAPI", "API request failed with status code " + response.code());
                }
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








