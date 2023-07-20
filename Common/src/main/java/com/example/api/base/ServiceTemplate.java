package com.example.api.base;

import com.example.bean.ErrorResponse;
import com.example.bean.MambuError;
import com.example.ulti.JsonUtil;
import cores.model.ApiException;
import cores.model.RestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class ServiceTemplate<I, O> implements ServiceInterface<I, O> {
	protected I request;
	protected String messageSn;
	private Class<I> inferedClass;
	private HttpStatus httpStatus;
	protected List<MultipartFile> listFileUpload;
	protected long startTime;
	protected String lang;
	static HttpHeaders responseHeaders = new HttpHeaders();
	static {
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
	}
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<O> execute(I requestBody) {
		startTime = System.currentTimeMillis();
		this.request = requestBody;

		this.lang = lang;
		// verify signature from tokeNo
		try {
			MambuError errorValidator = validator();
			if (errorValidator != null) {
				throw new ApiException(errorValidator);
			}
			Object errorProcess = process();
			if (errorProcess != null) {
				if (errorProcess instanceof RestError) {
					throw new ApiException((RestError) errorProcess);
				}
				throw new ApiException((MambuError) errorProcess);
			}
			O response = bindDataResponse();
			if (response == null) {
				setHttpStatus(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<O>(response, responseHeaders, getHttpStatusSucc());
		} catch (Exception e) {
			if (e instanceof ApiException) {
				ApiException apiException = (ApiException) e;
				ErrorResponse error;
				if (apiException.getErrorResponse() != null) {
					error = apiException.getErrorResponse();
				} else {
					error = new ErrorResponse(new RestError(apiException));
				}
				return new ResponseEntity(error, responseHeaders, getHttpStatus());
			}
			logger.error("", e);
			ErrorResponse error = new ErrorResponse(MambuError.INTERNAL_ERROR_CONSUMER);
			return new ResponseEntity(error, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private HttpStatus getHttpStatusSucc() {
		if (this.httpStatus == null) {
			return HttpStatus.OK;
		}
		return this.httpStatus;
	}

	protected abstract MambuError validator() throws Exception;

	protected abstract Object process() throws Exception;

	protected abstract O bindDataResponse() throws Exception;
	
	
	

	private ResponseEntity createResponseMessage(MambuError response, HttpStatus status) {
		ErrorResponse error = new ErrorResponse(response);
		return new ResponseEntity<Object>(error, responseHeaders, status);
	}

	protected I convertParameter(String bodyRequest) {
		Class<I> classI = getGenericClass();
		if ("java.lang.String".equals(classI.getName())) {
			return (I) bodyRequest;
		}
		return (I) JsonUtil.toObject(bodyRequest, classI);
	}

	protected Class<I> getGenericClass() {
		if (inferedClass == null) {
			Type mySuperclass = getClass().getGenericSuperclass();
			Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
			String classNameS=tType.toString();
			String className =classNameS.split(" ")[1];
			try {
				inferedClass = (Class<I>) Class.forName(className);
			} catch (ClassNotFoundException e) {
				logger.error("", e);
			}
		}
		return inferedClass;
	}

	protected void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	private HttpStatus getHttpStatus() {
		if (this.httpStatus == null) {
			return HttpStatus.BAD_REQUEST;
		}
		return this.httpStatus;
	}



	protected String className() {
		return  this.getClass().getSimpleName();
	}
	
	protected boolean isLog() {
		return true;
	}
	
	protected boolean isLogFail() {
		return true;
	}

}
