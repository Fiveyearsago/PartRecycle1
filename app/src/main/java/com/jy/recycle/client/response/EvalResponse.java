package com.jy.recycle.client.response;

/**
 * 定损响应数据
 * @author zhaowenbin
 *
 */
public class EvalResponse {
	/**
	 * 正常定损完成
	 */
	public final static String RESPONSE_CODE_SUCCESS = "1" ; 
	/**
	 * 发生错误返回
	 */
	public final static String RESPONSE_CODE_ERROR = "0" ;
	/**
	 * 返回键退出
	 */
	public final static String RESPONSE_CODE_EXIT_WITH_BACK = "2";
	
	/**
	 * 响应码
	 */
	private String responseCode ; 
	/**
	 * 错误信息
	 */
	private String errorMessage ; 

	/**
	 * 用户名：固定为jy
	 */
	private String userCode ; 
	/**
	 * 密码：固定为jy
	 */
	private String password ; 
	/**
	 * 定损单损失信息
	 */
	private EvalLossInfo evalLossInfo ;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public EvalLossInfo getEvalLossInfo() {
		return evalLossInfo;
	}
	public void setEvalLossInfo(EvalLossInfo evalLossInfo) {
		this.evalLossInfo = evalLossInfo;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	} 
	
}
