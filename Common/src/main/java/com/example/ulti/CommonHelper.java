package com.example.ulti;

import com.example.api.base.ServiceInterface;
import com.example.bean.ErrorResponse;
import com.example.bean.MambuError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Chuyen sang service
 *
 * 
 */
@Service
public class CommonHelper {
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
	public static  ServiceInterface loadClassService(String packet, String sClassName) {
		try {
			ClassLoader myClassLoader = ServiceInterface.class.getClassLoader();
			Class imlClass = null;
			imlClass = myClassLoader.loadClass(packet + sClassName);
			ServiceInterface object = (ServiceInterface) imlClass.newInstance();
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static List<File> toFile(List<MultipartFile> listFilePart) {
		if (listFilePart == null) {
			return null;
		}
		List<File> listFileUpload = new ArrayList<File>();
		for (MultipartFile filePart : listFilePart) {
			File file = multipartToFile(filePart);
			if (file == null) {
				return null;
			}
			listFileUpload.add(file);
		}
		return listFileUpload;
	}

	public static File multipartToFile(MultipartFile multipart) {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
		try {
			multipart.transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
			log.error("", e);
			return null;
		}
		return convFile;
	}

	public static ResponseEntity<Object> createResponse(MambuError mambuError, HttpStatus status) {
		return new ResponseEntity<Object>(new ErrorResponse(mambuError), status);
	}
}
