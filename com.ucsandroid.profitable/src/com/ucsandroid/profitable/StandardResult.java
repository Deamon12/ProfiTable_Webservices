package com.ucsandroid.profitable;

public class StandardResult {

	private boolean success;
	private String message;
	private Object result;
	
	public StandardResult(boolean success, Object result) {
		super();
		this.success = success;
		this.result = result;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}