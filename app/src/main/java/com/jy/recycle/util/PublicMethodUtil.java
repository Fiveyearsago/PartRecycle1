package com.jy.recycle.util;

import com.jy.recycle.client.response.HSDInfo;
import com.jy.recycle.client.response.PJInfo;
import com.jy.recycle.client.response.TihuoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/7/4.
 * 公共方法，供不同地方调用
 */
public class PublicMethodUtil {
    public static HSDInfo analysisHSDJson(String jsonString) {
        // TODO 下载回收单数据

        HSDInfo hsdInfo = new HSDInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String remId = jsonObject.getString("remId");
            String vin = jsonObject.getString("vin");
            String cph = jsonObject.getString("cph");
            String bah = jsonObject.getString("bah");
            String carProperty = jsonObject.getString("carProperty");
            String ppmc = jsonObject.getString("ppmc");
            String ppbm = jsonObject.getString("ppbm");
            String vehSeriName = jsonObject.getString("vehSeriName");
            String vehSeriId = jsonObject.getString("vehSeriId");
            String vehCertainId = jsonObject.getString("vehCertainId");
            String vehCertainName = jsonObject.getString("vehCertainName");
            String vehCertainBm = jsonObject.getString("vehCertainBm");
            String assessName = jsonObject.getString("assessName");
            String repairPhone = jsonObject.getString("repairPhone");
            String repairAddress = jsonObject.getString("repairAddress");
            String sfid = jsonObject.getString("sfid");
            String sfmc = jsonObject.getString("sfmc");
            String csid = jsonObject.getString("csid");
            String csmc = jsonObject.getString("csmc");
            String vipRoleDate = jsonObject.getString("vipRoleDate");
            String repairFlag= jsonObject.getString("repairFlag");
            JSONArray array = jsonObject.getJSONArray("list");

            List<PJInfo> pjInfos=new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String goodListId = object.getString("goodListId");
                String ljmc = object.getString("ljmc");
                String ycljh = object.getString("ycljh");
                String appNo = object.getString("appNo");
                String ljsm = object.getString("ljsm");
                String partId = object.getString("partId");
                String carestate = object.getString("Carestate");
                String oldDetail = object.getString("oldDetail");
                String huishouFlag = object.getString("ljsl");//是否已回收状态
                PJInfo pjInfo = new PJInfo(goodListId, ljmc, ycljh, appNo,
                        ljsm, partId, carestate, oldDetail, huishouFlag);
                pjInfos.add(pjInfo);
            }
            JSONArray array1 = jsonObject.getJSONArray("hsslist");
            ArrayList<TihuoInfo> tihuoInfos=new ArrayList<>();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object = array1.getJSONObject(i);
                String hssid = object.getString("hssid");
                String gsmc = object.getString("gsmc");
                String lxr = object.getString("lxr");
                String lxdh = object.getString("lxdh");
                String sjhm = object.getString("sjhm");
                String sfmc1 = object.getString("sfmc");
                String csmc1 = object.getString("csmc");
                String mrbz = object.getString("mrbz");
                TihuoInfo tihuoInfo = new TihuoInfo(hssid, gsmc, lxr, lxdh,
                        sjhm, sfmc1, csmc1, mrbz, true);

                tihuoInfos.add(tihuoInfo);
            }
            hsdInfo = new HSDInfo(remId, vin, cph, bah, carProperty, ppmc,
                    ppbm, vehSeriName, vehCertainName, vehCertainBm,
                    assessName, repairPhone, repairAddress, sfid, sfmc, csid,
                    csmc, vipRoleDate, vehSeriId, vehCertainId,repairFlag, pjInfos,
                    tihuoInfos);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hsdInfo;
    }
}
