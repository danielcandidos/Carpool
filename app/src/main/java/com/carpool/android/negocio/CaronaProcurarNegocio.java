package com.carpool.android.negocio;


import com.carpool.android.dominio.PontoEndereco;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaronaProcurarNegocio {

    private static ArrayList<LatLng> listaDePontos = new ArrayList<>();

    public void procurarCaronas(PontoEndereco pontoEndereco, double raio){


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

    public void pedirCarona(){

    }

    public void aceitarCaroneiro(){

    }

}
