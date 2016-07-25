package com.jy.recycle.util;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jy.ah.bus.data.Response;
import com.jy.recycle.pojo.ClaimEval;
import com.jy.recycle.pojo.EvalPart;

/**
 * Json数据操作类
 * 
 * @author zhaowenbin 2012.02.14
 * 
 */
public class JsonUtil {

	/**
	 * 根据理赔系统传入的数据初始化页面
	 */
	public static ClaimEval initClame(Bundle bundles) {
		ClaimEval dto = null;
		if (bundles != null) {
			dto = new ClaimEval();
			dto.taskNo = bundles.getString("evalNo");
			dto.vehicleCode = bundles.getString("vehicleCode");
			dto.handlerCode = bundles.getString("handlerCode");// 机构代码
			dto.orgCode = bundles.getString("orgCode");
			dto.priceSourceName = bundles.getString("priceSourceName");
			dto.priceSource = bundles.getString("priceSource");
			dto.priceCode = bundles.getString("priceType");
			dto.priceName = bundles.getString("priceTypeName");
			dto.evalType = bundles.getString("evalFlag");
			String returnPakUrl = bundles.getString("evalReturnClaUrl");
			String returnClaUrl = bundles.getString("evalReturnPakUrl");

			double manageFee = bundles.getDouble("managerRate") / 100;

			if ("1".equals(dto.evalType)) {

				dto.vehicleName = bundles.getString("vehicleName");
				dto.vehicleId = bundles.getString("vehicleId");

				dto.vehicleBian = bundles.getString("vehicleBian");
				dto.vehicleMark = bundles.getString("vehicleMark");
				dto.vehiclePail = bundles.getString("vehiclePail");
				dto.vehicleFadong = bundles.getString("vehicleFadong");
				dto.brandName = bundles.getString("vehicleBrand");
				dto.vehicleCode = bundles.getString("vehicleFlag");

				dto.vehYearType = bundles.getString("vehYearType");
				dto.familyName = bundles.getString("vehSeriName");
				dto.familyType = bundles.getString("vehSeriCode");

				try {
					String picUrlList = bundles.getString("partList");
					if (picUrlList != null && !"".equals(picUrlList)) {
						JSONObject json = new JSONObject(picUrlList);

						JSONArray jg = json.getJSONArray("fitsArray");
						if (jg.length() > 0) {
							ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
							for (int i = 0; i < jg.length(); i++) {
								JSONObject js = jg.getJSONObject(i);
								String partId = parseStrJson(js, "partId");
								String ljname = parseStrJson(js, "partName");
								String price = parseStrJson(js, "partPrice");
								String selFalg = js.getString("selfConfigFlag");
								String partPriceType = js
										.getString("priceType");
								String partPriceTypeName = js
										.getString("priceTypeName");
								String chgCompSetCode = js
										.getString("chgCompSetCode");
								String chgCompSetName = js
										.getString("chgCompSetName");
								if (selFalg != null
										&& (selFalg.equals("1") || selFalg
												.equals("2"))) {
									EvalPart ljxx = new EvalPart();
									ljxx.refPrice1 = Double
											.valueOf(parseJgJson(js,
													"partRefPrice1"));
									ljxx.refPrice2 = Double
											.valueOf(parseJgJson(js,
													"partRefPrice2"));

									ljxx.partGroupCode = js
											.getString("partGroupCode");
									ljxx.partGroupName = js
											.getString("partGroupName");
									ljxx.remark = parseStrJson(js, "partRemark");
									ljxx.totalSum = Double
											.parseDouble(parseStrJson(js,
													"partNum"));
									ljxx.partId = partId;
									ljxx.partName = ljname;
									ljxx.originNo = parseStrJson(js,
											"partOriginalId");
									ljxx.originalName = js
											.getString("originalName");

								}
							}
						}
					}
				} catch (JSONException e) {
				}
			}
		}
		return dto;
	}

	private static String parseStrJson(JSONObject js, String str) {
		String reStr = null;
		try {
			reStr = js.getString(str);
		} catch (JSONException e) {
			reStr = " ";
		}
		return reStr;
	}

	private static String parseJgJson(JSONObject js, String str) {
		String reStr = null;
		try {
			reStr = js.getString(str);
			if ("".equals(reStr)) {
				reStr = "0.0";
				return reStr;
			}
			reStr = Float.parseFloat(reStr) + "";
		} catch (JSONException e) {
			reStr = "0.0";
		}
		return reStr;
	}

	/**
	 * @param <T>
	 * @param response
	 * @param clazz
	 * @return
	 */
	public static <T> Object getSpDto(Response response, Class<T> clazz) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		if (response != null && "1".equals(response.getResponseCode())) {
			String spData = response.getData();
			if (spData != null && !"".equals(spData.trim())) {
				return gson.fromJson(response.getData(), clazz);
			}
		}
		return null;

	}

	/**
	 * 
	 * 从object中获取属性name的值。
	 * 
	 * 如果抛出JSONException,写异常日志并返回null .
	 * 
	 * @param object
	 * @param name
	 * @return
	 */
	public static String parseJson(JSONObject object, String key) {
		String value = null;
		try {
			value = object.getString(key);
		} catch (JSONException e) {
			value = null;
			Loger.e("JsonUtil", "parseJson:", e);
		}
		return value;
	}

	/**
	 * 
	 * 从object中获取属性name的数组。
	 * 
	 * 如果抛出JSONException,写异常日志并返回null .
	 * 
	 * 
	 * @param object
	 * @param name
	 * @return
	 */
	public static JSONArray parseJsonArray(JSONObject object, String key) {
		JSONArray reStr = null;
		try {
			reStr = object.getJSONArray(key);
		} catch (JSONException e) {
			reStr = null;
			Loger.e("JsonUtil", "parseJsonArray", e);
		}
		return reStr;
	}

	/**
	 * Json字符过滤
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String charFilter(String sourceStr) {
		if (sourceStr != null) {
			sourceStr = sourceStr.replace("null", "");
			sourceStr = sourceStr.replace("\n", "");
			sourceStr = sourceStr.replace("\r", "");
			sourceStr = sourceStr.replace("\\", "");
		}
		return sourceStr;
	}
}
