package com.carpool.android.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carpool.android.R;

public class PerfilActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Mapeando e reconhecendo a toolbar da tela
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.buildToolbar();
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

}
