package com.jy.recycle.server;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jy.ah.bus.data.Request;
import com.jy.ah.bus.data.Response;
import com.jy.framework.android.util.LogUtil;
import com.jy.mobile.dict.RequestType;
import com.jy.mobile.dto.PartGroupDTO;
import com.jy.mobile.dto.RepairGroupDTO;
import com.jy.mobile.dto.ZcClfzxxbDTO;
import com.jy.mobile.dto.ZcCxxxbDTO;
import com.jy.mobile.request.QtAcceptTaskDTO;
import com.jy.mobile.request.QtArriveSceneDTO;
import com.jy.mobile.request.QtAssistListDTO;
import com.jy.mobile.request.QtAutoVehicleDTO;
import com.jy.mobile.request.QtDispatchTaskDTO;
import com.jy.mobile.request.QtGpsInfoDTO;
import com.jy.mobile.request.QtLatestVersionInfoDTO;
import com.jy.mobile.request.QtLocalPriceDTO;
import com.jy.mobile.request.QtLoginDTO;
import com.jy.mobile.request.QtLogoutDTO;
import com.jy.mobile.request.QtPartGroupDTO;
import com.jy.mobile.request.QtPartListDTO;
import com.jy.mobile.request.QtQueryTaskDetailDTO;
import com.jy.mobile.request.QtQueryTaskListDTO;
import com.jy.mobile.request.QtRebackDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.request.QtRecieveTaskResponseDTO;
import com.jy.mobile.request.QtRepairGroupDTO;
import com.jy.mobile.request.QtRepairListDTO;
import com.jy.mobile.request.QtSearchVehicleDTO;
import com.jy.mobile.request.QtTaskStatusDTO;
import com.jy.mobile.request.QtUserSettingsDTO;
import com.jy.mobile.request.QtVehicleGroupListDTO;
import com.jy.mobile.request.QtVehicleListDTO;
import com.jy.mobile.request.QtVehicleSerialListDTO;
import com.jy.mobile.response.SpPartGroupDTO;
import com.jy.mobile.response.SpPartListDTO;
import com.jy.mobile.response.SpRepairGroupDTO;
import com.jy.mobile.response.SpVehicleGroupListDTO;
import com.jy.mobile.response.SpVehicleListDTO;
import com.jy.mobile.response.SpVehicleSerialListDTO;
import com.jy.recycle.client.response.EvalLossInfo;
import com.jy.recycle.dao.LocalEvalDao;
import com.jy.recycle.dao.LocalGroupDao;
import com.jy.recycle.pojo.VersionInfoReq;
import com.jy.recycle.pojo.VersionInfoRsp;
import com.jy.recycle.util.ClaimFlag;
import com.jy.recycle.util.Constants;
import com.jy.recycle.util.HttpUtil;
import com.jy.recycle.util.JsonUtil;
import com.jy.recycle.util.MD5;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.SpinnerItem;
import com.jy.recycle.util.XMLUtil;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ����˽ӿڷ����� �������������˵Ľ���
 *
 * @author zhaowenbin 2012-02-15
 */
public class ServerApiManager {
    private static SharedData share;

    /**
     * ��ö�����Ϣ
     *
     * @return
     */
    public static Response getEvalLossInfo(QtRebackDTO qtRebackDTO) {
        Response response = getResponse(qtRebackDTO,
                RequestType.REBACK_EVAL_LOSS_INFO);
        return response;
    }

    /**
     * ���ʷ���������¼
     *
     * @param loginName
     * @param password
     * @return
     */
    public static Response login(String loginName, String password,
                                 String devicesId, String model, int verCode) {
        QtLoginDTO request = new QtLoginDTO();
        request.setUserId(loginName);
        request.setPassword(password);
        request.setDeviceId(devicesId);
        request.setModel(model);
        request.setVersionCode(String.valueOf(verCode));
        request.setCurrentDate(new Date());

        return getResponse(request, RequestType.LOGIN);
    }

    /**
     * ���ʷ��������˳�ϵͳ
     *
     * @param loginName
     * @return
     */
    public static Response logout(String loginName) {
        QtLogoutDTO request = new QtLogoutDTO();
        request.setUserCode(loginName);
        request.setLoginSessionId(SharedData.data().getCookie(
                Constants.LOGIN_SESSION_ID));

        return getResponse(request, RequestType.LOGOUT);
    }

    /**
     * ����֯���������ȡ�۸񷽰��б�
     *
     * @param comCode
     */
    public static Response getUserSettings(String comCode, String userCode) {
        QtUserSettingsDTO request = new QtUserSettingsDTO();
        request.setUserId(userCode);
        request.setOrgCode(comCode);

        return getResponse(request, RequestType.USER_SETTINGS);
    }

    /**
     * ����˵�qtclaim �ӿڣ���ȡ����
     *
     * @param userId
     * @param phoneNumber
     * @return
     */
    public static Response getEvalClaim(String userId, String phoneNumber) {
        QtRecieveTaskDTO qtRecieveTaskDTO = new QtRecieveTaskDTO();
        qtRecieveTaskDTO.setUserId(userId);

        return getResponse(qtRecieveTaskDTO, RequestType.RECIEVE_TASK);
    }

    /**
     * �汾У��
     *
     * @return
     * @throws Exception
     */

    public final static VersionInfoRsp checkVersion(
            VersionInfoReq versionInfoReq) throws Exception {
        XMLUtil util = new XMLUtil();
        String xmlData = util.writeVersionInfoToString(versionInfoReq)
                .substring(54);
        VersionInfoRsp versionInfoRsp = new VersionInfoRsp();
        String urlDownload = SharedData.data().getUploadUrl()
                + "/mobileplat/CheckVersionServlet";
        versionInfoRsp = util.sendData(urlDownload, xmlData);
        return versionInfoRsp;
    }

