package com.example.bean;

public class Condition {	
	String key;
	Object value;
	Object secondValue;
	
	public Object getSecondValue() {
		return secondValue;
	}
	public void setSecondValue(Object secondValue) {
		this.secondValue = secondValue;
	}

	String compare;
	/**
	 * @return the compare
	 */
	public String getCompare() {
		return compare;
	}
	/**
	 * @param compare the compare to set
	 */
	public void setCompare(String compare) {
		this.compare = compare;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * @param key
	 * @param value
	 * @param compare
	 */
	public Condition(String key, Object value, String compare) {
		super();
		this.key = key;
		this.value = value;
		this.compare = compare;
	}
	/**
	 * @param key2
	 * @param value2
	 * @param equal
	 * 
	 */
	public Condition(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
		this.compare = TYPE_COMPARE.EQUAL;
	}
	
	public Condition() {
		super();
	}
	
	/**
	 * @return the param
	/**
	 * @param key
	 * @param value
	 * @param compare
	 * @param param
	 */
	public static final class TYPE_COMPARE {
		public static final String EQUAL="=";
		public static final String LIKE="LIKE";
		public static final String GREATTHAN=">";
		public static final String LESSTHAN="<";
		public static final String IN="IN";
		public static final String GREATTHAN_OR_EQUAL=">=";
		public static final String LESSTHAN_OR_EQUAL="<=";
		public static final String NOT_EQUAL="!=";
		public static final String BETWEEN ="BETWEEN";
	};

	

}
