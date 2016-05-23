package com.carpool.android.dominio;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Carona {
    private Itinerario itinerario;
    private ArrayList<Pessoa> caroneiros;
    private String nomeCarona;
    private int vagas;
    private Date data;
    private String horario;
    private Carro carro;

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public ArrayList<Pessoa> getCaroneiros() {
        return caroneiros;
    }

    public void setCaroneiros(ArrayList<Pessoa> caroneiros) {
        this.caroneiros = caroneiros;
    }

    public String getNomeCarona() {
        return nomeCarona;
    }

    public void setNomeCarona(String nomeCarona) {
        this.nomeCarona = nomeCarona;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

}
