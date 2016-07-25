package com.jy.recycle.exception;

public class CommonException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode ;
	
	public CommonException(String errorCode , String message){
		super(message);
		this.errorCode = errorCode; 
	}
	
	public CommonException(){
		this.errorCode = "-1" ; 
	}
	
	public CommonException(String message){
		super(message);
		this.errorCode = "-1" ; 
	}
	
	public CommonException( String message , Throwable cause ){
		super(message , cause);
		this.errorCode = "-1" ; 
	}
	
	public CommonException( Throwable cause ){
		super(cause);
		this.errorCode = "-1" ; 
	}
	
	public String getErrorCode(){
		return errorCode ;
	}
}
