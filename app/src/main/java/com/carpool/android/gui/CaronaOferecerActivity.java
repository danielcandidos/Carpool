package com.carpool.android.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.carpool.android.R;
import com.carpool.android.dominio.Itinerario;
import com.carpool.android.dominio.PontoEndereco;
import com.carpool.android.negocio.CaronaOferecerNegocio;
import com.carpool.android.negocio.UsuarioNegocio;
import com.carpool.android.service.UsuarioService;
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

    private CaronaOferecerNegocio caronaOferecerNegocio = new CaronaOferecerNegocio();
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

    public void oferecerCarona(View view) {
        Itinerario itinerarioCriado = this.montarItinerario(); // monta e cria uma instancia de Itinerário

        // chama metodo de negocio para verificar Itinerario
        try {
            caronaOferecerNegocio.validarItinerario(itinerarioCriado);
        } catch (Exception exception){
            // TRATAR ERRO
        }
        // com tudo certo, com o itinerário já salvo estaticamente em negócio, chama tela de confirmação de carona
        Util.trocarTela(CaronaOferecerActivity.this, CaronaConfirmarActivity.class);
    }

    private Itinerario montarItinerario(){
        ArrayList<PontoEndereco> listaPontosReferencia =  new ArrayList<>();

        for (Marker marcador : listaMarcadores) {
            LatLng ponto = marcador.getPosition();

            PontoEndereco pontoEndereco = new PontoEndereco();
            pontoEndereco.setLatitude(ponto.latitude);
            pontoEndereco.setLongitude(ponto.longitude);

            listaPontosReferencia.add(pontoEndereco);
        }

        UsuarioNegocio usuarioNegocio = new UsuarioNegocio();

        Itinerario itinerario = new Itinerario();
        itinerario.setNomeItinerario("Nome provisório");
        itinerario.setMotorista(usuarioNegocio.getUsuarioLogado());
        itinerario.setListaPontosEndereco(listaPontosReferencia);

        return itinerario;
    }

    /// - - - - - - - - - AÇÕES DO MAPA - - - - - - - - - ///
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
                .title("  x")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_0))
                .draggable(true));
        if (zoom){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15.0f));
        }
        this.listaMarcadores.add(marcadorNovo); // adiciona o marcador na lista de marcadores
    }

    /**
     * Chamado quando ocorre um longo clique no mapa
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        localizacaoAtual = latLng;
        this.setMarker(localizacaoAtual, false);
        Util.showMsgToastShort(CaronaOferecerActivity.this, getString(R.string.msg_ponto_adicionado));
    }

    /**
     * Chamado quando um marcador começa a ser arrastado
     *
     * @param marker
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_x)); // troca para a imagem do marcador com um X na base
        //marker.setAnchor(0.5f, 2.0f);
        this.listaMarcadores.remove(marker); // remove o marcador da lista de marcadores
    }

    /**
     * Chamado constantemente durante o movimento do marcador
     * @param marker
     */
    @Override
    public void onMarkerDrag(Marker marker) {

    }

    /**
     * Chamado ao término do movimento do marcador
     *
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.carpool_test_0)); // troca para a imagem normal do marcador
        //marker.setAnchor(0.5f, 1.0f);
        this.listaMarcadores.add(marker); // adiciona o marcador na lista de marcadores
        Util.showMsgToastShort(CaronaOferecerActivity.this, getString(R.string.msg_ponto_alterado));
    }

    /**
     * Chamado quando a janela de informações de algum marcador é clicada
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        this.listaMarcadores.remove(marker); // remove o marcador da lista de marcadores
        marker.remove(); // remove o marcador do mapa
        Util.showMsgToastShort(CaronaOferecerActivity.this, getString(R.string.msg_ponto_removido));
    }

    /// - - - - - - - - - AÇÕES DO MENU - - - - - - - - - ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_carona_oferecer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_settings:
                Util.showMsgToast(MainActivity2.this, "Configurações");
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Util.showMsgToast(MainActivity2.this, "Favoritos");
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
