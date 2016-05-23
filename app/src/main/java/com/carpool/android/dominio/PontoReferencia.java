package com.carpool.android.dominio;


public class PontoReferencia {
    private PontoEndereco ponto;
    private boolean ativo;
    private String nomePonto;

    public String getNomePonto() {
        return nomePonto;
    }

    public void setNomePonto(String nomePonto) {
        this.nomePonto = nomePonto;
    }

    public PontoEndereco getPonto() {
        return ponto;
    }

    public void setPonto(PontoEndereco ponto) {
        this.ponto = ponto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
