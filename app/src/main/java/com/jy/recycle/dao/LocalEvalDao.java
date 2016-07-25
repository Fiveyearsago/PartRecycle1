package com.jy.recycle.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.jy.mobile.dto.EvalLossInfoDTO;
import com.jy.mobile.dto.PjJgfaxxbBdDTO;
import com.jy.mobile.dto.ZcClfzxxbDTO;
import com.jy.mobile.dto.ZcCxxxbDTO;
import com.jy.mobile.response.SpAutoVehicleDTO;
import com.jy.mobile.response.SpVehicleListDTO;
import com.jy.recycle.util.Loger;
import com.jy.recycle.util.SpinnerItem;
import com.jy.recycle.util.jm.EncryptUtil;

public class LocalEvalDao {
	public static final String TABLE = "M_Vehicle";
	public static final String TABLE_D = "PJ_ZC_CXDYB";
	public static final String TABLE_JGFA = "M_JGFA";

	private static LocalEvalDao epd;

	public static LocalEvalDao getInstance() {
		if (epd == null) {
			epd = new LocalEvalDao();
		}
		return epd;
	}

	private LocalEvalDao() {
	}
	/**
	 * 由品牌名称获取车系数据
	 * 
	 * @param brandName
	 * @return List<ZcCxxxbDTO>
	 */
	public List<ZcCxxxbDTO> getVehicleSerialListByBrandName(String brandName) {
		
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_VEHICLE);
		List<ZcCxxxbDTO> ZcCxxxbList = null;
		if(gDb!=null){
			
			if (brandName != null && !brandName.equals("")) {
				ZcCxxxbList = new ArrayList<ZcCxxxbDTO>();
				String[] arg = { String.valueOf(brandName) };
				Cursor cursor = gDb.querySql("select distinct CXMC,CXBM from "
						+ TABLE + " where " + Columns.PPMC + " =?", arg);
				if (cursor != null) {
					while (cursor.moveToNext()) {
						ZcCxxxbDTO ZcCxxxb = getEvalCXByCursor(cursor);
						ZcCxxxbList.add(ZcCxxxb);
					}
					cursor.close();
				}
				LocalDb.close();
			}
		}
		return ZcCxxxbList;
	}

	private ZcCxxxbDTO getEvalCXByCursor(Cursor cursor) {
		ZcCxxxbDTO ZcCxxxb = new ZcCxxxbDTO();

		ZcCxxxb.setId(cursor.getString(cursor.getColumnIndex(Columns.CXBM)));
		ZcCxxxb.setSerialName(cursor.getString(cursor
				.getColumnIndex(Columns.CXMC)));

		return ZcCxxxb;
	}

	/**
	 * 由车系ID获取车组数据
	 * 
	 * @param SerialId
	 * @return List<ZcCxxxbDTO>
	 */
	public List<ZcClfzxxbDTO> getVehicleGroupListBySerialId(String SerialId) {
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_VEHICLE);
		List<ZcClfzxxbDTO> ZcClfzxxbList = null;
		if(gDb!=null){
			
			if (SerialId != null && !SerialId.equals("")) {
				ZcClfzxxbList = new ArrayList<ZcClfzxxbDTO>();
				String[] arg = { String.valueOf(SerialId) };
				Cursor cursor = gDb.querySql("select distinct CZMC,CZID from "
						+ TABLE + " where " + Columns.CXBM + " =?", arg);
				if (cursor != null) {
					while (cursor.moveToNext()) {
						ZcClfzxxbDTO ZcCxxxb = getEvalGroupByCursor(cursor);
						ZcClfzxxbList.add(ZcCxxxb);
					}
					cursor.close();
				}
				LocalDb.close();
		}
		}
		return ZcClfzxxbList;
	}

	private ZcClfzxxbDTO getEvalGroupByCursor(Cursor cursor) {
		ZcClfzxxbDTO ZcClfzxxb = new ZcClfzxxbDTO();

		ZcClfzxxb.setId(cursor.getString(cursor.getColumnIndex(Columns.CZID)));
		ZcClfzxxb
				.setCzmc(cursor.getString(cursor.getColumnIndex(Columns.CZMC)));

		return ZcClfzxxb;
	}

	/**
	 * 由车组编码获取车型信息
	 * 
	 * @param czbm
	 * @return SpVehicleListDTO
	 */
	public SpVehicleListDTO getVehicleByCZBM(String czbm) {
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_VEHICLE);
		SpVehicleListDTO spVehicleList = null;
		List<EvalLossInfoDTO> list = null;
		if(gDb!=null){
			
			if (czbm != null && !czbm.equals("")) {
				spVehicleList = new SpVehicleListDTO();
				String[] arg = { String.valueOf(czbm) };
				Cursor cursor = gDb
						.querySql(
								"select distinct CJMC,CJBM,PPMC,PPBM,CXMC,CXBM,CZMC,CZBM,CLID,CLMC,CLBM,NK,BSXLX,PL,CLZLMC,CLZLBM,CXID,CZID from "
										+ TABLE + " where " + Columns.CZID + " =?",
										arg);
				list = new ArrayList<EvalLossInfoDTO>();
				if (cursor != null) {
					while (cursor.moveToNext()) {
						EvalLossInfoDTO evalLossInfo = new EvalLossInfoDTO();
						evalLossInfo = getEvalVehicleByCursor(cursor);
						list.add(evalLossInfo);
					}
					cursor.close();
				}
				LocalDb.close();
		}
			spVehicleList.setVehicleList(list);
		}

		return spVehicleList;
	}

	private EvalLossInfoDTO getEvalVehicleByCursor(Cursor cursor) {
		EvalLossInfoDTO evalLossInfo = new EvalLossInfoDTO();
		evalLossInfo.setVehFactoryCode(cursor.getString(cursor
				.getColumnIndex(Columns.CJBM)));
		evalLossInfo.setVehFactoryName(cursor.getString(cursor
				.getColumnIndex(Columns.CJMC)));

		evalLossInfo.setVehBrandName(cursor.getString(cursor
				.getColumnIndex(Columns.PPMC)));
		evalLossInfo.setVehBrandCode(cursor.getString(cursor
				.getColumnIndex(Columns.PPBM)));

		evalLossInfo.setVehSeriCode(cursor.getString(cursor
				.getColumnIndex(Columns.CXBM)));
		evalLossInfo.setVehSeriName(cursor.getString(cursor
				.getColumnIndex(Columns.CXMC)));

		evalLossInfo.setVehGroupName(cursor.getString(cursor
				.getColumnIndex(Columns.CZMC)));

		evalLossInfo.setVehCertainCode(cursor.getString(cursor
				.getColumnIndex(Columns.CLBM)));
		evalLossInfo.setVehCertainName(cursor.getString(cursor
				.getColumnIndex(Columns.CLMC)));

		evalLossInfo.setVehYearType(cursor.getString(cursor
				.getColumnIndex(Columns.NK)));
		if(cursor.getString(cursor.getColumnIndex(Columns.BSXLX)).equals("null")){
			evalLossInfo.setBsxlx("");
		}else{
			
			evalLossInfo.setBsxlx(cursor.getString(cursor
					.getColumnIndex(Columns.BSXLX)));
		}
		
		if(cursor.getString(cursor.getColumnIndex(Columns.PL)).equals("null")){
			evalLossInfo.setDisplacement("");
		}else{
			
			evalLossInfo.setDisplacement(cursor.getString(cursor
					.getColumnIndex(Columns.PL)));
		}
		
//		evalLossInfo.setVehGroupId(cursor.getString(cursor
//				.getColumnIndex(Columns.CZID)));
//		evalLossInfo.setVehSeriId(cursor.getString(cursor
//				.getColumnIndex(Columns.CXID)));
		try {
			evalLossInfo.setVehCertainId(EncryptUtil.decrypt(cursor
					.getString(cursor.getColumnIndex(Columns.CLID))));
			evalLossInfo.setVehGroupCode(EncryptUtil.decrypt(cursor
					.getString(cursor.getColumnIndex(Columns.CZBM))));
			evalLossInfo.setVehKindName(EncryptUtil.decrypt(cursor
					.getString(cursor.getColumnIndex(Columns.CLZLMC))));
			evalLossInfo.setVehKindCode(EncryptUtil.decrypt(cursor
					.getString(cursor.getColumnIndex(Columns.CLZLBM))));
		} catch (Exception e) {
			Loger.e("xx", "车型数据解密失败！", e);
		}

		return evalLossInfo;
	}

	/**
	 * 由整车编码获取车型信息
	 * 
	 * @param zcbm
	 * @return SpAutoVehicleDTO
	 */
	public SpAutoVehicleDTO getAutoVehicleInfoByZcbm(String zcbm) {
		SpAutoVehicleDTO spAutoVehicleDTO = new SpAutoVehicleDTO();
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_VEHICLE);
		if(gDb!=null){
			
			List<EvalLossInfoDTO> list = null;
			if (zcbm != null && !zcbm.equals("")) {
				String[] arg = { String.valueOf(EncryptUtil.encrypt(zcbm)) };
				Cursor cursor = gDb
						.querySql(
								"select distinct CJMC,CJBM,PPMC,PPBM,CXMC,v.CXBM,CZMC,CZBM,CLID,CLMC,CLBM,NK,BSXLX,PL,CLZLMC,CLZLBM from "
										+ TABLE
										+ " v,"
										+ TABLE_D
										+ " P"
										+ " where "
										+ "p.CXID=v.CLID"
										+ " and p.CXBM" + " =?", arg);
				list = new ArrayList<EvalLossInfoDTO>();
				if (cursor != null) {
					while (cursor.moveToNext()) {
						EvalLossInfoDTO evalLossInfo = new EvalLossInfoDTO();
						evalLossInfo = getEvalVehicleByCursor(cursor);
						list.add(evalLossInfo);
					}
					cursor.close();
					LocalDb.close();
				}
		}
			spAutoVehicleDTO.setSpEvalLossInfoListDTO(list);
		}

		return spAutoVehicleDTO;
	}

	public static final class Columns {
		public static final String COM_CODE = "COM_CODE";// 车辆种类编码
		public static final String CJMC = "CJMC";// 厂家名称
		public static final String CJBM = "CJBM";// 厂家编码
		public static final String PPMC = "PPMC";// 品牌名称
		public static final String PPBM = "PPBM";// 品牌编码
		public static final String CXMC = "CXMC";// 车系名称
		public static final String CXBM = "CXBM";// 车系编码
		public static final String CXID = "CXID";// 车系ID
		public static final String CZMC = "CZMC";// 车组名称
		public static final String CZBM = "CZBM";// 车组编码
		public static final String CZID = "CZID";// 车组ID
		public static final String CLID = "CLID";// 车型ID
		public static final String CLMC = "CLMC";// 车型名称
		public static final String CLBM = "CLBM";// 车型编码
		public static final String NK = "NK";// 年款
		public static final String BSXLX = "BSXLX";// 变速箱类型
		public static final String PL = "PL";// 排量
		public static final String CLZLMC = "CLZLMC";// 车辆种类名称
		public static final String CLZLBM = "CLZLBM";// 车辆种类编码
	}

}
