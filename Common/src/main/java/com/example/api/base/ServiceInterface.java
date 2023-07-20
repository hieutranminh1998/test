package com.example.api.base;

import com.example.bean.model.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceInterface<I, O> {
	ResponseEntity<O> executeString(String messageSn, UserInfo userInfo, String requestBody,
											 List<MultipartFile> listFileRef);

	ResponseEntity<O> execute(String messageSn, UserInfo userInfo, I requestBody,
									   List<MultipartFile> listFileRef);
	default boolean isAsyn() {
		return false;
	}
}
