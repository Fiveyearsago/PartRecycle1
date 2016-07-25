package com.jy.recycle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jy.mobile.dto.PjJgfaxxbBdDTO;

import android.content.Context;
import android.widget.ArrayAdapter;

public final class StaticCode {
	/** �����¹ʷ������ */
	public static HashMap<Integer, String> damageTypeMap;
	/** �¹ʴ����� */
	public static ArrayList<SpinnerItem> dealDepartmentCodeList;;
	/** �¹����λ��ִ��� */
	public static ArrayList<SpinnerItem> damageDutyList;
	/** �¹ʴ���ʽ���� */
	public static ArrayList<SpinnerItem> accidentHandleTypeList;
	/** ����ԭ����� */
	public static ArrayList<SpinnerItem> damageCauseList;
	/** �����϶������ʹ��� */
	public static ArrayList<SpinnerItem> subCertiTypeList;
	/** ����������� */
	public static ArrayList<SpinnerItem> cardTypeCodeList;
	/** �б��������� */
	public static ArrayList<SpinnerItem> insuranceCompanyList;
	/** �Ƿ������� "0", "��" "1", "��"*/
	public static ArrayList<SpinnerItem> flagList;
	/** �������Դ��� */
	public static ArrayList<SpinnerItem> vehiclePropertyList;
	/** ����������� */
	public static ArrayList<SpinnerItem> vehicleTypeList;
	/** �������� */
	public static ArrayList<SpinnerItem> areaList;
	/** �Ʋ����Դ��� */
	public static ArrayList<SpinnerItem> protectPropertyList;
	/** ��Ա���Դ��� */
	public static ArrayList<SpinnerItem> personPropertyList;
	/** �������ʹ��� */
	public static ArrayList<SpinnerItem> personPayTypeList;
	/** ͼƬ���ʹ��� */
	public static ArrayList<SpinnerItem> imageTypeList;
	/** ����������� */
	public static ArrayList<SpinnerItem> damageAreaList;
	/** ����ʽ���� */
	public static ArrayList<SpinnerItem> evalTypeList;
	/** �ֳ������� */
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
	/** ���������־ */
	public static ArrayList<SpinnerItem> selfFlagList;
	/** ��ʻ������� */
	public static ArrayList<SpinnerItem> runAreaList;
	/** ��ʧ�̶ȴ��� */
	public static ArrayList<SpinnerItem> lossLevelList;
	/** �¹ʴ�����Ϣ���� */
	public static ArrayList<SpinnerItem> accidentHandleInfoList;
	/** �����̶ȴ��� */
	public static ArrayList<SpinnerItem> urgencyLevelList;
	/** �¹����ʹ��� */
	public static ArrayList<SpinnerItem> accidentClassList;
	/** ��ײ�̶ȴ��� */
	public static ArrayList<SpinnerItem> pzcdList;
	/** ��ײ��λ���� */
	public static ArrayList<SpinnerItem> pzbwList;
	
	/** �Զ��嶨�� */
	public static ArrayList<SpinnerItem> autoVtnList;
	
	/**��������*/
	public static ArrayList<SpinnerItem> otherFeeNameList;

	static {
		initSpinnerItemList();
	}

