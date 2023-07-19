package com.example.bean.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "log_io")
public class LogInOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	String request;
	String response;
	String exception;
	
	@Transient
	Object requestObject;
	
	@Transient
	Object responseObject;
	
	@Transient
	Exception exceptionObject;
	
	
	
	
	
	public Object getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	public Exception getExceptionObject() {
		return exceptionObject;
	}
	public void setExceptionObject(Exception exceptionObject) {
		this.exceptionObject = exceptionObject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LogInOut() {
		super();
	}

	
}
