package com.jy.recycle.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.text.TextUtils;

import com.jy.mobile.dto.FitsDTO;
import com.jy.mobile.dto.PartGroupDTO;
import com.jy.mobile.response.SpPartGroupDTO;
import com.jy.mobile.response.SpPartListDTO;
import com.jy.recycle.util.Loger;
import com.jy.recycle.util.MathUtil;
import com.jy.recycle.util.SpinnerItem;
import com.jy.recycle.util.jm.EncryptUtil;

/**
 * @author Administrator
 * 
 */
public class LocalGroupDao {

	private static final String XTJG = "xtjg";
	private static final String BDJG = "bdjg";
	/**
	 * 常用配件查询
	 * 
	 * @param vehicleId
	 * @param jgfaId
	 * @param pageno
	 * @return
	 */
	public static SpPartListDTO getPjDataAuto(String vehicleId, String jgfaId,String pageno) {
		String sql = "select p.*,s.[YCJSCJG],s.[SYJG]"
				+ " from  m_part p	"
				+ " left outer join  m_sys_area_price s	on p.id = s.jyid and s.qyid = ?"
				+ " where  p.CLID = ? and (PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ? or PJBZBM = ?)  ";
		String[] where = { "?", vehicleId,
				EncryptUtil.encrypt("001100"),
				EncryptUtil.encrypt("002000"),
				EncryptUtil.encrypt("002200"),
				EncryptUtil.encrypt("004000"),
				EncryptUtil.encrypt("005000"),
				EncryptUtil.encrypt("010000"),
				EncryptUtil.encrypt("110000"),
				EncryptUtil.encrypt("119400"),
				EncryptUtil.encrypt("120400"),
				EncryptUtil.encrypt("122100")};
		return getPartData(jgfaId, sql, where);
	}
	/**
	 * 获取父组零件组 第一级数据
	 * 
	 * @param vehicleId
	 * @return
	 */
	public static SpPartGroupDTO getFljData(String vehicleId) {
		String dbName = getVehGroupCode(vehicleId);
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_PART(dbName));
		SpPartGroupDTO dto = null;
		if (gDb != null) {
			Cursor cursor = gDb.querySql(
					"select * from M_PART_GROUP where fzgx = '0'", null);
			if (cursor != null) {
				dto = new SpPartGroupDTO();
				List<PartGroupDTO> list = new ArrayList<PartGroupDTO>(
						cursor.getCount());
				while (cursor.moveToNext()) {
					PartGroupDTO partGroupDTO = new PartGroupDTO();
					partGroupDTO.setId(cursor.getString(cursor
							.getColumnIndex("id")));
					try {
						partGroupDTO.setName(EncryptUtil.decrypt(cursor
								.getString(cursor.getColumnIndex("bjmc"))));
					} catch (IOException e) {
						Loger.e("xx", "getFljData", e);
					}
					list.add(partGroupDTO);
				}

				dto.setPartGroupList(list);
			}
			LocalDb.close();
		}
		return dto;
	}

	/**
	 * 获取父组零件组 第二级数据
	 * 
	 * @param vehicleId
	 * @param ljzId
	 * @return
	 */
	public static SpPartGroupDTO getLjGroupData(String vehicleId, String ljzId) {
		String dbName = getVehGroupCode(vehicleId);
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_PART(dbName));
		SpPartGroupDTO dto = null;
		if (gDb != null) {
			Cursor cursor = gDb.querySql(
					"select * from M_PART_GROUP where fzgx = ?",
					new String[] { ljzId });
			if (cursor != null) {
				dto = new SpPartGroupDTO();
				List<PartGroupDTO> list = new ArrayList<PartGroupDTO>(
						cursor.getCount());
				while (cursor.moveToNext()) {
					PartGroupDTO partGroupDTO = new PartGroupDTO();
					partGroupDTO.setId(cursor.getString(cursor
							.getColumnIndex("id")));
					try {
						partGroupDTO.setName(EncryptUtil.decrypt(cursor
								.getString(cursor.getColumnIndex("bjmc"))));
					} catch (IOException e) {
						Loger.e("xx", "getFljData", e);
					}
					list.add(partGroupDTO);
				}

				dto.setPartGroupList(list);
			}
			LocalDb.close();
		}
		return dto;
	}

	/**
	 * 获取系统价格区域方案
	 * 
	 * @param jgfaId
	 *            价格方案ID
	 * @return 本地价格 系统价格
	 */
	private static PjJgxxbBd getXTQYFA(String jgfaId) {
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_JGFA_DB_NAME);
		PjJgxxbBd jg = new PjJgxxbBd();
		if (gDb != null) {
			Cursor cursor = gDb.querySql("select * from M_jgfa_qydy where jgfaid = ? and txjglx like 'xtjg%' order by txjglx limit 1",
							new String[] { jgfaId});
			if (cursor != null && cursor.moveToFirst()) {
				jg.setId(cursor.getString(cursor.getColumnIndex("ID")));
				jg.setTxjglx(cursor.getString(cursor.getColumnIndex("TXJGLX")));
				jg.setJglylx(cursor.getString(cursor.getColumnIndex("JGLYLX")));
				jg.setDyjglybh(cursor.getString(cursor.getColumnIndex("DYJGLYBH")));
				jg.setJglx(cursor.getString(cursor.getColumnIndex("JGLX")));
				jg.setJgfaid(cursor.getString(cursor.getColumnIndex("JGFAID")));
			}
			LocalDb.close();
		}
		return jg;
	}
	/**
	 * 获取本地价格区域方案
	 * 
	 * @param jgfaId
	 *            价格方案ID
	 * @return 本地价格 系统价格
	 */
	private static PjJgxxbBd getBDQYFA(String jgfaId) {
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_JGFA_DB_NAME);
		PjJgxxbBd jg = new PjJgxxbBd();
		if (gDb != null) {
			Cursor cursor = gDb.querySql(
							"select * from M_jgfa_qydy where jgfaid = ? and txjglx like 'bdjg%' order by txjglx limit 1",
							new String[] { jgfaId});
			if (cursor != null && cursor.moveToFirst()) {
				jg.setId(cursor.getString(cursor.getColumnIndex("ID")));
				jg.setTxjglx(cursor.getString(cursor.getColumnIndex("TXJGLX")));
				jg.setJglylx(cursor.getString(cursor.getColumnIndex("JGLYLX")));
				jg.setDyjglybh(cursor.getString(cursor.getColumnIndex("DYJGLYBH")));
				jg.setJglx(cursor.getString(cursor.getColumnIndex("JGLX")));
				jg.setJgfaid(cursor.getString(cursor.getColumnIndex("JGFAID")));
			}
			LocalDb.close();
		}
		return jg;
	}

	/**
	 * 获得车组编码
	 * 
	 * @param vehId
	 *            车型ID
	 * @return
	 */
	private static String getVehGroupCode(String vehId) {
		Cursor czbmCs = DBTools
				.getInstance()
				.querySql(
						"select VEH_GROUP_CODE from  M_EVAL_LOSS_INFO where veh_certain_id = ?", // 获取车组编码，得到数据库名称
						new String[] { vehId });// where[2]为车型id
		if (czbmCs != null && czbmCs.moveToFirst()) {
			return czbmCs.getString(0);
		}
		return null;
	}

