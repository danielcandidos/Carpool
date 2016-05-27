package com.carpool.android.dominio;

public class FiltroCarona {
	private PontoEndereco pontoBusca;
	private double raio;
	private String horarioInicio;
	private String horarioFim;
	private boolean pagar;

	public PontoEndereco getPontoBusca() {
		return pontoBusca;
	}

	public void setPontoBusca(PontoEndereco pontoBusca) {
		this.pontoBusca = pontoBusca;
	}

	public double getRaio() {
		return raio;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}

	public String getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(String hoararioInicio) {
		this.horarioInicio = hoararioInicio;
	}

	public String getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(String horarioFim) {
		this.horarioFim = horarioFim;
	}

	public boolean isPagar() {
		return pagar;
	}

	public void setPagar(boolean pagar) {
		this.pagar = pagar;
	}

}
