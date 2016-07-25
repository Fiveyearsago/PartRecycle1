package com.jy.recycle.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.jy.recycle.util.Constants;
import com.jy.recycle.util.HttpUtil;
import com.jy.recycle.util.SharedData;


public class IFCService {
	
//	static String request(String json) throws Exception{
//		OutputStream os = null ; 
//		String data = null ; 
//		try{
//			//1.建立连接
//			URL url = new URL(Constants.URL_IFC);
//			URLConnection connetion = url.openConnection();
//			connetion.setDoInput(true);
//			connetion.setDoOutput(true);
//			connetion.setConnectTimeout(120000);
//			connetion.setReadTimeout(120000);
//			//2.发送请求
//			os = connetion.getOutputStream();
//			os.write(json.toString().getBytes(Constants.ENCODING));
//			os.flush();
//			//3.接受响应
//			data = convertStreamToString(connetion.getInputStream());
//		}finally{
//			if(os!=null){
//				os.close();
//			}
//		}
//		
//		return data ; 
//	}
	
	static String request(String json) throws Exception{
		//发送任务数据包
//		HttpParams httpParameters = new BasicHttpParams();  
//		HttpConnectionParams.setConnectionTimeout(httpParameters,20000);
//		HttpConnectionParams.setSoTimeout(httpParameters, 20000);
		
//		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpContext localContext = new BasicHttpContext();
//		HttpPost httpPost = new HttpPost(SharedData.data().getEvalUrl() + "/ifc");
		HttpPost httpPost = new HttpPost(Constants.URL_BASE +"ifc");
		byte[] dataBuffer = json.getBytes(Constants.ENCODING);
		ByteArrayInputStream bis = new ByteArrayInputStream(dataBuffer) ; 
		InputStreamEntity entity = new InputStreamEntity(bis , dataBuffer.length );
		httpPost.setEntity(entity);
		HttpResponse response = HttpUtil.getHttpClient().execute(httpPost, localContext);
		//接收响应结果
		BufferedReader reader = null ;
		String data = "";
		try{
			reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Constants.ENCODING));
			String line = null;
			while ((line = reader.readLine()) != null) {
				data += line;
			}
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		
		return data ; 
	}
	
	public static String convertStreamToString(InputStream is) throws Exception{   
	      StringBuilder sb = new StringBuilder();     
	      String line = null;   
	      InputStreamReader isr = null ; 
	      BufferedReader reader = null ; 
	      try {
	    	  isr = new InputStreamReader(is, Constants.ENCODING);
	    	  reader = new BufferedReader(isr);
	          while ((line = reader.readLine()) != null) {   
	              sb.append(line + "\n");   
	          }
	          isr.close();
	          reader.close();
	          
	      } finally {   
	          try {   
	              is.close();   
	          } catch (IOException e) {   
	              e.printStackTrace();   
	          }  
	          try {   
	        	  isr.close();   
	          } catch (IOException e) {   
	              e.printStackTrace();   
	          }  
	          try {   
	        	  reader.close();   
	          } catch (IOException e) {   
	              e.printStackTrace();   
	          }  
	      }     
	      return sb.toString();   
	  } 
}
