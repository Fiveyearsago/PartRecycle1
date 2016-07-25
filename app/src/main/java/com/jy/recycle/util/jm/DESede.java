package com.jy.recycle.util.jm;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESede {
	 //加密
    public static byte[] encrypt(byte[] KEY_DATA,byte[] data ){
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        SecretKeySpec key = new SecretKeySpec(KEY_DATA, "DESede");//生成加密解密需要的Key
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] res = cipher.doFinal(data);
	        return res ; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
    
    //解密
    public static byte[] decrypt(byte[] KEY_DATA, byte[] res ){
        Cipher cipher;
        byte[] resdata =null;
		try {
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        SecretKeySpec key = new SecretKeySpec(KEY_DATA, "DESede");//生成加密解密需要的Key
	    	cipher.init(Cipher.DECRYPT_MODE, key);
	          resdata = cipher.doFinal(res);
		}catch (Exception e) {
			e.printStackTrace();
		}
    	return resdata;
    }
  
    //根据需要生成Key
    public static void genKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DESede");
            SecretKey key = kg.generateKey();
            String keyStr = DESede.convertToBase64(key.getEncoded());
            System.out.println(keyStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 

    public static byte[] convertFromBase64(String s) throws IOException {
        Base64 decoder = new Base64();
        return decoder.decode(s);
    }
    public static String convertToBase64(byte[] s) {
    	Base64 encoder = new Base64();
        return encoder.encode(s , 0 , s.length);
    }
}
