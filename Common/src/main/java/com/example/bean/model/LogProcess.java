package com.example.bean.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the log_process database table.
 * 
 */
@Entity
@Table(name = "log_process")
@NamedQuery(name = "LogProcess.findAll", query = "SELECT l FROM LogProcess l")
public class LogProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message_sn")
	private String messageSn;

	private String request;

	private String response;

	private String url;
	
	private Long interval;
	
	public Long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	private Timestamp time;

	@Transient
	Object requestObject;

	@Transient
	Object responseObject;

	public LogProcess() {
		super();
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageSn() {
		return this.messageSn;
	}

	public void setMessageSn(String messageSn) {
		this.messageSn = messageSn;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "LogProcess [id=" + id + ", messageSn=" + messageSn + ", request=" + request + ", response=" + response
				+ ", url=" + url + ", interval=" + interval + ", time=" + time + ", requestObject=" + requestObject
				+ ", responseObject=" + responseObject + "]";
	}
	
}