package com.jy.recycle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jy.mobile.dto.PjJgfaxxbBdDTO;

import android.content.Context;
import android.widget.ArrayAdapter;

public final class StaticCode {
	/** 保险事故分类代码 */
	public static HashMap<Integer, String> damageTypeMap;
	/** 事故处理部门 */
	public static ArrayList<SpinnerItem> dealDepartmentCodeList;;
	/** 事故责任划分代码 */
	public static ArrayList<SpinnerItem> damageDutyList;
	/** 事故处理方式代码 */
	public static ArrayList<SpinnerItem> accidentHandleTypeList;
	/** 出险原因代码 */
	public static ArrayList<SpinnerItem> damageCauseList;
	/** 责任认定书类型代码 */
	public static ArrayList<SpinnerItem> subCertiTypeList;
	/** 号牌种类代码 */
	public static ArrayList<SpinnerItem> cardTypeCodeList;
	/** 承保机构代码 */
	public static ArrayList<SpinnerItem> insuranceCompanyList;
	/** 是否代码代码 "0", "否" "1", "是"*/
	public static ArrayList<SpinnerItem> flagList;
	/** 车辆属性代码 */
	public static ArrayList<SpinnerItem> vehiclePropertyList;
	/** 车辆种类代码 */
	public static ArrayList<SpinnerItem> vehicleTypeList;
	/** 地区代码 */
	public static ArrayList<SpinnerItem> areaList;
	/** 财产属性代码 */
	public static ArrayList<SpinnerItem> protectPropertyList;
	/** 人员属性代码 */
	public static ArrayList<SpinnerItem> personPropertyList;
	/** 伤亡类型代码 */
	public static ArrayList<SpinnerItem> personPayTypeList;
	/** 图片类型代码 */
	public static ArrayList<SpinnerItem> imageTypeList;
	/** 出险区域代码 */
	public static ArrayList<SpinnerItem> damageAreaList;
	/** 定损方式代码 */
	public static ArrayList<SpinnerItem> evalTypeList;
	/** 现场类别代码 */
	public static ArrayList<SpinnerItem> firstSeneFlagList;
	public static ArrayList<SpinnerItem> driverLocalList;
	public static ArrayList<SpinnerItem> lossPartList;
	public static ArrayList<SpinnerItem> drivercarCodeList;
	public static ArrayList<SpinnerItem> bjHandleTypeList;
	public static ArrayList<SpinnerItem> shHandleTypeList;
	public static ArrayList<SpinnerItem> existDutyBillList;
	public static ArrayList<SpinnerItem> dutyClaimList;
	public static ArrayList<SpinnerItem> driverSexList;
	public static ArrayList<SpinnerItem> diaTypeList;
	public static ArrayList<SpinnerItem> lossTypeList;
	public static ArrayList<SpinnerItem> credTypeList;
	public static ArrayList<SpinnerItem> handleDeptList;
	public static ArrayList<SpinnerItem> roadFlagList;
	/** 互碰自赔标志 */
	public static ArrayList<SpinnerItem> selfFlagList;
	/** 行驶区域代码 */
	public static ArrayList<SpinnerItem> runAreaList;
	/** 损失程度代码 */
	public static ArrayList<SpinnerItem> lossLevelList;
	/** 事故处理信息代码 */
	public static ArrayList<SpinnerItem> accidentHandleInfoList;
	/** 紧急程度代码 */
	public static ArrayList<SpinnerItem> urgencyLevelList;
	/** 事故类型代码 */
	public static ArrayList<SpinnerItem> accidentClassList;
	/** 碰撞程度代码 */
	public static ArrayList<SpinnerItem> pzcdList;
	/** 碰撞部位代码 */
	public static ArrayList<SpinnerItem> pzbwList;
	
	/** 自定义定型 */
	public static ArrayList<SpinnerItem> autoVtnList;
	
	/**其他费用*/
	public static ArrayList<SpinnerItem> otherFeeNameList;

	static {
		initSpinnerItemList();
	}

