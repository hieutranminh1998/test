package com.example.ulti;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * TODO: Class description here.
 *
 * 
 */
public class UniqueKeyFactory {
	public static String getKey() {
		UUID one = UUID.randomUUID();
		return one.toString();
	}

	public static String getKeyFromString(String name) throws UnsupportedEncodingException {
		byte[] bytes = name.getBytes("UTF-8");
		UUID uuid = UUID.nameUUIDFromBytes(bytes);
		return uuid.toString();
	}
}
