package com.carpool.android.dominio;


public class PontoEndereco {
	private int idPontoEndereco;
	private int idItinerario;
    private double latitude;
    private double longitude;
    
	public int getIdPontoEndereco() {
		return idPontoEndereco;
	}
	public void setIdPontoEndereco(int idPontoEndereco) {
		this.idPontoEndereco = idPontoEndereco;
	}
	public int getIdItinerario() {
		return idItinerario;
	}
	public void setIdItinerario(int idItinerario) {
		this.idItinerario = idItinerario;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
