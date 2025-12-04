package com.example.granja.patos.enums;

public enum StatusPato {
	DISPONIVEL("D", "Disponível"),
	VENDIDO("V", "Vendido");
	
	private String codigo;
	private String status;
	
	private StatusPato(String codigo, String status) {
		this.status = status;
		this.codigo = codigo;
	}

	public String getStatus() {
		return status;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static StatusPato fromCodigo(String codigo) {
		for (StatusPato tipoStatusAprovacao : StatusPato.values()) {
			if (tipoStatusAprovacao.getCodigo().equals(codigo)) {
				return tipoStatusAprovacao;
			}
		}
		return null;
	}
}
	
