package com.jy.recycle.client.response;

/**
 * ������Ӧ����
 * @author zhaowenbin
 *
 */
public class EvalResponse {
	/**
	 * �����������
	 */
	public final static String RESPONSE_CODE_SUCCESS = "1" ; 
	/**
	 * �������󷵻�
	 */
	public final static String RESPONSE_CODE_ERROR = "0" ;
	/**
	 * ���ؼ��˳�
	 */
	public final static String RESPONSE_CODE_EXIT_WITH_BACK = "2";
	
	/**
	 * ��Ӧ��
	 */
	private String responseCode ; 
	/**
	 * ������Ϣ
	 */
	private String errorMessage ; 

	/**
	 * �û������̶�Ϊjy
	 */
	private String userCode ; 
	/**
	 * ���룺�̶�Ϊjy
	 */
	private String password ; 
	/**
	 * ������ʧ��Ϣ
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
