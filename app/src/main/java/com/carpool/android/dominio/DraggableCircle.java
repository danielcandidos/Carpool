package com.carpool.android.dominio;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Daniel on 06/05/2016.
 */
public class DraggableCircle {

    private GoogleMap mMap;

    private final int mStrokeColor = -16777216;
    private final int mFillColor = 680656640;
    private final int mWidthValue = 5;

    private final Marker centerMarker;
    private final Circle circle;
    private double radius;

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public DraggableCircle(LatLng center, double radius) {
        this.radius = radius;
        centerMarker = mMap.addMarker(new MarkerOptions()
                .position(center)
                .draggable(true));
        circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(mWidthValue)
                .strokeColor(mStrokeColor)
                .fillColor(mFillColor));
    }

    public boolean onMarkerMoved(Marker marker) {
        //teste = marker.getPosition(); //TESTANDO PESQUISAl
        if (marker.equals(centerMarker)) {
            circle.setCenter(marker.getPosition());
            return true;
        }
        return false;
    }

    public void onStyleChange() {
        circle.setStrokeWidth(mWidthValue);
        circle.setFillColor(mFillColor);
        circle.setStrokeColor(mStrokeColor);
        circle.setRadius(radius);
    }

}
