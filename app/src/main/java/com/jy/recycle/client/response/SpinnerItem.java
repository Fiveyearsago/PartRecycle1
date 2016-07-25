package com.jy.recycle.client.response;

public class SpinnerItem {
	private String key;
	private String value;

	public SpinnerItem(String key, String value) {
		// TODO Auto-generated constructor stub
		this.key=key;
		this.value=value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
