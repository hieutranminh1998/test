package com.example.bean.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Log_action")
public class LogAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "service_name")
	private String serviceName;
	
	private String status;
	
	private Long interval;
	
	@Column(name = "message_sn")
	private String messageSn;
	
	
	@Transient
	private Object request;
	
	@Transient
	private String channelCode="UNDEFINE";
	
	public String getChannelCode() {
		return channelCode;
	}


	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}


	@Transient
	private Date startTime;
	
	
	@Transient
	private Date endTime;
	
	
	public Object getRequest() {
		return request;
	}


	public void setRequest(Object request) {
		this.request = request;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public String getMessageSn() {
		return messageSn;
	}


	public void setMessageSn(String messageSn) {
		this.messageSn = messageSn;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getInterval() {
		return interval;
	}


	public void setInterval(Long interval) {
		this.interval = interval;
	}


	public LogAction() {
		super();
	}


	@Override
	public String toString() {
		return "LogAction [id=" + id + ", userId=" + userId + ", serviceName=" + serviceName + ", status=" + status
				+ ", interval=" + interval + ", messageSn=" + messageSn + "]";
	}
	
	
	
}