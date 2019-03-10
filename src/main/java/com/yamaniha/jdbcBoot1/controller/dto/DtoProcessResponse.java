package com.yamaniha.jdbcBoot1.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yamaniha.jdbcBoot1.model.Integracao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class DtoProcessResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Integracao> sucess;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Integracao> error;

}
