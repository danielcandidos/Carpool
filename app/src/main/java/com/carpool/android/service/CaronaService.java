package com.carpool.android.service;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.FiltroCarona;
import com.carpool.android.dominio.PontoEndereco;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaronaService {
    private static ArrayList<Carona> listaCaronas = new ArrayList<>();

    public static ArrayList<Carona> procurarCaronas(FiltroCarona filtroCarona){

        PontoEndereco pontoBusca = filtroCarona.getPontoBusca();
        double raio = filtroCarona.getRaio();

        ArrayList<Carona> caronasNoRaio = new ArrayList<>();

        for (Carona carona : listaCaronas) {
            for (PontoEndereco ponto : carona.getItinerario().getListaPontosEndereco()){
                if ((6371
                        * Math.acos(
                        Math.cos(Math.toRadians(pontoBusca.getLatitude())) *
                                Math.cos(Math.toRadians(ponto.getLatitude())) *
                                Math.cos(Math.toRadians(pontoBusca.getLongitude()) - Math.toRadians(ponto.getLongitude())) +
                                Math.sin(Math.toRadians(pontoBusca.getLatitude())) *
                                        Math.sin(Math.toRadians(ponto.getLatitude())))
                ) <= (raio / 1000.0)) {
                    if (!caronasNoRaio.contains(carona)){
                        caronasNoRaio.add(carona);
                    }
                }
            }
        }
        return caronasNoRaio;
    }

    public static void pedirCarona(Carona caronaPedida){
        //conecta com webservice
        return;
    }

    public static void oferecerCarona(Carona carona){
        listaCaronas.add(carona);
        return;
    }

}
