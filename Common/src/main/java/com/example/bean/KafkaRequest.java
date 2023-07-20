
package com.example.bean;


import com.example.bean.model.UserInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaRequest {
	String paramCondition;
	String serviceName;
	UserInfo userInfo;
	String messageSn;

	@Override
	public String toString() {
		return "KafkaRequest [paramCondition=" + paramCondition + ", serviceName=" + serviceName + ", userInfo="
				+ userInfo + ", messageSn=" + messageSn + "]";
	}
	
	
}