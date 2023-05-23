package com.example.sustainwebable;

public class ActualDataModel {
    float bytes;
    String url;
    double grid;
    double renewable;
    boolean green;
    double cleanerthan;
    double energy;

    public ActualDataModel(float bytes, String url, double grid, double renewable, boolean green, double cleanerthan, double energy) {
        this.bytes = bytes;
        this.url = url;
        this.grid = grid;
        this.renewable = renewable;
        this.green = green;
        this.cleanerthan = cleanerthan;
        this.energy = energy;
    }

    public float getBytes() {
        return bytes;
    }

    public String getUrl() {
        return url;
    }

    public double getGrid() {
        return grid;
    }

    public double getRenewable() {
        return renewable;
    }

    public boolean isGreen() {
        return green;
    }

    public double getCleanerthan() {
        return cleanerthan;
    }

    public double getEnergy() {
        return energy;
    }
}
