package com.yamaniha.jdbcBoot1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.yamaniha.jdbcBoot1.model.Integracao;

public class IntegracaoMapper implements RowMapper<Integracao>  {

	@Override
	public Integracao mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integracao registro = new Integracao();
		registro.setCodigo(rs.getLong("codigo"));
		registro.setDetalhe(rs.getString("detalhe"));
		registro.setDataHora(rs.getTimestamp("data_hora"));
		registro.setStatus(rs.getString("status"));
		registro.setInfo(rs.getString("info"));
		return registro;
	}

}