	private static void initSpinnerItemList() {
		
		pzbwList = new ArrayList<SpinnerItem>();
		pzbwList.add(new SpinnerItem("1", "车辆头部"));
		pzbwList.add(new SpinnerItem("1", "车辆头部"));
		pzbwList.add(new SpinnerItem("2", "左前车身"));
		pzbwList.add(new SpinnerItem("3", "右前车身"));
		pzbwList.add(new SpinnerItem("4", "左侧车身"));
		pzbwList.add(new SpinnerItem("5", "右侧车身"));
		pzbwList.add(new SpinnerItem("6", "左后车身"));
		pzbwList.add(new SpinnerItem("7", "右后车身"));
		pzbwList.add(new SpinnerItem("8", "车辆尾部"));

		pzcdList = new ArrayList<SpinnerItem>();
		pzcdList.add(new SpinnerItem("A", "轻度碰撞"));
		pzcdList.add(new SpinnerItem("A", "轻度碰撞"));
		pzcdList.add(new SpinnerItem("B", "中度碰撞"));
		pzcdList.add(new SpinnerItem("C", "重度碰撞"));
		
		evalTypeList = new ArrayList<SpinnerItem>();
		evalTypeList.add(new SpinnerItem("01", "修复定损"));
		evalTypeList.add(new SpinnerItem("02", "推定全损"));
		evalTypeList.add(new SpinnerItem("03", "一次性协议定损"));
		
		
		autoVtnList=new ArrayList<SpinnerItem>();
		autoVtnList.add(new SpinnerItem("A", "标准客车"));
		autoVtnList.add(new SpinnerItem("B", "标准货车"));
		autoVtnList.add(new SpinnerItem("C", "标准桥车"));
		autoVtnList.add(new SpinnerItem("D", "其它车型"));
		
		

		accidentHandleInfoList = new ArrayList<SpinnerItem>();
		accidentHandleInfoList.add(new SpinnerItem("01", "现场查勘"));
		accidentHandleInfoList.add(new SpinnerItem("02", "第一现场报案"));
		accidentHandleInfoList.add(new SpinnerItem("03", "进行救助"));
		accidentHandleInfoList.add(new SpinnerItem("04", "共保"));
		accidentHandleInfoList.add(new SpinnerItem("05", "客户放弃索赔"));
		accidentHandleInfoList.add(new SpinnerItem("06", "不属于保险责任"));
		accidentHandleInfoList.add(new SpinnerItem("07", "重大案件通报"));
		accidentHandleInfoList.add(new SpinnerItem("08", "单方肇事"));

		lossLevelList = new ArrayList<SpinnerItem>();
		lossLevelList.add(new SpinnerItem("01", "轻微受损"));
		lossLevelList.add(new SpinnerItem("02", "轻度受损"));
		lossLevelList.add(new SpinnerItem("03", "中度受损"));
		lossLevelList.add(new SpinnerItem("04", "严重受损"));
		lossLevelList.add(new SpinnerItem("05", "非常严重"));
		lossLevelList.add(new SpinnerItem("99", "其他"));

		urgencyLevelList = new ArrayList<SpinnerItem>();
		urgencyLevelList.add(new SpinnerItem("01", "一般"));
		urgencyLevelList.add(new SpinnerItem("02", "重要"));
		urgencyLevelList.add(new SpinnerItem("03", "紧急"));

		accidentClassList = new ArrayList<SpinnerItem>();
		accidentClassList.add(new SpinnerItem("01", "单方事故"));
		accidentClassList.add(new SpinnerItem("02", "双方事故"));
		accidentClassList.add(new SpinnerItem("03", "多方事故"));
		accidentClassList.add(new SpinnerItem("99", "其他"));

		runAreaList = new ArrayList<SpinnerItem>();
		runAreaList.add(new SpinnerItem("01", "本市（地区）"));
		runAreaList.add(new SpinnerItem("02", "省内行驶"));
		runAreaList.add(new SpinnerItem("03", "跨省（市、区"));
		runAreaList.add(new SpinnerItem("04", "粤港两地"));
		runAreaList.add(new SpinnerItem("05", "国内（含港澳）"));
		runAreaList.add(new SpinnerItem("06", "国内（不含港澳台）"));
		runAreaList.add(new SpinnerItem("07", "有固定行驶路线"));
		runAreaList.add(new SpinnerItem("08", "无固定行驶路线"));
		runAreaList.add(new SpinnerItem("99", "其它区域"));

		dealDepartmentCodeList = new ArrayList<SpinnerItem>();
		dealDepartmentCodeList.add(new SpinnerItem("01", "交警部门"));
		dealDepartmentCodeList.add(new SpinnerItem("02", "保险公司"));
		dealDepartmentCodeList.add(new SpinnerItem("03", "其他部门"));

		damageAreaList = new ArrayList<SpinnerItem>();
		damageAreaList.add(new SpinnerItem("01", "市内"));
		damageAreaList.add(new SpinnerItem("02", "市外"));
		damageAreaList.add(new SpinnerItem("03", "省内"));
		damageAreaList.add(new SpinnerItem("04", "省外"));
		damageAreaList.add(new SpinnerItem("05", "港澳"));
		damageAreaList.add(new SpinnerItem("10", "中国境外"));
		damageAreaList.add(new SpinnerItem("99", "其他"));

		personPropertyList = new ArrayList<SpinnerItem>();
		personPropertyList.add(new SpinnerItem("1", "本车人员"));
		personPropertyList.add(new SpinnerItem("2", "车外人员"));

		personPayTypeList = new ArrayList<SpinnerItem>();
		personPayTypeList.add(new SpinnerItem("1", "伤残"));
		personPayTypeList.add(new SpinnerItem("2", "死亡"));

		damageDutyList = new ArrayList<SpinnerItem>();
		damageDutyList.add(new SpinnerItem("01", "全责"));
		damageDutyList.add(new SpinnerItem("02", "主责"));
		damageDutyList.add(new SpinnerItem("03", "同责"));
		damageDutyList.add(new SpinnerItem("04", "次责"));
		damageDutyList.add(new SpinnerItem("05", "无责"));
		damageDutyList.add(new SpinnerItem("06", "未定责"));

		firstSeneFlagList = new ArrayList<SpinnerItem>();
		firstSeneFlagList.add(new SpinnerItem("1", "第一现场"));
		firstSeneFlagList.add(new SpinnerItem("0", "非第一现场"));

		roadFlagList = new ArrayList<SpinnerItem>();
		roadFlagList.add(new SpinnerItem("1", "属道路交通事故"));
		roadFlagList.add(new SpinnerItem("0", "非道路交通事故"));

		selfFlagList = new ArrayList<SpinnerItem>();
		selfFlagList.add(new SpinnerItem("0", "非互碰自赔"));
		selfFlagList.add(new SpinnerItem("1", "互碰自赔"));

		lossTypeList = new ArrayList<SpinnerItem>();
		lossTypeList.add(new SpinnerItem("0", "无"));
		lossTypeList.add(new SpinnerItem("1", "车损"));
		lossTypeList.add(new SpinnerItem("2", "物损"));
		lossTypeList.add(new SpinnerItem("3", "人伤"));
		lossTypeList.add(new SpinnerItem("4", "盗抢"));

		credTypeList = new ArrayList<SpinnerItem>();
		credTypeList.add(new SpinnerItem("01", "身份证"));
		credTypeList.add(new SpinnerItem("02", "户口簿"));
		credTypeList.add(new SpinnerItem("03", "驾驶证"));
		credTypeList.add(new SpinnerItem("04", "军官证"));
		credTypeList.add(new SpinnerItem("05", "士兵证"));
		credTypeList.add(new SpinnerItem("06", "军官离退休证"));
		credTypeList.add(new SpinnerItem("07", "中国护照"));
		credTypeList.add(new SpinnerItem("41", "港澳通行证"));
		credTypeList.add(new SpinnerItem("42", "台湾通行证"));
		credTypeList.add(new SpinnerItem("51", "外国护照"));
		credTypeList.add(new SpinnerItem("52", "旅行证"));
		credTypeList.add(new SpinnerItem("53", "居留证件"));
		credTypeList.add(new SpinnerItem("71", "组织机构代码证"));
		credTypeList.add(new SpinnerItem("99", "其他证件"));

		// if ("YSPZ".equals(damageType)) {
		// damageType = "碰撞";
		// } else if ("CLPS".equals(damageType)) {
		// damageType = "单方碰撞(公路上)";
		// } else if ("CLPW".equals(damageType)) {
		// damageType = "单方碰撞(公路外)";
		// } else if ("CLPZ".equals(damageType)) {
		// damageType = "两车以上碰撞";
		// } else if ("CL3Z".equals(damageType)) {
		// damageType = "第三者责任";
		// } else if ("CLBL".equals(damageType)) {
		// damageType = "玻璃破碎险";
		// } else if ("CLCZ".equals(damageType)) {
		// damageType = "车载货物撞击";
		// } else if ("CLDL".equals(damageType)) {
		// damageType = "车载货物掉落";
		// } else if ("CLQF".equals(damageType)) {
		// damageType = "倾覆";
		// } else if ("CLDQ".equals(damageType)) {
		// damageType = "盗抢险";
		// } else if ("CLQT".equals(damageType)) {
		// damageType = "其它";
		// }
		damageCauseList = new ArrayList<SpinnerItem>();
		damageCauseList.add(new SpinnerItem("01", "追尾"));
		damageCauseList.add(new SpinnerItem("02", "逆行"));
		damageCauseList.add(new SpinnerItem("03", "倒车"));
		damageCauseList.add(new SpinnerItem("04", "溜车"));
		damageCauseList.add(new SpinnerItem("08", "单车事故"));
		damageCauseList.add(new SpinnerItem("05", "开关车门"));
		damageCauseList.add(new SpinnerItem("06", "违反交通信号"));
		damageCauseList.add(new SpinnerItem("07", "未按规定让行"));
		damageCauseList.add(new SpinnerItem("98", "一方全责一方无责的其他情形"));
		// damageCauseList.add(new SpinnerItem("A10001", "火灾"));
		// damageCauseList.add(new SpinnerItem("A10002", "爆炸"));
		// damageCauseList.add(new SpinnerItem("A10003", "暴雨"));
		// damageCauseList.add(new SpinnerItem("A10004", "暴风"));
		// damageCauseList.add(new SpinnerItem("A10005", "雷击"));
		// damageCauseList.add(new SpinnerItem("A10006", "台风"));
		// damageCauseList.add(new SpinnerItem("A10007", "地面下陷/地陷"));
		// damageCauseList.add(new SpinnerItem("A10008", "雪灾"));
		// damageCauseList.add(new SpinnerItem("A10009", "雹灾"));
		// damageCauseList.add(new SpinnerItem("A10010", "冰凌"));
		// damageCauseList.add(new SpinnerItem("A10011", "突发性滑坡"));
		// damageCauseList.add(new SpinnerItem("A10012", "崖崩"));
		// damageCauseList.add(new SpinnerItem("A10013", "泥石流"));
		// damageCauseList.add(new SpinnerItem("A10014", "洪水"));
		// damageCauseList.add(new SpinnerItem("A10015", "海啸"));
		// damageCauseList.add(new SpinnerItem("A10016", "地震"));
		// damageCauseList.add(new SpinnerItem("A10017", "龙卷风"));
		// damageCauseList.add(new SpinnerItem("A10018", "雇员忠诚"));
		// damageCauseList.add(new SpinnerItem("A10020", "工人技术人员缺乏经验、疏忽、过失"));
		// damageCauseList.add(new SpinnerItem("A10021", "原材料缺陷或工艺不善"));
		// damageCauseList.add(new SpinnerItem("A10022", "玻璃单独破碎"));
		// damageCauseList.add(new SpinnerItem("A10023", "盗窃抢劫"));
		// damageCauseList.add(new SpinnerItem("A10024", "水管爆裂"));
		// damageCauseList.add(new SpinnerItem("A10025", "外界物体坠落"));
		// damageCauseList.add(new SpinnerItem("A10026", "空坠物"));
		// damageCauseList.add(new SpinnerItem("A10027", "电气事故其它"));
		// damageCauseList.add(new SpinnerItem("A10028", "意外断电"));

		damageTypeMap = new HashMap<Integer, String>();
		damageTypeMap.put(100, "交通事故类");
		damageTypeMap.put(111, "正面相撞");
		damageTypeMap.put(112, "侧面相撞");
		damageTypeMap.put(113, "尾随相撞");
		damageTypeMap.put(121, "对面刮擦");
		damageTypeMap.put(122, "同向刮擦");
		damageTypeMap.put(130, "碾压");
		damageTypeMap.put(140, "翻车 ");
		damageTypeMap.put(150, "坠车");
		damageTypeMap.put(160, "失火");
		damageTypeMap.put(170, "撞固定物");
		damageTypeMap.put(180, "撞路人");
		damageTypeMap.put(190, "撞静止车辆");
		damageTypeMap.put(199, "其它交通事故");
		damageTypeMap.put(200, "单方事故类");
		damageTypeMap.put(201, "火灾（非自燃）");
		damageTypeMap.put(202, "爆炸");
		damageTypeMap.put(203, "自燃");
		damageTypeMap.put(204, "盗抢");
		damageTypeMap.put(205, "载运保险车辆的渡船遭受自然灾害");
		damageTypeMap.put(221, "玻璃单独破碎");
		damageTypeMap.put(222, "车身划痕");
		damageTypeMap.put(223, "轮胎单独损坏");
		damageTypeMap.put(224, "车内物品或行李被盗抢");
		damageTypeMap.put(225, "涉水损失、发动机进水损失");
		damageTypeMap.put(226, "车载固定仪器、设备损坏");
		damageTypeMap.put(231, "车载货物碰撞责任");
		damageTypeMap.put(232, "车载货物掉落责任");
		damageTypeMap.put(241, "外部物体倒塌");
		damageTypeMap.put(242, "外部物体坠落");
		damageTypeMap.put(260, "救助服务");
		damageTypeMap.put(261, "代理驾驶");
		damageTypeMap.put(262, "送油、送水服务");
		damageTypeMap.put(263, "更换轮胎");
		damageTypeMap.put(264, "电瓶充电");
		damageTypeMap.put(265, "拖拽服务");
		damageTypeMap.put(270, "事故费用");
		damageTypeMap.put(299, "其它非交通事故类保险事故");
		damageTypeMap.put(500, "自然灾害类");
		damageTypeMap.put(501, "雷击");
		damageTypeMap.put(502, "暴风");
		damageTypeMap.put(503, "龙卷风");
		damageTypeMap.put(504, "暴雨");
		damageTypeMap.put(505, "洪水");
		damageTypeMap.put(506, "海啸");
		damageTypeMap.put(507, "地陷");
		damageTypeMap.put(508, "冰陷");
		damageTypeMap.put(509, "崖崩");
		damageTypeMap.put(510, "雪崩");
		damageTypeMap.put(511, "雹灾");
		damageTypeMap.put(512, "泥石流");
		damageTypeMap.put(513, "滑坡");
		damageTypeMap.put(599, "其它自然灾害");
		damageTypeMap.put(600, "社会灾害类");
		damageTypeMap.put(601, "动乱、群体性暴力事件");
		damageTypeMap.put(602, "战争");
		damageTypeMap.put(603, "恐怖活动");
		damageTypeMap.put(699, "其它社会灾害类原因");
		damageTypeMap.put(900, "其它保险事故");
		damageTypeMap.put(901, "油污污损");
		damageTypeMap.put(902, "精神损害");

		// 人员属性
		protectPropertyList = new ArrayList<SpinnerItem>();
		protectPropertyList.add(new SpinnerItem("1", "本车财产"));
		protectPropertyList.add(new SpinnerItem("2", "车外财产"));

		driverSexList = new ArrayList<SpinnerItem>();
		driverSexList.add(new SpinnerItem("1", "男"));
		driverSexList.add(new SpinnerItem("2", "女"));

		vehiclePropertyList = new ArrayList<SpinnerItem>();
		vehiclePropertyList.add(new SpinnerItem("1", "本车"));
		vehiclePropertyList.add(new SpinnerItem("2", "三者车"));

		vehicleTypeList = new ArrayList<SpinnerItem>();
		vehicleTypeList.add(new SpinnerItem(11, "六座以下客车"));
		vehicleTypeList.add(new SpinnerItem(12, "六座至十座客车"));
		vehicleTypeList.add(new SpinnerItem(13, "十座至二十座以下客车"));
		vehicleTypeList.add(new SpinnerItem(14, "二十座至三十六座客车"));
		vehicleTypeList.add(new SpinnerItem(15, "三十六座以上客车"));
		vehicleTypeList.add(new SpinnerItem(21, "二吨以下货车"));
		vehicleTypeList.add(new SpinnerItem(22, "二吨至五吨货车"));
		vehicleTypeList.add(new SpinnerItem(23, "五吨至十吨货车"));
		vehicleTypeList.add(new SpinnerItem(24, "十吨以上货车"));
		vehicleTypeList.add(new SpinnerItem(25, "二吨以下挂车"));
		vehicleTypeList.add(new SpinnerItem(26, "二吨至五吨挂车"));
		vehicleTypeList.add(new SpinnerItem(27, "五吨至十吨挂车"));
		vehicleTypeList.add(new SpinnerItem(28, "十吨以上挂车"));
		vehicleTypeList.add(new SpinnerItem(30, "油罐车、汽罐车、液罐车"));
		vehicleTypeList.add(new SpinnerItem(31, "特种车一挂车"));
		vehicleTypeList.add(new SpinnerItem(40, "特种车二"));
		vehicleTypeList.add(new SpinnerItem(41, "特种车二挂车"));
		vehicleTypeList.add(new SpinnerItem(50, "特种车三"));
		vehicleTypeList.add(new SpinnerItem(51, "特种车三挂车"));
		vehicleTypeList.add(new SpinnerItem(60, "特种车四"));
		vehicleTypeList.add(new SpinnerItem(71, "摩托车（50cc及以下）"));
		vehicleTypeList.add(new SpinnerItem(72, "摩托车（50cc-250cc）"));
		vehicleTypeList.add(new SpinnerItem(73, "摩托车（250cc以上及侧三轮）"));
		vehicleTypeList.add(new SpinnerItem(81, "拖拉机（14.7kw及以下）"));
		vehicleTypeList.add(new SpinnerItem(82, "拖拉机14.7kw以上"));
		vehicleTypeList.add(new SpinnerItem(91, "运输型拖拉机14.7KW及以下"));
		vehicleTypeList.add(new SpinnerItem(92, "运输型拖拉机14.7KW以上"));
		vehicleTypeList.add(new SpinnerItem(93, "低速载货汽车"));
		vehicleTypeList.add(new SpinnerItem(94, "三轮汽车"));

		diaTypeList = new ArrayList<SpinnerItem>();
		diaTypeList.add(new SpinnerItem("1", "本人PC"));
		diaTypeList.add(new SpinnerItem("0", "改派他人"));

		subCertiTypeList = new ArrayList<SpinnerItem>();
		subCertiTypeList.add(new SpinnerItem("1", "交通事故责任认定书"));
		subCertiTypeList.add(new SpinnerItem("2", "简易事故处理协议"));
		subCertiTypeList.add(new SpinnerItem("3", "其他认定事故责任的证明"));

		insuranceCompanyList = new ArrayList<SpinnerItem>();
		insuranceCompanyList.add(new SpinnerItem("TPIC", "太平保险有限公司"));
		insuranceCompanyList.add(new SpinnerItem("CLPC", "中国人寿财产保险公司"));
		insuranceCompanyList.add(new SpinnerItem("HNIC", "华农财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("TAIC", "天安保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("AHIC", "安华农业保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("AICS", "永诚财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("DBIC", "都邦财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("AAIC", "安信农业保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("CPIC", "中国太平洋财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("CAPL", "长安责任保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("HTIC", "华泰财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("MACN", "民安保险(中国)有限公司  "));
		insuranceCompanyList.add(new SpinnerItem("ABIC", "安邦财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("PICC", "中国人民财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("YAIC", "永安财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("CICP", "中华联合财产保险公司    "));
		insuranceCompanyList.add(new SpinnerItem("ZKIC", "紫金财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("ZMIC", "中煤财产保险股份有限公司"));
		insuranceCompanyList
				.add(new SpinnerItem("AAAA", "无保险公司              "));
		insuranceCompanyList.add(new SpinnerItem("YGBX", "阳光财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("ACIC", "安诚财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("PAIC", "中国平安财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("BOCI", "中银保险有限公司        "));
		insuranceCompanyList.add(new SpinnerItem("DHIC", "鼎和财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("BPIC", "渤海财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("CCIC", "中国大地财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("SMIC", "阳光农业相互保险公司    "));
		insuranceCompanyList.add(new SpinnerItem("HAIC", "华安财产保险股份有限公司"));
		insuranceCompanyList.add(new SpinnerItem("DICC", "大众保险股份有限公司    "));
		insuranceCompanyList.add(new SpinnerItem("TPBX", "天平汽车保险股份有限公司"));

		areaList = new ArrayList<SpinnerItem>();
		areaList.add(new SpinnerItem("310000", "上海"));
		areaList.add(new SpinnerItem("120000", "天津"));
		areaList.add(new SpinnerItem("500000", "重庆"));
		areaList.add(new SpinnerItem("110000", "北京"));
		areaList.add(new SpinnerItem("230000", "黑龙江"));
		areaList.add(new SpinnerItem("220000", "吉林"));
		areaList.add(new SpinnerItem("210000", "辽宁"));
		areaList.add(new SpinnerItem("130000", "河北"));
		areaList.add(new SpinnerItem("140000", "山西"));
		areaList.add(new SpinnerItem("370000", "山东"));
		areaList.add(new SpinnerItem("340000", "安徽"));
		areaList.add(new SpinnerItem("320000", "江苏"));
		areaList.add(new SpinnerItem("330000", "浙江"));
		areaList.add(new SpinnerItem("350000", "福建"));
		areaList.add(new SpinnerItem("360000", "江西"));
		areaList.add(new SpinnerItem("440000", "广东"));
		areaList.add(new SpinnerItem("460000", "海南"));
		areaList.add(new SpinnerItem("450000", "广西"));
		areaList.add(new SpinnerItem("430000", "湖南"));
		areaList.add(new SpinnerItem("420000", "湖北"));
		areaList.add(new SpinnerItem("410000", "河南"));
		areaList.add(new SpinnerItem("530000", "云南"));
		areaList.add(new SpinnerItem("520000", "贵州"));
		areaList.add(new SpinnerItem("510000", "四川"));
		areaList.add(new SpinnerItem("610000", "陕西"));
		areaList.add(new SpinnerItem("620000", "甘肃"));
		areaList.add(new SpinnerItem("640000", "宁夏"));
		areaList.add(new SpinnerItem("650000", "新疆"));
		areaList.add(new SpinnerItem("150000", "内蒙古"));
		areaList.add(new SpinnerItem("210200", "大连"));
		areaList.add(new SpinnerItem("370200", "青岛"));
		areaList.add(new SpinnerItem("330200", "宁波"));
		areaList.add(new SpinnerItem("440300", "深圳"));
		areaList.add(new SpinnerItem("630000", "青海"));
		areaList.add(new SpinnerItem("540000", "西藏"));
		areaList.add(new SpinnerItem("350200", "厦门"));

		/**
		 * claimBankList = new ArrayList<SpinnerItem>(); claimBankList.add(new
		 * SpinnerItem("01", "中国工商银行")); claimBankList.add(new SpinnerItem("02",
		 * "中国农业银行")); claimBankList.add(new SpinnerItem("03", "中国银行"));
		 * claimBankList.add(new SpinnerItem("04", "中国建设银行"));
		 * claimBankList.add(new SpinnerItem("05", "交通银行"));
		 * claimBankList.add(new SpinnerItem("06", "兴业银行"));
		 * claimBankList.add(new SpinnerItem("07", "招商银行"));
		 * claimBankList.add(new SpinnerItem("08", "广东发展银行"));
		 * claimBankList.add(new SpinnerItem("09", "中国民生银行"));
		 * claimBankList.add(new SpinnerItem("10", "中信银行"));
		 * claimBankList.add(new SpinnerItem("11", "华夏银行"));
		 * claimBankList.add(new SpinnerItem("12", "江苏省农村信用合作联社"));
		 * claimBankList.add(new SpinnerItem("13", "中国光大银行"));
		 * claimBankList.add(new SpinnerItem("14", "广州商行"));
		 * claimBankList.add(new SpinnerItem("15", "上海浦东发展银行"));
		 * claimBankList.add(new SpinnerItem("16", "平安银行"));
		 * claimBankList.add(new SpinnerItem("17", "上海农信"));
		 * claimBankList.add(new SpinnerItem("18", "东亚银行"));
		 * claimBankList.add(new SpinnerItem("19", "珠海市农村信用合作社"));
		 * claimBankList.add(new SpinnerItem("20", "渤海银行"));
		 * claimBankList.add(new SpinnerItem("21", "温州商行"));
		 * claimBankList.add(new SpinnerItem("22", "晋城商行"));
		 * claimBankList.add(new SpinnerItem("23", "尧都商行"));
		 * claimBankList.add(new SpinnerItem("24", "宁波银行"));
		 * claimBankList.add(new SpinnerItem("25", "富滇银行"));
		 * claimBankList.add(new SpinnerItem("26", "顺德农村信用合作社"));
		 **/

		// driverLocalList = new ArrayList<SpinnerItem>();
		// driverLocalList.add(new SpinnerItem("11", "京"));
		// driverLocalList.add(new SpinnerItem("12", "津"));
		// driverLocalList.add(new SpinnerItem("13", "冀"));
		// driverLocalList.add(new SpinnerItem("14", "晋"));
		// driverLocalList.add(new SpinnerItem("15", "蒙"));
		// driverLocalList.add(new SpinnerItem("21", "辽"));
		// driverLocalList.add(new SpinnerItem("22", "吉"));
		// driverLocalList.add(new SpinnerItem("23", "黑"));
		// driverLocalList.add(new SpinnerItem("31", "沪"));
		// driverLocalList.add(new SpinnerItem("32", "苏"));
		// driverLocalList.add(new SpinnerItem("33", "浙"));
		// driverLocalList.add(new SpinnerItem("34", "皖"));
		// driverLocalList.add(new SpinnerItem("35", "闽"));
		// driverLocalList.add(new SpinnerItem("36", "赣"));
		// driverLocalList.add(new SpinnerItem("37", "鲁"));
		// driverLocalList.add(new SpinnerItem("41", "豫"));
		// driverLocalList.add(new SpinnerItem("42", "鄂"));
		// driverLocalList.add(new SpinnerItem("43", "湘"));
		// driverLocalList.add(new SpinnerItem("44", "粤"));
		// driverLocalList.add(new SpinnerItem("45", "桂"));
		// driverLocalList.add(new SpinnerItem("46", "琼"));
		// driverLocalList.add(new SpinnerItem("50", "渝"));
		// driverLocalList.add(new SpinnerItem("51", "川"));
		// driverLocalList.add(new SpinnerItem("52", "贵"));
		// driverLocalList.add(new SpinnerItem("53", "云"));
		// driverLocalList.add(new SpinnerItem("54", "藏"));
		// driverLocalList.add(new SpinnerItem("61", "陕"));
		// driverLocalList.add(new SpinnerItem("62", "甘"));
		// driverLocalList.add(new SpinnerItem("63", "青"));
		// driverLocalList.add(new SpinnerItem("64", "宁"));
		// driverLocalList.add(new SpinnerItem("65", "新"));
		// driverLocalList.add(new SpinnerItem("66", "农"));
		// driverLocalList.add(new SpinnerItem("71", "台"));
		// driverLocalList.add(new SpinnerItem("72", "中"));
		// driverLocalList.add(new SpinnerItem("73", "武"));
		// driverLocalList.add(new SpinnerItem("74", "WJ"));
		// driverLocalList.add(new SpinnerItem("75", "亥"));
		// driverLocalList.add(new SpinnerItem("76", "戌"));
		// driverLocalList.add(new SpinnerItem("77", "酉"));
		// driverLocalList.add(new SpinnerItem("78", "申"));
		// driverLocalList.add(new SpinnerItem("79", "未"));
		// driverLocalList.add(new SpinnerItem("80", "午"));
		// driverLocalList.add(new SpinnerItem("81", "巳"));
		// driverLocalList.add(new SpinnerItem("82", "辰"));
		// driverLocalList.add(new SpinnerItem("83", "卯"));
		// driverLocalList.add(new SpinnerItem("84", "寅"));
		// driverLocalList.add(new SpinnerItem("85", "丑"));
		// driverLocalList.add(new SpinnerItem("86", "子"));
		// driverLocalList.add(new SpinnerItem("87", "葵"));
		// driverLocalList.add(new SpinnerItem("88", "壬"));
		// driverLocalList.add(new SpinnerItem("89", "辛"));
		// driverLocalList.add(new SpinnerItem("90", "庚"));
		// driverLocalList.add(new SpinnerItem("91", "己"));
		// driverLocalList.add(new SpinnerItem("92", "戊"));
		// driverLocalList.add(new SpinnerItem("93", "丁"));
		// driverLocalList.add(new SpinnerItem("94", "丙"));
		// driverLocalList.add(new SpinnerItem("95", "乙"));
		// driverLocalList.add(new SpinnerItem("96", "甲"));
		// driverLocalList.add(new SpinnerItem("97", "河北"));
		// driverLocalList.add(new SpinnerItem("98", "山西"));
		// driverLocalList.add(new SpinnerItem("99", "北京"));
		// driverLocalList.add(new SpinnerItem("AA", "北"));
		// driverLocalList.add(new SpinnerItem("AB", "南"));
		// driverLocalList.add(new SpinnerItem("AC", "兰"));
		// driverLocalList.add(new SpinnerItem("AD", "沈"));
		// driverLocalList.add(new SpinnerItem("AE", "济"));
		// driverLocalList.add(new SpinnerItem("AF", "成"));
		// driverLocalList.add(new SpinnerItem("AG", "广"));
		// driverLocalList.add(new SpinnerItem("AH", "海"));
		// driverLocalList.add(new SpinnerItem("AI", "空"));
		// driverLocalList.add(new SpinnerItem("AJ", "军"));
		// driverLocalList.add(new SpinnerItem("AK", "京V"));
		// driverLocalList.add(new SpinnerItem("AL", "使"));
		//
		// // 损失部位
		// lossPartList = new ArrayList<SpinnerItem>();
		// lossPartList.add(new SpinnerItem("01", "全车"));
		// lossPartList.add(new SpinnerItem("02", "前部"));
		// lossPartList.add(new SpinnerItem("03", "左前部"));
		// lossPartList.add(new SpinnerItem("04", "右前部"));
		// lossPartList.add(new SpinnerItem("05", "左侧"));
		// lossPartList.add(new SpinnerItem("06", "右侧"));
		// lossPartList.add(new SpinnerItem("07", "左后部"));
		// lossPartList.add(new SpinnerItem("08", "右后部"));
		// lossPartList.add(new SpinnerItem("09", "底部"));
		// lossPartList.add(new SpinnerItem("10", "顶部"));
		// lossPartList.add(new SpinnerItem("11", "尾部"));
		// lossPartList.add(new SpinnerItem("12", "内部"));

		// 号牌种类
		cardTypeCodeList = new ArrayList<SpinnerItem>();
		cardTypeCodeList.add(new SpinnerItem("01", "大型汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("02", "小型汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("03", "使馆汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("04", "领馆汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("05", "境外汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("06", "外籍汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("07", "两、三轮摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("08", "轻便摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("09", "使馆摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("10", "领馆摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("11", "境外摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("12", "外籍摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("13", "农用运输车号牌"));
		cardTypeCodeList.add(new SpinnerItem("14", "拖拉机号牌"));
		cardTypeCodeList.add(new SpinnerItem("15", "挂车号牌"));
		cardTypeCodeList.add(new SpinnerItem("16", "教练汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("17", "教练摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("18", "试验汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("19", "试验摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("20", "临时入境汽车号牌"));
		cardTypeCodeList.add(new SpinnerItem("21", "临时入境摩托车号牌"));
		cardTypeCodeList.add(new SpinnerItem("22", "临时行驶车号牌"));
		cardTypeCodeList.add(new SpinnerItem("23", "公安警车号牌"));
		cardTypeCodeList.add(new SpinnerItem("24", "公安民用号牌"));
		cardTypeCodeList.add(new SpinnerItem("31", "武警号牌"));
		cardTypeCodeList.add(new SpinnerItem("32", "军队号牌"));
		cardTypeCodeList.add(new SpinnerItem("99", "其他号牌"));

		// 驾驶证准驾车型
		drivercarCodeList = new ArrayList<SpinnerItem>();
		drivercarCodeList.add(new SpinnerItem("C1", "小型汽车"));
		drivercarCodeList.add(new SpinnerItem("C2", "小型自动挡汽车"));
		drivercarCodeList.add(new SpinnerItem("C3", "低速载货汽车"));
		drivercarCodeList.add(new SpinnerItem("A3", "城市公交车"));
		drivercarCodeList.add(new SpinnerItem("B1", "中型客车"));
		drivercarCodeList.add(new SpinnerItem("A1", "大型客车"));
		drivercarCodeList.add(new SpinnerItem("C4", "三轮汽车"));
		drivercarCodeList.add(new SpinnerItem("D", "普通三轮摩托车"));
		drivercarCodeList.add(new SpinnerItem("E", "普通二轮摩托车"));
		drivercarCodeList.add(new SpinnerItem("B2", "大型货车"));
		drivercarCodeList.add(new SpinnerItem("A2", "牵引车"));

		// // 北京事故处理类型(accidentHandleType)
		// bjHandleTypeList = new ArrayList<SpinnerItem>();
		// bjHandleTypeList.add(new SpinnerItem("1", "当事人自行协商处理"));
		// bjHandleTypeList.add(new SpinnerItem("2", "交警简易程序处理"));
		// bjHandleTypeList.add(new SpinnerItem("3", "交警一般程序处理"));
		// bjHandleTypeList.add(new SpinnerItem("4", "单方事故处理"));
		// // 上海事故处理类型
		// shHandleTypeList = new ArrayList<SpinnerItem>();
		// shHandleTypeList.add(new SpinnerItem("1", "自行协商"));
		// shHandleTypeList.add(new SpinnerItem("2", "民警定责（中心内）"));
		// shHandleTypeList.add(new SpinnerItem("3", "民警定责（中心外）"));
		// shHandleTypeList.add(new SpinnerItem("4", "民警调解"));
		// shHandleTypeList.add(new SpinnerItem("5", "互碰自赔"));
		// shHandleTypeList.add(new SpinnerItem("9", "其他"));
		// 其它地区事故处理类型

		accidentHandleTypeList = new ArrayList<SpinnerItem>();
		// accidentHandleTypeList.add(new SpinnerItem("1", "保险公司处理"));
		// accidentHandleTypeList.add(new SpinnerItem("2", "自行协商"));
		// accidentHandleTypeList.add(new SpinnerItem("3", "交管正常处理"));
		// accidentHandleTypeList.add(new SpinnerItem("4", "交警简易程序处理"));
		// accidentHandleTypeList.add(new SpinnerItem("9", "其他"));
		accidentHandleTypeList.add(new SpinnerItem("01", "自行协商(上海)"));
		accidentHandleTypeList.add(new SpinnerItem("02", "民警定责(中心内)"));
		accidentHandleTypeList.add(new SpinnerItem("03", "民警定责(中心内)"));
		accidentHandleTypeList.add(new SpinnerItem("04", "民警调解"));
		accidentHandleTypeList.add(new SpinnerItem("09", "其他(上海)"));
		accidentHandleTypeList.add(new SpinnerItem("10", "保险公司处理"));
		accidentHandleTypeList.add(new SpinnerItem("11", "自行协商(非上海)"));
		accidentHandleTypeList.add(new SpinnerItem("12", "交管正常处理"));
		accidentHandleTypeList.add(new SpinnerItem("13", "交警简易程序处理"));
		accidentHandleTypeList.add(new SpinnerItem("14", "法院判决(非上海)"));
		accidentHandleTypeList.add(new SpinnerItem("15", "法院调解(非上海)"));
		accidentHandleTypeList.add(new SpinnerItem("16", "法院判决"));
		accidentHandleTypeList.add(new SpinnerItem("17", "法院调解"));
		accidentHandleTypeList.add(new SpinnerItem("19", "其他(非上海)"));

		// 交通事故责任书
		existDutyBillList = new ArrayList<SpinnerItem>();
		existDutyBillList.add(new SpinnerItem("1", "有"));
		existDutyBillList.add(new SpinnerItem("0", "无"));

		// 事故处理部门
		handleDeptList = new ArrayList<SpinnerItem>();
		handleDeptList.add(new SpinnerItem("01", "交警"));
		handleDeptList.add(new SpinnerItem("02", "车管所"));
		handleDeptList.add(new SpinnerItem("03", "其他部门"));

		// 三者车是否为机动车
		flagList = new ArrayList<SpinnerItem>();
		flagList.add(new SpinnerItem("0", "否"));
		flagList.add(new SpinnerItem("1", "是"));

		// 图片类型代码
		imageTypeList = new ArrayList<SpinnerItem>();
		imageTypeList.add(new SpinnerItem("0101", "《机动车辆保险索赔申请书》"));
		imageTypeList.add(new SpinnerItem("0201", "机动车辆保险单正本"));
		imageTypeList.add(new SpinnerItem("0301", "交通事故责任认定书"));
		imageTypeList.add(new SpinnerItem("0302", "调解书"));
		imageTypeList.add(new SpinnerItem("0303", "简易事故处理书"));
		imageTypeList.add(new SpinnerItem("0304", "其他认定事故责任的证明"));
		imageTypeList.add(new SpinnerItem("0401", "裁定书"));
		imageTypeList.add(new SpinnerItem("0402", "裁决书"));
		imageTypeList.add(new SpinnerItem("0403", "调解书"));
		imageTypeList.add(new SpinnerItem("0404", "判决书"));
		imageTypeList.add(new SpinnerItem("0405", "仲裁书"));
		imageTypeList.add(new SpinnerItem("0502", "车辆修理的正式发票(即汽车维修业专用发票)"));
		imageTypeList.add(new SpinnerItem("0503", "修理材料清单"));
		imageTypeList.add(new SpinnerItem("0504", "结算清单"));
		imageTypeList.add(new SpinnerItem("0601", "《机动车辆保险财产损失确认书》"));
		imageTypeList.add(new SpinnerItem("0602", "设备总体造价及损失程度证明"));
		imageTypeList.add(new SpinnerItem("0603", "设备恢复的工程预算"));
		imageTypeList.add(new SpinnerItem("0604", "财产损失清单"));
		imageTypeList.add(new SpinnerItem("0605", "购置、修复受损财产的有关费用单据"));
		imageTypeList.add(new SpinnerItem("0701", "县级以上医院诊断证明"));
		imageTypeList.add(new SpinnerItem("0702", "出院通知书"));
		imageTypeList.add(new SpinnerItem("0703", "需要护理人员证明"));
		imageTypeList.add(new SpinnerItem("0704", "医疗费报销凭证（须附处方及治疗、用药明细单据）"));
		imageTypeList.add(new SpinnerItem("0705",
				"伤、残、亡人员误工证明及收入情况证明（收入超过纳税金额的应提交纳税证明）"));
		imageTypeList.add(new SpinnerItem("0706",
				"护理人员误工证明及收入情况证明（收入超过纳税金额的应提交纳税证明）"));
		imageTypeList.add(new SpinnerItem("0707", "残者须提供法医伤残鉴定书"));
		imageTypeList.add(new SpinnerItem("0708", "亡者须提供死亡证明"));
		imageTypeList.add(new SpinnerItem("0709", "被扶养人证明材料"));
		imageTypeList.add(new SpinnerItem("0710", "户籍派出所出具的受害者家庭情况证明"));
		imageTypeList.add(new SpinnerItem("0711", "户口"));
		imageTypeList.add(new SpinnerItem("0712", "丧失劳动能力证明"));
		imageTypeList.add(new SpinnerItem("0713", "交通费报销凭证"));
		imageTypeList.add(new SpinnerItem("0714", "住宿费报销凭证"));
		imageTypeList.add(new SpinnerItem("0715", "参加事故处理人员工资证明"));
		imageTypeList.add(new SpinnerItem("0716",
				"向第三方支付赔偿费用的过款凭证（须由事故处理部门签章确认）"));
		imageTypeList.add(new SpinnerItem("0801", "机动车行驶证（原件）"));
		imageTypeList.add(new SpinnerItem("0802", "出险地县级以上公安刑侦部门出具的盗抢案件立案证明"));
		imageTypeList.add(new SpinnerItem("0803", "已登报声明的证明"));
		imageTypeList.add(new SpinnerItem("0804",
				"车辆购置附加费缴费凭证和收据(原件)或车辆购置税完税证明和代征车辆购置税缴税收据(原件)或免税证明(原件)"));
		imageTypeList.add(new SpinnerItem("0805", "机动车登记证书（原件）"));
		imageTypeList.add(new SpinnerItem("0806", "车辆停驶手续证明"));
		imageTypeList.add(new SpinnerItem("0807", "机动车来历凭证"));
		imageTypeList.add(new SpinnerItem("0808", "全套车钥匙"));
		imageTypeList.add(new SpinnerItem("0901", "出险地公安消防部门出具的车辆自燃火因的证明"));
		imageTypeList.add(new SpinnerItem("1001", "保险车辆《机动车行驶证》"));
		imageTypeList.add(new SpinnerItem("1002", "肇事驾驶人员的《机动车驾驶证》"));
		imageTypeList.add(new SpinnerItem("1101", "领取赔款授权书索赔申请书》"));
		imageTypeList.add(new SpinnerItem("1102", "被保险人身份证明"));
		imageTypeList.add(new SpinnerItem("1103", "领取赔款人员身份证明"));
		imageTypeList.add(new SpinnerItem("1201", "车辆损失照片"));
		imageTypeList.add(new SpinnerItem("9901", ""));
		imageTypeList.add(new SpinnerItem("9902", ""));
		imageTypeList.add(new SpinnerItem("9903", ""));
		imageTypeList.add(new SpinnerItem("9999", "其它"));
		
		//其他费用
				otherFeeNameList = new ArrayList<SpinnerItem>();
				otherFeeNameList.add(new SpinnerItem("0", "外加工费"));
				otherFeeNameList.add(new SpinnerItem("1", "专家工时费"));
		
	}

	public static String getValue(ArrayList<SpinnerItem> list, String id) {
		if (id != null && !"".equals(id)) {
			if (list == null) {
				initSpinnerItemList();
			}
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (id.equals(list.get(i).getID())) {
					return list.get(i).getValue();
				}
			}
			return id;
		}

		return "-";
	}

	public static String getValue(ArrayList<SpinnerItem> list, int id) {
		int i = 0;
		if (list == null) {
			initSpinnerItemList();
		}

		int size = list.size();
		for (; i < size; i++) {
			if (id == list.get(i).id) {
				return list.get(i).getValue();
			}
		}
		return "-";
	}

	/**
	 * @param list
	 * @param id
	 * @return
	 */
	public static int getIndex(ArrayList<SpinnerItem> list, String id) {
		int i = 0;
		if (id != null && !"".equals(id)) {
			if (list == null) {
				initSpinnerItemList();
			}
			int size = list.size();
			for (; i < size; i++) {
				if (id.equals(list.get(i).getID())) {
					return i;
				}
			}
		}
		return 0;
	}

	public static int getIndex(ArrayList<SpinnerItem> list, int id) {
		if (list == null) {
			initSpinnerItemList();
		}

		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (id == list.get(i).id) {
				return i;
			}
		}
		return id;
	}

	public static ArrayAdapter<SpinnerItem> getAdapter(Context context,
			List<SpinnerItem> list) {
		if (list == null) {
			initSpinnerItemList();
		}
		ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(
				context, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}
}
