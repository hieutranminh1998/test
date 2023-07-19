package com.example.bean.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the BK_SYS_PARAMETER database table.
 * 
 */
@Entity
@Table(name = "SYS_PARAMETER")
public class SysParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parameter_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int parameterId;

	private String code;

	@Column(name = "value")
	private String value;

	@Column(name = "seq_no")
	private Integer seqNo;

	@Column(name = "type")
	private String type;

	@Column(name = "note")
	private String note;

	public SysParameter() {
		super();
	}

	public int getParameterId() {
		return parameterId;
	}

	public void setParameterId(int parameterId) {
		this.parameterId = parameterId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public SysParameter(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

}