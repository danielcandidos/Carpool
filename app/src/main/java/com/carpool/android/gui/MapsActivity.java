package com.carpool.android.gui;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carpool.android.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LatLng localizacaoAtual;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Mapeando e reconhecendo a toolbar da tela
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Chamando metodo que cria e inicializa o NavigationDrawer
        Util.buildDrawer(MapsActivity.this, toolbar);

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;

        // Ativa a identificação da localização atual no google maps (pontinho azul no mapa)
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Util.showMsgToastLong(MapsActivity.this, e.getMessage().toString());
        }

        // Cria um 'Listener' para tratar o evento disparado todas as vezes que a localização é alterada
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location minhaLocalizacao) {
                // Limpa o mapa atual retirando marcadores, elipses, etc
                mMap.clear();

                // Atribue o valor da localizacao atual para o objeto LatLng localizacaoAtual
                localizacaoAtual = new LatLng(
                        minhaLocalizacao.getLatitude(),
                        minhaLocalizacao.getLongitude());

                // Adiciona um marcador no mapa para a localizacao atual
                mMap.addMarker(new MarkerOptions()
                        .position(localizacaoAtual)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 15.0f));

                /* Faz o processo de animação para a posição atual no mapa
                CameraPosition updateMinhaLocalizacao = new CameraPosition(localizacaoAtual, 17, 0, 0);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateMinhaLocalizacao), 2000, null);*/
            }
        });
    }

    public void irProcurarCarona (View view) {
        this.trocarTela(MapsActivity.this, ProcurarCaronaActivity.class, localizacaoAtual);
    }

    public void irOferecerCarona (View view) {
        this.trocarTela(MapsActivity.this, OferecerCaronaActivity.class, localizacaoAtual);
    }

    private void trocarTela (Activity activityAtual, Class activitySeguinte, LatLng ponto) {
        Intent intent = new Intent(activityAtual, activitySeguinte);
        intent.putExtra("latitude", ponto.latitude);
        intent.putExtra("longitude", ponto.longitude);
        activityAtual.startActivity(intent);
    }
}
