package com.carpool.android.negocio;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.Itinerario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaronaNegocio {

    private static ArrayList<LatLng> listaDePontos = new ArrayList<>();
    private static Itinerario itinerarioOferecido;

    public void validarItinerario(Itinerario itinerario){
        if (true){
            this.itinerarioOferecido = itinerario;
        }
    }

    public Itinerario getItinerario(){
        return this.itinerarioOferecido;
    }

    public void oferecerCarona(Carona carona){
        this.validarCarona(carona);
        // chama persistencia para cadastrar no banco e trata erros retornados, se não tiver nenhum, finaliza, confirmando a carona
    }

    public boolean validarCarona(Carona carona){
        return true;
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
