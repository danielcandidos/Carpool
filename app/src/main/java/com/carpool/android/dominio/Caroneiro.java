package com.carpool.android.dominio;

public class Caroneiro {
	private int idCaroneiro;
	private Usuario usuarioCaroneiro;
	private boolean confirmado;
	
	public int getIdCaroneiro() {
		return idCaroneiro;
	}
	public void setIdCaroneiro(int idCaroneiro) {
		this.idCaroneiro = idCaroneiro;
	}
	public Usuario getUsuarioCaroneiro() {
		return usuarioCaroneiro;
	}
	public void setUsuarioCaroneiro(Usuario usuarioCaroneiro) {
		this.usuarioCaroneiro = usuarioCaroneiro;
	}
	public boolean isConfirmado() {
		return confirmado;
	}
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

}
