package com.yamaniha.jdbcBoot1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yamaniha.jdbcBoot1.controller.dto.DtoProcessResponse;
import com.yamaniha.jdbcBoot1.dao.IntegracaoDao;
import com.yamaniha.jdbcBoot1.model.Integracao;

@Service
public class IntegracaoServiceImpl implements IntegracaoService {

	@Autowired
	IntegracaoDao dao;
	
	@Override
	public DtoProcessResponse process(List<Integracao> dados) {

		List<Integracao> sucessList = new ArrayList<Integracao>();
		List<Integracao> errorList= new ArrayList<Integracao>();
		dados.forEach(f->{
			Integracao result=null;
			try {
				result = processRecord(f);
				if (result!=null)
				{
					if (result.getStatus().equals("S"))
					{
						saveStatus(result.getCodigo(),"F", "Processamento finalizado com sucesso");
						sucessList.add(result);
					}
					else
					{
						saveStatus(result.getCodigo(),"N", "Integracao falhou.Error -> " + result.getDetalhe());
						errorList.add(result);
					}
				}
			} catch (Exception e) {
				saveStatus(result.getCodigo(),"E", "Integracao falhou.Exception -> " + e.getMessage());
				errorList.add(result);
			}
		});		
		return DtoProcessResponse.builder().sucess(sucessList).error(errorList).build();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false  )
	public Integracao processRecord(Integracao record) throws Exception
	{
		Integracao result = createRecord(record);
		if (result ==null)
			throw new Exception("Falha ao realizar o INSERT.");		
		validaRecord(result);
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false  )
	public void saveStatus(long codigo, String status, String detalhe)
	{
		dao.updateStatus(codigo, status, detalhe);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = false )
	public Integracao createRecord(Integracao record) 
	{
		if (dao.create(record)) {
			return dao.get(record.getCodigo());			
		}
		else
		{
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = false  )
	public void validaRecord(Integracao record) throws Exception
	{
		if (record.getStatus().equals("E")) {
			throw new Exception("Trigger Err:" + record.getDetalhe());
		}		
	}

	@Override
	public List<Integracao> getAllProcessSucess() {
		return dao.getAllByStatus("S");
	}

	@Override
	public List<Integracao> getAllProcessError() {
		return dao.getAllByStatus("E");
	}

}
