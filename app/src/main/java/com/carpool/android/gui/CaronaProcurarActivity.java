package com.carpool.android.gui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.carpool.android.R;
import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.FiltroCarona;
import com.carpool.android.dominio.PontoEndereco;
import com.carpool.android.negocio.CaronaProcurarNegocio;
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
import java.util.HashMap;

public class CaronaProcurarActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

    private CaronaProcurarNegocio caronaProcurarNegocio = new CaronaProcurarNegocio();
    private ArrayList<Carona> listaCaronasVisiveis = new ArrayList<>();

    private static final int RADIUS_MAX = 3000;
    private static final int RADIUS_INIT = 1000;
    private int raioAtual;
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
        Util.buildToolbarHomeButton(CaronaProcurarActivity.this, toolbar);

        // Obtendo o SupportMapFragment para ser notificado quando o mapa estiver pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Mapeando o TextView referente ao numero dinamico do raio de busca
        txtRaio = (TextView) findViewById(R.id.txtRaio);

        // Mapeando o SeekBar referente ao raio de busca e inicializando valores padroes
        seekbarRaio = (SeekBar) findViewById(R.id.seekbarRaio);
        seekbarRaio.setMax(RADIUS_MAX);
        seekbarRaio.setProgress(RADIUS_INIT);
        raioAtual = seekbarRaio.getProgress();

        // Mapeando EditText que abrem Pickers de horarios
        edtHorarioInicio = (EditText) findViewById(R.id.edtHorarioInicio);
        edtHorarioFim = (EditText) findViewById(R.id.edtHorarioFim);

        // Buscando e recuperando os valores da localizacao atual enviada pela tela anterior
        Intent intent = getIntent();
        localizacaoAtual = new LatLng(
                intent.getDoubleExtra(getString(R.string.latitude), 0),
                intent.getDoubleExtra(getString(R.string.longitude), 0));
    }

    /**
     * Método para selecionar horário de inicio e fim para busca de caronas
     *
     * @param view
     */
    public void setTime(View view) {
        final EditText edtHorario = (EditText) view;
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(
                CaronaProcurarActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String edtHorarioTemp = selectedHour + ":" + selectedMinute;
                        edtHorario.setText(edtHorarioTemp);
                    }
                },
                hora,
                minuto,
                true);// 24 horas ou 12 horas am/pm

        if (edtHorario.equals(edtHorarioInicio)) {
            timePickerDialog.setTitle(getString(R.string.horario_inicio));
        } else if (edtHorario.equals(edtHorarioFim)) {
            timePickerDialog.setTitle(getString(R.string.horario_fim));
        }
        timePickerDialog.show();
    }

    public void procurarPontos(View view) {
        mMap.clear();
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, raioAtual);
        mMap = draggableCircle.getmMap();

        PontoEndereco pontoBusca = new PontoEndereco();
        pontoBusca.setLatitude(this.localizacaoAtual.latitude);
        pontoBusca.setLongitude(this.localizacaoAtual.longitude);

        FiltroCarona filtroCarona = new FiltroCarona();
        filtroCarona.setPontoBusca(pontoBusca);
        filtroCarona.setRaio(raioAtual);
        filtroCarona.setHorarioInicio(edtHorarioInicio.getText().toString());
        filtroCarona.setHorarioFim(edtHorarioFim.getText().toString());
        filtroCarona.setPagar(true);

        try {
            listaCaronasVisiveis = caronaProcurarNegocio.procurarCaronas(filtroCarona);
        } catch (Exception excecao) {
            Util.showMsgToastLong(CaronaProcurarActivity.this, excecao.getMessage().toString());
        }

        if (!(listaCaronasVisiveis.size() > 0)){
            Util.showMsgToastLong(
                    CaronaProcurarActivity.this,
                    getString(R.string.nenhuma_carona) + getString(R.string.novos_filtros));
        } else {
            int i = 0;
            for (Carona carona : listaCaronasVisiveis) {
                for (PontoEndereco ponto : carona.getItinerario().getListaPontosEndereco()) {
                    i += 1;
                    LatLng pontoParadaCarona = new LatLng(ponto.getLatitude(), ponto.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(pontoParadaCarona)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro))
                            .title(carona.getNomeCarona()+ " Ponto" + i));
                }
            }
        }

    }

    /// - - - - - - - AÇÕES SEEKBAR - - - - - - - ///
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekbarRaio) {
            raioAtual = seekbarRaio.getProgress();
            String txtRaioTemp = getString(R.string.raio_de_busca) + raioAtual + getString(R.string.metros);
            txtRaio.setText(txtRaioTemp);
        }
        draggableCircle.setRadius(raioAtual);
        mMap = draggableCircle.getmMap();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /// - - - - - - - AÇÕES DO MAPA - - - - - - - ///
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Mapeia um ChageListener para o SeekBar
        seekbarRaio.setOnSeekBarChangeListener(this);

        // Mapeia um DragListener e um LongClickListener para o mapa
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        // Cria um objeto DraggableCircle para inicializar o efeito de raio
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, raioAtual);
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

    /**
     * Metódo para gerar novo circulo, com clique longo no mapa
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        localizacaoAtual = latLng;
        draggableCircle = new DraggableCircle(mMap, localizacaoAtual, raioAtual);
        mMap = draggableCircle.getmMap();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Util.showMsgToastLong(CaronaProcurarActivity.this, marker.getId().toString());
        Util.trocarTela(CaronaProcurarActivity.this, CaronaInfoActivity.class);
    }
}
