package com.ericballao.testevalidacaodocumento.entidade;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ericballao.testevalidacaodocumento.entity.dominio.DominioStatusDocumento;

import lombok.Data;

@Data
@Document
public class Documento {

	@Id
	private String id;
	
	@Indexed(unique = true)
	private String numeroDocumento;
	
	private LocalDate dataCriacao;
	
	private DominioStatusDocumento status;
	
}
