package com.carpool.android.dominio;

import java.util.ArrayList;

public class Itinerario {
	private int idItinerario;
    private Usuario motorista;
    private String nomeItinerario;
    private ArrayList<PontoEndereco> listaPontosEndereco = new ArrayList<PontoEndereco>();
	
    public int getIdItinerario() {
		return idItinerario;
	}
	public void setIdItinerario(int idItinerario) {
		this.idItinerario = idItinerario;
	}
	public Usuario getMotorista() {
		return motorista;
	}
	public void setMotorista(Usuario motorista) {
		this.motorista = motorista;
	}
	public String getNomeItinerario() {
		return nomeItinerario;
	}
	public void setNomeItinerario(String nomeItinerario) {
		this.nomeItinerario = nomeItinerario;
	}
	public ArrayList<PontoEndereco> getListaPontosEndereco() {
		return listaPontosEndereco;
	}
	public void setListaPontosEndereco(ArrayList<PontoEndereco> listaPontosEndereco) {
		this.listaPontosEndereco = listaPontosEndereco;
	}
    
}