    /**
     * ��ȡ���µĿͻ��˰汾��Ϣ �Լ�
     *
     * @param nextVerCode
     * @return
     */
    public final static String[] getLatestVersionInfo4APK(int nextVerCode) {
        String[] ver = null;
        try {
            StringBuilder url = new StringBuilder(Constants.getVersionInfoUrl());
            url.append("&uid=").append(SharedData.data().getEvalUid());
            url.append("&verCode=").append(nextVerCode);

            String str = null;
            try {
                str = HttpUtil.queryStringForPost(url.toString());
            } catch (IOException e) {
                LogUtil.log(
                        "ServerApiManager-getLatestVersionInfo():"
                                + e.getMessage(), null);
            }
            // ��������ͣ��汾���汾���ƣ���ַ
            if (str != null && !"".equals(str)) {
                ver = new String[6];
                JSONObject js = new JSONObject(str);
                String st = JsonUtil.parseJson(js, "result");
                ver[0] = st;
                if (st != null && st.equals("0")) {
                    ver[1] = JsonUtil.parseJson(js, "verCode");
                    ver[2] = JsonUtil.parseJson(js, "verName");
                    ver[3] = JsonUtil.parseJson(js, "verAddr");
                    ver[4] = JsonUtil.parseJson(js, "verSize");
                    ver[5] = JsonUtil.parseJson(js, "verId");
                }
            }
        } catch (JSONException e) {
            LogUtil.log("EvalService_getRepairChild:" + e.getMessage(), null);
        }
        return ver;
    }

    /**
     * ��ȡ����������������˷����Ѿ����յ�������
     *
     * @param flowIds
     * @param type
     * @return
     */
    public static Response getEvalClaimResponse(String flowIds, String type) {
        QtRecieveTaskResponseDTO qtRecieveTaskResponseDTO = new QtRecieveTaskResponseDTO();
        qtRecieveTaskResponseDTO.setFlowId(flowIds);
        qtRecieveTaskResponseDTO.setType(type);
        return getResponse(qtRecieveTaskResponseDTO,
                RequestType.RECIEVE_TASK_RESPONSE);
    }

    /**
     * ��ȡ���µĿͻ��˰汾��Ϣ
     *
     * @param nextVerCode
     * @return
     */

    public final static Response getLatestVersionInfo(int nextVerCode) {
        QtLatestVersionInfoDTO dto = new QtLatestVersionInfoDTO();
        dto.setVersionCode(nextVerCode);
        return getResponse(dto, RequestType.GET_LATEST_VERSION_INFO);
    }

    /**
     * �ɳ���ID��ȡ��������
     *
     * @param groupId
     * @return
     */
    public final static SpVehicleListDTO getListVehData(String groupId) {
        QtVehicleListDTO dto = new QtVehicleListDTO();
        SpVehicleListDTO vehicleListDto = null;
        vehicleListDto = new SpVehicleListDTO();
        if (SharedData.data().getIsLocal()) {
            LocalEvalDao localEvalDao = LocalEvalDao.getInstance();
            vehicleListDto = localEvalDao.getVehicleByCZBM(groupId);
        } else {
            dto.setGroupId(groupId);
            Response response = getResponse(dto, RequestType.GET_VEHICLE_LIST);
            if (response != null && "1".equals(response.getResponseCode())) {
                vehicleListDto = (SpVehicleListDTO) JsonUtil.getSpDto(response,
                        SpVehicleListDTO.class);
            }

        }
        return vehicleListDto;

    }

    /**
     * ��ȡ���ؼ۸� he SYstemPrice
     *
     * @param ljid
     * @return
     */
    public final static Response getBdPrice(String ljid) {
        QtLocalPriceDTO qtDto = new QtLocalPriceDTO();
        qtDto.setPartId(ljid);
        return getResponse(qtDto, RequestType.GET_LOCAL_PRICE);
    }

    /**
     * ����GSP��Ϣ
     *
     * @param lat
     * @param lng
     */
    public final static Response sendGpsInfo(double lat, double lng,
                                             String userId) {
        QtGpsInfoDTO qtDto = new QtGpsInfoDTO();
        qtDto.setLatitude(lat);
        qtDto.setLongitude(lng);
        qtDto.setUserId(userId);
        return getResponse(qtDto, RequestType.SEND_GPS_INFO);
    }

    /**
     * Զ�̻�ȡ���ݣ�����JSON
     *
     * @param taskNo   �����
     * @param taskType ��������
     * @return
     */
    public final static Response getSurveyRemoteInfo(String taskNo,
                                                     String reportId, String taskState, String taskType) {
        QtQueryTaskDetailDTO qtDto = new QtQueryTaskDetailDTO();
        qtDto.setFlowId(taskNo);
        qtDto.setFlowTypeId(taskType);
        qtDto.setReportId(reportId);
        qtDto.setTaskState(taskState);
        return getResponse(qtDto, RequestType.QUERY_SURVEY_TASK_INFO);
    }

    /**
     * @param taskNo    �����
     * @param reportId
     * @param taskState
     * @param taskType  ��������
     * @return
     */
    public final static Response getEvalRemoteInfo(String taskNo,
                                                   String reportId, String taskState, String taskType) {
        QtQueryTaskDetailDTO qtDto = new QtQueryTaskDetailDTO();
        qtDto.setFlowId(taskNo);
        qtDto.setFlowTypeId(taskType);
        qtDto.setReportId(reportId);
        qtDto.setTaskState(taskState);
        return getResponse(qtDto, RequestType.QUERY_EVAL_TASK_INFO);
    }

    /**
     * ��Ʒ�����ƻ�ȡ��ϵ����
     *
     * @param brandName
     * @return
     */
    public final static ArrayList<SpinnerItem> getSeriData(String brandName) {
        ArrayList<SpinnerItem> cxData = null;
        cxData = new ArrayList<SpinnerItem>();
        if (SharedData.data().getIsLocal()) {
            LocalEvalDao localEvalDao = LocalEvalDao.getInstance();
            List<ZcCxxxbDTO> ZcCxxxbList = new ArrayList<ZcCxxxbDTO>();
            ZcCxxxbList = localEvalDao
                    .getVehicleSerialListByBrandName(brandName);
            if (ZcCxxxbList != null) {
                for (ZcCxxxbDTO serialDTO : ZcCxxxbList) {
                    SpinnerItem si = new SpinnerItem(serialDTO.getId(),
                            serialDTO.getSerialName());
                    cxData.add(si);
                }
            }
        } else {
            QtVehicleSerialListDTO dto = new QtVehicleSerialListDTO();
            dto.setBrandName(brandName);
            Response response = getResponse(dto,
                    RequestType.GET_VEHICLE_SERIAL_LIST);
            // ����
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            if (response != null && "1".equals(response.getResponseCode())) {
                String spData = response.getData();
                if (spData != null && !"".equals(spData.trim())) {
                    SpVehicleSerialListDTO spVehicleSerialListDTO = gson
                            .fromJson(spData, SpVehicleSerialListDTO.class);
                    if (spVehicleSerialListDTO != null
                            && spVehicleSerialListDTO.getVehicleSerialList() != null) {
                        for (ZcCxxxbDTO serialDTO : spVehicleSerialListDTO
                                .getVehicleSerialList()) {
                            SpinnerItem si = new SpinnerItem(serialDTO.getId(),
                                    serialDTO.getSerialName());
                            cxData.add(si);
                        }
                    }
                }
            }
        }
        return cxData;
    }

