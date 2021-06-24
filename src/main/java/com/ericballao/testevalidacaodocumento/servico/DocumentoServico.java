package com.ericballao.testevalidacaodocumento.servico;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ericballao.testevalidacaodocumento.entidade.Documento;

@Component
public interface DocumentoServico {
	
	Documento findByNumeroDocumento(String numeroDocumento);
	
	Documento createOrUpdate(Documento documento);
	
	Optional<Documento> findById(String id);
	
	void delete(String id);
	
	Page<Documento> findAll(int page, int count);
	
	Page<Documento> findByParameters(int page, int count, String numeroDocumento, String status);

}
