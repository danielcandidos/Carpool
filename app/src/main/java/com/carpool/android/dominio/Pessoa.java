package com.carpool.android.dominio;

import java.util.ArrayList;

public class Pessoa {
    private String nome;
    private String email;
    private String urlFoto;
    private boolean verificado;
    private PontoEndereco residencia;
    private ArrayList<Carro> listaCarros;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public PontoEndereco getResidencia() {
        return residencia;
    }

    public void setResidencia(PontoEndereco residencia) {
        this.residencia = residencia;
    }

    public ArrayList<Carro> getListaCarros() {
        return listaCarros;
    }

    public void setListaCarros(ArrayList<Carro> listaCarros) {
        this.listaCarros = listaCarros;
    }
}