    /**
     * �ɳ�ϵ��ѯ������Ϣ
     *
     * @param serId
     * @return
     */
    public final static List<Map<String, String>> getGroupData(String serId) {

        List<Map<String, String>> data = null;

        if (SharedData.data().getIsLocal()) {
            LocalEvalDao localEvalDao = LocalEvalDao.getInstance();
            List<ZcClfzxxbDTO> ZcClfzxxbList = new ArrayList<ZcClfzxxbDTO>();
            ZcClfzxxbList = localEvalDao.getVehicleGroupListBySerialId(serId);
            if (ZcClfzxxbList != null) {
                data = new ArrayList<Map<String, String>>();
                for (ZcClfzxxbDTO zDto : ZcClfzxxbList) {
                    HashMap<String, String> p = new HashMap<String, String>();
                    p.put("id", zDto.getId());
                    p.put("czmc", zDto.getCzmc());
                    data.add(p);
                }
            }
        } else {
            QtVehicleGroupListDTO qtDto = new QtVehicleGroupListDTO();
            qtDto.setSerialId(serId);
            Response response = getResponse(qtDto,
                    RequestType.GET_VEHICLE_GROUP_LIST);

            // ����
            SpVehicleGroupListDTO spDto = (SpVehicleGroupListDTO) JsonUtil
                    .getSpDto(response, SpVehicleGroupListDTO.class);

            if (spDto != null && spDto.getVehicleGroupList() != null) {

                data = new ArrayList<Map<String, String>>();
                for (ZcClfzxxbDTO zDto : spDto.getVehicleGroupList()) {
                    HashMap<String, String> p = new HashMap<String, String>();
                    p.put("id", zDto.getId());
                    p.put("czmc", zDto.getCzmc());
                    data.add(p);
                }
            }
        }
        return data;
    }

    /**
     * ��������
     *
     * @param flowId
     * @return
     */
    public final static Response acceptTask(String flowId) {
        QtAcceptTaskDTO qtAcceptTaskDTO = new QtAcceptTaskDTO();
        qtAcceptTaskDTO.setFlowId(flowId);
        return getResponse(qtAcceptTaskDTO, RequestType.ACCEPT_TASK);
    }

    /**
     * �������
     *
     * @param flowId
     * @param taskType
     * @param applyDesc
     * @return
     */
    public final static Response dispatchTask(String flowId, String taskType,
                                              String applyDesc, String reperFlag) {
        QtDispatchTaskDTO qtDispatchTaskDTO = new QtDispatchTaskDTO();
        qtDispatchTaskDTO.setFlowId(flowId);
        qtDispatchTaskDTO.setReperFlag(reperFlag);
        qtDispatchTaskDTO.setFlowTypeId(taskType);
        qtDispatchTaskDTO.setUserId(SharedData.data().getEvalUid());
        qtDispatchTaskDTO.setApplyDescription(applyDesc);
        return getResponse(qtDispatchTaskDTO, RequestType.APPLY_DISPATCH_TASK);
    }

