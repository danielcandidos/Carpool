package com.carpool.android.gui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OferecerCaronaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LatLng localizacaoAtual;
    private Toolbar toolbar;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferecer_carona);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.buildToolbar();

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

    public void oferecerCarona (View view) {
        Util.showMsgToastLong(OferecerCaronaActivity.this, "Em desenvolvimento.");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions()
                .position(localizacaoAtual)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 15.0f));
    }
}
