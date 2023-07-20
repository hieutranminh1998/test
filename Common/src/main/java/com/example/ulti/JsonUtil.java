package com.example.ulti;

import com.example.api.controller.ApiController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * TODO: Class description here.
 *

 */
public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
	static {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
	}

	public static String toString(Object object) {
		if(object==null) {
			return "";
		}
		if(object instanceof String) {
			return object+"";
		}
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("", e);
			return "";
		}
	}

	public static <T> T toObject(String stringObject, Class<T> clazz) {
		if(StringUtil.isEmpty(stringObject)) {
			return defaultValue(clazz);
		}
		try {
			return mapper.readValue(stringObject, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("", e);
			return defaultValue(clazz);
		}
	}
	private static <T> T defaultValue(Class<T> clazz) {
		if("vn.com.msb.db.bean.ErrorResponse".equals(clazz.getName())) {
			return toObject("{\r\n" + 
					"    \"errors\": [\r\n" + 
					"        {\r\n" + 
					"            \"MambuError\": -19,\r\n" + 
					"            \"errorSource\": null,\r\n" + 
					"            \"errorReason\": \"TRANSACTION_FAIL\",\r\n" + 
					"            \"timeout\": false\r\n" + 
					"        }\r\n" + 
					"    ]\r\n" + 
					"}",clazz);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static <T> T toObject(String stringObject, TypeReference<T> type) {
		if(StringUtil.isEmpty(stringObject)) {
			return null;
		}
		try {
			return mapper.readValue(stringObject, type);
		} catch (IOException e) {
			log.error("", e);
		}
		;
		return null;
	}
	
	public static <C> C toObject(String stringObject,Class<C> clazz,Class subClazz) {
		if(StringUtil.isEmpty(stringObject)) {
			return null;
		}
		try {
			JavaType type = mapper.getTypeFactory().constructParametricType(clazz, subClazz);
			return mapper.readValue(stringObject, type);
		} catch (IOException e) {
			log.error("", e);
		}
		;
		return null;
	}
	
	public static JsonNode toJsonObject(String input) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(input);
		} catch (IOException e) {
			log.error("", e);
		}
		return actualObj;
	}
	public static Map<String, String> toHashMap(String stringObject) {
		if(StringUtil.isEmpty(stringObject)) {
			return null;
		}
		try {
			return mapper.readValue(stringObject, Map.class);
		} catch (IOException e) {
			log.error("", e);
		}
		;
		return null;
	}

}
