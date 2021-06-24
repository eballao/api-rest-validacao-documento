package com.ericballao.testevalidacaodocumento.entity.dominio;

public enum DominioStatusDocumento {
	
	// @formatter:off
	ATIVO("Ativo"),
	BLACKLIST("Blacklist");
	// @formatter:on

	private String descricao;

	private DominioStatusDocumento(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
