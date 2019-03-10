package com.yamaniha.jdbcBoot1.service;

import java.util.List;

import com.yamaniha.jdbcBoot1.controller.dto.DtoProcessResponse;
import com.yamaniha.jdbcBoot1.model.Integracao;

public interface IntegracaoService {

	DtoProcessResponse process(List<Integracao> dados) throws Exception;	
	List<Integracao> getAllProcessSucess();
	List<Integracao> getAllProcessError();
	
	Integracao processRecord(Integracao record) throws Exception;
	void saveStatus(long codigo, String status, String detalhe);
	Integracao createRecord(Integracao record) ;
	void validaRecord(Integracao record) throws Exception;
	
}
