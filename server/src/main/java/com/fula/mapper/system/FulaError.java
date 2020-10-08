package com.fula.mapper.system;

public enum FulaError {

	// 数据操作错误定义
	SUCCESS("5000", ""),
	INTERNAL_SERVER_ERROR("5001", "服务器内部错误!"),
	;

	/** 错误码 */
	private String resultCode;

	/** 错误描述 */
	private String resultMsg;

	FulaError(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

}