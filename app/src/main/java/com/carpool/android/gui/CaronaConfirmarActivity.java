package com.carpool.android.gui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.carpool.android.R;
import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.Carro;
import com.carpool.android.dominio.Itinerario;
import com.carpool.android.negocio.CaronaNegocio;

import java.util.Calendar;

public class CaronaConfirmarActivity extends AppCompatActivity {

    private CaronaNegocio caronaNegocio;
    private Carro carro;

    private Toolbar toolbar;
    private EditText edtNomeCarona, edtCarroCarona, edtHorarioPartidaCarona;
    private Spinner spnVagasCarona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carona_confirmar);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(CaronaConfirmarActivity.this, toolbar);

        // Mapeando elementos de tela
        edtNomeCarona = (EditText) findViewById(R.id.edtNomeCarona);
        edtCarroCarona = (EditText) findViewById(R.id.edtCarroCarona);
        edtHorarioPartidaCarona = (EditText) findViewById(R.id.edtHorarioPartidaCarona);
        spnVagasCarona = (Spinner) findViewById(R.id.spnVagasCarona);

        caronaNegocio = new CaronaNegocio();
    }

    /**
     * Método para selecionar horário de partida para a carona
     *
     * @param view
     */
    public void setHorarioPartida(View view) {
        final EditText edtHorarioPartida = (EditText) view;
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(
                CaronaConfirmarActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int horaSelecionada, int minutoSelecionado) {
                        String horarioTemp = horaSelecionada + ":" + minutoSelecionado;
                        edtHorarioPartida.setText(horarioTemp);
                    }
                },
                hora,
                minuto,
                true);// 24 horas ou 12 horas am/pm
        timePickerDialog.setTitle(getString(R.string.horario_partida));
        timePickerDialog.show();
    }

    /**
     * Método para selecionar/alterar carro que será utlilizado na carona
     *
     * @param view
     */
    public void setCarro(View view) {
        final AlertDialog alertDialog = new AlertDialog.Builder(CaronaConfirmarActivity.this).create();
        alertDialog.setTitle(getString(R.string.titulo_trocar_carro));
        alertDialog.setMessage(getString(R.string.msg_trocar_carro));
        alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                getString(R.string.btn_positivo_sim),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.trocarTela(CaronaConfirmarActivity.this, PerfilActivity.class);
                    }
                });
        alertDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                getString(R.string.btn_negativo_cancelar),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void confirmarOferecerCarona(View view) {
        Carona caronaMontada = this.montarCarona();
        try {
            caronaNegocio.oferecerCarona(caronaMontada);
        } catch (Exception exception){
            // TRATAR ERRO
        }
        Util.showMsgToastShort(CaronaConfirmarActivity.this, "Sua carona foi ofertada! ;)");
        this.finish();
    }

    private Carona montarCarona() {
        Itinerario itinerario = caronaNegocio.getItinerario();

        Carona caronaNova = new Carona();
        caronaNova.setNomeCarona(edtNomeCarona.getText().toString());
        caronaNova.setCarro(carro);
        caronaNova.setHorario(edtHorarioPartidaCarona.getText().toString());
        caronaNova.setVagas(Integer.valueOf(spnVagasCarona.getSelectedItem().toString()));
        caronaNova.setItinerario(itinerario);

        return caronaNova;
    }

}