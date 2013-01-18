package com.skala.jellydroid.net;

public class AjaxStatus {
	public static final int SUCCESS = 200;
	public static final int NETWORK_ERROR = -101;
	public static final int PARSE_ERROR = -102;

	private int mCode;

	public void setCode(int code) {
		mCode = code;
	}

	public int getCode() {
		return mCode;
	}
}
