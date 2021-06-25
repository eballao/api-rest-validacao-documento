package com.ericballao.testevalidacaodocumento.recurso;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericballao.testevalidacaodocumento.entidade.Documento;
import com.ericballao.testevalidacaodocumento.response.Response;
import com.ericballao.testevalidacaodocumento.servico.DocumentoServico;

import io.micrometer.core.annotation.Timed;

@RestController
@Timed
@RequestMapping("/api/documento")
public class DocumentoRecurso {
	
	@Autowired
	private DocumentoServico documentoServico;
	
	@PostMapping
	public ResponseEntity<Response<Documento>> create(HttpServletRequest request, @RequestBody Documento documento, 
			BindingResult result){
		Response<Documento> response = new Response<Documento>();
		try {
			validateCreateDocumento(documento, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Documento documentoCriado = documentoServico.createOrUpdate(documento);
			response.setData(documentoCriado);
			
		} catch (DuplicateKeyException dE) {
			response.getErrors().add("Numero de documento registrado!");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	private void validateCreateDocumento(Documento documento, BindingResult result) {
		if(documento.getNumeroDocumento().isBlank()){
			result.addError(new ObjectError("Documento", "Numero do documento não informado"));
		}		
	}
	
	@PutMapping
	public ResponseEntity<Response<Documento>> update(HttpServletRequest request, @RequestBody Documento documento, 
			BindingResult result){
		Response<Documento> response = new Response<Documento>();
		try {
			validateUpdateDocumento(documento, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Documento documentoAlterado = documentoServico.createOrUpdate(documento);
			response.setData(documentoAlterado);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdateDocumento(Documento documento, BindingResult result) {
		if(documento.getId().isBlank()){
			result.addError(new ObjectError("Documento", "Id não informado"));
		}	
		if(documento.getNumeroDocumento().isBlank()){
			result.addError(new ObjectError("Documento", "Numero do documento não informado"));
		}	
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Documento>> findById(@PathVariable("id") String id){
		Response<Documento> response = new Response<Documento>();
		Optional<Documento> documento = documentoServico.findById(id);
		if(documento.isPresent()) {
			response.setData(documento.get());
			return ResponseEntity.ok(response);
		}
		response.getErrors().add("Resgistro não encontrado para o id: "+id);
		return ResponseEntity.badRequest().body(response);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id){
		Response<String> response = new Response<String>();
		Optional<Documento> documento = documentoServico.findById(id);
		if (documento.isPresent()) {
			documentoServico.delete(id);
			return ResponseEntity.ok(new Response<String>());
		}
		response.getErrors().add("Resgistro não encontrado para o id: "+id);
		return ResponseEntity.badRequest().body(response);
	}
	
	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Documento>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<Documento>> response = new Response<Page<Documento>>();
		Page<Documento> documentos = documentoServico.findAll(page, count);
		response.setData(documentos);
		return ResponseEntity.ok(response);
	}
	
	

}
