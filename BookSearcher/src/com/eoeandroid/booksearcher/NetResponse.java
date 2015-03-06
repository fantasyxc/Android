package com.eoeandroid.booksearcher;

public class NetResponse {
	private int mCode;
	private Object mMessage;

	public NetResponse(int mCode, Object mMessage) {
		this.mCode = mCode;
		this.mMessage = mMessage;
	}

	public int getCode() {
		return mCode;
	}

	public void setCode(int mCode) {
		this.mCode = mCode;
	}

	public Object getMessage() {
		return mMessage;
	}

	public void setMessage(Object mMessage) {
		this.mMessage = mMessage;
	}

}
