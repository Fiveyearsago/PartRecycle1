package com.jy.recycle.ui.eval;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.jy.recycle.R;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.action.QuestionDetailAction;
import com.jy.recycle.client.response.CityInfo;
import com.jy.recycle.client.response.ContentInfo;
import com.jy.recycle.client.response.EvalLossInfo;
import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.client.response.HSDInfo;
import com.jy.recycle.client.response.PJInfo;
import com.jy.recycle.client.response.ProvinceInfo;
import com.jy.recycle.client.response.TihuoInfo;
import com.jy.recycle.dao.EvalLossInfoDao;
import com.jy.recycle.dao.EvalPartDao;
import com.jy.recycle.pojo.QuestionAnswerDetial;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.EvalApplication;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.adapter.CityAdapter;
import com.jy.recycle.ui.adapter.ImageAdapter;
import com.jy.recycle.ui.adapter.ProvinceAdapter;
import com.jy.recycle.ui.eval.dx.EvalVehiclActivity;
import com.jy.recycle.ui.eval.part.EvalPartActivity;
import com.jy.recycle.ui.eval.photo.PhotoPicActivity;
import com.jy.recycle.ui.eval.pick.PickActivity;
import com.jy.recycle.ui.view.MyGridView;
import com.jy.recycle.ui.view.MyListView;
import com.jy.recycle.ui.view.SlipButton;
import com.jy.recycle.util.DialogUtil;
import com.jy.recycle.util.GetLocation;
import com.jy.recycle.util.ImageUtils;
import com.jy.recycle.util.NUtils;
import com.jy.recycle.util.NUtils.Callback;
import com.jy.recycle.util.PublicMethodUtil;
import com.jy.recycle.util.PublicStrings;
import com.jy.recycle.util.QRCodeUtil;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.TimestampTool;
import com.jy.recycle.util.UnicodeConverter;
import com.jy.recycle.util.ValidateUtil;
import com.jy.recycle.util.ZBarUtil;
import com.jy.recycle.util.mutiphotochoser.constant.Constant;
import com.jy.recycle.util.mutiphotochoser.utils.DisplayUtils;
import com.jy.recycle.zxing.UploadPicActivity;
import com.jy.recycle.zxing.saomiao.MipcaActivityCapture;
import com.jy.zbar.ZBarCaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * 大地回收主页面
 *
 * @author Administrator
 */
