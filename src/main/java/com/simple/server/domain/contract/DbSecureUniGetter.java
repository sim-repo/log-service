package com.simple.server.domain.contract;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.simple.server.config.HandlerResultType;
import com.simple.server.domain.AbstractLogMsg;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = DbSecureUniGetter.class)
public class DbSecureUniGetter extends AbstractLogMsg{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	protected Integer id;
	
	String method;
	String endpointId;
	Map<String, List<String>> webParamsByMethod;
	String executedFunctionName;
	Map<String, String> functParamByWebParam;
		
	String hibernateParamsMap;
	String resultHandlerType;
	String description;
	
	@Override
	public String getClazz() {
		return DbSecureUniGetter.class.getName();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getEndpointId() {
		return endpointId;
	}


	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}


	public Map<String, List<String>> getWebParamsByMethod() {
		return webParamsByMethod;
	}


	public void setWebParamsByMethod(Map<String, List<String>> webParamsByMethod) {
		this.webParamsByMethod = webParamsByMethod;
	}


	public String getExecutedFunctionName() {
		return executedFunctionName;
	}


	public void setExecutedFunctionName(String executedFunctionName) {
		this.executedFunctionName = executedFunctionName;
	}


	public Map<String, String> getFunctParamByWebParam() {
		return functParamByWebParam;
	}


	public void setFunctParamByWebParam(Map<String, String> functParamByWebParam) {
		this.functParamByWebParam = functParamByWebParam;
	}


	public String getHibernateParamsMap() {
		return hibernateParamsMap;
	}


	public void setHibernateParamsMap(String hibernateParamsMap) {
		this.hibernateParamsMap = hibernateParamsMap;
	}

	

	public HandlerResultType getResultHandlerType() {
		return HandlerResultType.fromValue(resultHandlerType);
	}


	public void setResultHandlerType(HandlerResultType resultHandlerType) {
		this.resultHandlerType = resultHandlerType.toValue();
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
}
