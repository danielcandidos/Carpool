package com.carpool.android.dominio;

public class Instituicao {
	private int idInstituicao;
	private String nomeCompleto;
	private String nomeAbreviado;
	private PontoEndereco pontoEndereco;
	
	public int getIdInstituicao() {
		return idInstituicao;
	}
	public void setIdInstituicao(int idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getNomeAbreviado() {
		return nomeAbreviado;
	}
	public void setNomeAbreviado(String nomeAbreviado) {
		this.nomeAbreviado = nomeAbreviado;
	}
	public PontoEndereco getPontoEndereco() {
		return pontoEndereco;
	}
	public void setPontoEndereco(PontoEndereco pontoEndereco) {
		this.pontoEndereco = pontoEndereco;
	}
	
}
