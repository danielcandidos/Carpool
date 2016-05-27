package com.carpool.android.negocio;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.FiltroCarona;
import com.carpool.android.service.CaronaService;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaronaProcurarNegocio {

    private static ArrayList<LatLng> listaDePontos = new ArrayList<>();

    public ArrayList<Carona> procurarCaronas(FiltroCarona filtroCarona) throws Exception {

        ArrayList<Carona> listaCaronas = new ArrayList<>();

        try {
            this.validarFiltroCarona(filtroCarona);
            listaCaronas = CaronaService.procurarCaronas(filtroCarona);
        } catch (Exception excecao) {
            throw new Exception(excecao);
        }

        return listaCaronas;
    }

    private void validarFiltroCarona(FiltroCarona filtroCarona) throws Exception {
        try {

        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }

    public void pedirCarona(Carona caronaPedida) throws Exception {
        try {
            CaronaService.pedirCarona(caronaPedida);
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }

    public ArrayList<LatLng> procurarCarona(LatLng localizacaoAtual, double raio){

        ArrayList<LatLng> pontosNoRaio = new ArrayList<LatLng>();

        for (LatLng pontoReferenciaMotorista : listaDePontos) {
            if ((6371
                    * Math.acos(
                    Math.cos(Math.toRadians(localizacaoAtual.latitude)) *
                            Math.cos(Math.toRadians(pontoReferenciaMotorista.latitude)) *
                            Math.cos(Math.toRadians(localizacaoAtual.longitude) - Math.toRadians(pontoReferenciaMotorista.longitude)) +
                            Math.sin(Math.toRadians(localizacaoAtual.latitude)) *
                                    Math.sin(Math.toRadians(pontoReferenciaMotorista.latitude)))
            ) <= (raio / 1000)) {

                pontosNoRaio.add(pontoReferenciaMotorista);

            }
        }
        return pontosNoRaio;
    }

}
