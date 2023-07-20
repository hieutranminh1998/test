
package com.example.bean;

import com.example.ulti.JsonUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaResponse {
	int code = 200;
	String body;

	public boolean isSuccess() {
		if (this.code >= 200 && this.code < 400) {
			return true;
		}
		return false;
	}

	public KafkaResponse(int code, Object body) {
		super();
		this.code = code;
		this.body = JsonUtil.toString(body);
	}

	public KafkaResponse() {
		super();
	}

}