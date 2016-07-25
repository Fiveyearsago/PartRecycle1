package com.jy.recycle.dao;

import java.io.IOException;

import com.jy.recycle.util.Constants;

public final class DbConstants {
	public final static String BASE_PATH = Constants.DIR_SDCARD
			.concat("/jydata/");
	private final static String ONE = "01/";
	private final static String TWO = "02/";
	private final static String THR = "03/";
	private final static String FOR = "04/";

	public final static String DB_VEHICLE = ONE.concat("v.db");
	public final static String DB_JGFA_DB_NAME = FOR.concat("l_b.db");

	public static String ORG_CODE = "02";

	public final static String DB_PART(String czbm) {
		return TWO.concat(czbm).concat(".db");
	}

	public final static String DB_LOCAL_PRICE(String czbm, String code) {
		return THR.concat("l_").concat(czbm).concat("_")
				.concat(code).concat(".db");
	}

}
