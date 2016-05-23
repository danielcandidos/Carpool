package com.carpool.android.negocio;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaronaNegocio {

    private static ArrayList<LatLng> listaDePontos = new ArrayList<>();

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

    public void oferecerCarona(ArrayList<LatLng> pontosReferenciaMotorista){
        //chamar persistencia
        for (LatLng coordenada : pontosReferenciaMotorista) {
            listaDePontos.add(coordenada);
        }
    }

    public void oferecerCarona(LatLng pontoReferenciaMotorista){
        //chamar persistencia
        listaDePontos.add(pontoReferenciaMotorista);
    }

    public void aceitarCaroneiro(){

    }

}
