package com.carpool.android.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carpool.android.R;

public class CaronaInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_carona);

        // Mapeando e reconhecendo a toolbar da tela
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(CaronaInfoActivity.this, toolbar);
    }

    public void verPerfil(View view) {
        Util.trocarTela(CaronaInfoActivity.this, PerfilActivity.class);
    }

    public void pedirCarona(View view) {
        Util.showMsgToastLong(CaronaInfoActivity.this, "Pedindo carona... Aguarde");
    }
}
