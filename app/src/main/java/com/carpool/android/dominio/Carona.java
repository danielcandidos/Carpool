package com.carpool.android.dominio;

import java.util.ArrayList;

public class Carona {
	private int idCarona;
    private Itinerario itinerario;
    private String nomeCarona;
    private int vagasCarona;
    private String horarioSaida;
    private ArrayList<Usuario> caroneiros;
    private Carro carro;
    
	public int getIdCarona() {
		return idCarona;
	}
	public void setIdCarona(int idCarona) {
		this.idCarona = idCarona;
	}
	public Itinerario getItinerario() {
		return itinerario;
	}
	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}
	public String getNomeCarona() {
		return nomeCarona;
	}
	public void setNomeCarona(String nomeCarona) {
		this.nomeCarona = nomeCarona;
	}
	public int getVagasCarona() {
		return vagasCarona;
	}
	public void setVagasCarona(int vagasCarona) {
		this.vagasCarona = vagasCarona;
	}
	public String getHorarioSaida() {
		return horarioSaida;
	}
	public void setHorarioSaida(String horarioSaida) {
		this.horarioSaida = horarioSaida;
	}
	public ArrayList<Usuario> getCaroneiros() {
		return caroneiros;
	}
	public void setCaroneiros(ArrayList<Usuario> caroneiros) {
		this.caroneiros = caroneiros;
	}
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}

}