//	/**
//	 * 一次查询零件价格
//	 * 
//	 * @deprecated 服务端没有零件本地价格时，离线数据没有本地价格库
//	 * @param jgfaId
//	 *            价格方案id
//	 * @param sql
//	 *            查询语句
//	 * @param where
//	 *            查询条件
//	 * @return
//	 */
//	private static SpPartListDTO getPartData2(String jgfaId, String sql,
//			String[] where) {
//		SpPartListDTO spPartListDTO = null;
//
//		Map<String, String> qyfa = getQYFA(jgfaId);
//		if (qyfa != null) {
//			String dbName = "";
//			try {
//				dbName = getVehGroupCode(where[2]);
//				LocalDb gDb = LocalDb.openDb(DbConstants.DB_PART(dbName));
//				if (gDb != null) {
//					String loadSql = "ATTACH DATABASE  \"" // 链接数据库
//							+ DbConstants.DB_LOCAL_PRICE(dbName,
//									DbConstants.ORG_CODE) + "\"  AS  \"bdjg\"";
//					gDb.execSQL(loadSql);
//
//					where[0] = qyfa.get(XTJG);// 补全参数
//					where[1] = qyfa.get(BDJG);
//
//					Cursor cursor = gDb.querySql(sql, where);
//					spPartListDTO = getSpPartListDTO(cursor);
//				}
//			} catch (Exception e) {
//				Loger.e("getPartData2", dbName, e);
//			} finally {
//
//				LocalDb.close();
//			}
//
//		}
//		return spPartListDTO;
//	}

	/**
	 * 多次查询零件价格，先查零件，在填充价格
	 * 
	 * @param jgfaId
	 * @param sql
	 * @param where
	 * @return
	 */
	private static SpPartListDTO getPartData(String jgfaId, String sql,
			String[] where) {
		SpPartListDTO dto = null;
		PjJgxxbBd xt = new PjJgxxbBd();
		xt = getXTQYFA(jgfaId);
		if (xt != null) {
			String dbName = getVehGroupCode(where[1]);
			if (!TextUtils.isEmpty(dbName)) {
				LocalDb gDb = LocalDb.openDb(DbConstants.DB_PART(dbName));
				if (gDb != null) {
					where[0] = EncryptUtil.encrypt(xt.getDyjglybh());
					Cursor cursor = gDb.querySql(sql, where);
					dto = getSpPartListDTO(cursor,xt);//将系统价格给dto
					LocalDb.close();
					if (dto != null) {
						try {
							PjJgxxbBd bd = new PjJgxxbBd();
							bd = getBDQYFA(jgfaId);
							if(bd!=null){
								setBdjg(dto.getPartList(), dbName, bd);//将本地价格给dto
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return dto;

	}

	/**
	 * 车组
	 * 
	 * @param vehicleId
	 * @param jgfaId
	 * @param ljzId
	 * @param pageno
	 * @return
	 */
	public static SpPartListDTO getJtljData(String vehicleId, String jgfaId,
			String ljzId, String pageno) {
		String sql = "select p.*,s.[YCJSCJG],s.[SYJG]"
				+ " from  m_part p	"
				+ " left outer join  m_sys_area_price s	on p.id = s.jyid and s.qyid = ?"
				+ " where  p.CLID = ? and p.PJFZID=?  ";
		ljzId = EncryptUtil.encrypt(ljzId);
		String[] where = { "?", vehicleId, ljzId };
		return getPartData(jgfaId, sql, where);
	}

	/**
	 * 原厂名称和原厂零件号 字
	 * 
	 * @param vehicleId
	 * @param jgfaId
	 * @param ljmc
	 * @param ycljh
	 * @param pageno
	 * @return
	 */
	public static SpPartListDTO getJtljData(String vehicleId, String jgfaId,
			String ljmc, String ycljh, String pageno) {
		String sql = "select p.*,s.[YCJSCJG],s.[SYJG]"
				+ " from  m_part p	"
				+ " left outer join  m_sys_area_price s	on p.id = s.jyid and s.qyid = ?"
				+ " where  p.CLID = ? and (YCLJMC like ? or YCLJH like ?)  ";
		String[] where = { "?", vehicleId,
				TextUtils.isEmpty(ljmc) ? "" : "%".concat(ljmc).concat("%"),
				TextUtils.isEmpty(ycljh) ? "" : "%".concat(ycljh).concat("%") };
		return getPartData(jgfaId, sql, where);
	}

	/**
	 * 
	 * 碰撞
	 * 
	 * @param vehicleId
	 * @param jgfaId
	 * @param pzcdStr
	 * @param pzbwStr
	 * @param pageNo
	 * @return
	 */
	public static SpPartListDTO getPzJtljData(String vehicleId, String jgfaId,
			String pzcdStr, String pzbwStr, String pageNo) {
		// String sql =
		// "select * from M_PART where  CLID = ? and ( YCLJMC = ? or YCLJH=? )";
		String sql = "select p.*,s.[YCJSCJG],s.[SYJG] "
				+ " from  m_part p	"
				+ " left outer join  m_sys_area_price s	on p.id = s.jyid and s.qyid = ?"
				+ " where  p.CLID = ? and (YCLJMC like ? or YCLJH like ?)  ";
		String[] where = { vehicleId,
				TextUtils.isEmpty(pzbwStr) ? "" : pzbwStr,
				TextUtils.isEmpty(pzcdStr) ? "" : pzcdStr };
		return getPartData(jgfaId, sql, where);
	}

	private static SpPartListDTO getSpPartListDTO(Cursor cursor,PjJgxxbBd xtjgfa) {
		SpPartListDTO dto = null;
		if (cursor != null) {
			dto = new SpPartListDTO();
			dto.setTotal(cursor.getCount());

			List<FitsDTO> list = new ArrayList<FitsDTO>(cursor.getCount());
			while (cursor.moveToNext()) {
				FitsDTO fitsDTO = new FitsDTO();
				fitsDTO.setLjid(cursor.getString(cursor.getColumnIndex("ID")));

				fitsDTO.setLjbzmc(cursor.getString(cursor
						.getColumnIndex("PJBZMC")));

				// fitsDTO.setLjfzmc(cursor.getString(cursor.getColumnIndex("")));
				fitsDTO.setYcbh(cursor.getString(cursor.getColumnIndex("YCLJH")));
				fitsDTO.setYcljmc(cursor.getString(cursor
						.getColumnIndex("YCLJMC")));
				if(cursor.getString(cursor.getColumnIndex("BZ")).equals("null")){
					fitsDTO.setBz("");
				}else{
					fitsDTO.setBz(cursor.getString(cursor.getColumnIndex("BZ")));
				}

				try {

					fitsDTO.setLjbzbh(EncryptUtil.decrypt(cursor
							.getString(cursor.getColumnIndex("PJBZBM"))));
					fitsDTO.setRefPrice1(Double.parseDouble(EncryptUtil
							.decrypt(cursor.getString(cursor
									.getColumnIndex("CFZDJG")))));// 厂房指导价
					// 零件价格
					fitsDTO.setRefPrice2(MathUtil.getDouble(EncryptUtil
							.decrypt(cursor.getString(cursor
									.getColumnIndex("YCJSCJG"))), 0));// 市场价格
					fitsDTO.setRefPrice3(MathUtil.getDouble(EncryptUtil
							.decrypt(cursor.getString(cursor
									.getColumnIndex("SYJG"))), 0));// 系统区域适用价
					
					fitsDTO.setXtjg(getXTjg(fitsDTO,xtjgfa));//给系统价格赋值
					
					fitsDTO.setJyidrd(cursor.getString(cursor.getColumnIndex("JYID")));

				} catch (Exception e) {
					Loger.e("xx", "getSpPartListDTO", e);
				}
				list.add(fitsDTO);
			}

			dto.setPartList(list);
		}
		return dto;
	}
	/**
	 * 给系统价格赋值
	 */
	private static Double getXTjg(FitsDTO fitsDTO,PjJgxxbBd xtjgfa){
		Double xtjg= 0.0;
		if(xtjgfa.getJglylx().equals("DQ")){
				if(xtjgfa.getJglx().equals("SCJG")){
					xtjg=fitsDTO.getRefPrice2();
				}else if(xtjgfa.getJglx().equals("SYJG")){
					xtjg=fitsDTO.getRefPrice3();
				}else if(xtjgfa.getJglx().equals("YCJG")){
					xtjg=fitsDTO.getRefPrice1();
				}
		}
		return xtjg;
	}
	/**
	 * 填充零件本地价格
	 * 
	 * @param list
	 * @param bdjgfa
	 * @throws IOException 
	 */
	private static void setBdjg(List<FitsDTO> list, String vehGroupCode,PjJgxxbBd bdjgfa) throws IOException {
		LocalDb db = LocalDb.openDb(DbConstants.DB_LOCAL_PRICE(vehGroupCode,
				DbConstants.ORG_CODE));
		if (db != null) {
			for (FitsDTO dto : list) {
				String a = EncryptUtil.decrypt("EKj4IBCufd0rXtfUysxizyxcXSxjc5oXh8WjOeI4q1nBjf2g11bJDg==");
				String jyid=EncryptUtil.encrypt(dto.getLjid());
				String qyid= EncryptUtil.encrypt(bdjgfa.getDyjglybh());
				Cursor cs = db
						.querySql(
								"select [BDJG1],BDJG2,BDJG3 from  m_local_price where jyid = ? and qyid = ?",
								new String[] {jyid,qyid });
				if (cs != null && cs.moveToFirst()) {

					try {
						dto.setLocPrice1(MathUtil.getDouble(
								EncryptUtil.decrypt(cs.getString(0)), 0));
						dto.setLocPrice2(MathUtil.getDouble(
								EncryptUtil.decrypt(cs.getString(1)), 0));
						dto.setLocPrice3(MathUtil.getDouble(
								EncryptUtil.decrypt(cs.getString(2)), 0));
						dto.setBdjg(getBDjg(dto,bdjgfa));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		LocalDb.close();
	}
	/**
	 * 给系统价格赋值
	 */
	private static Double getBDjg(FitsDTO fitsDTO,PjJgxxbBd bdjgfa){
		Double xtjg= 0.0;
		if(bdjgfa.getJglylx().equals("DQ")){
				if(bdjgfa.getJglx().equals("SCJG")){
					xtjg=fitsDTO.getLocPrice2();
				}else if(bdjgfa.getJglx().equals("SYJG")){
					xtjg=fitsDTO.getLocPrice3();
				}else if(bdjgfa.getJglx().equals("YCJG")){
					xtjg=fitsDTO.getLocPrice1();
				}
		}
		return xtjg;
	}

	/**
	 * 获得价格方案列表
	 * 
	 * @param orgCode
	 * @return
	 */
	public static List<SpinnerItem> getJgfa(String orgCode) {
		// if(Constants.DEBUG){
		// orgCode = "1010100";//TODO zl 删除
		// }
		List<SpinnerItem> list = null;
		LocalDb gDb = LocalDb.openDb(DbConstants.DB_JGFA_DB_NAME);
		if (gDb != null) {
			Cursor cursor = gDb.querySql(
					"select ID,JGFAMC from m_jgfa where com_code = ? ",
					new String[] { orgCode });

			if (cursor != null && cursor.getCount() > 0) {
				list = new ArrayList<SpinnerItem>(cursor.getCount());
				while (cursor.moveToNext()) {
					list.add(new SpinnerItem(cursor.getString(0), cursor
							.getString(1)));
				}
			}
			LocalDb.close();
		}

		return list;
	}
}
