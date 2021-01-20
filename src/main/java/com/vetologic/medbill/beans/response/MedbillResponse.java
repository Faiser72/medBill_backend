package com.vetologic.medbill.beans.response;

import java.util.List;

public class MedbillResponse {

	private boolean success;
	private String message;
	private Object object;
	private List<?> listObject;
	private byte[] byteArray;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<?> getListObject() {
		return listObject;
	}

	public void setListObject(List<?> listObject) {
		this.listObject = listObject;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

}