	private static void initSpinnerItemList() {
		
		pzbwList = new ArrayList<SpinnerItem>();
		pzbwList.add(new SpinnerItem("1", "����ͷ��"));
		pzbwList.add(new SpinnerItem("1", "����ͷ��"));
		pzbwList.add(new SpinnerItem("2", "��ǰ����"));
		pzbwList.add(new SpinnerItem("3", "��ǰ����"));
		pzbwList.add(new SpinnerItem("4", "��೵��"));
		pzbwList.add(new SpinnerItem("5", "�Ҳ೵��"));
		pzbwList.add(new SpinnerItem("6", "�����"));
		pzbwList.add(new SpinnerItem("7", "�Һ���"));
		pzbwList.add(new SpinnerItem("8", "����β��"));

		pzcdList = new ArrayList<SpinnerItem>();
		pzcdList.add(new SpinnerItem("A", "�����ײ"));
		pzcdList.add(new SpinnerItem("A", "�����ײ"));
		pzcdList.add(new SpinnerItem("B", "�ж���ײ"));
		pzcdList.add(new SpinnerItem("C", "�ض���ײ"));
		
		evalTypeList = new ArrayList<SpinnerItem>();
		evalTypeList.add(new SpinnerItem("01", "�޸�����"));
		evalTypeList.add(new SpinnerItem("02", "�ƶ�ȫ��"));
		evalTypeList.add(new SpinnerItem("03", "һ����Э�鶨��"));
		
		
		autoVtnList=new ArrayList<SpinnerItem>();
		autoVtnList.add(new SpinnerItem("A", "��׼�ͳ�"));
		autoVtnList.add(new SpinnerItem("B", "��׼����"));
		autoVtnList.add(new SpinnerItem("C", "��׼�ų�"));
		autoVtnList.add(new SpinnerItem("D", "��������"));
		
		

		accidentHandleInfoList = new ArrayList<SpinnerItem>();
		accidentHandleInfoList.add(new SpinnerItem("01", "�ֳ��鿱"));
		accidentHandleInfoList.add(new SpinnerItem("02", "��һ�ֳ�����"));
		accidentHandleInfoList.add(new SpinnerItem("03", "���о���"));
		accidentHandleInfoList.add(new SpinnerItem("04", "����"));
		accidentHandleInfoList.add(new SpinnerItem("05", "�ͻ���������"));
		accidentHandleInfoList.add(new SpinnerItem("06", "�����ڱ�������"));
		accidentHandleInfoList.add(new SpinnerItem("07", "�ش󰸼�ͨ��"));
		accidentHandleInfoList.add(new SpinnerItem("08", "��������"));

		lossLevelList = new ArrayList<SpinnerItem>();
		lossLevelList.add(new SpinnerItem("01", "��΢����"));
		lossLevelList.add(new SpinnerItem("02", "�������"));
		lossLevelList.add(new SpinnerItem("03", "�ж�����"));
		lossLevelList.add(new SpinnerItem("04", "��������"));
		lossLevelList.add(new SpinnerItem("05", "�ǳ�����"));
		lossLevelList.add(new SpinnerItem("99", "����"));

		urgencyLevelList = new ArrayList<SpinnerItem>();
		urgencyLevelList.add(new SpinnerItem("01", "һ��"));
		urgencyLevelList.add(new SpinnerItem("02", "��Ҫ"));
		urgencyLevelList.add(new SpinnerItem("03", "����"));

		accidentClassList = new ArrayList<SpinnerItem>();
		accidentClassList.add(new SpinnerItem("01", "�����¹�"));
		accidentClassList.add(new SpinnerItem("02", "˫���¹�"));
		accidentClassList.add(new SpinnerItem("03", "�෽�¹�"));
		accidentClassList.add(new SpinnerItem("99", "����"));

		runAreaList = new ArrayList<SpinnerItem>();
		runAreaList.add(new SpinnerItem("01", "���У�������"));
		runAreaList.add(new SpinnerItem("02", "ʡ����ʻ"));
		runAreaList.add(new SpinnerItem("03", "��ʡ���С���"));
		runAreaList.add(new SpinnerItem("04", "��������"));
		runAreaList.add(new SpinnerItem("05", "���ڣ����۰ģ�"));
		runAreaList.add(new SpinnerItem("06", "���ڣ������۰�̨��"));
		runAreaList.add(new SpinnerItem("07", "�й̶���ʻ·��"));
		runAreaList.add(new SpinnerItem("08", "�޹̶���ʻ·��"));
		runAreaList.add(new SpinnerItem("99", "��������"));

		dealDepartmentCodeList = new ArrayList<SpinnerItem>();
		dealDepartmentCodeList.add(new SpinnerItem("01", "��������"));
		dealDepartmentCodeList.add(new SpinnerItem("02", "���չ�˾"));
		dealDepartmentCodeList.add(new SpinnerItem("03", "��������"));

		damageAreaList = new ArrayList<SpinnerItem>();
		damageAreaList.add(new SpinnerItem("01", "����"));
		damageAreaList.add(new SpinnerItem("02", "����"));
		damageAreaList.add(new SpinnerItem("03", "ʡ��"));
		damageAreaList.add(new SpinnerItem("04", "ʡ��"));
		damageAreaList.add(new SpinnerItem("05", "�۰�"));
		damageAreaList.add(new SpinnerItem("10", "�й�����"));
		damageAreaList.add(new SpinnerItem("99", "����"));

		personPropertyList = new ArrayList<SpinnerItem>();
		personPropertyList.add(new SpinnerItem("1", "������Ա"));
		personPropertyList.add(new SpinnerItem("2", "������Ա"));

		personPayTypeList = new ArrayList<SpinnerItem>();
		personPayTypeList.add(new SpinnerItem("1", "�˲�"));
		personPayTypeList.add(new SpinnerItem("2", "����"));

		damageDutyList = new ArrayList<SpinnerItem>();
		damageDutyList.add(new SpinnerItem("01", "ȫ��"));
		damageDutyList.add(new SpinnerItem("02", "����"));
		damageDutyList.add(new SpinnerItem("03", "ͬ��"));
		damageDutyList.add(new SpinnerItem("04", "����"));
		damageDutyList.add(new SpinnerItem("05", "����"));
		damageDutyList.add(new SpinnerItem("06", "δ����"));

		firstSeneFlagList = new ArrayList<SpinnerItem>();
		firstSeneFlagList.add(new SpinnerItem("1", "��һ�ֳ�"));
		firstSeneFlagList.add(new SpinnerItem("0", "�ǵ�һ�ֳ�"));

		roadFlagList = new ArrayList<SpinnerItem>();
		roadFlagList.add(new SpinnerItem("1", "����·��ͨ�¹�"));
		roadFlagList.add(new SpinnerItem("0", "�ǵ�·��ͨ�¹�"));

		selfFlagList = new ArrayList<SpinnerItem>();
		selfFlagList.add(new SpinnerItem("0", "�ǻ�������"));
		selfFlagList.add(new SpinnerItem("1", "��������"));

		lossTypeList = new ArrayList<SpinnerItem>();
		lossTypeList.add(new SpinnerItem("0", "��"));
		lossTypeList.add(new SpinnerItem("1", "����"));
		lossTypeList.add(new SpinnerItem("2", "����"));
		lossTypeList.add(new SpinnerItem("3", "����"));
		lossTypeList.add(new SpinnerItem("4", "����"));

		credTypeList = new ArrayList<SpinnerItem>();
		credTypeList.add(new SpinnerItem("01", "���֤"));
		credTypeList.add(new SpinnerItem("02", "���ڲ�"));
		credTypeList.add(new SpinnerItem("03", "��ʻ֤"));
		credTypeList.add(new SpinnerItem("04", "����֤"));
		credTypeList.add(new SpinnerItem("05", "ʿ��֤"));
		credTypeList.add(new SpinnerItem("06", "����������֤"));
		credTypeList.add(new SpinnerItem("07", "�й�����"));
		credTypeList.add(new SpinnerItem("41", "�۰�ͨ��֤"));
		credTypeList.add(new SpinnerItem("42", "̨��ͨ��֤"));
		credTypeList.add(new SpinnerItem("51", "�������"));
		credTypeList.add(new SpinnerItem("52", "����֤"));
		credTypeList.add(new SpinnerItem("53", "����֤��"));
		credTypeList.add(new SpinnerItem("71", "��֯��������֤"));
		credTypeList.add(new SpinnerItem("99", "����֤��"));

		// if ("YSPZ".equals(damageType)) {
		// damageType = "��ײ";
		// } else if ("CLPS".equals(damageType)) {
		// damageType = "������ײ(��·��)";
		// } else if ("CLPW".equals(damageType)) {
		// damageType = "������ײ(��·��)";
		// } else if ("CLPZ".equals(damageType)) {
		// damageType = "����������ײ";
		// } else if ("CL3Z".equals(damageType)) {
		// damageType = "����������";
		// } else if ("CLBL".equals(damageType)) {
		// damageType = "����������";
		// } else if ("CLCZ".equals(damageType)) {
		// damageType = "���ػ���ײ��";
		// } else if ("CLDL".equals(damageType)) {
		// damageType = "���ػ������";
		// } else if ("CLQF".equals(damageType)) {
		// damageType = "�㸲";
		// } else if ("CLDQ".equals(damageType)) {
		// damageType = "������";
		// } else if ("CLQT".equals(damageType)) {
		// damageType = "����";
		// }
		damageCauseList = new ArrayList<SpinnerItem>();
		damageCauseList.add(new SpinnerItem("01", "׷β"));
		damageCauseList.add(new SpinnerItem("02", "����"));
		damageCauseList.add(new SpinnerItem("03", "����"));
		damageCauseList.add(new SpinnerItem("04", "�ﳵ"));
		damageCauseList.add(new SpinnerItem("08", "�����¹�"));
		damageCauseList.add(new SpinnerItem("05", "���س���"));
		damageCauseList.add(new SpinnerItem("06", "Υ����ͨ�ź�"));
		damageCauseList.add(new SpinnerItem("07", "δ���涨����"));
		damageCauseList.add(new SpinnerItem("98", "һ��ȫ��һ���������������"));
		// damageCauseList.add(new SpinnerItem("A10001", "����"));
		// damageCauseList.add(new SpinnerItem("A10002", "��ը"));
		// damageCauseList.add(new SpinnerItem("A10003", "����"));
		// damageCauseList.add(new SpinnerItem("A10004", "����"));
		// damageCauseList.add(new SpinnerItem("A10005", "�׻�"));
		// damageCauseList.add(new SpinnerItem("A10006", "̨��"));
		// damageCauseList.add(new SpinnerItem("A10007", "��������/����"));
		// damageCauseList.add(new SpinnerItem("A10008", "ѩ��"));
		// damageCauseList.add(new SpinnerItem("A10009", "����"));
		// damageCauseList.add(new SpinnerItem("A10010", "����"));
		// damageCauseList.add(new SpinnerItem("A10011", "ͻ���Ի���"));
		// damageCauseList.add(new SpinnerItem("A10012", "�±�"));
		// damageCauseList.add(new SpinnerItem("A10013", "��ʯ��"));
		// damageCauseList.add(new SpinnerItem("A10014", "��ˮ"));
		// damageCauseList.add(new SpinnerItem("A10015", "��Х"));
		// damageCauseList.add(new SpinnerItem("A10016", "����"));
		// damageCauseList.add(new SpinnerItem("A10017", "�����"));
		// damageCauseList.add(new SpinnerItem("A10018", "��Ա�ҳ�"));
		// damageCauseList.add(new SpinnerItem("A10020", "���˼�����Աȱ�����顢�������ʧ"));
		// damageCauseList.add(new SpinnerItem("A10021", "ԭ����ȱ�ݻ��ղ���"));
		// damageCauseList.add(new SpinnerItem("A10022", "������������"));
		// damageCauseList.add(new SpinnerItem("A10023", "��������"));
		// damageCauseList.add(new SpinnerItem("A10024", "ˮ�ܱ���"));
		// damageCauseList.add(new SpinnerItem("A10025", "�������׹��"));
		// damageCauseList.add(new SpinnerItem("A10026", "��׹��"));
		// damageCauseList.add(new SpinnerItem("A10027", "�����¹�����"));
		// damageCauseList.add(new SpinnerItem("A10028", "����ϵ�"));

		damageTypeMap = new HashMap<Integer, String>();
		damageTypeMap.put(100, "��ͨ�¹���");
		damageTypeMap.put(111, "������ײ");
		damageTypeMap.put(112, "������ײ");
		damageTypeMap.put(113, "β����ײ");
		damageTypeMap.put(121, "����β�");
		damageTypeMap.put(122, "ͬ��β�");
		damageTypeMap.put(130, "��ѹ");
		damageTypeMap.put(140, "���� ");
		damageTypeMap.put(150, "׹��");
		damageTypeMap.put(160, "ʧ��");
		damageTypeMap.put(170, "ײ�̶���");
		damageTypeMap.put(180, "ײ·��");
		damageTypeMap.put(190, "ײ��ֹ����");
		damageTypeMap.put(199, "������ͨ�¹�");
		damageTypeMap.put(200, "�����¹���");
		damageTypeMap.put(201, "���֣�����ȼ��");
		damageTypeMap.put(202, "��ը");
		damageTypeMap.put(203, "��ȼ");
		damageTypeMap.put(204, "����");
		damageTypeMap.put(205, "���˱��ճ����Ķɴ�������Ȼ�ֺ�");
		damageTypeMap.put(221, "������������");
		damageTypeMap.put(222, "������");
		damageTypeMap.put(223, "��̥������");
		damageTypeMap.put(224, "������Ʒ���������");
		damageTypeMap.put(225, "��ˮ��ʧ����������ˮ��ʧ");
		damageTypeMap.put(226, "���ع̶��������豸��");
		damageTypeMap.put(231, "���ػ�����ײ����");
		damageTypeMap.put(232, "���ػ����������");
		damageTypeMap.put(241, "�ⲿ���嵹��");
		damageTypeMap.put(242, "�ⲿ����׹��");
		damageTypeMap.put(260, "��������");
		damageTypeMap.put(261, "�����ʻ");
		damageTypeMap.put(262, "���͡���ˮ����");
		damageTypeMap.put(263, "������̥");
		damageTypeMap.put(264, "��ƿ���");
		damageTypeMap.put(265, "��ק����");
		damageTypeMap.put(270, "�¹ʷ���");
		damageTypeMap.put(299, "�����ǽ�ͨ�¹��ౣ���¹�");
		damageTypeMap.put(500, "��Ȼ�ֺ���");
		damageTypeMap.put(501, "�׻�");
		damageTypeMap.put(502, "����");
		damageTypeMap.put(503, "�����");
		damageTypeMap.put(504, "����");
		damageTypeMap.put(505, "��ˮ");
		damageTypeMap.put(506, "��Х");
		damageTypeMap.put(507, "����");
		damageTypeMap.put(508, "����");
		damageTypeMap.put(509, "�±�");
		damageTypeMap.put(510, "ѩ��");
		damageTypeMap.put(511, "����");
		damageTypeMap.put(512, "��ʯ��");
		damageTypeMap.put(513, "����");
		damageTypeMap.put(599, "������Ȼ�ֺ�");
		damageTypeMap.put(600, "����ֺ���");
		damageTypeMap.put(601, "���ҡ�Ⱥ���Ա����¼�");
		damageTypeMap.put(602, "ս��");
		damageTypeMap.put(603, "�ֲ��");
		damageTypeMap.put(699, "��������ֺ���ԭ��");
		damageTypeMap.put(900, "���������¹�");
		damageTypeMap.put(901, "��������");
		damageTypeMap.put(902, "������");

		// ��Ա����
		protectPropertyList = new ArrayList<SpinnerItem>();
		protectPropertyList.add(new SpinnerItem("1", "�����Ʋ�"));
		protectPropertyList.add(new SpinnerItem("2", "����Ʋ�"));

		driverSexList = new ArrayList<SpinnerItem>();
		driverSexList.add(new SpinnerItem("1", "��"));
		driverSexList.add(new SpinnerItem("2", "Ů"));

		vehiclePropertyList = new ArrayList<SpinnerItem>();
		vehiclePropertyList.add(new SpinnerItem("1", "����"));
		vehiclePropertyList.add(new SpinnerItem("2", "���߳�"));

		vehicleTypeList = new ArrayList<SpinnerItem>();
		vehicleTypeList.add(new SpinnerItem(11, "�������¿ͳ�"));
		vehicleTypeList.add(new SpinnerItem(12, "������ʮ���ͳ�"));
		vehicleTypeList.add(new SpinnerItem(13, "ʮ������ʮ�����¿ͳ�"));
		vehicleTypeList.add(new SpinnerItem(14, "��ʮ������ʮ�����ͳ�"));
		vehicleTypeList.add(new SpinnerItem(15, "��ʮ�������Ͽͳ�"));
		vehicleTypeList.add(new SpinnerItem(21, "�������»���"));
		vehicleTypeList.add(new SpinnerItem(22, "��������ֻ���"));
		vehicleTypeList.add(new SpinnerItem(23, "�����ʮ�ֻ���"));
		vehicleTypeList.add(new SpinnerItem(24, "ʮ�����ϻ���"));
		vehicleTypeList.add(new SpinnerItem(25, "�������¹ҳ�"));
		vehicleTypeList.add(new SpinnerItem(26, "��������ֹҳ�"));
		vehicleTypeList.add(new SpinnerItem(27, "�����ʮ�ֹҳ�"));
		vehicleTypeList.add(new SpinnerItem(28, "ʮ�����Ϲҳ�"));
		vehicleTypeList.add(new SpinnerItem(30, "�͹޳������޳���Һ�޳�"));
		vehicleTypeList.add(new SpinnerItem(31, "���ֳ�һ�ҳ�"));
		vehicleTypeList.add(new SpinnerItem(40, "���ֳ���"));
		vehicleTypeList.add(new SpinnerItem(41, "���ֳ����ҳ�"));
		vehicleTypeList.add(new SpinnerItem(50, "���ֳ���"));
		vehicleTypeList.add(new SpinnerItem(51, "���ֳ����ҳ�"));
		vehicleTypeList.add(new SpinnerItem(60, "���ֳ���"));
		vehicleTypeList.add(new SpinnerItem(71, "Ħ�г���50cc�����£�"));
		vehicleTypeList.add(new SpinnerItem(72, "Ħ�г���50cc-250cc��"));
		vehicleTypeList.add(new SpinnerItem(73, "Ħ�г���250cc���ϼ������֣�"));
		vehicleTypeList.add(new SpinnerItem(81, "��������14.7kw�����£�"));
		vehicleTypeList.add(new SpinnerItem(82, "������14.7kw����"));
		vehicleTypeList.add(new SpinnerItem(91, "������������14.7KW������"));
		vehicleTypeList.add(new SpinnerItem(92, "������������14.7KW����"));
		vehicleTypeList.add(new SpinnerItem(93, "�����ػ�����"));
		vehicleTypeList.add(new SpinnerItem(94, "��������"));

		diaTypeList = new ArrayList<SpinnerItem>();
		diaTypeList.add(new SpinnerItem("1", "����PC"));
		diaTypeList.add(new SpinnerItem("0", "��������"));

		subCertiTypeList = new ArrayList<SpinnerItem>();
		subCertiTypeList.add(new SpinnerItem("1", "��ͨ�¹������϶���"));
		subCertiTypeList.add(new SpinnerItem("2", "�����¹ʴ���Э��"));
		subCertiTypeList.add(new SpinnerItem("3", "�����϶��¹����ε�֤��"));

		insuranceCompanyList = new ArrayList<SpinnerItem>();
		insuranceCompanyList.add(new SpinnerItem("TPIC", "̫ƽ�������޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("CLPC", "�й����ٲƲ����չ�˾"));
		insuranceCompanyList.add(new SpinnerItem("HNIC", "��ũ�Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("TAIC", "�찲���չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("AHIC", "����ũҵ���չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("AICS", "���ϲƲ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("DBIC", "����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("AAIC", "����ũҵ���չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("CPIC", "�й�̫ƽ��Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("CAPL", "�������α��չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("HTIC", "��̩�Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("MACN", "�񰲱���(�й�)���޹�˾  "));
		insuranceCompanyList.add(new SpinnerItem("ABIC", "����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("PICC", "�й�����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("YAIC", "�����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("CICP", "�л����ϲƲ����չ�˾    "));
		insuranceCompanyList.add(new SpinnerItem("ZKIC", "�Ͻ�Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("ZMIC", "��ú�Ʋ����չɷ����޹�˾"));
		insuranceCompanyList
				.add(new SpinnerItem("AAAA", "�ޱ��չ�˾              "));
		insuranceCompanyList.add(new SpinnerItem("YGBX", "����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("ACIC", "���ϲƲ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("PAIC", "�й�ƽ���Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("BOCI", "�����������޹�˾        "));
		insuranceCompanyList.add(new SpinnerItem("DHIC", "���ͲƲ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("BPIC", "�����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("CCIC", "�й���زƲ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("SMIC", "����ũҵ�໥���չ�˾    "));
		insuranceCompanyList.add(new SpinnerItem("HAIC", "�����Ʋ����չɷ����޹�˾"));
		insuranceCompanyList.add(new SpinnerItem("DICC", "���ڱ��չɷ����޹�˾    "));
		insuranceCompanyList.add(new SpinnerItem("TPBX", "��ƽ�������չɷ����޹�˾"));

		areaList = new ArrayList<SpinnerItem>();
		areaList.add(new SpinnerItem("310000", "�Ϻ�"));
		areaList.add(new SpinnerItem("120000", "���"));
		areaList.add(new SpinnerItem("500000", "����"));
		areaList.add(new SpinnerItem("110000", "����"));
		areaList.add(new SpinnerItem("230000", "������"));
		areaList.add(new SpinnerItem("220000", "����"));
		areaList.add(new SpinnerItem("210000", "����"));
		areaList.add(new SpinnerItem("130000", "�ӱ�"));
		areaList.add(new SpinnerItem("140000", "ɽ��"));
		areaList.add(new SpinnerItem("370000", "ɽ��"));
		areaList.add(new SpinnerItem("340000", "����"));
		areaList.add(new SpinnerItem("320000", "����"));
		areaList.add(new SpinnerItem("330000", "�㽭"));
		areaList.add(new SpinnerItem("350000", "����"));
		areaList.add(new SpinnerItem("360000", "����"));
		areaList.add(new SpinnerItem("440000", "�㶫"));
		areaList.add(new SpinnerItem("460000", "����"));
		areaList.add(new SpinnerItem("450000", "����"));
		areaList.add(new SpinnerItem("430000", "����"));
		areaList.add(new SpinnerItem("420000", "����"));
		areaList.add(new SpinnerItem("410000", "����"));
		areaList.add(new SpinnerItem("530000", "����"));
		areaList.add(new SpinnerItem("520000", "����"));
		areaList.add(new SpinnerItem("510000", "�Ĵ�"));
		areaList.add(new SpinnerItem("610000", "����"));
		areaList.add(new SpinnerItem("620000", "����"));
		areaList.add(new SpinnerItem("640000", "����"));
		areaList.add(new SpinnerItem("650000", "�½�"));
		areaList.add(new SpinnerItem("150000", "���ɹ�"));
		areaList.add(new SpinnerItem("210200", "����"));
		areaList.add(new SpinnerItem("370200", "�ൺ"));
		areaList.add(new SpinnerItem("330200", "����"));
		areaList.add(new SpinnerItem("440300", "����"));
		areaList.add(new SpinnerItem("630000", "�ຣ"));
		areaList.add(new SpinnerItem("540000", "����"));
		areaList.add(new SpinnerItem("350200", "����"));

		/**
		 * claimBankList = new ArrayList<SpinnerItem>(); claimBankList.add(new
		 * SpinnerItem("01", "�й���������")); claimBankList.add(new SpinnerItem("02",
		 * "�й�ũҵ����")); claimBankList.add(new SpinnerItem("03", "�й�����"));
		 * claimBankList.add(new SpinnerItem("04", "�й���������"));
		 * claimBankList.add(new SpinnerItem("05", "��ͨ����"));
		 * claimBankList.add(new SpinnerItem("06", "��ҵ����"));
		 * claimBankList.add(new SpinnerItem("07", "��������"));
		 * claimBankList.add(new SpinnerItem("08", "�㶫��չ����"));
		 * claimBankList.add(new SpinnerItem("09", "�й���������"));
		 * claimBankList.add(new SpinnerItem("10", "��������"));
		 * claimBankList.add(new SpinnerItem("11", "��������"));
		 * claimBankList.add(new SpinnerItem("12", "����ʡũ�����ú�������"));
		 * claimBankList.add(new SpinnerItem("13", "�й��������"));
		 * claimBankList.add(new SpinnerItem("14", "��������"));
		 * claimBankList.add(new SpinnerItem("15", "�Ϻ��ֶ���չ����"));
		 * claimBankList.add(new SpinnerItem("16", "ƽ������"));
		 * claimBankList.add(new SpinnerItem("17", "�Ϻ�ũ��"));
		 * claimBankList.add(new SpinnerItem("18", "��������"));
		 * claimBankList.add(new SpinnerItem("19", "�麣��ũ�����ú�����"));
		 * claimBankList.add(new SpinnerItem("20", "��������"));
		 * claimBankList.add(new SpinnerItem("21", "��������"));
		 * claimBankList.add(new SpinnerItem("22", "��������"));
		 * claimBankList.add(new SpinnerItem("23", "Ң������"));
		 * claimBankList.add(new SpinnerItem("24", "��������"));
		 * claimBankList.add(new SpinnerItem("25", "��������"));
		 * claimBankList.add(new SpinnerItem("26", "˳��ũ�����ú�����"));
		 **/

		// driverLocalList = new ArrayList<SpinnerItem>();
		// driverLocalList.add(new SpinnerItem("11", "��"));
		// driverLocalList.add(new SpinnerItem("12", "��"));
		// driverLocalList.add(new SpinnerItem("13", "��"));
		// driverLocalList.add(new SpinnerItem("14", "��"));
		// driverLocalList.add(new SpinnerItem("15", "��"));
		// driverLocalList.add(new SpinnerItem("21", "��"));
		// driverLocalList.add(new SpinnerItem("22", "��"));
		// driverLocalList.add(new SpinnerItem("23", "��"));
		// driverLocalList.add(new SpinnerItem("31", "��"));
		// driverLocalList.add(new SpinnerItem("32", "��"));
		// driverLocalList.add(new SpinnerItem("33", "��"));
		// driverLocalList.add(new SpinnerItem("34", "��"));
		// driverLocalList.add(new SpinnerItem("35", "��"));
		// driverLocalList.add(new SpinnerItem("36", "��"));
		// driverLocalList.add(new SpinnerItem("37", "³"));
		// driverLocalList.add(new SpinnerItem("41", "ԥ"));
		// driverLocalList.add(new SpinnerItem("42", "��"));
		// driverLocalList.add(new SpinnerItem("43", "��"));
		// driverLocalList.add(new SpinnerItem("44", "��"));
		// driverLocalList.add(new SpinnerItem("45", "��"));
		// driverLocalList.add(new SpinnerItem("46", "��"));
		// driverLocalList.add(new SpinnerItem("50", "��"));
		// driverLocalList.add(new SpinnerItem("51", "��"));
		// driverLocalList.add(new SpinnerItem("52", "��"));
		// driverLocalList.add(new SpinnerItem("53", "��"));
		// driverLocalList.add(new SpinnerItem("54", "��"));
		// driverLocalList.add(new SpinnerItem("61", "��"));
		// driverLocalList.add(new SpinnerItem("62", "��"));
		// driverLocalList.add(new SpinnerItem("63", "��"));
		// driverLocalList.add(new SpinnerItem("64", "��"));
		// driverLocalList.add(new SpinnerItem("65", "��"));
		// driverLocalList.add(new SpinnerItem("66", "ũ"));
		// driverLocalList.add(new SpinnerItem("71", "̨"));
		// driverLocalList.add(new SpinnerItem("72", "��"));
		// driverLocalList.add(new SpinnerItem("73", "��"));
		// driverLocalList.add(new SpinnerItem("74", "WJ"));
		// driverLocalList.add(new SpinnerItem("75", "��"));
		// driverLocalList.add(new SpinnerItem("76", "��"));
		// driverLocalList.add(new SpinnerItem("77", "��"));
		// driverLocalList.add(new SpinnerItem("78", "��"));
		// driverLocalList.add(new SpinnerItem("79", "δ"));
		// driverLocalList.add(new SpinnerItem("80", "��"));
		// driverLocalList.add(new SpinnerItem("81", "��"));
		// driverLocalList.add(new SpinnerItem("82", "��"));
		// driverLocalList.add(new SpinnerItem("83", "î"));
		// driverLocalList.add(new SpinnerItem("84", "��"));
		// driverLocalList.add(new SpinnerItem("85", "��"));
		// driverLocalList.add(new SpinnerItem("86", "��"));
		// driverLocalList.add(new SpinnerItem("87", "��"));
		// driverLocalList.add(new SpinnerItem("88", "��"));
		// driverLocalList.add(new SpinnerItem("89", "��"));
		// driverLocalList.add(new SpinnerItem("90", "��"));
		// driverLocalList.add(new SpinnerItem("91", "��"));
		// driverLocalList.add(new SpinnerItem("92", "��"));
		// driverLocalList.add(new SpinnerItem("93", "��"));
		// driverLocalList.add(new SpinnerItem("94", "��"));
		// driverLocalList.add(new SpinnerItem("95", "��"));
		// driverLocalList.add(new SpinnerItem("96", "��"));
		// driverLocalList.add(new SpinnerItem("97", "�ӱ�"));
		// driverLocalList.add(new SpinnerItem("98", "ɽ��"));
		// driverLocalList.add(new SpinnerItem("99", "����"));
		// driverLocalList.add(new SpinnerItem("AA", "��"));
		// driverLocalList.add(new SpinnerItem("AB", "��"));
		// driverLocalList.add(new SpinnerItem("AC", "��"));
		// driverLocalList.add(new SpinnerItem("AD", "��"));
		// driverLocalList.add(new SpinnerItem("AE", "��"));
		// driverLocalList.add(new SpinnerItem("AF", "��"));
		// driverLocalList.add(new SpinnerItem("AG", "��"));
		// driverLocalList.add(new SpinnerItem("AH", "��"));
		// driverLocalList.add(new SpinnerItem("AI", "��"));
		// driverLocalList.add(new SpinnerItem("AJ", "��"));
		// driverLocalList.add(new SpinnerItem("AK", "��V"));
		// driverLocalList.add(new SpinnerItem("AL", "ʹ"));
		//
		// // ��ʧ��λ
		// lossPartList = new ArrayList<SpinnerItem>();
		// lossPartList.add(new SpinnerItem("01", "ȫ��"));
		// lossPartList.add(new SpinnerItem("02", "ǰ��"));
		// lossPartList.add(new SpinnerItem("03", "��ǰ��"));
		// lossPartList.add(new SpinnerItem("04", "��ǰ��"));
		// lossPartList.add(new SpinnerItem("05", "���"));
		// lossPartList.add(new SpinnerItem("06", "�Ҳ�"));
		// lossPartList.add(new SpinnerItem("07", "���"));
		// lossPartList.add(new SpinnerItem("08", "�Һ�"));
		// lossPartList.add(new SpinnerItem("09", "�ײ�"));
		// lossPartList.add(new SpinnerItem("10", "����"));
		// lossPartList.add(new SpinnerItem("11", "β��"));
		// lossPartList.add(new SpinnerItem("12", "�ڲ�"));

		// ��������
		cardTypeCodeList = new ArrayList<SpinnerItem>();
		cardTypeCodeList.add(new SpinnerItem("01", "������������"));
		cardTypeCodeList.add(new SpinnerItem("02", "С����������"));
		cardTypeCodeList.add(new SpinnerItem("03", "ʹ����������"));
		cardTypeCodeList.add(new SpinnerItem("04", "�����������"));
		cardTypeCodeList.add(new SpinnerItem("05", "������������"));
		cardTypeCodeList.add(new SpinnerItem("06", "�⼮��������"));
		cardTypeCodeList.add(new SpinnerItem("07", "��������Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("08", "���Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("09", "ʹ��Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("10", "���Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("11", "����Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("12", "�⼮Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("13", "ũ�����䳵����"));
		cardTypeCodeList.add(new SpinnerItem("14", "����������"));
		cardTypeCodeList.add(new SpinnerItem("15", "�ҳ�����"));
		cardTypeCodeList.add(new SpinnerItem("16", "������������"));
		cardTypeCodeList.add(new SpinnerItem("17", "����Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("18", "������������"));
		cardTypeCodeList.add(new SpinnerItem("19", "����Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("20", "��ʱ�뾳��������"));
		cardTypeCodeList.add(new SpinnerItem("21", "��ʱ�뾳Ħ�г�����"));
		cardTypeCodeList.add(new SpinnerItem("22", "��ʱ��ʻ������"));
		cardTypeCodeList.add(new SpinnerItem("23", "������������"));
		cardTypeCodeList.add(new SpinnerItem("24", "�������ú���"));
		cardTypeCodeList.add(new SpinnerItem("31", "�侯����"));
		cardTypeCodeList.add(new SpinnerItem("32", "���Ӻ���"));
		cardTypeCodeList.add(new SpinnerItem("99", "��������"));

		// ��ʻ֤׼�ݳ���
		drivercarCodeList = new ArrayList<SpinnerItem>();
		drivercarCodeList.add(new SpinnerItem("C1", "С������"));
		drivercarCodeList.add(new SpinnerItem("C2", "С���Զ�������"));
		drivercarCodeList.add(new SpinnerItem("C3", "�����ػ�����"));
		drivercarCodeList.add(new SpinnerItem("A3", "���й�����"));
		drivercarCodeList.add(new SpinnerItem("B1", "���Ϳͳ�"));
		drivercarCodeList.add(new SpinnerItem("A1", "���Ϳͳ�"));
		drivercarCodeList.add(new SpinnerItem("C4", "��������"));
		drivercarCodeList.add(new SpinnerItem("D", "��ͨ����Ħ�г�"));
		drivercarCodeList.add(new SpinnerItem("E", "��ͨ����Ħ�г�"));
		drivercarCodeList.add(new SpinnerItem("B2", "���ͻ���"));
		drivercarCodeList.add(new SpinnerItem("A2", "ǣ����"));

		// // �����¹ʴ�������(accidentHandleType)
		// bjHandleTypeList = new ArrayList<SpinnerItem>();
		// bjHandleTypeList.add(new SpinnerItem("1", "����������Э�̴���"));
		// bjHandleTypeList.add(new SpinnerItem("2", "�������׳�����"));
		// bjHandleTypeList.add(new SpinnerItem("3", "����һ�������"));
		// bjHandleTypeList.add(new SpinnerItem("4", "�����¹ʴ���"));
		// // �Ϻ��¹ʴ�������
		// shHandleTypeList = new ArrayList<SpinnerItem>();
		// shHandleTypeList.add(new SpinnerItem("1", "����Э��"));
		// shHandleTypeList.add(new SpinnerItem("2", "�񾯶��������ڣ�"));
		// shHandleTypeList.add(new SpinnerItem("3", "�񾯶��������⣩"));
		// shHandleTypeList.add(new SpinnerItem("4", "�񾯵���"));
		// shHandleTypeList.add(new SpinnerItem("5", "��������"));
		// shHandleTypeList.add(new SpinnerItem("9", "����"));
		// ���������¹ʴ�������

		accidentHandleTypeList = new ArrayList<SpinnerItem>();
		// accidentHandleTypeList.add(new SpinnerItem("1", "���չ�˾����"));
		// accidentHandleTypeList.add(new SpinnerItem("2", "����Э��"));
		// accidentHandleTypeList.add(new SpinnerItem("3", "������������"));
		// accidentHandleTypeList.add(new SpinnerItem("4", "�������׳�����"));
		// accidentHandleTypeList.add(new SpinnerItem("9", "����"));
		accidentHandleTypeList.add(new SpinnerItem("01", "����Э��(�Ϻ�)"));
		accidentHandleTypeList.add(new SpinnerItem("02", "�񾯶���(������)"));
		accidentHandleTypeList.add(new SpinnerItem("03", "�񾯶���(������)"));
		accidentHandleTypeList.add(new SpinnerItem("04", "�񾯵���"));
		accidentHandleTypeList.add(new SpinnerItem("09", "����(�Ϻ�)"));
		accidentHandleTypeList.add(new SpinnerItem("10", "���չ�˾����"));
		accidentHandleTypeList.add(new SpinnerItem("11", "����Э��(���Ϻ�)"));
		accidentHandleTypeList.add(new SpinnerItem("12", "������������"));
		accidentHandleTypeList.add(new SpinnerItem("13", "�������׳�����"));
		accidentHandleTypeList.add(new SpinnerItem("14", "��Ժ�о�(���Ϻ�)"));
		accidentHandleTypeList.add(new SpinnerItem("15", "��Ժ����(���Ϻ�)"));
		accidentHandleTypeList.add(new SpinnerItem("16", "��Ժ�о�"));
		accidentHandleTypeList.add(new SpinnerItem("17", "��Ժ����"));
		accidentHandleTypeList.add(new SpinnerItem("19", "����(���Ϻ�)"));

		// ��ͨ�¹�������
		existDutyBillList = new ArrayList<SpinnerItem>();
		existDutyBillList.add(new SpinnerItem("1", "��"));
		existDutyBillList.add(new SpinnerItem("0", "��"));

		// �¹ʴ�����
		handleDeptList = new ArrayList<SpinnerItem>();
		handleDeptList.add(new SpinnerItem("01", "����"));
		handleDeptList.add(new SpinnerItem("02", "������"));
		handleDeptList.add(new SpinnerItem("03", "��������"));

		// ���߳��Ƿ�Ϊ������
		flagList = new ArrayList<SpinnerItem>();
		flagList.add(new SpinnerItem("0", "��"));
		flagList.add(new SpinnerItem("1", "��"));

		// ͼƬ���ʹ���
		imageTypeList = new ArrayList<SpinnerItem>();
		imageTypeList.add(new SpinnerItem("0101", "�����������������������顷"));
		imageTypeList.add(new SpinnerItem("0201", "�����������յ�����"));
		imageTypeList.add(new SpinnerItem("0301", "��ͨ�¹������϶���"));
		imageTypeList.add(new SpinnerItem("0302", "������"));
		imageTypeList.add(new SpinnerItem("0303", "�����¹ʴ�����"));
		imageTypeList.add(new SpinnerItem("0304", "�����϶��¹����ε�֤��"));
		imageTypeList.add(new SpinnerItem("0401", "�ö���"));
		imageTypeList.add(new SpinnerItem("0402", "�þ���"));
		imageTypeList.add(new SpinnerItem("0403", "������"));
		imageTypeList.add(new SpinnerItem("0404", "�о���"));
		imageTypeList.add(new SpinnerItem("0405", "�ٲ���"));
		imageTypeList.add(new SpinnerItem("0502", "�����������ʽ��Ʊ(������ά��ҵר�÷�Ʊ)"));
		imageTypeList.add(new SpinnerItem("0503", "��������嵥"));
		imageTypeList.add(new SpinnerItem("0504", "�����嵥"));
		imageTypeList.add(new SpinnerItem("0601", "�������������ղƲ���ʧȷ���顷"));
		imageTypeList.add(new SpinnerItem("0602", "�豸������ۼ���ʧ�̶�֤��"));
		imageTypeList.add(new SpinnerItem("0603", "�豸�ָ��Ĺ���Ԥ��"));
		imageTypeList.add(new SpinnerItem("0604", "�Ʋ���ʧ�嵥"));
		imageTypeList.add(new SpinnerItem("0605", "���á��޸�����Ʋ����йط��õ���"));
		imageTypeList.add(new SpinnerItem("0701", "�ؼ�����ҽԺ���֤��"));
		imageTypeList.add(new SpinnerItem("0702", "��Ժ֪ͨ��"));
		imageTypeList.add(new SpinnerItem("0703", "��Ҫ������Ա֤��"));
		imageTypeList.add(new SpinnerItem("0704", "ҽ�Ʒѱ���ƾ֤���븽���������ơ���ҩ��ϸ���ݣ�"));
		imageTypeList.add(new SpinnerItem("0705",
				"�ˡ��С�����Ա��֤�����������֤�������볬����˰����Ӧ�ύ��˰֤����"));
		imageTypeList.add(new SpinnerItem("0706",
				"������Ա��֤�����������֤�������볬����˰����Ӧ�ύ��˰֤����"));
		imageTypeList.add(new SpinnerItem("0707", "�������ṩ��ҽ�˲м�����"));
		imageTypeList.add(new SpinnerItem("0708", "�������ṩ����֤��"));
		imageTypeList.add(new SpinnerItem("0709", "��������֤������"));
		imageTypeList.add(new SpinnerItem("0710", "�����ɳ������ߵ��ܺ��߼�ͥ���֤��"));
		imageTypeList.add(new SpinnerItem("0711", "����"));
		imageTypeList.add(new SpinnerItem("0712", "ɥʧ�Ͷ�����֤��"));
		imageTypeList.add(new SpinnerItem("0713", "��ͨ�ѱ���ƾ֤"));
		imageTypeList.add(new SpinnerItem("0714", "ס�޷ѱ���ƾ֤"));
		imageTypeList.add(new SpinnerItem("0715", "�μ��¹ʴ�����Ա����֤��"));
		imageTypeList.add(new SpinnerItem("0716",
				"�������֧���⳥���õĹ���ƾ֤�������¹ʴ�����ǩ��ȷ�ϣ�"));
		imageTypeList.add(new SpinnerItem("0801", "��������ʻ֤��ԭ����"));
		imageTypeList.add(new SpinnerItem("0802", "���յ��ؼ����Ϲ������첿�ų��ߵĵ�����������֤��"));
		imageTypeList.add(new SpinnerItem("0803", "�ѵǱ�������֤��"));
		imageTypeList.add(new SpinnerItem("0804",
				"�������ø��ӷѽɷ�ƾ֤���վ�(ԭ��)��������˰��˰֤���ʹ�����������˰��˰�վ�(ԭ��)����˰֤��(ԭ��)"));
		imageTypeList.add(new SpinnerItem("0805", "�������Ǽ�֤�飨ԭ����"));
		imageTypeList.add(new SpinnerItem("0806", "����ͣʻ����֤��"));
		imageTypeList.add(new SpinnerItem("0807", "����������ƾ֤"));
		imageTypeList.add(new SpinnerItem("0808", "ȫ�׳�Կ��"));
		imageTypeList.add(new SpinnerItem("0901", "���յع����������ų��ߵĳ�����ȼ�����֤��"));
		imageTypeList.add(new SpinnerItem("1001", "���ճ�������������ʻ֤��"));
		imageTypeList.add(new SpinnerItem("1002", "���¼�ʻ��Ա�ġ���������ʻ֤��"));
		imageTypeList.add(new SpinnerItem("1101", "��ȡ�����Ȩ�����������顷"));
		imageTypeList.add(new SpinnerItem("1102", "�����������֤��"));
		imageTypeList.add(new SpinnerItem("1103", "��ȡ�����Ա���֤��"));
		imageTypeList.add(new SpinnerItem("1201", "������ʧ��Ƭ"));
		imageTypeList.add(new SpinnerItem("9901", ""));
		imageTypeList.add(new SpinnerItem("9902", ""));
		imageTypeList.add(new SpinnerItem("9903", ""));
		imageTypeList.add(new SpinnerItem("9999", "����"));
		
		//��������
				otherFeeNameList = new ArrayList<SpinnerItem>();
				otherFeeNameList.add(new SpinnerItem("0", "��ӹ���"));
				otherFeeNameList.add(new SpinnerItem("1", "ר�ҹ�ʱ��"));
		
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
