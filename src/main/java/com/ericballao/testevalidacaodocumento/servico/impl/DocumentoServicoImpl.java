package com.ericballao.testevalidacaodocumento.servico.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ericballao.testevalidacaodocumento.entidade.Documento;
import com.ericballao.testevalidacaodocumento.repositorio.DocumentoRepositorio;
import com.ericballao.testevalidacaodocumento.servico.DocumentoServico;

@Service
public class DocumentoServicoImpl implements DocumentoServico {
	
	@Autowired
	private DocumentoRepositorio documentoRepositorio;

	@Override
	public Documento findByNumeroDocumento(String numeroDocumento) {
		return documentoRepositorio.findByNumeroDocumento(numeroDocumento);
	}

	@Override
	public Documento createOrUpdate(Documento documento) {
		return documentoRepositorio.save(documento);
	}

	@Override
	public Optional<Documento> findById(String id) {
		return documentoRepositorio.findById(id);
	}

	@Override
	public void delete(String id) {
		documentoRepositorio.deleteById(id);
		
	}

	@Override
	public Page<Documento> findAll(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return documentoRepositorio.findAll(pages);
	}

	@Override
	public Page<Documento> findByParameters(int page, int count, String numeroDocumento, String status) {
		Pageable pages = PageRequest.of(page, count);  
		return documentoRepositorio.findByNumeroDocumentoIgnoreCaseContainingAndStatusIgnoreCase
				(numeroDocumento, status, pages);
	}

}
