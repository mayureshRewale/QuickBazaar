package com.qb.Dao.Beans;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ServiceResponseBean {
	
	private String message;
	
	private String errorMessage;
 
	@Default
	private Boolean status = Boolean.FALSE;
	
	private Object data = null;
	
	private Set<String> errors;
	
	private Set<Object> dataList;
}