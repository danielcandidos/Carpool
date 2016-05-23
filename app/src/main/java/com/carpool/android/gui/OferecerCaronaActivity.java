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

public class OferecerCaronaActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener {

    private CaronaNegocio caronaNegocio = new CaronaNegocio();
    private SharedPreferences preferences;
    private ArrayList<LatLng> pontosReferencia = new ArrayList<>();

    private LatLng localizacaoAtual;
    private Toolbar toolbar;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferecer_carona);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(OferecerCaronaActivity.this, toolbar);

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Buscando e recuperando os valores da localizacao atual enviada pela tela anterior
        Intent intent = getIntent();
        localizacaoAtual = new LatLng(
                intent.getDoubleExtra(getString(R.string.latitude), 0),
                intent.getDoubleExtra(getString(R.string.longitude), 0));
    }

    public void oferecerCarona(View view) {
        //PEGANDO INFORMAÇÕES DO PREFERENCES PARA VERIFICAR LOGIN ANTERIOR
        /*String login = preferences.getString("login", null);
        String senha = preferences.getString("senha", null);*/

        pontosReferencia.add(localizacaoAtual);
        caronaNegocio.oferecerCarona(pontosReferencia);

        // Recuperando Preferences, recuperando lista de pontos e setando novo ponto
        preferences = getSharedPreferences("pontos_referencia", Context.MODE_PRIVATE);
        /*pontos = (SetStringsPontos) preferences.getStringSet("pontos", new SetStringsPontos());
        pontos.add(localizacaoAtual.latitude+"/"+localizacaoAtual.longitude);*/
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pontos", localizacaoAtual.latitude + "/" + localizacaoAtual.longitude);
        editor.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Mapeia um DragListener e um LongClickListener para o mapa
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

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
        mMap.addMarker(new MarkerOptions()
                .position(localizacao)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool)))
                .setDraggable(true);
        if (zoom){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15.0f));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        localizacaoAtual = latLng;

        this.setMarker(localizacaoAtual, false);
    }

    private void onMarkerMoved(Marker marker) {
        mMap.clear();
        localizacaoAtual = marker.getPosition();

        this.setMarker(localizacaoAtual, false);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        this.onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        this.onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        this.onMarkerMoved(marker);
    }
}
