package com.ericballao.testevalidacaodocumento.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ericballao.testevalidacaodocumento.entidade.Documento;

public interface DocumentoRepositorio extends MongoRepository<Documento, String> {
	
	Documento findByNumeroDocumento(String numeroDocumento);
	
	Optional<Documento> findById(String id);
	
	Page<Documento> findByNumeroDocumentoIgnoreCaseContainingAndStatusIgnoreCase(
			String numeroDocumento, String status, Pageable pages);

}
