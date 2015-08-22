package br.edu.ifba.mobile.atendimento.bean;

public class Atendimento {

	private String cpf;
	private String tipoAtendimento;
	private String nomeAtendente;
	private int idAtendimento;
	private String horaAtendimento;

	public Atendimento(String cpf, String tipoAtendimento,
			String nomeAtendente, int idAtendimento, String hora) {
		this.cpf = cpf;
		this.tipoAtendimento = tipoAtendimento;
		this.nomeAtendente = nomeAtendente;
		this.idAtendimento = idAtendimento;
		this.horaAtendimento = hora;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(String tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public String getNomeAtendente() {
		return nomeAtendente;
	}

	public void setNomeAtendente(String nomeAtendente) {
		this.nomeAtendente = nomeAtendente;
	}

	public int getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(int idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public String getHoraAtendimento() {
		return horaAtendimento;
	}

	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
	}

	@Override
	public String toString() {
		return "#" + idAtendimento + ". " + tipoAtendimento + " - " + nomeAtendente + 
				" - Cliente: " + cpf + " às " + horaAtendimento + "h.";
	}
}
