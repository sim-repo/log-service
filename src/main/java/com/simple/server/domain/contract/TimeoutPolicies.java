package com.simple.server.domain.contract;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.simple.server.domain.AbstractLogMsg;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = TimeoutPolicies.class)
public class TimeoutPolicies extends AbstractLogMsg{

	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	protected Integer id;
	
	protected Integer frontSyncReadTimeout = 5000;	
	protected Integer frontSyncConnectionRequestTimeout = 5000;
	protected Integer frontSyncConnectionTimeout = 5000;
	
	protected Integer backAsyncReadTimeout = 5000;	
	protected Integer backAsyncConnectionRequestTimeout = 5000;
	protected Integer backAsyncConnectionTimeout = 5000;
	
	@Override
	public String getClazz() { 
		return this.getClass().getName();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getFrontSyncReadTimeout() {
		return frontSyncReadTimeout;
	}


	public void setFrontSyncReadTimeout(Integer frontSyncReadTimeout) {
		this.frontSyncReadTimeout = frontSyncReadTimeout;
	}


	public Integer getFrontSyncConnectionRequestTimeout() {
		return frontSyncConnectionRequestTimeout;
	}


	public void setFrontSyncConnectionRequestTimeout(Integer frontSyncConnectionRequestTimeout) {
		this.frontSyncConnectionRequestTimeout = frontSyncConnectionRequestTimeout;
	}


	public Integer getFrontSyncConnectionTimeout() {
		return frontSyncConnectionTimeout;
	}


	public void setFrontSyncConnectionTimeout(Integer frontSyncConnectionTimeout) {
		this.frontSyncConnectionTimeout = frontSyncConnectionTimeout;
	}


	public Integer getBackAsyncReadTimeout() {
		return backAsyncReadTimeout;
	}


	public void setBackAsyncReadTimeout(Integer backAsyncReadTimeout) {
		this.backAsyncReadTimeout = backAsyncReadTimeout;
	}


	public Integer getBackAsyncConnectionRequestTimeout() {
		return backAsyncConnectionRequestTimeout;
	}


	public void setBackAsyncConnectionRequestTimeout(Integer backAsyncConnectionRequestTimeout) {
		this.backAsyncConnectionRequestTimeout = backAsyncConnectionRequestTimeout;
	}


	public Integer getBackAsyncConnectionTimeout() {
		return backAsyncConnectionTimeout;
	}


	public void setBackAsyncConnectionTimeout(Integer backAsyncConnectionTimeout) {
		this.backAsyncConnectionTimeout = backAsyncConnectionTimeout;
	}

	
	

}