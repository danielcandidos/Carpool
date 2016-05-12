package com.carpool.android.dominio;

public class CirclePoints {
    private double latitude;
    private double longitude;

    public CirclePoints(double lat, double longit){
        this.latitude = lat;
        this.longitude = longit;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
