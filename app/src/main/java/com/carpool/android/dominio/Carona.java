package com.carpool.android.dominio;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Carona {
    private LatLng coordenada;
    private Carro carro;
    private Pessoa motorista;

    public LatLng getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(LatLng coordenada) {
        this.coordenada = coordenada;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Pessoa getMotorista() {
        return motorista;
    }

    public void setMotorista(Pessoa motorista) {
        this.motorista = motorista;
    }

    public ArrayList<Pessoa> getCaroneiros() {
        return caroneiros;
    }

    public void setCaroneiros(ArrayList<Pessoa> caroneiros) {
        this.caroneiros = caroneiros;
    }

    private ArrayList<Pessoa> caroneiros;
}
