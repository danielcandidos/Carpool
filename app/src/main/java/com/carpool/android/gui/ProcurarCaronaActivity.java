package com.carpool.android.gui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.carpool.android.R;
import com.carpool.android.dominio.CirclePoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class ProcurarCaronaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private static final int RADIUS_MAX = 3000;
    private static final int RADIUS_INIT = 1000;
    private int mRadiusValue;
    private LatLng localizacaoAtual;
    private DraggableCircle draggableCircle;

    private Toolbar toolbar;
    private GoogleMap mMap;
    private TextView txtRaio;
    private SeekBar seekbarRaio;
    private EditText edtHorarioInicio;
    private EditText edtHorarioFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar_carona);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(ProcurarCaronaActivity.this, toolbar);

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Mapeando o TextView referente ao numero dinamico do raio de busca
        txtRaio = (TextView) findViewById(R.id.txtRaio);

        // Mapeando o SeekBar referente ao raio de busca e inicializando valores padroes
        seekbarRaio = (SeekBar) findViewById(R.id.seekbarRaio);
        seekbarRaio.setMax(RADIUS_MAX);
        seekbarRaio.setProgress(RADIUS_INIT);
        mRadiusValue = seekbarRaio.getProgress();

        // Mapeando EditText que abrem Pickers de horarios
        edtHorarioInicio = (EditText) findViewById(R.id.edtHorarioInicio);
        edtHorarioFim = (EditText) findViewById(R.id.edtHorarioFim);

        // Buscando e recuperando os valores da localizacao atual enviada pela tela anterior
        Intent intent = getIntent();
        localizacaoAtual = new LatLng(
                intent.getDoubleExtra("latitude", 0),
                intent.getDoubleExtra("longitude", 0));
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
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, mRadiusValue);
        mMap = draggableCircle.getmMap();

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
        draggableCircle.setRadius(mRadiusValue);
        mMap = draggableCircle.getmMap();
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
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, mRadiusValue);
        mMap = draggableCircle.getmMap();

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
        localizacaoAtual = draggableCircle.onMarkerMoved(marker);
    }

    // Metódo para gerar novo circulo, com clique longo no mapa
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        localizacaoAtual = latLng;
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, mRadiusValue);
        mMap = draggableCircle.getmMap();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Util.trocarTela(ProcurarCaronaActivity.this, PerfilActivity.class);
        return false;
    }

}
