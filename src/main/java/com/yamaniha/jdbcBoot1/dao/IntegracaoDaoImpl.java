package com.yamaniha.jdbcBoot1.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.yamaniha.jdbcBoot1.model.Integracao;

@Component
public class IntegracaoDaoImpl implements IntegracaoDao {

	JdbcTemplate jdbcTemplate;

	private final String SQL_FIND = "select * from Integracao where codigo = ?";
	private final String SQL_INSERT = "insert into Integracao (detalhe, info, status) values(?,?,?)";
	private final String SQL_GET_AUTOINCREMENT = "SELECT LAST_INSERT_ID()";
	private final String SQL_UPDATE = "update Integracao set detalhe = ?,info=?, data_hora = ?, status  = ? where codigo = ?";
	private final String SQL_FIND_STATUS = "select * from Integracao where status =? ";
	private final String SQL_UPDATE_STATUS = "update Integracao set status  = ?,detalhe = ? where codigo = ?";
	
	@Autowired
	public IntegracaoDaoImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean create(Integracao model) {
		
		int qtdReg =  jdbcTemplate.update(SQL_INSERT, model.getDetalhe(), model.getInfo(), model.getStatus());
		if (qtdReg!=1)
			return false;
		SqlRowSet queryForRowSet =jdbcTemplate.queryForRowSet(SQL_GET_AUTOINCREMENT);
		while (queryForRowSet.next()) {
			model.setCodigo(queryForRowSet.getLong(1));
		}
		return true;
	}

	@Override
	public boolean update(Integracao model) {
		return jdbcTemplate.update(SQL_UPDATE, model.getDetalhe(), model.getInfo(), model.getDataHora(), model.getStatus(), model.getCodigo()) > 0;
	}

	@Override
	public boolean updateStatus(long codigo, String status, String detalhe) {
		return jdbcTemplate.update(SQL_UPDATE_STATUS, status, detalhe, codigo) > 0;
	}
	
	@Override
	public Integracao get(long codigo) {
		return jdbcTemplate.queryForObject(SQL_FIND, new Object[] { codigo }, new IntegracaoMapper());
	}

	@Override
	public List<Integracao> getAllByStatus(String status) {
		return jdbcTemplate.query(SQL_FIND_STATUS, new Object[] { status }, new IntegracaoMapper());
	}




	

}
