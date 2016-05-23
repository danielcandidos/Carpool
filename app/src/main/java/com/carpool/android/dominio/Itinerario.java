package com.carpool.android.dominio;


import java.util.ArrayList;

public class Itinerario {
    private Pessoa motorista = null;
    private PontoEndereco origem = null;
    private PontoEndereco destino = null;
    private String nomeItinerario = "";
    private ArrayList<PontoReferencia> listaPontosReferencia = new ArrayList<PontoReferencia>();

    public Pessoa getMotorista() {
        return motorista;
    }

    public void setMotorista(Pessoa motorista) {
        this.motorista = motorista;
    }

    public PontoEndereco getOrigem() {
        return origem;
    }

    public void setOrigem(PontoEndereco origem) {
        this.origem = origem;
    }

    public PontoEndereco getDestino() {
        return destino;
    }

    public void setDestino(PontoEndereco destino) {
        this.destino = destino;
    }

    public String getNomeItinerario() {
        return nomeItinerario;
    }

    public void setNomeItinerario(String nomeItinerario) {
        this.nomeItinerario = nomeItinerario;
    }

    public ArrayList<PontoReferencia> getListaPontosReferencia() {
        return listaPontosReferencia;
    }

    public void setListaPontosReferencia(ArrayList<PontoReferencia> listaPontosReferencia) {
        this.listaPontosReferencia = listaPontosReferencia;
    }
}
