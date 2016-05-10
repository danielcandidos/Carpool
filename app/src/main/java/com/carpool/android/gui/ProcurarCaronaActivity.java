package com.carpool.android.gui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.carpool.android.R;
import com.carpool.android.dominio.CirclePoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProcurarCaronaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private LatLng teste;

    private static final int RADIUS_MAX = 3000;
    private final int mStrokeColor = -16777216;
    private final int mFillColor = 680656640;
    private final int mWidthValue = 5;

    private int mRadiusValue;
    private List<DraggableCircle> mCircles = new ArrayList<DraggableCircle>(1);
    private LatLng localizacaoAtual;

    private Toolbar toolbar;
    private GoogleMap mMap;
    private TextView txtRaio;
    private SeekBar seekbarRaio;
    private EditText edtHorarioInicio;
    private EditText edtHorarioFim;

    private class DraggableCircle {

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar_carona);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.buildToolbar();

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Mapeando o TextView referente ao numero dinamico do raio de busca
        txtRaio = (TextView) findViewById(R.id.txtRaio);

        // Mapeando o SeekBar referente ao raio de busca e inicializando valores padroes
        seekbarRaio = (SeekBar) findViewById(R.id.seekbarRaio);
        seekbarRaio.setMax(RADIUS_MAX);
        mRadiusValue = 1000;
        seekbarRaio.setProgress(mRadiusValue);

        // Testando Pickers de horarios
        edtHorarioInicio = (EditText) findViewById(R.id.edtHorarioInicio);
        edtHorarioFim = (EditText) findViewById(R.id.edtHorarioFim);

        // Buscando e recuperando os valores da localizacao atual enviada pela tela anterior
        Intent intent = getIntent();
        localizacaoAtual = new LatLng(
                intent.getDoubleExtra("latitude", 0),
                intent.getDoubleExtra("longitude", 0));
    }

    private void buildToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechar();
            }
        });
    }

    private void fechar(){
        this.finish();
    }

    public void setTime(View view){
        final EditText edtHorario = (EditText) view;

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(
                ProcurarCaronaActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtHorario.setText(selectedHour + ":" + selectedMinute);
                    }
                },
                hour,
                minute,
                true);// Yes 24 hour time

        if (edtHorario.equals(edtHorarioInicio)){
            timePickerDialog.setTitle("Horário início");
        } else if (edtHorario.equals(edtHorarioFim)) {
            timePickerDialog.setTitle("Horário fim");
        }
        timePickerDialog.show();
    }

    public void procurarPontos(View view){
        mMap.clear();
        DraggableCircle circle = new DraggableCircle(localizacaoAtual, mRadiusValue);
        mCircles.add(circle);
        //LatLng centro = new LatLng(teste.latitude, teste.longitude);

        ArrayList<CirclePoints> pontos = new ArrayList<CirclePoints>();

        CirclePoints points1 = new CirclePoints(-8.0289460,-34.9218160); // Casa gabi
        pontos.add(points1);
        CirclePoints points2 = new CirclePoints(-8.01579630,-34.9503266); // Rural
        pontos.add(points2);
        CirclePoints points3 = new CirclePoints(-8.0275370,-34.9137170); // Treze de maio
        pontos.add(points3);
        CirclePoints points4 = new CirclePoints(-8.0291815,-34.9067797); // Hospital
        pontos.add(points4);
        CirclePoints points5 = new CirclePoints(-8.0291820,-34.9067780); // Qualquer coisa
        pontos.add(points5);
        CirclePoints points6 = new CirclePoints(-8.0520590,-34.9451160); // Reitoria UFPE
        pontos.add(points6);
        CirclePoints points7 = new CirclePoints(-8.0486960,-34.9447970); // Sudene
        pontos.add(points7);
        CirclePoints points8 = new CirclePoints(-8.0588620,-34.9475320); // IFPE
        pontos.add(points8);

        ArrayList<CirclePoints> pontosNoRaio = new ArrayList<CirclePoints>();

        for (CirclePoints points : pontos){
            if ((6371
                    * Math.acos(
                    //Math.cos(Math.toRadians(-8.0268792)) *
                    Math.cos(Math.toRadians(localizacaoAtual.latitude)) *
                            Math.cos(Math.toRadians(points.getLatitude())) *
                            //Math.cos(Math.toRadians(-34.9147138) - Math.toRadians(points.getLongitude())) +
                            Math.cos(Math.toRadians(localizacaoAtual.longitude) - Math.toRadians(points.getLongitude())) +
                            //Math.sin(Math.toRadians(-8.0268792)) *
                            Math.sin(Math.toRadians(localizacaoAtual.latitude)) *
                                    Math.sin(Math.toRadians(points.getLatitude())))
            ) <= (mRadiusValue/1000)){

                pontosNoRaio.add(points);

            }
        }

        int i = 0;
        for (CirclePoints points : pontosNoRaio){
            i += 1;
            LatLng agora = new LatLng(points.getLatitude(), points.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(agora)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro))
                    .title("Ponto" + i));
        }
    }

    /// - - - - - - - REGION SEEKBAR LISTENER - - - - - - - ///
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekbarRaio){
            mRadiusValue = seekbarRaio.getProgress();
            txtRaio.setText("Raio de busca: "+mRadiusValue+" m");
        }
        for (DraggableCircle draggableCircle : mCircles) {
            draggableCircle.onStyleChange();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }


    /// - - - - - - - REGION ON MAP READER - - - - - - - ///
    @Override
     public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Mapeia um ChageListener para o SeekBar
        seekbarRaio.setOnSeekBarChangeListener(this);

        // Mapeia um DragListener e um LongClickListener para o mapa
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        // Cria um objeto DraggableCircle para inicializar o efeito de raio
        DraggableCircle circle = new DraggableCircle(localizacaoAtual, mRadiusValue);
        mCircles.add(circle);

        // Move the map so that it is centered on the initial circle
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 14.5f));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        onMarkerMoved(marker);
    }

    private void onMarkerMoved(Marker marker) {
        for (DraggableCircle draggableCircle : mCircles) {
            if (draggableCircle.onMarkerMoved(marker)) {
                break;
            }
        }
    }

    // Metódo para gerar novo circulo, com clique longo no mapa
    @Override
    public void onMapLongClick(LatLng latLng) {
        View view = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map))
                .getView();
        mMap.clear();
        localizacaoAtual = latLng;
        DraggableCircle circle = new DraggableCircle(localizacaoAtual, mRadiusValue);
        mCircles.add(circle);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Util.trocarTela(ProcurarCaronaActivity.this, PerfilActivity.class);
        return false;
    }

}