public class EvalActivity4 extends JyBaseActivity implements DialogUtil.DialogCallBack {
    private final static int REQUEST_MAIN_EVAL_INFO = 0;
    private Context context;
    private SharedData share;
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID};
    private EvalLossInfoAction evalLossInfoAction;
    private EvalLossInfoDao evalLossInfoDao = new EvalLossInfoDao();
    private EvalPartDao evalPartAction;
    private EvalLossInfo evalLossInfo;
    private String newVerUrl;
    private ProgressDialog pBar;

    private String vehCertainId;
    private String partId;
    private String titleFlag;

    // 车型信息
    private TextView tvVehBrandName;
    private TextView tvVehCertainName;

    private ListView partList;

    private Button btnCxing;
    private Button btnPart;
    // 企业类型
    private TextView companyName;
    // 代理品牌
    private TextView actingBand;
    private Button btnBack;

    private boolean go;

    private String zcbm = null;
    private String userCode = null;
    private long evalId = -1;
    private double unitManHourPrice = 10;
    private int iCount = 0;
    private static final int NEXT = 0x10001;
    private ProgressDialog progressDialog = null;
    private EditText ed;
    private Dialog inputDialog;
    private String inputMark;
    private TextView tvVehCheXingName;
    private RadioGroup typeRadioGroup;
    private RadioButton typesanzhe;
    private RadioButton typebiaodi;
    private RadioButton radioRepairYes;
    private RadioButton radioRepairNO;
    protected String danwei_flag = "0";
    private TextView saoMiaoTextView;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private final static int SCANNIN_PHOTO_CODE = 2;
    private QuestionDetailAction detailAction;
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int TIHUO_REQUEST_CODE = 4;

    private MyGridView gridView;
    private List<String> list_path;
    private ImageAdapter adapter;
    private EditText carNo;
    private EditText person;
    private EditText personTel;
    private EditText personAdd;
    private EditText reginstNo3;
    private EditText reginstNo2;
    private EditText reginstNo1;

    private EditText carText;
    private Button contentPersonTxt;
    private SlipButton guanZhu;
    protected String address = "";
    protected String address1 = "";
    private Button personAddressButton;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String mProvince = "", mCity = "";
    private Spinner mProvinceSpinner, mCitySpinner;
    private String tihuoJson = "";// 提货商Json数据
    private List<ProvinceInfo> provinceInfos;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private List<CityInfo> cityInfos;
    private List<TihuoInfo> tihuoInfos;
    private String vipRoleDate = "";
    private String pjbm = "";
    private String tihuoString = "";// 回收商名称
    private TextView mTextTihuoshang;
    private List<TihuoInfo> tInfos;
    private String mHsdString = "";
    private List<PJInfo> pjInfos;// 配件实体类
    private List<TihuoInfo> tihuohsd;// 提货商实体类
    private HSDInfo mHsdInfo = new HSDInfo();
    private String mState = "";// 回收单状态
    private String remId = "";// 回收单ID
    private ArrayList<EvalPartInfo> evalPartList = new ArrayList<>();
    private Button mbtnDelAll;
    private EvalPartAdapter partAdp;
    private QuestionDetailAction action;
    private String ljmc = "";
    private final static int REQUEST_PICK_PHOTO = 6;
    private Handler mHandler;
    private String startTime="";

    /**
     * 打开定损主信息页面的请求代码
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eval_main4);
        context = this;
        GetLocation.getLoc(this, new GetLocation.LocationCallBack() {
            @Override
            public void locationResponse(String address, String province, String city) {
                address1 =address;
                mProvince=province;
                mCity=city;
            }
        });
        action = new QuestionDetailAction(context);
        pjInfos = new ArrayList<>();
        tihuohsd = new ArrayList<TihuoInfo>();
        EvalApplication.getInstance().addActivity(EvalActivity4.this);
        initBaseData();
        Bundle mBbundle = getIntent().getExtras();
        // responseData
        tihuoJson = mBbundle.getString("responseData");
        mState = mBbundle.getString("state");
        SharedData.data().saveState(mState);
        // bxgsgz=SharedData.data().getBxgsgz();
        String sfcp = SharedData.data().getSfcp();
        findViews();
        getCityInfo();// 获取城市信息
        analysisTihuo(tihuoJson);
        if (!mState.equals("")) {
            remId = mBbundle.getString("remId");
        }
        if (mState.equals("1000")) {
            mHsdString = mBbundle.getString("hsdInfo");
            mHsdInfo= PublicMethodUtil.analysisHSDJson(mHsdString);
            pjInfos=mHsdInfo.getPjInfos();
            bindHsdData();
        } else if (mState.equals("1003")) {
            mHsdString = mBbundle.getString("hsdInfo");
            mHsdInfo= PublicMethodUtil.analysisHSDJson(mHsdString);
            pjInfos=mHsdInfo.getPjInfos();
            tihuohsd= mHsdInfo.getTihuoInfos();
            disableViews();
            bindHsdData();

        }
        Log.i("mHsdString", mHsdString);
        if (mState.equals("")) {
            evalId = evalLossInfoAction.getEvalState();
            if (evalId == -1) {
                evalId = evalLossInfoAction.initInsertEvalInfo();
                carText.setText(sfcp);
            }
        }
        // 下载配件图片并保存到数据库中
        if (!mState.equals("")) {
            analysisPicJson(mHsdString);
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        saoMiaoTextView.setText(msg.obj.toString());
                        break;
                }
            }
        };
    }

    private void analysisPicJson(String jsonString) {
        // TODO 下载配件图片
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray array = jsonObject.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                final String partId = object.getString("partId");
                final String ljmc = object.getString("ljmc");

                JSONArray arrayPic = object.getJSONArray("piclist");
                for (int j = 0; j < arrayPic.length(); j++) {
                    JSONObject objectPic = arrayPic.getJSONObject(j);
                    String url = objectPic.getString("url");
                    Log.i("url:::", url);
                    NUtils.path = Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            + File.separator
                            + context.getString(R.string.dir)
                            + context.getString(R.string.img_dir)
                            + "/"
                            + evalId + "/";
                    NUtils.get(url, new Callback() {

                        @Override
                        public void response(String url, byte[] bytes,
                                             String evalId) {
                            // TODO Auto-generated method stub
                            try {
                                String picPath = NUtils.saveImg(url, bytes,
                                        evalId);
                                // 把图片路径存到数据库
                                QuestionAnswerDetial details = new QuestionAnswerDetial();
                                details.setEval_id(String.valueOf(evalId));
                                details.setQuestion_id(partId);
                                details.setPic_name(ljmc);
                                details.setPic_path(picPath);
                                action.insertQuestionAnswerDetial(details);
                                Log.i("url:", evalId + "：" + partId);
                                partAdp.notifyDataSetChanged();
                                partList.setAdapter(partAdp);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }, evalId + "");
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void initBaseData() {
        context = this;
        share = SharedData.data();
        this.evalLossInfoAction = new EvalLossInfoAction(this);
        evalPartAction = EvalPartDao.getInstance();
        detailAction = new QuestionDetailAction(this);
    }
    public void loadSpinnerProvince(String province){
        for (int i = 0; i < provinceInfos.size(); i++) {
            // Log.i("mProvince2", pInfo.getSfmc()+" "+mProvince2);
            if (provinceInfos.get(i).getSfmc().contains(province)) {
                Log.i("address", i + "");
                mProvinceSpinner.setSelection(i, true);
                Log.i("位置", mProvinceSpinner.getSelectedItemPosition() + "");
                return;
            }
        }

    }
    /**
     * 处理结果
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x11:
                switch (resultCode) {
                    case RESULT_OK:
                        // 完成
                        String selectAddress=data.getStringExtra("selectAddress");
                        String selectedProvince=selectAddress.substring(0,2);
                        loadSpinnerProvince(selectedProvince);
                        personAdd.setText(selectAddress);
                        break;

                }
                break;
            // 定损主信息页面的返回结果
            case REQUEST_MAIN_EVAL_INFO:
                switch (resultCode) {
                    case RESULT_OK:
                        // 完成
                        btnCxing.setEnabled(true);
                        btnPart.setEnabled(true);
                        break;
                    case RESULT_CANCELED:
                        // 取消
                        break;

                }
                break;
            case PublicStrings.REQUEST_PICK_ERWEIMA:
                if (resultCode == RESULT_OK) {
                    final ArrayList<String> images = data
                            .getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
//                    QRCodeUtil.getQRString(images, context, new QRCodeUtil.QRCodeCallBack() {
//                        @Override
//                        public void response(String recode) {
//                            Message message=new Message();
//                            message.what=2;
//                            message.obj=recode;
//                            mHandler.sendMessage(message);
//                        }
//                    });
                    ZBarUtil.getZBarString(images, context, new ZBarUtil.QRCodeCallBack() {
                        @Override
                        public void response(String recode) {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = recode;
                            mHandler.sendMessage(message);
                        }
                    });
                }
                break;
            case REQUEST_PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> images = data
                            .getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
                    String PATH = Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            + File.separator
                            + context.getString(R.string.dir)
                            + context.getString(R.string.img_dir) + "/";
                    ImageUtils.makeDirs(PATH + evalId);
                    for (int i = 0; i < images.size(); i++) {
                        String picName = (TimestampTool.getStrDateTime() + String.valueOf(i)).concat("_")
                                .concat(partId).concat(".jpg");
                        String picPath = PATH.concat(String.valueOf(evalId))
                                .concat("/").concat(picName);
                        int imgType = ImageUtils.MAX_PIXELS;
                        String cString = carText.getText().toString()
                                + carNo.getText().toString();
                        Bitmap bitmap = BitmapFactory.decodeFile(images.get(i));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        String turnFlag = "0";
                        ImageUtils.compressBitmap1(baos.toByteArray(), ljmc,
                                address1, picPath, imgType, turnFlag, cString);
                        QuestionAnswerDetial details = new QuestionAnswerDetial();
                        details.setEval_id(String.valueOf(evalId));
                        details.setQuestion_id(partId);
                        details.setPic_name(ljmc);
                        details.setPic_path(picPath);
                        action.insertQuestionAnswerDetial(details);
                    }

                    list_path = detailAction.getPicPath(String.valueOf(evalId),
                            partId);
                    adapter = new ImageAdapter(context, list_path);
                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    gridView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            String path = list_path.get(position);

                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            int degree = ImageUtils.readPictureDegree(path);
                            bitmap = ImageUtils.rotaingImageView(degree, bitmap);

                            showImageDialog(bitmap, path, partId, gridView);
                        }
                    });

                }
                break;
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    saoMiaoTextView.setText(bundle.getString("result"));
                    if (!"".equals(bundle.getString("result"))
                            && evalPartAction.getExistsErWeiMa(evalId, partId,
                            bundle.getString("result"))) {
                        DialogUtil.showPosDialog(context, "已扫描过此零件，不能进行二次扫描!", PublicStrings.REQUEST_CODE_QRCODE_REPEAT, this);
                    }
                }
                break;
            case SCANNIN_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    partId = data.getExtras().getString("question_id");
                    list_path = detailAction.getPicPath(String.valueOf(evalId),
                            partId);
                    adapter = new ImageAdapter(context, list_path);

                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    gridView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            String path = list_path.get(position);

                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            int degree = ImageUtils.readPictureDegree(path);
                            bitmap = ImageUtils.rotaingImageView(degree, bitmap);

                            showImageDialog(bitmap, path, partId, gridView);
                        }
                    });
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    ContentResolver resolver = context.getContentResolver();
                    // 获取手机联系人
                    Cursor phoneCursor2 = resolver.query(contactData,
                            PHONES_PROJECTION, null, null, null);
                    ContentInfo contentInfo;

                    contentInfo = getPhone(phoneCursor2);
                    if (contentInfo != null) {
                        person.setText(contentInfo.getContentPer());
                        personTel.setText((contentInfo.getNumber())
                                .replace(" ", "").replace("-", ""));
                        // Log.i("(contentInfo.getNumber()).trim()",(contentInfo.getNumber()).replace("
                        // ",
                        // ""));
                    }
                }
                break;
            case TIHUO_REQUEST_CODE:
                // 标题栏返回事件，会触发setResult()事件
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        vipRoleDate = bundle.getString("time");
                        startTime=bundle.getString("startTime");
                        tihuoInfos = (List<TihuoInfo>) bundle
                                .getSerializable("tihuoList");
                        tInfos = new ArrayList<>();
                        tihuoString = "";
                        boolean first_tag = false;
                        if (tihuoInfos != null && tihuoInfos.size() > 0) {
                            for (int i = 0; i < tihuoInfos.size(); i++) {
                                if (tihuoInfos.get(i).getIsSelect()) {
                                    if (!first_tag) {
                                        tihuoString = tihuoInfos.get(i).getGsmc();
                                        first_tag = true;
                                    }
                                    tInfos.add(tihuoInfos.get(i));
                                }
                            }
                        }
                        if (!tihuoString.equals("")) {
                            mTextTihuoshang.setText(tihuoString);
                        } else {
                            mTextTihuoshang.setText("未选择");
                        }
                    }
                }
                break;
        }

    }

    public ContentInfo getPhone(Cursor phoneCursor2) {
        ContentInfo contentInfo = new ContentInfo();
        if (phoneCursor2 != null) {
            while (phoneCursor2.moveToNext()) {

                // 得到手机号码
                String phoneNumber = phoneCursor2
                        .getString(PHONES_NUMBER_INDEX);
                // // 当手机号码为空的或者为空字段 跳过当前循环
                // if (TextUtils.isEmpty(phoneNumber))
                // continue;
                // 得到联系人名称
                String contactName = phoneCursor2
                        .getString(PHONES_DISPLAY_NAME_INDEX);

                // // 得到联系人ID
                // Long contactid =
                // phoneCursor2.getLong(PHONES_CONTACT_ID_INDEX);
                //
                // // 得到联系人头像ID
                // Long photoid = phoneCursor2.getLong(PHONES_PHOTO_ID_INDEX);

                phoneCursor2.close();
                contentInfo.setContentPer(contactName);
                contentInfo.setNumber(phoneNumber);
                break;
            }
        }
        return contentInfo;

    }

    private void getCityInfo() {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(EvalActivity4.this,
                        "请稍候", "正在加载中...");
            }

            @Override
            protected JSONObject doInBackground(Void... params) {

                return ServerApiManager.getCity(share.data()
                        .getUserName(), "");
            }

            @Override
            public void onPostExecute(JSONObject dataJson) {
                if (dataJson != null) {
                    // Log.i("CityJson", dataJson.toString());
                    analysisJson(dataJson.toString());
                    // 绑定城市信息
                    if (!mState.equals("")) {
                        loadSpinnerData(mHsdInfo.getSfmc(), mHsdInfo.getCsmc(),
                                true);
                    }
                    progressDialog.dismiss();
                }
            }
        }.execute();
    }

    public List<TihuoInfo> analysisTihuo(String json) {
        try {
            JSONArray array = new JSONObject(json).getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String hssid = object.getString("hssid");
                String gsmc = object.getString("gsmc");
                String lxr = object.getString("lxr");
                String lxdh = object.getString("lxdh");
                String sjhm = object.getString("sjhm");
                String sfmc = object.getString("sfmc");
                String csmc = object.getString("csmc");
                String mrbz = object.getString("mrbz");
                TihuoInfo tihuoInfo = new TihuoInfo(hssid, gsmc, lxr, lxdh,
                        sjhm, sfmc, csmc, mrbz, false);
                // Log.i("tihuoInfo", tihuoInfo.toString());
                tihuoInfos.add(tihuoInfo);
            }
            // Log.i("tihuoInfos", tihuoInfos.size() + "");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tihuoInfos;
    }

    public List<ProvinceInfo> analysisJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
            JSONArray array = jsonObject.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String sfid = object.getString("sfid");
                String sfmc = object.getString("sfmc");
                JSONArray array1 = object.getJSONArray("city");
                List<CityInfo> cInfos = new ArrayList<>();
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject object1 = array1.getJSONObject(j);
                    String csid = object1.getString("csid");
                    String csmc = object1.getString("csmc");
                    String sfbm = object1.getString("sfbm");
                    String csbm = object1.getString("csbm");
                    CityInfo cityInfo = new CityInfo(csid, csmc, sfbm, csbm);
                    cInfos.add(cityInfo);
                    // Log.i("cityInfo",cityInfo.toString());
                }
                ProvinceInfo provinceInfo = new ProvinceInfo(sfid, sfmc, cInfos);
                // Log.i("provinceInfo",provinceInfo.toString());
                provinceInfos.add(provinceInfo);
            }
            // Log.i("provinceInfos", provinceInfos.size() + "");
            List<CityInfo> ciInfos = new ArrayList<>();
            ciInfos.add(new CityInfo("-1", "请选择", "", ""));
            provinceInfos.add(0, new ProvinceInfo("-1", "请选择", ciInfos));
            provinceAdapter.notifyDataSetChanged();
            /*
			 * cityAdapter.notifyDataSetChanged(); if(cityAdapter.isEmpty()){
			 * Log.i("cityAdapter", "Empty"); }
			 * mCitySpinner.setAdapter(cityAdapter);
			 */

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return provinceInfos;
    }

    private void findViews() {
        // 导航设置
        tihuoInfos = new ArrayList<>();
        tInfos = new ArrayList<>();
        // TextView titlename = null;
        TextView titlename = (TextView) findViewById(R.id.menu_title_name);
        titlename.setText("配件回收");
        this.mbtnDelAll = (Button) findViewById(R.id.del_hsd);
        if (mState.equals("")) {
            mbtnDelAll.setVisibility(View.GONE);
        }
        mbtnDelAll.setOnClickListener(this);
        this.mTextTihuoshang = (TextView) findViewById(R.id.text_tihuoshang);
        this.tvVehBrandName = (TextView) findViewById(R.id.veh_brand_name);
        this.tvVehCertainName = (TextView) findViewById(R.id.veh_certain_name);
        this.tvVehCheXingName = (TextView) findViewById(R.id.veh_cx_name);
        this.carText = ((EditText) findViewById(R.id.car));
        this.carText.addTextChangedListener(textcarText);
        // this.carText.setText(sfcp);
        this.reginstNo1 = (EditText) findViewById(R.id.loss_no1);
        this.reginstNo2 = (EditText) findViewById(R.id.loss_no2);
        this.reginstNo3 = (EditText) findViewById(R.id.loss_no3);
        reginstNo1.addTextChangedListener(textReport1);
        reginstNo2.addTextChangedListener(textReport2);
        reginstNo3.addTextChangedListener(textReport3);
        this.carNo = (EditText) findViewById(R.id.car_No);
        carNo.addTextChangedListener(textCarNo);
        this.person = (EditText) findViewById(R.id.text_contentPerson);
        person.addTextChangedListener(textPerson);
        this.personTel = (EditText) findViewById(R.id.text_personTel);
        personTel.addTextChangedListener(textPersonTel);
        this.personAdd = (EditText) findViewById(R.id.text_address);
        this.personAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(EvalActivity4.this, SelectAddressActivity.class);
                    intent.putExtra("address", personAdd.getText() + "");
                    startActivityForResult(intent, 0x11);
                }
                return false;
            }
        });
        personAdd.addTextChangedListener(textPersonAdd);
        this.personAddressButton = (Button) findViewById(R.id.addressButton);
        this.mProvinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);// 省份下拉菜单
        this.mCitySpinner = (Spinner) findViewById(R.id.citySpinner);// 城市下拉菜单
        provinceInfos = new ArrayList<>();
        cityInfos = new ArrayList<>();
        provinceAdapter = new ProvinceAdapter(provinceInfos, this);
        mProvinceSpinner.setAdapter(provinceAdapter);
        cityAdapter = new CityAdapter(cityInfos, context);
        mCitySpinner.setAdapter(cityAdapter);
        mProvinceSpinner.setOnItemSelectedListener(mCitySlelectListener);
        mCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO 选择城市
                CityInfo cInfo = cityInfos.get(position);
                pjbm = cInfo.getSfbm() + cInfo.getCsbm();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        this.mTextTihuoshang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 跳转选择提货商页面
                gotoPick();
            }
        });
        personAddressButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 开始定位
                personAdd.setText(address1);
                loadSpinnerData(mProvince, mCity, true);
            }
        });

        typeRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        typesanzhe = (RadioButton) findViewById(R.id.radioYes);
        typebiaodi = (RadioButton) findViewById(R.id.radioNo);
        radioRepairYes = (RadioButton) findViewById(R.id.radioRepairYes);
        radioRepairNO = (RadioButton) findViewById(R.id.radioRepairNo);
        typeRadioGroup.clearCheck();
        ((RadioGroup) findViewById(R.id.radioRepairGroup)).clearCheck();
        this.contentPersonTxt = (Button) findViewById(R.id.contentPerson);
        contentPersonTxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(Phone.CONTENT_TYPE);
                EvalActivity4.this.startActivityForResult(intent, 3);
            }
        });
        partList = (MyListView) findViewById(R.id.eval_partList);
        partList.setDivider(new ColorDrawable(Color.GRAY));
        partList.setDividerHeight(1);
        btnBack = (Button) findViewById(R.id.menu_title_back);
        btnBack.setOnClickListener(this);
        btnCxing = (Button) findViewById(R.id.eval_btn_cxing);
        btnCxing.setOnClickListener(this);
        // 进入零件选择页面
        btnPart = (Button) findViewById(R.id.eval_btn_part);
        btnPart.setOnClickListener(this);
        TextView batchAdd = (TextView) findViewById(R.id.eval_btn_outLine);
        batchAdd.setText("上传");
        batchAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (checkPics()) {
                    List<EvalPartInfo> partList = evalPartAction
                            .getListEvalPart(evalId);
                    if (mState.equals("1003")
                            && pjInfos.size() == partList.size()) {
                        // 供货状态未对信息进行修改 不可上传
                        boolean flag = false;
                        for (PJInfo part : pjInfos) {
                            String erWeima = part.getAppNo();
                            if (!erWeima.equals("")) {
                                flag = true;
                            }

                        }
                        if (flag) {
                            DialogUtil.showPosDialog(context, "未对数据进行修改，无需上传!", PublicStrings.REQUEST_CODE_UPLOAD_NO_COMMIT, EvalActivity4.this);
                        }

                    } else {
                        if (tInfos != null && tInfos.size() == 0) {
                            DialogUtil.showDialog(context, "是否上传数据，数据将为暂存状态!", PublicStrings.REQUEST_CODE_UPLOAD_COMMIT, EvalActivity4.this);
                        } else {
                            Intent i = new Intent(EvalActivity4.this,
                                    UploadPicActivity.class);
                            i.putExtra("evalId", evalId);
                            i.putExtra("state", mState);
                            i.putExtra("content", commitContent());
                            startActivity(i);
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_address:
                //跳转到选择地址界面
                Intent intent=new Intent(this,SelectAddressActivity.class);
                intent.putExtra("address",personAdd.getText()+"");
                startActivityForResult(intent,0x11);
                break;
            case R.id.eval_btn_cxing:// 定型
                gotoVehicle();
                break;
            case R.id.eval_btn_part:// 换件
                gotoPictureSelectionMode(EvalPartActivity.class);
                break;
            case R.id.menu_title_back: // 返回
                DialogUtil.showDialog(context, "返回后，此次修改信息将不会保存！", PublicStrings.REQUEST_CODE_BACK, this);
                break;
            case R.id.del_hsd://整单删除
                DialogUtil.showDialog(context, "确定删除吗？", PublicStrings.REQUEST_CODE_DELETE_HSD, this);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            DialogUtil.showDialog(context, "返回后，此次修改信息将不会保存！", PublicStrings.REQUEST_CODE_BACK, this);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 选择提货商页面
     */
    private void gotoPick() {
        Intent intent = new Intent(context, PickActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tihuoJson", tihuoJson);
        bundle.putSerializable("tihuoList", (Serializable) tihuoInfos);
        bundle.putString("time", vipRoleDate);
        bundle.putString("startTime", startTime);
        bundle.putLong("evalId", evalId);
        intent.putExtras(bundle);
        startActivityForResult(intent, TIHUO_REQUEST_CODE);
    }

    /**
     * 定型页面
     */
    private void gotoVehicle() {
        Intent intent = new Intent(context, EvalVehiclActivity.class);
        intent.putExtra("title_flag", "");
        share.saveTaskNo(String.valueOf(evalId));
        intent.putExtra("evalId", evalId);
        intent.putExtra("state", mState);
        if (!mState.equals("")) {
            intent.putExtra("remId", remId);
        }
        context.startActivity(intent);
    }

    /**
     * 换件
     */
    private void gotoPictureSelectionMode(Class<?> clazz) {
        // 确定车型之后才能进入零件选择页面
        if (vehCertainId != null && !vehCertainId.equals("")) {
            if ("D".equals(vehCertainId)) {
                // 非标准车型没有零件组
                Builder builder = new Builder(context);
                builder.setTitle("提醒").setMessage("非标准车型没有标准件！")
                        .setCancelable(false).setPositiveButton("确定", null)
                        .show();
            } else {
                Intent intent = new Intent(context, clazz);
                intent.putExtra("title_flag", "");
                intent.putExtra("evalId", evalId);
                intent.putExtra("vehCertainId", vehCertainId);
                context.startActivity(intent);
            }
        } else {
            showNotSelectVehicleDialog();
        }
    }

    /**
     * 显示没有选择车型的对话框
     */
    private void showNotSelectVehicleDialog() {
        // 给出提示并进入车型定型页面
        Builder builder = new Builder(context);
        builder.setTitle("选择定型").setMessage("请先进行车型定型,然后再进行零件点选!")
                .setCancelable(false)
                .setPositiveButton("定型", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gotoVehicle();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();// 添加配件后返回到详情页面 要刷新数据
    }

    /**
     * 刷新数据
     */
    private void fillData() {
        EvalLossInfoAction evalLossInfoAction = new EvalLossInfoAction(this);
        evalLossInfo = evalLossInfoAction.getEvalLossInfoByEvalId(evalId);
        if (evalLossInfo != null) {
            // 车型信息
            this.reginstNo1.setText(evalLossInfo.getReportOne());
            // this.reginstNo2.setText(evalLossInfo.getReportTwo());

            if (evalLossInfo.getReportTwo() != null
                    && !"".equals(evalLossInfo.getReportTwo())) {
                this.reginstNo2.setText(evalLossInfo.getReportTwo());
            } else {
                evalLossInfoDao.updateregist2(evalId, reginstNo2.getText()
                        .toString());
            }

            this.reginstNo3.setText(evalLossInfo.getReportThree());
            this.carNo.setText(evalLossInfo.getCarNo());
            // this.carText.setText(evalLossInfo.getCarText());

            if (evalLossInfo.getCarText() != null
                    && !"".equals(evalLossInfo.getCarText())) {
                this.carText.setText(evalLossInfo.getCarText());
            } else {
                evalLossInfoDao.updatercarText(evalId, carText.getText()
                        .toString());
            }

            this.person.setText(evalLossInfo.getPersonName());
            this.personTel.setText(evalLossInfo.getPersonTel());
            this.personAdd.setText(evalLossInfo.getPersonAdd());
            this.tvVehBrandName.setText(evalLossInfo.getVehBrandName());
            if (mState.equals("1003")) {
                this.mTextTihuoshang.setText(evalLossInfo.getTiHuoShang());
            } else {
                this.mTextTihuoshang.setText(tihuoString);
            }
            this.tvVehCertainName.setText(evalLossInfo.getVehSeriName());
            this.tvVehCheXingName.setText(evalLossInfo.getVehCertainName());
            this.vehCertainId = evalLossInfo.getVehCertainId();
            if (evalLossInfo.getCarProperty() != null) {
                if (evalLossInfo.getCarProperty().equals("0")) {
                    typesanzhe.setChecked(true);
                } else if (evalLossInfo.getCarProperty().equals("1")) {
                    typebiaodi.setChecked(true);
                }
            }
            if (evalLossInfo.getRepairFlag() != null) {
                if (evalLossInfo.getRepairFlag().equals("0")) {
                    radioRepairYes.setChecked(true);
                } else if (evalLossInfo.getRepairFlag().equals("1")) {
                    radioRepairNO.setChecked(true);
                }
            }
            // 换件列表
            evalPartList = evalPartAction.getListEvalPart(evalId);

            partAdp = new EvalPartAdapter(context, evalPartList);
            partList.setAdapter(partAdp);
            // if (evalPartList != null && evalPartList.size() != 0) {
            // this.textPartPlan.setText(evalPartList.get(0)
            // .getChgCompSetName());
            // } else {
            // textPartPlan.setText("");
            // }

        }
    }

    /**
     * 弹出对话框修改零件
     *
     * @return
     */
    private void partDialog(EvalPartInfo eInfo) {
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        final EvalPartInfo evalPart = eInfo;
        partId = evalPart.getPartId();
        // final EvalPartInfo evalPart = mState.equals("") ? evalPartAction
        // .getEvalPartById(partId) : eInfo;
        View layout;
        if (mState.equals("1003") && evalPart.getOldDetail().equals("0")) {
            layout = LayoutInflater.from(context).inflate(
                    R.layout.dag_editpart_disable, null);
        } else {
            layout = LayoutInflater.from(context).inflate(
                    R.layout.dag_editpart, null);
            Button selectButton = (Button) layout
                    .findViewById(R.id.select_photo);
            selectButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 相册选取照片
                    // ***改成应用的包名
                    ljmc = evalPart.getPartStandard();
                    partId = evalPart.getPartId();
                    Intent intent = new Intent(
                            "com.jy.recycle.action.CHOSE_PHOTOS");
                    // 指定图片最大选择数
                    intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 10);
                    startActivityForResult(intent, REQUEST_PICK_PHOTO);
                }
            });
            Button mButtonSaomiao = (Button) layout
                    .findViewById(R.id.select_erweima);
            mButtonSaomiao.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 选择照片扫描
                    Intent intent = new Intent("com.jy.recycle.action.CHOSE_PHOTOS");
                    // 指定图片最大选择数
                    intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 1);
                    startActivityForResult(intent, PublicStrings.REQUEST_PICK_ERWEIMA);
                }
            });

        }

        Button mButton = (Button) layout.findViewById(R.id.button1);
        guanZhu = (SlipButton) layout.findViewById(R.id.check_invoiceFlag);
        if (mState.equals("1003") && evalPart.getOldDetail().equals("0")) {
            guanZhu.setEnabled(false);
            guanZhu.setClickable(false);
        }
        // 初始化选择
        if (evalPart.getCareState() != null) {
            guanZhu.setChecked("1".equals(evalPart.getCareState()));
        }
        saoMiaoTextView = (TextView) layout.findViewById(R.id.result);
        saoMiaoTextView.setText(evalPart.getInsureTerm());
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EvalActivity4.this, ZBarCaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        if (!mState.equals("1003")) {
            Button selectButton = (Button) layout
                    .findViewById(R.id.select_photo);
            selectButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 相册选取照片
                    // ***改成应用的包名
                    ljmc = evalPart.getPartStandard();
                    partId = evalPart.getPartId();
                    Intent intent = new Intent(
                            "com.jy.recycle.action.CHOSE_PHOTOS");
                    // 指定图片最大选择数
                    intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 10);
                    startActivityForResult(intent, REQUEST_PICK_PHOTO);
                }
            });
        }
        // 照相按钮
        Button photoButton = (Button) layout.findViewById(R.id.button_photo);
        photoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(EvalActivity4.this, PhotoPicActivity.class);
                i.putExtra("evalId", String.valueOf(evalId));
                Log.i("partId", evalPart.getPartId());
                i.putExtra("questionId", evalPart.getPartId());
                i.putExtra("carNo", evalLossInfo.getCarText()+evalLossInfo.getCarNo());
                i.putExtra("partStandName", evalPart.getPartStandard());
                startActivityForResult(i, SCANNIN_PHOTO_CODE);
            }
        });
        // 显示照片
        gridView = (MyGridView) layout.findViewById(R.id.gridView);

        list_path = detailAction.getPicPath(String.valueOf(evalId),
                evalPart.getPartId());

        if (list_path != null) {
            adapter = new ImageAdapter(context, list_path);
            // gridView = list_gridView.get(i).getGridView();
            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String path = list_path.get(position);

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    int degree = ImageUtils.readPictureDegree(path);
                    bitmap = ImageUtils.rotaingImageView(degree, bitmap);

                    showImageDialog(bitmap, path, evalPart.getPartId(),
                            gridView);
                }
            });
        }

        // 原厂号
        final EditText etOriginalNo = (EditText) layout
                .findViewById(R.id.eval_part_original_id);
        etOriginalNo.setText(evalPart.getOriginalId());
        // 零件名称
        final EditText partStandardTv = (EditText) layout
                .findViewById(R.id.eval_part_original_name);
        partStandardTv.setText(evalPart.getPartStandard());
        Button dialogOk = (Button) layout.findViewById(R.id.dialog_ok);
        Button dialogDel = (Button) layout.findViewById(R.id.dialog_delete);
        Button dialogCancle = (Button) layout.findViewById(R.id.dialog_cancle);
        String selFlag = evalPart.getSelfConfigFlag();
        if ("0".equals(selFlag)) {// 系统件不可以修改名称和原厂零件号
            etOriginalNo.setEnabled(false);
            etOriginalNo.setTextColor(Color.BLACK);
            partStandardTv.setEnabled(false);
            partStandardTv.setTextColor(Color.BLACK);
        } else if ("2".equals(selFlag)) { // 二次点选件不可以修改零件名称
            partStandardTv.setEnabled(false);
        } else { // 自定义件可以修改名称和原厂零件号
            partStandardTv.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.white));
            partStandardTv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    textContentDialog(partStandardTv, "配件名称", partStandardTv
                            .getText().toString());
                }
            });
            etOriginalNo.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.white));
            etOriginalNo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    textContentDialog(etOriginalNo, "原厂编码", etOriginalNo
                            .getText().toString());
                }
            });
        }
        // 备注
        final TextView diaLjbz = (TextView) layout
                .findViewById(R.id.addpart_ljbz);
        final String remark = evalPart.getRemark();

        diaLjbz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textContentDialog(diaLjbz, "  备注信息", diaLjbz.getText()
                        .toString());
            }
        });
        diaLjbz.setText(remark);
        // 修改保存
        dialogOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // TODO Auto-generated method stub
                String saomiaoText = saoMiaoTextView.getText().toString()
                        .trim();

                String ljbz;
                ljbz = diaLjbz.getText().toString();
                Log.i("ljbz", ljbz);
                ljbz = ljbz.replaceAll("\"", "");
                Log.i("ljbz", ljbz);
                EvalPartInfo partInfo = new EvalPartInfo();

                partInfo.set_id(evalPart.get_id());
                partInfo.setChgCompSetCode(evalPart.getChgCompSetCode());
                partInfo.setChgCompSetName(evalPart.getChgCompSetName());
                partInfo.setChgLocPrice(evalPart.getChgLocPrice());
                partInfo.setChgRefPrice(evalPart.getChgRefPrice());
                partInfo.setLocPrice1(evalPart.getLocPrice1());
                partInfo.setLocPrice2(evalPart.getLocPrice2());
                partInfo.setLocPrice3(evalPart.getLocPrice3());
                partInfo.setRefPrice1(evalPart.getRefPrice1());
                partInfo.setRefPrice2(evalPart.getRefPrice2());
                partInfo.setRefPrice3(evalPart.getRefPrice3());
                partInfo.setEvalId(evalPart.getEvalId());
                partInfo.setInsureTermCode(evalPart.getInsureTermCode());
                partInfo.setInsureTerm(evalPart.getInsureTerm());
                partInfo.setJySystemId(evalPart.getJySystemId());
                partInfo.setOriginalId(etOriginalNo.getText().toString());
                partInfo.setOriginalName(evalPart.getOriginalName());
                partInfo.setPartGroupCode(evalPart.getPartGroupCode());
                partInfo.setPartGroupName(evalPart.getPartGroupName());
                partInfo.setPartId(evalPart.getPartId());
                partInfo.setPartStandard(partStandardTv.getText().toString());
                partInfo.setPartStandardCode(evalPart.getPartStandardCode());
                partInfo.setRemark(ljbz);
                partInfo.setRepairSitePrice(evalPart.getRepairSitePrice());
                partInfo.setSelfConfigFlag(evalPart.getSelfConfigFlag());
                partInfo.setSerialNo(evalPart.getSerialNo());
                partInfo.setInsureTerm(saomiaoText);
                partInfo.setCareState(guanZhu.getFlag());
                evalPartAction.updateEvalPart(partInfo);
                fillData();
            }

        });

        // 删除

        dialogDel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (evalPart.getOldDetail().equals("0")) {
                    Log.i("删除配件", remId + "   " + evalPart.getGoodListId());
                    delOnePart(remId, evalPart.getGoodListId());
                }
                dialog.dismiss();
                for (String path : list_path) {
                    File file = new File(path);
                    if (file.exists()) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                    detailAction.deletePicPath(path);
                }
                evalPartAction.delEvalPart(String.valueOf(evalPart.get_id()));
                fillData();
            }
        });

        dialogCancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display dp = getWindowManager().getDefaultDisplay();
        lp.width = dp.getWidth() * 5 / 6;
        window.setAttributes(lp);
        dialog.show();
    }

    private String contentCommit;

    private class OnClickListenerNumber implements
            OnClickListener {

        TextView t = new TextView(context);

        public OnClickListenerNumber(TextView text) {
            t = text;
        }

        @Override
        public void onClick(View v) {
            String str = ed.getText().toString();
            int iSelection = ed.getSelectionEnd();
            int iLen = str.length();
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            switch (v.getId()) {
                case R.id.keyboard_delete:
                    if (editable != null && editable.length() > 0 && iSelection > 0) {
                        editable.delete(iSelection - 1, iSelection);
                    }
                    break;
                case R.id.keyboard_ok:
                    String inputNum = ed.getText().toString();

                    inputDialog.dismiss();
                    t.setText(inputNum);
                    // ed.setSelection(ed.getText().length());
                    break;
                case R.id.keyboard_button1:
                    if (iSelection == iLen) {
                        ed.append("1");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "1"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button2:
                    if (iSelection == iLen) {
                        ed.append("2");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "2"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button3:
                    if (iSelection == iLen) {
                        ed.append("3");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "3"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button4:
                    if (iSelection == iLen) {
                        ed.append("4");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "4"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button5:
                    if (iSelection == iLen) {
                        ed.append("5");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "5"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button6:
                    if (iSelection == iLen) {
                        ed.append("6");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "6"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button7:
                    if (iSelection == iLen) {
                        ed.append("7");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "7"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button8:
                    if (iSelection == iLen) {
                        ed.append("8");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "8"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button9:
                    if (iSelection == iLen) {
                        ed.append("9");
                    } else {
                        ed.setText("");
                        ed.setText(str.substring(0, iSelection) + "9"
                                + str.substring(iSelection,

                                iLen));
                        ed.setSelection(iSelection + 1);
                    }
                    break;
                case R.id.keyboard_button0:
                    if (iSelection != 0) {
                        if (iSelection == iLen) {
                            ed.append("0");
                        } else {
                            ed.setText("");
                            ed.setText(str.substring(0, iSelection) + "0"
                                    + str.substring(iSelection,

                                    iLen));
                            ed.setSelection(iSelection + 1);
                        }
                    }
                    break;
                case R.id.keyboard_button_:
                    if (iSelection == 0) {
                        Boolean isHas_ = false;
                        for (int i = 0; i < str.length(); i++) {
                            String a = str.substring(i, i + 1);
                            if (a.equals("-")) {
                                isHas_ = true;
                            }
                        }
                        if (!isHas_) {
                            ed.setText("");
                            ed.setText(str.substring(0, iSelection)

                                    + "-" + str.substring

                                    (iSelection, iLen));
                            ed.setSelection(iSelection + 1);
                        }
                    }
                    break;
                case R.id.keyboard_dian:
                    if (iSelection != 0) {
                        Boolean isHasdian = false;
                        for (int i = 0; i < str.length(); i++) {
                            String a = str.substring(i, i + 1);
                            if (a.equals(".")) {
                                isHasdian = true;
                            }
                        }
                        if (!isHasdian) {
                            if (!str.substring(iSelection - 1,

                                    iSelection).equals("-")) {
                                ed.setText("");
                                ed.setText(str.substring(0,

                                        iSelection) + "." + str.substring

                                        (iSelection, iLen));
                                ed.setSelection(iSelection + 1);
                            }
                        }
                    }
                    break;
                case R.id.keyboard_left:
                    if (start > 0) {
                        ed.setSelection(start - 1);
                    }
                    break;
                case R.id.keyboard_right:
                    if (start < ed.length()) {
                        ed.setSelection(start + 1);
                    }
                    break;
                default:
                    break;
            }

        }

    }

    protected void showInputDialog(TextView text, String name, String title) {
        // TODO Auto-generated method stub
        inputDialog = new Dialog(this, R.style.DialogStyle);
        View v = LayoutInflater.from(context).inflate(
                R.layout.pop_keyboard_view, null);
        Button closeImg = (Button) v.findViewById(R.id.close_btn);
        closeImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                inputDialog.dismiss();
            }
        });
        inputDialog.setContentView(v);
        OnClickListener l = new OnClickListenerNumber(text);
        TextView tTitle = (TextView) v.findViewById(R.id.dialog_tilte);
        tTitle.setText(title);
        TextView tName = (TextView) v.findViewById(R.id.text_input_dialog);
        tName.setText(name + ":");
        ed = (EditText) v.findViewById(R.id.edit_input_dialog);
        ed.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        // 禁止Edittext弹出软键盘并且使光标正常显示
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(ed, false);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(ed, false);
        } catch (Exception e) {
            // TODO: handle exception
        }
        ed.setText(String.valueOf(text.getText().toString()));
        Button button1 = (Button) v.findViewById(R.id.keyboard_button1);
        Button button2 = (Button) v.findViewById(R.id.keyboard_button2);
        Button button3 = (Button) v.findViewById(R.id.keyboard_button3);
        Button button4 = (Button) v.findViewById(R.id.keyboard_button4);
        Button button5 = (Button) v.findViewById(R.id.keyboard_button5);
        Button button6 = (Button) v.findViewById(R.id.keyboard_button6);
        Button button7 = (Button) v.findViewById(R.id.keyboard_button7);
        Button button8 = (Button) v.findViewById(R.id.keyboard_button8);
        Button button9 = (Button) v.findViewById(R.id.keyboard_button9);
        Button button0 = (Button) v.findViewById(R.id.keyboard_button0);
        Button buttondian = (Button) v.findViewById(R.id.keyboard_dian);
        Button buttonfu = (Button) v.findViewById(R.id.keyboard_button_);
        Button buttonOk = (Button) v.findViewById(R.id.keyboard_ok);
        Button buttonDelete = (Button) v.findViewById(R.id.keyboard_delete);
        Button buttonLeft = (Button) v.findViewById(R.id.keyboard_left);
        Button buttonright = (Button) v.findViewById(R.id.keyboard_right);

        buttondian.setOnClickListener(l);
        buttonfu.setOnClickListener(l);
        button1.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        button4.setOnClickListener(l);
        button5.setOnClickListener(l);
        button6.setOnClickListener(l);
        button7.setOnClickListener(l);
        button8.setOnClickListener(l);
        button9.setOnClickListener(l);
        button0.setOnClickListener(l);
        buttonOk.setOnClickListener(l);
        buttonDelete.setOnClickListener(l);
        buttonLeft.setOnClickListener(l);
        buttonright.setOnClickListener(l);

        Window window = inputDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display dp = getWindowManager().getDefaultDisplay();
        lp.width = dp.getWidth() * 5 / 6;
        window.setAttributes(lp);
        inputDialog.show();

    }

    // 维修备注对话框
    private void textContentDialog(final TextView text, String title,
                                   String content) {
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View layout = LayoutInflater.from(context).inflate(
                R.layout.remark_dialog, null);
        TextView textTitle = (TextView) layout.findViewById(R.id.dialog_tilte);
        textTitle.setText(title);
        Button btnOk = (Button) layout.findViewById(R.id.btn_ok);
        Button closeImg = (Button) layout.findViewById(R.id.dialog_giveUp);
        final EditText txtRemark = (EditText) layout
                .findViewById(R.id.remark_dia);
        txtRemark.setText(content);
        // EditText聚焦
        Editable ea = txtRemark.getText();
        txtRemark.setSelection(ea.length());
        closeImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                inputMark = txtRemark.getText().toString();
                text.setText(inputMark);
                dialog.dismiss();

            }
        });
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display dp = getWindowManager().getDefaultDisplay();
        lp.width = dp.getWidth() * 5 / 6;
        window.setAttributes(lp);
        dialog.show();
    }

    private void showImageDialog(Bitmap bitamp, final String path,
                                 final String questionId, final MyGridView gridView) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.lay_big_image, null);
        Builder builder = new Builder(context);

        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("删除", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (mState.equals("1003")) {
                            Toast.makeText(context, "供货状态下不可删除图片！",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        File file = new File(path);
                        if (file.exists()) {
                            if (file.isFile()) {
                                file.delete();
                            }
                        }

                        detailAction.deletePicPath(path);
                        list_path = detailAction.getPicPath(
                                String.valueOf(evalId), questionId);
                        adapter = new ImageAdapter(context, list_path);

                        gridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });

        ImageView bigImageIv = (ImageView) view.findViewById(R.id.iv_big_image);
        bigImageIv.setImageBitmap(bitamp);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LayoutParams lp = bigImageIv.getLayoutParams();
        if (bitamp.getWidth() < bitamp.getHeight()) {
            lp.width = width * 5 / 6;
            lp.height = height * 5 / 6;
        } else {
            lp.width = bitamp.getWidth() * 2;
            lp.height = bitamp.getHeight() * 2;
        }
        bigImageIv.setLayoutParams(lp);
        builder.show();

    }

    private String commitContent() {
        EvalLossInfo eInfo = evalLossInfoAction.getEvalLossInfoByEvalId(evalId);
        String report = eInfo.getReportOne() + eInfo.getReportTwo()
                + eInfo.getReportThree();
        evalLossInfo.setPlateNo("RDD" + report);
        List<EvalPartInfo> partList = evalPartAction.getListEvalPart(evalId);

        try {
            // jsonObject.put("flag", Constants.REQUEST_SENDTYPE_CHECK);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("remId", remId);
            jsonObject1.put("username", share.getUserName());
            // jsonObject1.put("vin",
            // eInfo.getVinNo() == null ? "" : eInfo.getVinNo());
            jsonObject1.put("vin", "");
            jsonObject1.put(
                    "bah",

                    "RDD" + eInfo.getReportOne() + eInfo.getReportTwo() + eInfo.getReportThree());
            jsonObject1.put("cph", UnicodeConverter.toEncodedUnicode(
                    eInfo
                            .getCarText() + eInfo.getCarNo(), true));
            jsonObject1.put("vehSeriId", "");
            jsonObject1.put("vehSeriBm", "");
            jsonObject1.put(
                    "vehCertainId",
                    eInfo.getVehCertainId() == null ? "" : eInfo
                            .getVehCertainId());
            jsonObject1.put("assessName", UnicodeConverter.toEncodedUnicode(
                    eInfo.getPersonName() == null ? "" : eInfo.getPersonName(),
                    true));
            jsonObject1.put("repairAddress", UnicodeConverter.toEncodedUnicode(
                    eInfo.getPersonAdd() == null ? "" : eInfo.getPersonAdd(),
                    true));
            jsonObject1.put("repairPhone", eInfo.getPersonTel() == null ? ""
                    : eInfo.getPersonTel());
            jsonObject1.put("ppmc", UnicodeConverter.toEncodedUnicode(eInfo
                            .getVehBrandName() == null ? "" : eInfo.getVehBrandName(),
                    true));
            jsonObject1.put("ppbm", eInfo.getVehBrandCode() == null ? ""
                    : eInfo.getVehBrandCode());
            jsonObject1.put("vehSeriName", UnicodeConverter.toEncodedUnicode(
                    eInfo.getVehSeriName() == null ? "" : eInfo
                            .getVehSeriName(), true));
            jsonObject1.put("vehCertainName", UnicodeConverter
                    .toEncodedUnicode(eInfo.getVehCertainName() == null ? ""
                            : eInfo.getVehCertainName(), true));
            // jsonObject1.put("vehCertainBm",
            // UnicodeConverter.toEncodedUnicode(
            // eInfo.getVehCertainName() == null ? "" : eInfo
            // .getVehCertainName(), true));
            jsonObject1.put("vehCertainBm", UnicodeConverter.toEncodedUnicode(
                    eInfo.getVehSeriName() == null ? "" : eInfo
                            .getVehSeriName(), true));
            // jsonObject1.put("carProperty", share.getDanWeiFlag() == null ? ""
            // : share.getDanWeiFlag());
//			jsonObject1.put("carProperty", eInfo.getCarProperty() == null ? ""
//					: eInfo.getCarProperty());
            if (typesanzhe.isChecked()) {
                jsonObject1.put("carProperty", "0");
            } else if (typebiaodi.isChecked()) {
                jsonObject1.put("carProperty", "1");
            } else {
                jsonObject1.put("carProperty", "");
            }
            if (radioRepairYes.isChecked()) {
                jsonObject1.put("repairFlag", "0");
            } else if (radioRepairNO.isChecked()) {
                jsonObject1.put("repairFlag", "1");
            } else {
                jsonObject1.put("repairFlag", "");
            }
            jsonObject1.put("pjbm", pjbm);
            boolean dateFlag = false;
            JSONArray jsonArray = new JSONArray();
            evalPartList = evalPartAction.getListEvalPart(evalId);
            if (partList != null && partList.size() > 0) {
                int j = 0;
                for (int i = 0; i < partList.size(); i++) {
                    EvalPartInfo epart = evalPartList.get(i);
                    // if (!epart.getOldDetail().equals("0")) {
                    if (mState.equals("1003")) {
                        dateFlag = true;
                    }
                    JSONObject jb3 = new JSONObject();
                    jb3.put("partId",
                            epart.getPartId() == null ? "" : epart.getPartId());
                    jb3.put("ljmc", UnicodeConverter.toEncodedUnicode(
                            epart.getPartStandard() == null ? "" : epart
                                    .getPartStandard(), true));
                    jb3.put("ycljh", UnicodeConverter.toEncodedUnicode(
                            epart.getOriginalId() == null ? "" : epart
                                    .getOriginalId(), false));
                    // jb3.put("appNo", UnicodeConverter.toEncodedUnicode(
                    // epart.getInsureTerm() == null ? "" : epart
                    // .getInsureTerm(), true));
                    jb3.put("appNo",
                            epart.getInsureTerm() == null ? "" : epart
                                    .getInsureTerm());
                    jb3.put("ljsm", UnicodeConverter.toEncodedUnicode(
                            epart.getRemark() == null ? "" : epart.getRemark(),
                            true));
                    if (epart.getCareState() == null) {
                        jb3.put("carestate", "");
                    } else {
                        jb3.put("carestate", epart.getCareState()
                                .equals("null") ? "" : epart.getCareState());
                    }

                    jsonArray.put(j, jb3);
                    j++;
                }

                // }
                // String partListJson= jsonArray.toString();
                jsonObject1.put("list", jsonArray);
                // String data= jsonObject1.toString();
                // jsonObject.put("data", jsonObject1);
                // 回收商

            }
            JSONArray jsonArrayHss = new JSONArray();
            if (tInfos != null && tInfos.size() > 0) {
                for (int i = 0; i < tInfos.size(); i++) {
                    TihuoInfo tihuoInfo = tInfos.get(i);
                    JSONObject jObject = new JSONObject();
                    jObject.put("hssid", tihuoInfo.getHssid() == null ? ""
                            : tihuoInfo.getHssid());
                    jObject.put("mrbz", tihuoInfo.getMrbz() == null ? ""
                            : tihuoInfo.getMrbz());
                    jsonArrayHss.put(i, jObject);
                }
            }
            jsonObject1.put("hsslist", jsonArrayHss);
            // vipRoleDate
            if (dateFlag) {
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE, 5);//
                // 把日期往后增加一天.整数往后推,负数往前移动
                date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String currentDate = sdf.format(date); // 当期日期
                jsonObject1.put("vipRoleDate", currentDate.equals("null") ? ""
                        : currentDate);
            } else {
                jsonObject1.put("vipRoleDate", vipRoleDate.equals("null") ? ""
                        : vipRoleDate);
            }
            jsonObject1.put("startRoleDate", startTime.equals("null") ? ""
                    : startTime);
            contentCommit = jsonObject1.toString();
            Log.i("contentCommit", contentCommit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contentCommit;

    }

    class EvalPartAdapter extends BaseAdapter {
        private List<EvalPartInfo> data;
        private Context context;

        public EvalPartAdapter(Context context, List<EvalPartInfo> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public EvalPartInfo getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // return data.get(position).get_id();
            return 0;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final EvalPartInfo epart = data.get(position);

            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout) vi.inflate(
                    R.layout.evalpart_list, parent, false);
            final CheckBox chBox = (CheckBox) layout
                    .findViewById(R.id.xl_selBox);

            if (epart.getDeleteFlag() != null
                    && "1".equals(epart.getDeleteFlag())) {
                chBox.setChecked(true);
                chBox.setClickable(false);
            }
            TextView partName = (TextView) layout
                    .findViewById(R.id.eval_part_name);
            partName.setText(epart.getPartStandard());
            TextView partPhoto = (TextView) layout
                    .findViewById(R.id.eval_part_photo);
            TextView partSaomiao = (TextView) layout
                    .findViewById(R.id.eval_part_saomiao);
            TextView partDel = (TextView) layout
                    .findViewById(R.id.eval_part_del);

            partSaomiao.setVisibility(View.VISIBLE);
            partPhoto.setVisibility(View.VISIBLE);
            partDel.setVisibility(View.GONE);
            TextView huishouFlag = (TextView) layout
                    .findViewById(R.id.eval_part_huishou);
            huishouFlag.setVisibility(View.VISIBLE);
            if (epart.getHuishouFlag() != null && epart.getHuishouFlag().equals("1")) {
                huishouFlag.setText("已收");
            } else {
                huishouFlag.setText("未收");
            }
            if (epart.getInsureTerm() != null
                    && !"".equals(epart.getInsureTerm())) {
                partSaomiao.setText("已扫描");
                partName.setTextColor(Color.BLACK);
                // partSaomiao.setTextColor(Color.RED);
            } else {
                partName.setTextColor(Color.BLACK);
                partSaomiao.setText("未扫描");
                // partSaomiao.setTextColor(Color.BLACK);
            }
            List<String> listphotopath = detailAction.getPicPath(
                    String.valueOf(evalId), epart.getPartId());
            if (listphotopath != null && listphotopath.size() > 0) {
                // if (partPhotoList!=null&&partPhotoList.size()>0) {
                partPhoto.setText("已拍照");
                // partPhoto.setTextColor(Color.RED);
                // partName.setTextColor(getResources().getColor(R.color.chengse));
            } else {
                // partName.setTextColor(Color.BLACK);
                partPhoto.setText("未拍照");
                // partPhoto.setTextColor(Color.BLACK);
            }
            if (epart.getInsureTerm() != null
                    && !"".equals(epart.getInsureTerm())
                    && (listphotopath != null && listphotopath.size() > 0)) {
                partName.setTextColor(Color.parseColor("#FF0000"));
            }
            layout.setId(position);
            partId = epart.getPartId();
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // if (epart.getOldDetail() != null
                    // && epart.getOldDetail().equals("0")) {
                    // } else {
                    // Log.i("position", position + "  " + epart.getPartId());
                    // partDialog(epart);
                    // }
                    partDialog(epart);
                }
            });
            partDel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 删除单个配件
                    final AlertDialog alertDialog = new Builder(
                            EvalActivity4.this).create();
                    alertDialog.show();
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_tishi, null, false);
                    alertDialog.setContentView(view);
                    WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
                    params.width = DisplayUtils.dip2px(300, context);
                    params.height = DisplayUtils.dip2px(140, context);
                    alertDialog.getWindow().setAttributes(params);

                    TextView tv_message = (TextView) view
                            .findViewById(R.id.dialog_text);
                    tv_message.setText("确定删除“" + epart.getPartStandard()
                            + "”吗？");
                    TextView tv_pos = (TextView) view
                            .findViewById(R.id.dialog_pos);
                    TextView tv_nag = (TextView) view
                            .findViewById(R.id.dialog_nag);
                    tv_pos.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.i("position",
                                    position + "  " + epart.getGoodListId());
                            Log.i("position", position + "");
                            delOnePart(remId, epart.getGoodListId());
                            evalPartList.remove(position);
                            evalLossInfoAction.deleteEvalLossInfo(evalId,
                                    partId);

                            partAdp.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    });
                    tv_nag.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            alertDialog.dismiss();
                        }
                    });

                }
            });
            return layout;
        }
    }

    TextWatcher textcarText = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            evalLossInfoDao.updatercarText(evalId, s.toString());
            if (!carText.getText().toString().equals("") && evalLossInfo != null)
                evalLossInfo.setCarText(carText.getText().toString());

        }
    };
    TextWatcher textReport1 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

            evalLossInfoDao.updateregist1(evalId, s.toString());
            evalLossInfo.setReportOne(reginstNo1.getText().toString());

        }
    };

    TextWatcher textReport2 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

            evalLossInfoDao.updateregist2(evalId, s.toString());
            evalLossInfo.setReportTwo(reginstNo2.getText().toString());
        }
    };

    TextWatcher textReport3 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

            evalLossInfoDao.updateregist3(evalId, s.toString());
            evalLossInfo.setReportThree(reginstNo3.getText().toString());
        }
    };

    TextWatcher textCarNo = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            evalLossInfoDao.updateCarNo(evalId, s.toString());
            evalLossInfo.setCarNo(s.toString());

        }
    };

    TextWatcher textPerson = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            evalLossInfoDao.updatePerson(evalId, s.toString());
            evalLossInfo.setPersonName(s.toString());

        }
    };

    TextWatcher textPersonTel = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            evalLossInfoDao.updatePersonTel(evalId, s.toString());
            evalLossInfo.setPersonTel(s.toString());

        }
    };

    TextWatcher textPersonAdd = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            evalLossInfoDao.updateAddress(evalId, s.toString());
            evalLossInfo.setPersonAdd(s.toString());

        }
    };

    private boolean checkPics() {
//		String report = evalLossInfo.getReportOne()
//				+ evalLossInfo.getReportTwo() + evalLossInfo.getReportThree();
//
//		if (report == null || report.equals("")) {
//			Toast.makeText(getApplicationContext(), "报案号不能为空,且报案号必须是18位", 1)
//					.show();
//			return false;
//		}
        if (reginstNo1.getText().length() < 1) {
            Toast.makeText(getApplicationContext(), "报案号长度是1位", Toast.LENGTH_SHORT).show();
            reginstNo1.requestFocus();
            Selection.setSelection((Spannable) reginstNo1.getText(), reginstNo1
                    .getText().length());
            return false;
        }
        if (reginstNo3.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), "报案号长度是7位", Toast.LENGTH_SHORT).show();
            reginstNo3.requestFocus();
            Selection.setSelection((Spannable) reginstNo3.getText(), reginstNo3
                    .getText().length());
            return false;
        }
        String carNoString = carText.getText().toString() + carNo.getText();
        if (carNoString == null || carNoString.equals("")) {
            Toast.makeText(getApplicationContext(), "车牌号 不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidateUtil.isCheNo(carNoString)) {
            Toast.makeText(getApplicationContext(), "车牌号格式不正确 ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tInfos != null && tInfos.size() > 0) {
            String person = evalLossInfo.getPersonName();
            if (person == null || person.equals("")) {
                Toast.makeText(getApplicationContext(), "请选择联系人", Toast.LENGTH_SHORT).show();
                return false;
            }

            String personTel = evalLossInfo.getPersonTel();
            if (personTel == null || personTel.equals("")) {
                Toast.makeText(getApplicationContext(), "请选择联系人", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (!ValidateUtil.isMobileNO(personTel)
                        && !ValidateUtil.isPhoneNO(personTel)) {
                    Toast.makeText(getApplicationContext(), "联系人电话格式不正确", Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            }
            if (pjbm.equals("")) {
                Toast.makeText(getApplicationContext(), "请选择提货城市", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!radioRepairYes.isChecked() && !radioRepairNO.isChecked()) {
                Toast.makeText(getApplicationContext(), "请选择维修资质类型", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!typesanzhe.isChecked() && !typebiaodi.isChecked()) {
                Toast.makeText(getApplicationContext(), "请选择损失类型", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (tvVehBrandName.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "请先选择车型", Toast.LENGTH_SHORT).show();
                return false;
            }

            List<EvalPartInfo> partList = evalPartAction
                    .getListEvalPart(evalId);
            // Log.i("partList", partList.size() + "");
            if (partList.size() == 0) {
                Toast.makeText(getApplicationContext(), "请选择回收配件", Toast.LENGTH_SHORT).show();
                return false;
            }
            for (EvalPartInfo part : partList) {
                String erWeima = part.getInsureTerm();
                String partID = part.getPartId();
                if (erWeima == null || erWeima.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            part.getPartStandard() + "未扫描二维码,请扫描", Toast.LENGTH_SHORT).show();
                    return false;
                }
                list_path = detailAction.getPicPath(String.valueOf(evalId),
                        partID);
                if (list_path == null || list_path.size() == 0) {
                    Toast.makeText(getApplicationContext(),
                            part.getPartStandard() + "未进行拍照，请拍照", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (tInfos != null) {
                if (tInfos.size() > 0) {
                    if (vipRoleDate.equals("") || vipRoleDate.equals("null")) {
                        Toast.makeText(getApplicationContext(), "未选择提货截止日期", Toast.LENGTH_SHORT)
                                .show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // 定位回调接口
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                address = location.getAddrStr();
                mProvince = location.getProvince();
                mCity = location.getCity();

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                address = location.getAddrStr();
                mProvince = location.getProvince();
                mCity = location.getCity();
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                // Log.i("EvalActivity", "GPS定位结果");
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                address = location.getAddrStr();
                mProvince = location.getProvince();
                mCity = location.getCity();
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            if (address != "" && address != null) {
                personAdd.setText(address);
                loadSpinnerData(mProvince, mCity, true);
                mLocationClient.stop();
            }
            ;
        }

    }

    private OnItemSelectedListener mCitySlelectListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            // TODO 选择省份
            Log.i("position", position + "");
            ProvinceInfo provinceInfo = provinceInfos.get(position);
            cityInfos.clear();
            List<CityInfo> cList = provinceInfo.getList();
            cityInfos.addAll(cList);
            cityAdapter.notifyDataSetChanged();
            mCitySpinner.setAdapter(cityAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    public void loadSpinnerData(String mProvince2, String mCity2, boolean flag) {
        if (flag) {
            mProvinceSpinner.setOnItemSelectedListener(null);
            loadSpinnerData(mProvince2, mCity2);
            mProvinceSpinner.setOnItemSelectedListener(mCitySlelectListener);
        } else
            loadSpinnerData(mProvince2, mCity2);
    }

    // 定位后变换省份和城市Spinner中的数据
    public void loadSpinnerData(String mProvince2, String mCity2) {
        // TODO Auto-generated method stub\
        // mProvince2 = "浙江省";
        // mCity2 = "杭州市";
        if (provinceInfos.size() == 0) {
            Log.i("城市信息", "为空");
        }
        ProvinceInfo pInfo = null;
        for (int i = 0; i < provinceInfos.size(); i++) {
            pInfo = provinceInfos.get(i);
            // Log.i("mProvince2", pInfo.getSfmc()+" "+mProvince2);
            if (pInfo.getSfmc().equals(mProvince2)) {
                Log.i("address", i + "");
                mProvinceSpinner.setSelection(i, true);
                Log.i("位置", mProvinceSpinner.getSelectedItemPosition() + "");
                break;
            }
        }

        cityInfos.clear();
        cityInfos.addAll(pInfo.getList());
        cityAdapter.notifyDataSetChanged();
        mCitySpinner.setAdapter(cityAdapter);
        for (int i = 0; i < cityInfos.size(); i++) {
            CityInfo cInfo = cityInfos.get(i);
            // Log.i("mProvince2", pInfo.getSfmc()+" "+mProvince2);
            if (cInfo.getCsmc().equals(mCity2)) {
                Log.i("address1", i + "");
                mCitySpinner.setSelection(i, true);
                break;
            }
        }
    }

    private void delOnePart(final String remId, final String goodListId) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                // progressDialog = ProgressDialog.show(EvalActivity.this,
                // "请稍候。。。", "正在删除中。。");
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                JSONObject response = ServerApiManager.delPart(share.data()
                        .getUserName(), remId, goodListId);

                return response;
            }

            @Override
            public void onPostExecute(JSONObject dataJson) {
                if (dataJson != null) {
                    Log.i("删除配件", dataJson.toString());
                    // /progressDialog.dismiss();
                }
            }
        }.execute();
    }

    private void delAllInfo(final String remId) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                // progressDialog = ProgressDialog.show(EvalActivity.this,
                // "请稍候。。。", "正在删除中。。");
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                JSONObject response = ServerApiManager.delAllInfo(share.data()
                        .getUserName(), remId);

                return response;
            }

            @Override
            public void onPostExecute(JSONObject dataJson) {
                if (dataJson != null) {
                    Log.i("删除整单", dataJson.toString());
                    // /progressDialog.dismiss();
                }
            }
        }.execute();
    }

    public void disableViews() {
        TextView textPass = (TextView) findViewById(R.id.pass);
        textPass.setTextColor(Color.BLACK);
        btnCxing.setEnabled(false);
        reginstNo1.setEnabled(false);
        reginstNo2.setEnabled(false);
        reginstNo3.setEnabled(false);
        carText.setEnabled(false);
        carNo.setEnabled(false);
        person.setEnabled(false);
        personTel.setEnabled(false);
        personAdd.setEnabled(false);
        personAddressButton.setEnabled(false);
        mProvinceSpinner.setEnabled(false);
        mCitySpinner.setEnabled(false);
        mTextTihuoshang.setEnabled(false);
        typeRadioGroup.setEnabled(false);
        contentPersonTxt.setEnabled(false);
        typesanzhe.setEnabled(false);
        typebiaodi.setEnabled(false);
//        radioRepairNO.setEnabled(false);
//        radioRepairYes.setEnabled(false);
        reginstNo1.setFocusable(false);
        reginstNo2.setFocusable(false);
        reginstNo3.setFocusable(false);
        carText.setFocusable(false);
        carNo.setFocusable(false);
        person.setFocusable(false);
        personTel.setFocusable(false);
        personAdd.setFocusable(false);
        personAddressButton.setFocusable(false);
        mTextTihuoshang.setFocusable(false);
        contentPersonTxt.setFocusable(false);
        btnPart.setBackgroundResource(R.drawable.btn_dict_huanjian);
        mbtnDelAll.setVisibility(View.GONE);
        reginstNo1.setTextColor(Color.BLACK);
        reginstNo2.setTextColor(Color.BLACK);
        reginstNo3.setTextColor(Color.BLACK);
        carText.setTextColor(Color.BLACK);
        carNo.setTextColor(Color.BLACK);
        person.setTextColor(Color.BLACK);
        personTel.setTextColor(Color.BLACK);
        personAdd.setTextColor(Color.BLACK);
        personAddressButton.setEnabled(false);
        mProvinceSpinner.setEnabled(false);
        mCitySpinner.setEnabled(false);
        mTextTihuoshang.setTextColor(Color.BLACK);
        typeRadioGroup.setEnabled(false);
        contentPersonTxt.setTextColor(Color.BLACK);
        personAddressButton.setTextColor(Color.BLACK);
    }

    private void bindHsdData() {
        // TODO 控件赋值 下载数据保存到数据库中
        EvalLossInfo eInfo = new EvalLossInfo();
        eInfo.setUserName(share.getUserName());

        if (!mHsdInfo.getBah().equals("null") && !mHsdInfo.getBah().equals("")) {
            try {
                eInfo.setReportOne(mHsdInfo.getBah().substring(3, 4));
                eInfo.setReportTwo(mHsdInfo.getBah().substring(4, 15));
                eInfo.setReportThree(mHsdInfo.getBah().substring(15, 22));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        if (!mHsdInfo.getCph().equals("null") && !mHsdInfo.getCph().equals("")) {
            try {
                eInfo.setCarNo(mHsdInfo.getCph().substring(1,
                        mHsdInfo.getCph().length()));
                eInfo.setCarText(mHsdInfo.getCph().substring(0, 1));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        vipRoleDate = mHsdInfo.getVipRoleDate();
        // eInfo.setVipRoleDate(mHsdInfo.getVipRoleDate());
        eInfo.setPersonName(mHsdInfo.getAssessName());
        eInfo.setPersonTel(mHsdInfo.getRepairPhone());
        eInfo.setPersonAdd(mHsdInfo.getRepairAddress());
        eInfo.setVehCertainId(mHsdInfo.getVehCertainId());
        eInfo.setVehCertainName(mHsdInfo.getVehCertainName());
        eInfo.setVehSeriId(mHsdInfo.getVehSeriId());
        eInfo.setVehSeriName(mHsdInfo.getVehSeriName());
        eInfo.setVehBrandName(mHsdInfo.getPpmc());
        eInfo.setVehBrandCode(mHsdInfo.getPpbm());
        if (mState.equals("1003")) {
            eInfo.setTiHuoShang(tihuohsd.get(0).getGsmc());
            tInfos = tihuohsd;
        }
        eInfo.setCarProperty(mHsdInfo.getCarProperty());
        eInfo.setRepairFlag(mHsdInfo.getRepairFlag());
        eInfo.setHsdState(mState);
        evalId = evalLossInfoAction.insertEvalInfo(eInfo);
        for (int i = 0; i < pjInfos.size(); i++) {
            EvalPartInfo hsdEInfo = new EvalPartInfo();
            hsdEInfo.setPartStandard(pjInfos.get(i).getLjmc());
            hsdEInfo.setOldDetail("0");
            hsdEInfo.setInsureTerm((pjInfos.get(i).getAppNo())
                    .substring((pjInfos.get(i).getAppNo()).lastIndexOf(",") + 1));
            hsdEInfo.setPartId(pjInfos.get(i).getPartId());
            hsdEInfo.setEvalId(evalId);
            hsdEInfo.setOriginalId(pjInfos.get(i).getYcljh());
            hsdEInfo.setRemark(pjInfos.get(i).getLjsm());

            hsdEInfo.setCareState(pjInfos.get(i).getCarestate());
            hsdEInfo.setGoodListId(pjInfos.get(i).getGoodListId());
            hsdEInfo.setHuishouFlag(pjInfos.get(i).getHuishouFlag());
            evalPartAction.insertEvalFits(hsdEInfo);
        }
    }

    @Override
    public void response(int flag) {
        switch (flag) {
            //返回事件
            case PublicStrings.REQUEST_CODE_BACK:
                finish();
                break;
            //整单删除
            case PublicStrings.REQUEST_CODE_DELETE_HSD:
                delAllInfo(remId);
                finish();
                break;
            //上传提交暂存提示
            case PublicStrings.REQUEST_CODE_UPLOAD_COMMIT:
                Intent i = new Intent(EvalActivity4.this,
                        UploadPicActivity.class);
                i.putExtra("evalId", evalId);
                i.putExtra("state", mState);
                i.putExtra("content", commitContent());
                startActivity(i);
                break;
            //二维码重复提示
            case PublicStrings.REQUEST_CODE_QRCODE_REPEAT:
                saoMiaoTextView.setText("");
                break;
            //删除照片提示
            case PublicStrings.REQUEST_CODE_DELETE_PHOTO:
                break;
            //添加配件
            case PublicStrings.REQUEST_CODE_ADD_PART:
                break;
            //删除配件提示
            case PublicStrings.REQUEST_CODE_DELETE_PART:
                break;
            //供货不进行修改无需上传
            case PublicStrings.REQUEST_CODE_UPLOAD_NO_COMMIT:
                break;
        }
    }
}