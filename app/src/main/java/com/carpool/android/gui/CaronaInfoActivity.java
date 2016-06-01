package com.carpool.android.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.carpool.android.R;
import com.carpool.android.dominio.Carona;
import com.carpool.android.negocio.CaronaProcurarNegocio;

public class CaronaInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtPerfilNome, txtNomeCarona, txtHorarioSaida, txtVagasCarona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_carona);

        // Mapeando e reconhecendo a toolbar da tela
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(CaronaInfoActivity.this, toolbar);

        // Mapeando objetos da tela
        txtPerfilNome = (TextView) findViewById(R.id.txtPerfilNome);
        txtNomeCarona = (TextView) findViewById(R.id.txtNomeCarona);
        txtHorarioSaida = (TextView) findViewById(R.id.txtHorarioSaida);
        txtVagasCarona = (TextView) findViewById(R.id.txtVagasCarona);

        // Carregando objetos da tela
        if(CaronaProcurarNegocio.getCaronaPedida() != null){
            Carona caronaBuscada = CaronaProcurarNegocio.getCaronaPedida();
            txtPerfilNome.setText(caronaBuscada.getItinerario().getMotorista().getNomeUsuario());
            txtNomeCarona.setText(caronaBuscada.getNomeCarona());
            txtHorarioSaida.setText(caronaBuscada.getHorarioSaida());
            txtVagasCarona.setText(String.valueOf(caronaBuscada.getVagasCarona()));
        }
    }

    public void verPerfil(View view) {
        Util.trocarTela(CaronaInfoActivity.this, PerfilActivity.class);
    }

    public void pedirCarona(View view) {
        Util.showMsgToastLong(CaronaInfoActivity.this, "Pedindo carona... Aguarde");
    }
}