    /**
     * �ɼ۸񷽰���������ȡ���������Ϣ
     *
     * @param vehicleId
     * @param jgfaId
     * @param ljzId
     * @param pageno
     * @return
     */
    public final static SpPartListDTO getJtljData(String vehicleId,
                                                  String jgfaId, String ljzId, String pageno) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        SpPartListDTO spPartListDTO = null;
        if (SharedData.data().getIsLocal()) {
            spPartListDTO = LocalGroupDao.getJtljData(vehicleId, jgfaId, ljzId,
                    pageno);
        } else {
            QtPartListDTO qtPartListDTO = new QtPartListDTO();
            qtPartListDTO.setVehicleId(vehicleId);
            qtPartListDTO.setJgfaId(jgfaId);
            qtPartListDTO.setPartGroupId(ljzId);
            qtPartListDTO.setPageNo(Integer.valueOf(pageno));
            qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

            Response response = getResponse(qtPartListDTO,
                    RequestType.GET_PART_LIST_BY_PART_GROUP);
            if (response != null && "1".equals(response.getResponseCode())) {
                spPartListDTO = (SpPartListDTO) JsonUtil.getSpDto(response,
                        SpPartListDTO.class);
            }
        }
        return spPartListDTO;

    }

    /**
     * �ɼ۸񷽰���������ȡ���������Ϣ----��׼��ѡ
     *
     * @param vehicleId
     * @param jgfaId
     * @param ljzId
     * @param pageno
     * @return
     */
    public final static Response getJtljDataB(String vehicleId, String jgfaId,
                                              String ljzId, String pageno, String b) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        QtPartListDTO qtPartListDTO = new QtPartListDTO();
        qtPartListDTO.setPartId(b);
        qtPartListDTO.setVehicleId(vehicleId);
        qtPartListDTO.setJgfaId(jgfaId);
        qtPartListDTO.setPartGroupId(ljzId);
        qtPartListDTO.setPageNo(Integer.valueOf(pageno));
        qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

        return getResponse(qtPartListDTO,
                RequestType.GET_PART_LIST_BY_PART_GROUP);
    }

    /**
     * �ɼ۸񷽰���ԭ������ż�������ƻ�ȡ���������Ϣ
     *
     * @param vehicleId
     * @param jgfaId
     * @param ljmc
     * @param ycljh
     * @param pageno
     * @return
     */
    public final static SpPartListDTO getJtljData(String vehicleId,
                                                  String jgfaId, String ljmc, String ycljh, String pageno) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        SpPartListDTO spPartListDTO = null;
        if (SharedData.data().getIsLocal()) {
            spPartListDTO = LocalGroupDao.getJtljData(vehicleId, jgfaId, ljmc,
                    ycljh, pageno);
        } else {
            QtPartListDTO qtPartListDTO = new QtPartListDTO();
            qtPartListDTO.setVehicleId(vehicleId);
            qtPartListDTO.setJgfaId(jgfaId);
            qtPartListDTO.setPartName(ljmc);
            qtPartListDTO.setPartCode(ycljh);
            qtPartListDTO.setPageNo(Integer.valueOf(pageno));
            qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

            Response response = getResponse(qtPartListDTO,
                    RequestType.GET_PART_LIST_BY_PART_NAME);
            if (response != null && "1".equals(response.getResponseCode())) {
                spPartListDTO = (SpPartListDTO) JsonUtil.getSpDto(response,
                        SpPartListDTO.class);
            }
        }
        return spPartListDTO;
        // QtPartListDTO qtPartListDTO = new QtPartListDTO();
        // qtPartListDTO.setVehicleId(vehicleId);
        // qtPartListDTO.setJgfaId(jgfaId);
        // qtPartListDTO.setPartName(ljmc);
        // qtPartListDTO.setPartCode(ycljh);
        // qtPartListDTO.setPageNo(Integer.valueOf(pageno));
        // qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);
        //
        // return getResponse(qtPartListDTO,
        // RequestType.GET_PART_LIST_BY_PART_NAME);
    }

    /**
     * �ɼ۸񷽰���ԭ������ż�������ƻ�ȡ���������Ϣ------��׼��ѡ
     *
     * @param vehicleId
     * @param jgfaId
     * @param ljmc
     * @param ycljh
     * @param pageno
     * @return
     */
    public final static Response getJtljDataB(String vehicleId, String jgfaId,
                                              String ljmc, String ycljh, String pageno, String b) {

        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        QtPartListDTO qtPartListDTO = new QtPartListDTO();
        qtPartListDTO.setPartId(b);
        qtPartListDTO.setVehicleId(vehicleId);
        qtPartListDTO.setJgfaId(jgfaId);
        qtPartListDTO.setPartName(ljmc);
        qtPartListDTO.setPartCode(ycljh);
        qtPartListDTO.setPageNo(Integer.valueOf(pageno));
        qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

        return getResponse(qtPartListDTO,
                RequestType.GET_PART_LIST_BY_PART_NAME);
    }

    /**
     * ��ײ����
     *
     * @param pzcdStr
     * @param pzbwStr
     * @return
     */
    public final static SpPartListDTO getPzJtljData(String vehicleId,
                                                    String jgfaId, String pzcdStr, String pzbwStr, String pageNo) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        SpPartListDTO spPartListDTO = null;
        if (SharedData.data().getIsLocal()) {
            spPartListDTO = LocalGroupDao.getPzJtljData(vehicleId, jgfaId,
                    pzcdStr, pzbwStr, pageNo);
        } else {
            QtPartListDTO qtPartListDTO = new QtPartListDTO();
            qtPartListDTO.setVehicleId(vehicleId);
            qtPartListDTO.setJgfaId(jgfaId);
            qtPartListDTO.setCollisionSite(pzbwStr);
            qtPartListDTO.setCollisionDegree(pzcdStr);
            qtPartListDTO.setPageNo(Integer.valueOf(pageNo));
            qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

            Response response = getResponse(qtPartListDTO,
                    RequestType.GET_PART_LIST_BY_COLLISION);
            if (response != null && "1".equals(response.getResponseCode())) {
                spPartListDTO = (SpPartListDTO) JsonUtil.getSpDto(response,
                        SpPartListDTO.class);
            }
        }
        return spPartListDTO;
        //
        // QtPartListDTO qtPartListDTO = new QtPartListDTO();
        // qtPartListDTO.setVehicleId(vehicleId);
        // qtPartListDTO.setJgfaId(jgfaId);
        // qtPartListDTO.setCollisionSite(pzbwStr);
        // qtPartListDTO.setCollisionDegree(pzcdStr);
        // qtPartListDTO.setPageNo(Integer.valueOf(pageNo));
        // qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);
        //
        // return getResponse(qtPartListDTO,
        // RequestType.GET_PART_LIST_BY_COLLISION);
    }

    /**
     * ��ײ����-----��׼��ѡ
     *
     * @param pzcdStr
     * @param pzbwStr
     * @return
     */
    public final static Response getPzJtljDataB(String vehicleId,
                                                String jgfaId, String pzcdStr, String pzbwStr, String pageNo,
                                                String b) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        QtPartListDTO qtPartListDTO = new QtPartListDTO();
        qtPartListDTO.setPartId(b);
        qtPartListDTO.setVehicleId(vehicleId);
        qtPartListDTO.setJgfaId(jgfaId);
        qtPartListDTO.setCollisionSite(pzbwStr);
        qtPartListDTO.setCollisionDegree(pzcdStr);
        qtPartListDTO.setPageNo(Integer.valueOf(pageNo));
        qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

        return getResponse(qtPartListDTO,
                RequestType.GET_PART_LIST_BY_COLLISION);
    }

    /**
     * �������ҳ��ʱ������004010�Ĳ�ѯ��� �ӿ�������룺004010.
     * �������ݣ��ο�������Ʋ�ѯ�ģ�����Ҫ��������ƺͱ��룬��Ҫ���۸񷽰��ͳ���ID
     *
     * @param vehicleId
     * @param jgfaId
     * @param pageno
     * @return
     */
    public final static SpPartListDTO getPjDataAuto(String vehicleId,
                                                    String jgfaId, String pageno) {

        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        SpPartListDTO spPartListDTO = null;
        if (SharedData.data().getIsLocal()) {
            spPartListDTO = LocalGroupDao.getPjDataAuto(vehicleId, jgfaId,
                    pageno);
            // if (spPartListDTO != null &&
            // spPartListDTO.getPartList().size()>0){
            // SharedData.data().saveIsLocal(true);
        } else {
            QtPartListDTO qtPartListDTO = new QtPartListDTO();
            qtPartListDTO.setVehicleId(vehicleId);
            qtPartListDTO.setJgfaId(jgfaId);
            qtPartListDTO.setPageNo(Integer.valueOf(pageno));
            qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);

            Response response = getResponse(qtPartListDTO, "004010");
            if (response != null && "1".equals(response.getResponseCode())) {
                spPartListDTO = (SpPartListDTO) JsonUtil.getSpDto(response,
                        SpPartListDTO.class);
            }
            // SharedData.data().saveIsLocal(false);
        }
        return spPartListDTO;

        // QtPartListDTO qtPartListDTO = new QtPartListDTO();
        // qtPartListDTO.setVehicleId(vehicleId);
        // qtPartListDTO.setJgfaId(jgfaId);
        // qtPartListDTO.setPageNo(Integer.valueOf(pageno));
        // qtPartListDTO.setPageSize(ClaimFlag.PART_PAGE_SIZE);
        //
        // Response response =
        // getResponse(qtPartListDTO,RequestType.GET_PART_LIST_BY_SIMPLE);
        // return response;
    }

    /**
     * ��ײ��ѯ
     *
     * @param vehicelId
     * @param pzcdStr
     * @param pzbwStr
     * @param pageNo
     * @return
     */
    // public final static String getPzCxData(String vehicelId,
    // String pzcdStr, String pzbwStr, String pageNo) {
    // StringBuilder url = new StringBuilder();
    // url.append(Constants.URL_GET_PART_LIST_BYPZ_CX).append(vehicelId)
    // .append("&pzbwid=").append(pzbwStr)
    // .append("&pzcdid=").append(pzcdStr).append("&pageno=").append(pageNo)
    // .append("&pagesize=").append(ClaimFlag.PART_PAGE_SIZE);
    //
    // String str = null;
    // try {
    // str = HttpUtil.queryStringForPost(url.toString());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return str;
    // }

    /**
     * ��ȡ��������� ��һ������
     *
     * @param vehicleId
     * @return
     */
    public final static ArrayList<SpinnerItem> getFljData(String vehicleId) {
        ArrayList<SpinnerItem> fljData = null;

        SpPartGroupDTO spPargGroupDTO = null;

        if (SharedData.data().getIsLocal()) {
            spPargGroupDTO = LocalGroupDao.getFljData(vehicleId);
            if (spPargGroupDTO != null
                    && spPargGroupDTO.getPartGroupList() != null) {

                fljData = new ArrayList<SpinnerItem>();
                for (PartGroupDTO dto : spPargGroupDTO.getPartGroupList()) {
                    SpinnerItem si = new SpinnerItem(dto.getId(), dto.getName());
                    fljData.add(si);
                }
            }
        } else {
            QtPartGroupDTO qtPartGroupDTO = new QtPartGroupDTO();
            qtPartGroupDTO.setVehicleId(vehicleId);
            qtPartGroupDTO.setParentId(null);

            Response response = getResponse(qtPartGroupDTO,
                    RequestType.GET_PART_GROUP);
            // ����
            if (response != null && "1".equals(response.getResponseCode())) {
                String spData = response.getData();
                if (spData != null && !"".equals(spData.trim())) {
                    spPargGroupDTO = (SpPartGroupDTO) JsonUtil.getSpDto(
                            response, SpPartGroupDTO.class);
                    if (spPargGroupDTO != null
                            && spPargGroupDTO.getPartGroupList() != null) {

                        fljData = new ArrayList<SpinnerItem>();
                        for (PartGroupDTO dto : spPargGroupDTO
                                .getPartGroupList()) {
                            SpinnerItem si = new SpinnerItem(dto.getId(),
                                    dto.getName());
                            fljData.add(si);
                        }
                    }
                }
            }
        }
        return fljData;

    }

    /**
     * ��ȡ��������� �ڶ�������
     *
     * @param vehicleId
     * @param ljzId
     * @return
     */
    public final static List<Map<String, String>> getLjGroupData(
            String vehicleId, String ljzId) {

        List<Map<String, String>> groupData = null;
        SpPartGroupDTO spPargGroupDTO = null;
        if (SharedData.data().getIsLocal()) {
            spPargGroupDTO = LocalGroupDao.getLjGroupData(vehicleId, ljzId);
            if (spPargGroupDTO != null
                    && spPargGroupDTO.getPartGroupList() != null) {

                groupData = new ArrayList<Map<String, String>>();
                for (PartGroupDTO dto : spPargGroupDTO.getPartGroupList()) {
                    HashMap<String, String> p = new HashMap<String, String>();
                    p.put("id", dto.getId());
                    p.put("fzmc", dto.getName());
                    groupData.add(p);
                }
            }
        } else {

            QtPartGroupDTO qtPartGroupDTO = new QtPartGroupDTO();
            qtPartGroupDTO.setVehicleId(vehicleId);
            qtPartGroupDTO.setParentId(ljzId);
            // ����
            Response response = getResponse(qtPartGroupDTO,
                    RequestType.GET_PART_GROUP);

            if (response != null && "1".equals(response.getResponseCode())) {
                String spData = response.getData();
                if (spData != null && !"".equals(spData.trim())) {
                    Log.i("spData", spData);
                    spPargGroupDTO = (SpPartGroupDTO) JsonUtil.getSpDto(
                            response, SpPartGroupDTO.class);
                    if (spPargGroupDTO != null
                            && spPargGroupDTO.getPartGroupList() != null) {

                        groupData = new ArrayList<Map<String, String>>();
                        for (PartGroupDTO dto : spPargGroupDTO
                                .getPartGroupList()) {
                            HashMap<String, String> p = new HashMap<String, String>();
                            p.put("id", dto.getId());
                            p.put("fzmc", dto.getName());
                            groupData.add(p);
                        }
                    }
                }
            }
        }

        return groupData;
    }

    /**
     * ��ȡ��������Ŀ��������
     *
     * @param flag
     * @param type
     * @return
     */
    public final static ArrayList<SpinnerItem> getRepairParent(String flag,
                                                               String type) {
        QtRepairGroupDTO qtDto = new QtRepairGroupDTO();
        qtDto.setParentId(type);
        Response response = getResponse(qtDto, RequestType.GET_REPAIR_GROUP);
        // ����
        SpRepairGroupDTO spDto = (SpRepairGroupDTO) JsonUtil.getSpDto(response,
                SpRepairGroupDTO.class);
        List<RepairGroupDTO> list = spDto.getRepairGroupList();
        ArrayList<SpinnerItem> itemList = null;
        if (list != null && list.size() > 0) {
            itemList = new ArrayList<SpinnerItem>();
            for (RepairGroupDTO pgDto : list) {
                itemList.add(new SpinnerItem(pgDto.getId(), pgDto.getName()));
            }
        }
        return itemList;
    }

    /**
     * ��ȡ������������
     *
     * @param seledId
     * @return
     */
    public final static Response getRepairChild(String comCode,
                                                String vehCertainCode, String seledId) {

        QtRepairListDTO qtDto = new QtRepairListDTO();
        qtDto.setRepairGroupId(seledId);
        qtDto.setOrgId(comCode);
        qtDto.setVehicleId(vehCertainCode);

        Response response = getResponse(qtDto, RequestType.GET_REPAIR_LIST);
        return response;
        // // ����
        // SpRepairListDTO spDto = (SpRepairListDTO) JsonUtil.getSpDto(response,
        // SpRepairListDTO.class);
        // List<RepairItemDTO> list=null;
        // if(spDto!=null)
        // list = spDto.getRepairList();
        // List<HashMap<String, String>> data = null;
        // if (list != null && list.size() > 0) {
        // data = new ArrayList<HashMap<String, String>>();
        // int i = 0;
        // for (RepairItemDTO pgDto : list) {
        // HashMap<String, String> p = new HashMap<String, String>();
        // p.put("id", pgDto.getId());
        // p.put("mc", pgDto.getRepairName());
        // p.put("index", String.valueOf(i++));
        // data.add(p);
        // }
        // }
        // return data;
    }

    // public final static List<HashMap<String,String>> getRepairChild(String
    // seledId){
    //
    // QtRepairListDTO qtDto = new QtRepairListDTO();
    // qtDto.setRepairGroupId(seledId);
    //
    // Response response = getResponse(qtDto, RequestType.GET_REPAIR_LIST);
    // // ����
    // SpRepairListDTO spDto = (SpRepairListDTO) JsonUtil.getSpDto(response,
    // SpRepairListDTO.class);
    // List<RepairItemDTO> list=null;
    // if(spDto!=null)
    // list = spDto.getRepairList();
    // List<HashMap<String, String>> data = null;
    // if (list != null && list.size() > 0) {
    // data = new ArrayList<HashMap<String, String>>();
    // int i = 0;
    // for (RepairItemDTO pgDto : list) {
    // HashMap<String, String> p = new HashMap<String, String>();
    // p.put("id", pgDto.getId());
    // p.put("mc", pgDto.getRepairName());
    // p.put("index", String.valueOf(i++));
    // data.add(p);
    // }
    // }
    // return data;
    // }

    /**
     * �ӷ�����Զ�̻�ȡ����
     *
     * @param where ��ѯ����
     * @return json�ַ���
     */
    public final static Response getListClaimRemote(String where) {
        String[] keyValue = where.split("=");
        String key = keyValue[0];
        String value = keyValue[1];
        QtQueryTaskListDTO qtDto = new QtQueryTaskListDTO();

        if ("REPORT_NO".equals(key)) {
            key = "reportId";
            qtDto.setReportNo(value);
        } else if ("POLICY_NO".equals(key)) {
            key = "policyCode";
            qtDto.setPolicyNo(value);
        }
        qtDto.setUserId(SharedData.data().getEvalUid());
        return getResponse(qtDto, RequestType.QUERY_TASK_LIST);
    }

    /**
     * �鿱������������˷�����Ϣ
     *
     * @param taskNo
     */
    public final static Response sendTaskOver(String flag, String taskNo,
                                              String reportNo) {
        String queryFlag = null;
        QtTaskStatusDTO qtDto = new QtTaskStatusDTO();
        qtDto.setFlowId(queryFlag);
        qtDto.setTaskNo(taskNo);

        return getResponse(qtDto, queryFlag);
    }

    /**
     * �����ֳ�
     *
     * @param userId
     * @param taskId
     * @param reportNo
     * @param lat
     * @param lng
     * @param remark
     * @return
     */
    public final static Response arriveScene(String userId, String taskId,
                                             String reportNo, double lat, double lng, String remark) {

        QtArriveSceneDTO request = new QtArriveSceneDTO();
        request.setUserCode(userId);
        request.setTaskNo(taskId);
        request.setReportNo(reportNo);
        request.setResponseTime(new Date());
        request.setLatitude(String.valueOf(lat));
        request.setLongitude(String.valueOf(lng));
        request.setRemark(remark);

        return getResponse(request, RequestType.ARRIVE_SCENE);
    }

    /**
     * @param dto
     * @param requestCode ���ʴ���
     * @return
     */
    private static Response getResponse(Object dto, String requestCode) {
        Response response = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String qtData = gson.toJson(dto);
        Log.i("qtData", qtData);

        Request busRequest = new Request();
        busRequest.setRequestCode(requestCode);
        busRequest.setData(qtData);

        String responseData = null;
        try {
            Log.i("gson.toJson(busRequest)", gson.toJson(busRequest));
            responseData = IFCService.request(gson.toJson(busRequest));
            Log.i("responseData", responseData.toString());
            response = gson.fromJson(responseData, Response.class);
        } catch (SocketTimeoutException ste) {
            Log.e(dto.getClass().getSimpleName(), "��ȡ���ݳ�ʱ", ste);
            response = new Response();
            response.setResponseCode("0");
            response.setErrorMessage("����ʧ��:���粻�ȶ������Ժ�����");
        } catch (Exception e) {
            Log.e(dto.getClass().getSimpleName(), "��ȡ���������ʧ��", e);
            response = new Response();
            response.setResponseCode("0");
            response.setErrorMessage("����ʧ��" + e.getMessage());
        } finally {
            if (response == null) {
                response = new Response();
                response.setResponseCode("0");
                response.setErrorMessage("δ֪����");
            }
        }

        return response;
    }

    public static Response AutoVehicleService(String zcbm) {
        QtAutoVehicleDTO qtAutoVehicleDTO = new QtAutoVehicleDTO();
        qtAutoVehicleDTO.setZcbm(zcbm);
        return getResponse(qtAutoVehicleDTO, RequestType.AUTO_VEHICLE);
    }

    /**
     * ���ݳ���Id�͸�����Id����ȡͼ�η����б�
     *
     * @param vehicleId
     * @param parentId
     * @return
     */
    public static SpPartGroupDTO getPartGroup(String vehicleId, String parentId) {
        QtPartGroupDTO tDto = new QtPartGroupDTO();
        tDto.setVehicleId(vehicleId);
        tDto.setParentId(parentId);

        Response response = getResponse(tDto, RequestType.GET_TX_FITS_GROUP);
        SpPartGroupDTO spDto = (SpPartGroupDTO) JsonUtil.getSpDto(response,
                SpPartGroupDTO.class);

        return spDto;
    }

    /**
     * ��ѯ����ͼ
     *
     * @param vehicleId
     * @param parentId
     * @return
     */
    public static SpPartListDTO getPartGroupIndex(String vehicleId,
                                                  String parentId) {
        QtPartGroupDTO tDto = new QtPartGroupDTO();
        tDto.setVehicleId(vehicleId);
        tDto.setParentId(parentId);

        SpPartListDTO spDto = null;

        // String key = "C"+vehicleId+"-"+parentId ;
        // String cacheData = SharedData.data().getCookie(key);
        // //���û�л������ݣ���ӷ�������ȡ
        // if(cacheData!=null && !"".equals(cacheData.trim())){
        // GsonBuilder builder = new GsonBuilder();
        // Gson gson = builder.create();
        // spDto = (SpPartListDTO ) gson.fromJson(cacheData ,
        // SpPartListDTO.class);
        // return spDto ;
        // }else{
        Response response = getResponse(tDto, RequestType.GET_TX_INDEX_LIST);
        // SharedData.data().saveCookie( key,response.getData() );

        spDto = (SpPartListDTO) JsonUtil
                .getSpDto(response, SpPartListDTO.class);
        // }

        return spDto;
    }

    /**
     * �������ID����ȡͼ���б�ӿ�
     *
     * @param vehicleId
     * @param partId
     * @param jgfaId
     * @return
     */
    public static SpPartListDTO getPartPictureList(String vehicleId,
                                                   String partId, String jgfaId) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        QtPartListDTO tDto = new QtPartListDTO();
        tDto.setVehicleId(vehicleId);
        tDto.setPartId(partId);
        tDto.setJgfaId(jgfaId);

        // Response response = getResponse(tDto, RequestType.GET_TX_LIST);
        // SpPartListDTO spDto = (SpPartListDTO) JsonUtil.getSpDto(response,
        // SpPartListDTO.class);

        SpPartListDTO spDto = null;

        // String key = "P"+vehicleId+"-"+partId+"-"+jgfaId ;
        //
        // String cacheData = SharedData.data().getCookie(key);
        // //���û�л������ݣ���ӷ�������ȡ
        // if(cacheData!=null && !"".equals(cacheData.trim())){
        // GsonBuilder builder = new GsonBuilder();
        // Gson gson = builder.create();
        // spDto = (SpPartListDTO ) gson.fromJson(cacheData ,
        // SpPartListDTO.class);
        // return spDto ;
        // }else{
        Response response = getResponse(tDto, RequestType.GET_TX_LIST);
        // SharedData.data().saveCookie( key,response.getData() );

        spDto = (SpPartListDTO) JsonUtil
                .getSpDto(response, SpPartListDTO.class);
        // }

        return spDto;
    }

    /**
     * �������ID����ȡͼ���б�ӿ�
     *
     * @param vehicleId
     * @param jgfaId
     * @return
     */
    public static SpPartListDTO getPartListByPictureId(String vehicleId,
                                                       String pictureId, String jgfaId) {
        jgfaId = SharedData.data().getJgfaidByJgfabm(jgfaId);

        QtPartListDTO tDto = new QtPartListDTO();
        tDto.setVehicleId(vehicleId);
        tDto.setPartId(pictureId);
        tDto.setPictureId(pictureId);
        tDto.setJgfaId(jgfaId);

        SpPartListDTO spDto = null;

        // String key = "V"+vehicleId+"-"+pictureId+"-"+jgfaId ;
        // String cacheData = SharedData.data().getCookie(key);
        // //���û�л������ݣ���ӷ�������ȡ
        // if(cacheData!=null && !"".equals(cacheData.trim())){
        // GsonBuilder builder = new GsonBuilder();
        // Gson gson = builder.create();
        // spDto = (SpPartListDTO ) gson.fromJson(cacheData ,
        // SpPartListDTO.class);
        // return spDto ;
        // }else{
        Response response = getResponse(tDto, RequestType.GET_TX_PART_LIST);
        // SharedData.data().saveCookie( key,response.getData() );

        spDto = (SpPartListDTO) JsonUtil
                .getSpDto(response, SpPartListDTO.class);
        // }

        return spDto;
    }

    /**
     * ���ݳ���Id�͸�����Id����ȡͼ�η����б�
     *
     * @param vehicleId
     * @return
     */
    public static Response getAssistList(String vehicleId) {
        String orgId = SharedData.data().getEvalComCode();
        QtAssistListDTO qtDto = new QtAssistListDTO();
        qtDto.setOrgId(orgId);
        qtDto.setVehicleId(vehicleId);
        Response response = getResponse(qtDto, RequestType.GET_ASSIST_LIST);

        return response;
    }

    /**
     * �ϴ�������Ϣ
     *
     * @param evalLossInfo
     * @return
     */
    public static Response uploadEvalLossInfo(EvalLossInfo evalLossInfo) {
        Response response = getResponse(evalLossInfo,
                RequestType.UPLOAD_EVAL_LOSS_INFO);
        return response;
    }

    /**
     * ��¼�ӿ�
     *
     * @param loginName
     * @param password
     * @return
     */
    public static JSONObject loginUser(String loginName, String password,
                                       String path) {
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", Constants.REQUEST_SENDTYPE_CHECK);

            JSONObject jsonObject1 = new JSONObject();
            loginName = SharedData.data().getUserName();
            password = SharedData.data().getUserPwd();
            jsonObject1.put("username", loginName);
            // MD5����
            jsonObject1.put("password", MD5.encrypt(password + loginName));
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����json
            String msg = sendData(jsonObject.toString(), path);
            // //����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    /*
     * ��ȡʡ�ݳ�����Ϣ
     */
    public static JSONObject getCity(String loginName, String path) {
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0400");
            // loginName = "ygbx_sd_dsy";
            loginName = share.data().getUserName();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", loginName);
            // MD5����
            jsonObject.put("data", jsonObject1);
            Log.i("CityjsonObject", jsonObject.toString());
            // ����json
            String msg = sendData(jsonObject.toString(), path);
            // //����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    	public static String sendData(String xmlData, String path) throws Exception {

		String dataString = null;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,30000);
            HttpConnectionParams.setSoTimeout(httpParameters, 30000);
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpContext localContext = new BasicHttpContext();

		// ���������ip��ַ�Ͷ˿ں� ȷ����ַ
		String url = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
            HttpPost httpPost =new HttpPost(url);
            HttpEntity entity = new StringEntity(xmlData, "GBK");
            // ָ���������ݵ�����
            httpPost.setHeader("Content-type", "text/html; charset=GBK");
            httpPost.setEntity(entity);

			// ִ��postMethod
            HttpResponse ht=httpClient.execute(httpPost, localContext);
            if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = ht.getEntity();
                InputStream is = he.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
                String response = "";
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
//response = br.readLine();
                    response = response + new String(readLine.getBytes("GBK"),"GBK");
                }
                is.close();
                br.close();

                dataString = response;
            }
		return dataString;
	}
