package com.yamaniha.jdbcBoot1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yamaniha.jdbcBoot1.controller.dto.DtoProcessRequest;
import com.yamaniha.jdbcBoot1.controller.dto.DtoProcessResponse;
import com.yamaniha.jdbcBoot1.model.Integracao;
import com.yamaniha.jdbcBoot1.service.IntegracaoServiceImpl;
import com.yamaniha.jdbcBoot1.service.IntegracaoServicePImpl;

@RestController
public class HomeController {

	
	@Autowired 
	IntegracaoServiceImpl srv;
	
	@Autowired 
	IntegracaoServicePImpl srvP;
		
	@RequestMapping("/")
    public String getHome(){
       return "Response Get - Ok" ;
    }
	
	@PostMapping("/v0/integracao")
    public DtoProcessResponse postIntegracao0(@RequestBody   DtoProcessRequest request){
       
		List<Integracao> dtoServiceRequest = new ArrayList<Integracao>();
		request.getInfo().forEach(f-> dtoServiceRequest.add(Integracao.builder().info(f).build()));
	    return srv.process(dtoServiceRequest);
    }
	
	

	@PostMapping("/integracao")
    public DtoProcessResponse postIntegracao(@RequestBody   DtoProcessRequest request){
       
		List<Integracao> dtoServiceRequest = new ArrayList<Integracao>();
		request.getInfo().forEach(f-> dtoServiceRequest.add(Integracao.builder().info(f).build()));

		List<Integracao> sucessList = new ArrayList<Integracao>();
		List<Integracao> errorList= new ArrayList<Integracao>();
		
		dtoServiceRequest.forEach(f->{
			Integracao result=null;
			try {
				result = srv.processRecord(f);
				if (result!=null)
				{
					if (result.getStatus().equals("S"))
					{
						srv.saveStatus(result.getCodigo(),"F", "Processamento finalizado com sucesso");
						sucessList.add(result);
					}
					else
					{
						srv.saveStatus(result.getCodigo(),"N", "Integracao falhou.Error -> " + result.getDetalhe());
						errorList.add(result);
						result.setStatus("N");
					}
				}
			} catch (Exception e) {
				if (result==null)				
					result = f;				
				else 
					srv.saveStatus(result.getCodigo(),"E", "Integracao falhou.Exception -> " + e.getMessage());
				result.setStatus("E");
				result.setDetalhe(e.getMessage());
				errorList.add(result);
			}
		});		

		return DtoProcessResponse.builder()
				.sucess((sucessList!=null && sucessList.isEmpty()) ? null :sucessList)
				.error((errorList!=null && errorList.isEmpty()) ? null :errorList)
				.build();
    }
	
	
	@PostMapping("/v1/integracao")
    public DtoProcessResponse postIntegracaoV1(@RequestBody   DtoProcessRequest request){
       
		List<Integracao> dtoServiceRequest = new ArrayList<Integracao>();
		request.getInfo().forEach(f-> dtoServiceRequest.add(Integracao.builder().info(f).build()));

		List<Integracao> sucessList = new ArrayList<Integracao>();
		List<Integracao> errorList= new ArrayList<Integracao>();
		
		dtoServiceRequest.forEach(f->{
			Integracao result=null;
			try {
				result = srvP.processRecord(f);
				if (result!=null)
				{
					if (result.getStatus().equals("S"))
					{
						srvP.saveStatus(result.getCodigo(),"F", "Processamento finalizado com sucesso");
						sucessList.add(result);
					}
					else
					{
						srvP.saveStatus(result.getCodigo(),"N", "Integracao falhou.Error -> " + result.getDetalhe());
						errorList.add(result);
						result.setStatus("N");
					}
				}
			} catch (Exception e) {
				if (result==null)				
					result = f;				
				else 
					srvP.saveStatus(result.getCodigo(),"E", "Integracao falhou.Exception -> " + e.getMessage());
				result.setStatus("E");
				result.setDetalhe(e.getMessage());
				errorList.add(result);
			}
		});		

		return DtoProcessResponse.builder()
				.sucess((sucessList!=null && sucessList.isEmpty()) ? null :sucessList)
				.error((errorList!=null && errorList.isEmpty()) ? null :errorList)
				.build();
    }
}
