package com.jy.recycle.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jy.recycle.pojo.ReqHeadVo;
import com.jy.recycle.pojo.RspMsgVo;
import com.jy.recycle.pojo.VersionInfoReq;
import com.jy.recycle.pojo.VersionInfoRsp;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Xml;

public class XMLUtil {
	
	public String writeVersionInfoToString(VersionInfoReq versionInfoReq){ 
        //实现xml信息序列号的一个对象     
      XmlSerializer serializer=Xml.newSerializer();       
      StringWriter writer=new StringWriter(); 
      try{ 
          //xml数据经过序列化后保存到String中
          serializer.setOutput(writer);                    
          //文档开始         
          serializer.startDocument("GBK", true);                
          //开始一个节点            
          serializer.startTag("", "VersionInfoReq"); 
          serializer.startTag("", "ReqHeadVo");  
          
          serializer.startTag("", "tokenId");   
          serializer.text(versionInfoReq.getReqHeadVo().getTokenID()); 
          serializer.endTag("", "tokenId");
          
          serializer.startTag("", "userCode");   
          serializer.text(versionInfoReq.getReqHeadVo().getUserCode()); 
          serializer.endTag("", "userCode");
          
          serializer.startTag("", "applicationId");   
          serializer.text(versionInfoReq.getReqHeadVo().getApplicationId()); 
          serializer.endTag("", "applicationId");
          
          serializer.startTag("", "IMEI");   
          serializer.text(versionInfoReq.getReqHeadVo().getIMEI()); 
          serializer.endTag("", "IMEI");
          SimpleDateFormat dfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
          serializer.startTag("", "flowinTime");   
          serializer.text(dfAll.format(versionInfoReq.getReqHeadVo().getFlowinTime())); 
          serializer.endTag("", "flowinTime");
          
          serializer.startTag("", "applicationVersionNo");   
          serializer.text(versionInfoReq.getReqHeadVo().getApplicationVersionNo()); 
          serializer.endTag("", "applicationVersionNo");
          
          serializer.endTag("", "ReqHeadVo");
          
          serializer.startTag("", "equipmentType");   
          serializer.text(versionInfoReq.getEquipmentType()); 
          serializer.endTag("", "equipmentType");
          
          serializer.startTag("", "applicationType");   
          serializer.text(versionInfoReq.getApplicationType()); 
          serializer.endTag("", "applicationType");
          
          serializer.startTag("", "messageType");   
          serializer.text(versionInfoReq.getMessageType()); 
          serializer.endTag("", "messageType");
           
           //关闭文档            
           serializer.endDocument(); 
            
      }catch (Exception e) { 
          Log.i("XMLVersion", e.getMessage()); 
      } 
      return writer.toString(); 
  } 
	public static VersionInfoRsp sendData(String url,String xmlData) throws Exception {
		String SERVER_URL = url;
		URL uploadServlet = new URL(SERVER_URL);
		URLConnection servletConnection = uploadServlet.openConnection();
		servletConnection.setUseCaches(false);
		servletConnection.setDoOutput(true);
		servletConnection.setDoInput(true);
		servletConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");  
		BufferedOutputStream output = new BufferedOutputStream(
				servletConnection.getOutputStream());
		output.write(xmlData.getBytes("GBK"));// 用GBK字节流发送
		output.flush();
		output.close();
		try {
			VersionInfoRsp versionInfoRsp = new VersionInfoRsp();
			InputStream inputStream = servletConnection.getInputStream();
			versionInfoRsp=	convertStreamToString(inputStream);
			return versionInfoRsp;
		} catch (Exception e) {
			System.out.println("数据已经发送，但没有返回结果！");
			e.printStackTrace();
			throw e;
		}
	}
//	
	public static VersionInfoRsp convertStreamToString(InputStream is) throws Exception{   
		
		VersionInfoRsp versionInfoRsp =new VersionInfoRsp();
		RspMsgVo rspMsgVo = new RspMsgVo();
		
//        XmlPullParser parser = Xml.newPullParser();  
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
        XmlPullParser parser = factory.newPullParser();  
////        
        byte[] wr = new byte[1024];
        int ch = 0;
        DataInputStream in = new DataInputStream(is);
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    while ((ch = in.read(wr)) != -1) {
	    	bout.write(wr, 0, ch);
	    }
	    String responseXml = new String(bout.toByteArray(),"GBK");
	    responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+URLDecoder.decode(responseXml, "utf-8");
	    InputStream inputStream = new ByteArrayInputStream(responseXml.getBytes("UTF-8"));
        parser.setInput(inputStream,"utf-8");  
//        parser.setInput(is,"UTF-8");  
          
        int event = parser.getEventType();//产生第一个事件  
//        versionInfoRsp.setIsUpdate("1");
//        versionInfoRsp.setUpgradePath("?id=11fc584607d94baabce0a3d0");
//        return versionInfoRsp;
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
//            case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
//                break;  
            case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
                if("tokenId".equals(parser.getName())){
                	rspMsgVo.setTokenId(parser.nextText());
                }
                if("responseCode".equals(parser.getName())){
                	rspMsgVo.setResponseCode(parser.nextText());
                }
                if("errorMessage".equals(parser.getName())){
                	rspMsgVo.setErrorMessage(parser.nextText());  
                }
                if("isDevice".equals(parser.getName())){  
                	versionInfoRsp.setIsDevice(parser.nextText());  
                }
                if("isApp".equals(parser.getName())){  
                	versionInfoRsp.setIsApp(parser.nextText());  
                }
                if("isUser".equals(parser.getName())){
                	versionInfoRsp.setIsUser(parser.nextText());  
                }
	            if("isUpdate".equals(parser.getName())){  
	            	versionInfoRsp.setIsUpdate(parser.nextText());  
	            }
                if("isForceUp".equals(parser.getName())){  
                	versionInfoRsp.setIsForceUp(parser.nextText());  
                }
                if("upgradePath".equals(parser.getName())){  
                	versionInfoRsp.setUpgradePath(parser.nextText());  
                }
                if("versionMessage".equals(parser.getName())){  
                	versionInfoRsp.setVersionMessage(parser.nextText());  
                }
                break;  
        	case XmlPullParser.END_TAG:
        		if("RspMsgVo".equals(parser.getName())){
        			versionInfoRsp.setRspMsgVo(rspMsgVo);
                }
        		break;
            }  
            event = parser.next();//进入下一个元素并触发相应事件  
        }//end while  
        return versionInfoRsp;  
	}
