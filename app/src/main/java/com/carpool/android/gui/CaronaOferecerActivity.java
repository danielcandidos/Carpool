package com.carpool.android.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carpool.android.R;
import com.carpool.android.negocio.CaronaNegocio;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class CaronaOferecerActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

    private CaronaNegocio caronaNegocio = new CaronaNegocio();
    private ArrayList<LatLng> pontosReferencia = new ArrayList<>();
    private ArrayList<Marker> listaMarcadores = new ArrayList<>();


    private LatLng localizacaoAtual;
    private Toolbar toolbar;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferecer_carona);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(CaronaOferecerActivity.this, toolbar);

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Buscando e recuperando os valores da localizacao atual enviada pela tela anterior
        Intent intent = getIntent();
        localizacaoAtual = new LatLng(
                intent.getDoubleExtra(getString(R.string.latitude), 0),
                intent.getDoubleExtra(getString(R.string.longitude), 0));
    }

    /**
     * Pega a localização atual e adiciona a lista
     * de pontos de referência para caronas.
     *
     * @param view
     */
    public void oferecerCarona(View view) {
        //pontosReferencia.add(localizacaoAtual);
        for (Marker marcador : listaMarcadores) {
            pontosReferencia.add(marcador.getPosition());
        }
        caronaNegocio.oferecerCarona(pontosReferencia);

        Util.showMsgToastShort(CaronaOferecerActivity.this, "Sua carona foi ofertada! ;)");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Mapeia um DragListener e um LongClickListener para o mapa
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        this.setMarker(localizacaoAtual, true);
    }

    /**
     * Insere um marcador no mapa na localização indicada pela
     * latitude e longitude, com ou sem zoom.
     *
     * @param localizacao - coordenadas para inserir marcador
     * @param zoom - se terá ou não zoom ao marcador inserido
     */
    private void setMarker(LatLng localizacao, boolean zoom) {
        Marker marcadorNovo = mMap.addMarker(new MarkerOptions()
                .position(localizacao)
                .title(" x")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_0))
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool))
                .draggable(true));
        if (zoom){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15.0f));
        }
        this.listaMarcadores.add(marcadorNovo);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //mMap.clear();
        localizacaoAtual = latLng;
        this.setMarker(localizacaoAtual, false);
        Util.showMsgToastShort(CaronaOferecerActivity.this, "Ponto adicionado!");
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_x));
        //marker.setAnchor(0.5f, 2.0f);
        this.listaMarcadores.remove(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_0));
        //marker.setAnchor(0.5f, 1.0f);
        this.listaMarcadores.add(marker);
        Util.showMsgToastShort(CaronaOferecerActivity.this, "Ponto adicionado!");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        this.listaMarcadores.remove(marker); // remove o marcador da lista de marcadores
        marker.remove(); // remove o marcador do mapa
        Util.showMsgToastShort(CaronaOferecerActivity.this, "Ponto removido!");
    }
}
