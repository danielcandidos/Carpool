package com.carpool.android.dominio;

public class Usuario {
	private int idUsuario;
    private String nomeUsuario;
    private String email;
	private String idPerfilFacebook;
	private String tokenFacebook;
	private boolean verificado;
    private boolean desejaAjudaCusto;
    private boolean dispostoAjudaCusto;
    private String urlFoto;
	private String urlPerfil;
    private Instituicao instituicao;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdPerfilFacebook() {
		return idPerfilFacebook;
	}

	public void setIdPerfilFacebook(String idPerfilFacebook) {
		this.idPerfilFacebook = idPerfilFacebook;
	}

	public String getTokenFacebook() {
		return tokenFacebook;
	}

	public void setTokenFacebook(String tokenFacebook) {
		this.tokenFacebook = tokenFacebook;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public boolean isDesejaAjudaCusto() {
		return desejaAjudaCusto;
	}

	public void setDesejaAjudaCusto(boolean desejaAjudaCusto) {
		this.desejaAjudaCusto = desejaAjudaCusto;
	}

	public boolean isDispostoAjudaCusto() {
		return dispostoAjudaCusto;
	}

	public void setDispostoAjudaCusto(boolean dispostoAjudaCusto) {
		this.dispostoAjudaCusto = dispostoAjudaCusto;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlPerfil() {
		return urlPerfil;
	}

	public void setUrlPerfil(String urlPerfil) {
		this.urlPerfil = urlPerfil;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

}
