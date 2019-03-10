package com.yamaniha.jdbcBoot1.dao;

import java.util.List;

import com.yamaniha.jdbcBoot1.model.Integracao;

public interface IntegracaoDao {

	boolean create(Integracao model);
	boolean update(Integracao model);
	boolean updateStatus(long codigo, String status, String detalhe);
	Integracao get(long codigo);
	List<Integracao> getAllByStatus(String status);
}
