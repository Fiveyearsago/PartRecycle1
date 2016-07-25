package com.jy.recycle.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {
	public static String encrypt(String password) {
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(password.getBytes());
			byte[] digesta = alg.digest();
			return byte2hex(digesta);
		} catch (NoSuchAlgorithmException NsEx) {
		}
		return null;
	}

	private static String byte2hex(byte[] bstr) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < bstr.length; ++n) {
			stmp = Integer.toHexString(bstr[n] & 0xFF);
			if (stmp.length() == 1) {
				hs.append("0");
				hs.append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(encrypt("123456001"));
	}
}