//    public static String sendData(String xmlData, String path) throws Exception {
//
//        String dataString = "";
//        HttpURLConnection connection;
//        String url = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
//        URL address_url = new URL(url);
//        connection = (HttpURLConnection) address_url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setConnectTimeout(10000);
//        connection.setReadTimeout(10000);
//        connection.setDoOutput(true);// �Ƿ��������
//        connection.setRequestProperty("Content-type", "text/html; charset=GBK");
//        StringBuffer params = new StringBuffer();
//        // ��������get��ʽһ��
//        params.append(xmlData);
//        byte[] bypes = params.toString().getBytes();
//        connection.getOutputStream().write(bypes);// �������
//        connection.connect();
//        InputStream inStream=connection.getInputStream();
//        BufferedReader br =new BufferedReader(new InputStreamReader(inStream));
//        String response = "";
//        String readLine =null;
//        while((readLine =br.readLine()) !=null){
////response = br.readLine();
//            response = response + readLine;
//        }
//        inStream.close();
//        br.close();
//        connection.disconnect();
//        dataString = response;
//
//        return dataString;
//    }

    /**
     * ���ݳ������ƻ�ȡ��������
     *
     * @return
     */
    public final static Response getListVehDataByName(String vehName,
                                                      String VinFlag, int pageNo) {
        QtSearchVehicleDTO dto = new QtSearchVehicleDTO();
        dto.setPpmc(vehName);
        dto.setVinFlag(VinFlag);
        return getResponse(dto, "003005");
    }

    /**
     * ���ػ��յ�
     *
     * @param userName �û���
     * @param pageno   ҳ��
     * @param pagesize ÿҳ����
     * @param cph
     * @param bah
     * @param time2
     * @param time1
     * @param state
     * @return
     */
    public static JSONObject downloadHSD(String userName, String bah,
                                         String cph, String pageno, String pagesize, String time1,
                                         String time2, String state) {
        // TODO ���ػ��յ�
        String path = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0500");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", userName);
            jsonObject1.put("bah", bah);
            jsonObject1.put("cph", cph);
            jsonObject1.put("begindate", time1);
            jsonObject1.put("enddate", time2);
            jsonObject1.put("pageno", pageno);
            jsonObject1.put("pagesize", pagesize);
            jsonObject1.put("state", state);
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����������󲢻�÷�������
            String msg = sendData(jsonObject.toString(), path);
            // ����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    /**
     * ���ػ��յ�
     *
     * @param userName �û���
     *                 ҳ��
     *                 ÿҳ����
     * @return
     */
    public static JSONObject downloadOneHSD(String userName, String id) {
        // TODO ���ػ��յ�
        String path = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0510");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", userName);
            jsonObject1.put("remId", id);
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����������󲢻�÷�������
            String msg = sendData(jsonObject.toString(), path);
            // ����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    /**
     * ɾ�����յ��е������
     *
     * @param userName
     * @param remId      ���յ�ID
     * @param goodListId ���������ID
     * @return
     */
    public static JSONObject delPart(String userName, String remId,
                                     String goodListId) {
        // TODO Auto-generated method stub
        String path = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0610");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", userName);
            // jsonObject1.put("remId", remId);
            jsonObject1.put("goodListId", goodListId);
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����������󲢻�÷�������
            String msg = sendData(jsonObject.toString(), path);
            // ����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    /**
     * ɾ�����յ��е������
     *
     * @param userName
     * @param remId    ���յ�ID
     *                 ���������ID
     * @return
     */
    public static JSONObject delAllInfo(String userName, String remId) {
        // TODO Auto-generated method stub
        String path = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
        JSONObject dataJson = null;
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0600");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", userName);
            jsonObject1.put("remId", remId);
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����������󲢻�÷�������
            String msg = sendData(jsonObject.toString(), path);
            // ����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

    public static JSONObject delAllPart(String userName, String remId) {
        // TODO Auto-generated method stub
        String path = Constants.URL_UPLOAD + "pjhsAppDataInteractionServlet";
        JSONObject dataJson = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "0620");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("username", userName);
            jsonObject1.put("remId", remId);
            jsonObject.put("data", jsonObject1);
            Log.i("jsonObject", jsonObject.toString());
            // ����������󲢻�÷�������
            String msg = sendData(jsonObject.toString(), path);
            // ����
            dataJson = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataJson;
    }

}