//	public static VersionInfoRsp convertStreamToString(InputStream is) throws Exception {   
//	      StringBuilder sb = new StringBuilder();     
//	      String line = null;   
//	      InputStreamReader isr = null ; 
//	      BufferedReader reader = null ; 
//	      try {
//	    	  isr = new InputStreamReader(is, "UTF-8");
//	    	  reader = new BufferedReader(isr);
//	          while ((line = reader.readLine()) != null) {   
//	              sb.append(line + "\n");   
//	          }
//	          isr.close();
//	          reader.close();
//	          
//	      } catch (IOException e) {   
//	          e.printStackTrace();   
//	      } finally {   
//	          try {   
//	              is.close();   
//	          } catch (IOException e) {   
//	              e.printStackTrace();   
//	          }  
//	          try {   
//	        	  isr.close();   
//	          } catch (IOException e) {   
//	              e.printStackTrace();   
//	          }  
//	          try {   
//	        	  reader.close();   
//	          } catch (IOException e) {   
//	              e.printStackTrace();   
//	          }  
//	      }     
//	      return  convertStreamToString2(sb.toString());
//	  }
//	
//	public static VersionInfoRsp convertStreamToString2(String str) throws Exception{   
//		
//		VersionInfoRsp versionInfoRsp =new VersionInfoRsp();
//		RspMsgVo rspMsgVo = new RspMsgVo();
//      XmlPullParser parser = Xml.newPullParser();  
//      ByteArrayInputStream inps = new ByteArrayInputStream(str.getBytes()); 
//      parser.setInput(inps, "UTF-8");  
//        
//      int event = parser.getEventType();//产生第一个事件  
//      while(event!=XmlPullParser.END_DOCUMENT){  
//          switch(event){  
//          case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
//              break;  
//          case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
//              if("tokenId".equals(parser.getName())){
//              	rspMsgVo.setTokenId(parser.getText());
//              }
//              if("responseCode".equals(parser.getName())){
//              	rspMsgVo.setResponseCode(parser.getText());
//              }
//              if("errorMessage".equals(parser.getName())){
//              	rspMsgVo.setErrorMessage(parser.getText());  
//              }
//              if("isDevice ".equals(parser.getName())){  
//              	versionInfoRsp.setIsDevice(parser.getText());  
//              }
//              if("isApp ".equals(parser.getName())){  
//              	versionInfoRsp.setIsApp(parser.getText());  
//              }
//              if("isUser ".equals(parser.getName())){
//              	versionInfoRsp.setIsUser(parser.getText());  
//              }
//	            if("isUpdate".equals(parser.getName())){  
//	            	versionInfoRsp.setIsUpdate(parser.getText());  
//	            }
//              if("isForceUp ".equals(parser.getName())){  
//              	versionInfoRsp.setIsForceUp(parser.getText());  
//              }
//              if("upgradePath".equals(parser.getName())){  
//              	versionInfoRsp.setUpgradePath(parser.getText());  
//              }
//              if("versionMessage".equals(parser.getName())){  
//              	versionInfoRsp.setVersionMessage(parser.getText());  
//              }
//              break;  
//      	case XmlPullParser.END_TAG:
//      		if("RspMsgVo".equals(parser.getName())){
//      			versionInfoRsp.setRspMsgVo(rspMsgVo);
//              }
//      		break;
//          }  
//          event = parser.next();//进入下一个元素并触发相应事件  
//      }//end while  
//      return versionInfoRsp;  
//	}

}
