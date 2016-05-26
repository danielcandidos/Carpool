package com.carpool.android.gui;

import com.carpool.android.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DraggableCircle {

    private final int mStrokeColor = -7829368;
    private final int mFillColor = 680656640;
    private final int mWidthValue = 3;

    private GoogleMap mMap;
    private final Marker centerMarker;
    private final Circle circle;
    private int radiusCircle;

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setRadius(int radius) {
        this.radiusCircle = radius;
        this.onStyleChange();
    }

    public DraggableCircle(GoogleMap map, LatLng center, int radius) {
        this.mMap = map;
        this.radiusCircle = radius;

        centerMarker = mMap.addMarker(new MarkerOptions()
                .position(center)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool))
                .draggable(true));
        circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radiusCircle)
                .strokeWidth(mWidthValue)
                .strokeColor(mStrokeColor)
                .fillColor(mFillColor));
    }

    public LatLng onMarkerMoved(Marker marker) {
        if (marker.equals(centerMarker)) {
            circle.setCenter(marker.getPosition());
        }
        return marker.getPosition();
    }

    public void onStyleChange() {
        circle.setStrokeWidth(mWidthValue);
        circle.setFillColor(mFillColor);
        circle.setStrokeColor(mStrokeColor);
        circle.setRadius(radiusCircle);
    }

    /*private class DraggableCircle {

        private final Marker centerMarker;
        private final Circle circle;
        private double radius;

        public DraggableCircle(LatLng center, double radius) {
            this.radius = radius;
            centerMarker = mMap.addMarker(new MarkerOptions()
                    .position(center)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool))
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
                localizacaoAtual = marker.getPosition();
                circle.setCenter(localizacaoAtual);
                return true;
            }
            return false;
        }

        public void onStyleChange() {
            circle.setStrokeWidth(mWidthValue);
            circle.setFillColor(mFillColor);
            circle.setStrokeColor(mStrokeColor);
            circle.setRadius(mRadiusValue);
        }
    }*/

}
