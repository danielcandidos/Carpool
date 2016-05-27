package com.carpool.android.dominio;

public class Carro {
	private int idCarro;
	private Usuario usuario;
    private String marca;
    private String modelo;
    private String cor;
    private boolean temArCondicionado;
    
	public int getIdCarro() {
		return idCarro;
	}
	public void setIdCarro(int idCarro) {
		this.idCarro = idCarro;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public boolean isTemArCondicionado() {
		return temArCondicionado;
	}
	public void setTemArCondicionado(boolean temArCondicionado) {
		this.temArCondicionado = temArCondicionado;
	}
    
}
