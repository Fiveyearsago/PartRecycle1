package com.jy.recycle.ui.eval.pick;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.recycle.R;
import com.jy.recycle.client.response.TihuoInfo;
import com.jy.recycle.dao.EvalLossInfoDao;
import com.jy.recycle.ui.EvalTestActivity;
import com.jy.recycle.ui.adapter.TihuoAdapter;
import com.jy.recycle.ui.eval.EvalActivity;
import com.jy.recycle.ui.view.pickerview.TimePickerDialog;
import com.jy.recycle.ui.view.pickerview.data.Type;
import com.jy.recycle.ui.view.pickerview.listener.OnDateSetListener;
import com.jy.recycle.util.SharedData;
import com.umeng.analytics.MobclickAgent;

public class PickActivity extends AppCompatActivity implements OnClickListener {
    private Button mBtnBack;
    private TextView mBtnSelect;
    private int TIHUO_REQUEST_CODE = 4;
    private ListView listView;
    private TihuoAdapter adapter;
    private List<TihuoInfo> tihuoInfos;
    private int count = 0, total = 0;// partList中已经列出的零件的数量
    private Context context = this;
    private SharedData share = new SharedData(context);
    private String tihuoJson = "";// 提货商Json数据
    private TextView mTextTime,mStartTime;// 截止时间
    private Button mSelectTime;// 选择截止时间
    private Button mBtnKuaipei;// 选择截止时间
    private String mYear, mMonth = "", mDay = "";// 保存年月日
    private List<TihuoInfo> tList;
    private String vipRoleDate = "";
    private String startTime="";
    private long evalId;
    public static boolean emptyFlag = true;
    private TimePickerDialog mDialogYearMonthDay,mStartYearMonthDay;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        Bundle bundle = getIntent().getExtras();
        tihuoInfos = new ArrayList<TihuoInfo>();
        // tihuoJson = bundle.getString("tihuoJson");
        tihuoInfos = (List<TihuoInfo>) bundle.getSerializable("tihuoList");
        vipRoleDate = bundle.getString("time");
        startTime= bundle.getString("startTime");
        evalId = bundle.getLong("evalId");
        // Log.i("tihuoJson", tihuoJson);
        findViews();
    }
    public void setInitDate(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        mStartTime.setText(str);
    }
    private void findViews() {
        // TODO 查找控件
        TextView titlename = (TextView) findViewById(R.id.menu_title_name);
        titlename.setText("选择提货商");
        mBtnBack = (Button) findViewById(R.id.menu_title_back);
        mBtnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 返回
                finish();
            }
        });
        mTextTime = (TextView) findViewById(R.id.text_time);
        mTextTime.setOnClickListener(this);
        mStartTime = (TextView) findViewById(R.id.text_start_time);
        mStartTime.setOnClickListener(this);
        if (vipRoleDate != "" && vipRoleDate.length() == 8) {
            mYear = vipRoleDate.substring(0, 4);
            mMonth = vipRoleDate.substring(4, 6);
            mDay = vipRoleDate.substring(6, 8);

            mTextTime.setText(mYear + "-" + mMonth + "-" + mDay);
        }
        setInitDate();
        if (startTime!=null&&startTime != "" && startTime.length() == 8) {
            mStartTime.setText(startTime.substring(0, 4) + "-" + startTime.substring(4, 6) + "-" + startTime.substring(6, 8));
        }
        // tList=new ArrayList<TihuoInfo>();
        if (tihuoInfos != null && tihuoInfos.size() > 0) {
            emptyFlag = true;
            for (int i = 0; i < tihuoInfos.size(); i++) {
                TihuoInfo tihuoInfo = tihuoInfos.get(i);
                if (tihuoInfo.getIsSelect()) {
                    emptyFlag = false;
                }
            }
        }
        mBtnSelect = (TextView) findViewById(R.id.eval_btn_outLine);
        mBtnSelect.setText("确定");
        mBtnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 确认返回
                if (tihuoInfos != null && tihuoInfos.size() > 0) {
                    emptyFlag = true;
                    for (int i = 0; i < tihuoInfos.size(); i++) {
                        TihuoInfo tihuoInfo = tihuoInfos.get(i);
                        if (tihuoInfo.getIsSelect()) {
                            emptyFlag = false;
                        }
                    }
                }
                if (!emptyFlag) {
                    if (mStartTime.getText().equals("")) {
                        Toast.makeText(context, "未选择开始日期", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if (mTextTime.getText().equals("")) {
                        Toast.makeText(context, "未选择截止日期", Toast.LENGTH_SHORT)
                                .show();
                    }else if (mTextTime.getText().toString().compareTo(mStartTime.getText().toString())<0) {
                        Toast.makeText(context, "截止日期不能小于开始日期", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
//                        if (mMonth.length() == 1) {
//                            mMonth = "0" + mMonth;
//                        }
//                        if (mDay.length() == 1) {
//                            mDay = "0" + mDay;
//                        }

                        bundle.putString("time", mTextTime.getText().toString().replace("-",""));
                        bundle.putString("startTime", mStartTime.getText().toString().replace("-",""));

                        bundle.putSerializable("tihuoList",
                                (Serializable) tihuoInfos);
                        updateTihuoshang(tihuoInfos);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    if (mMonth.length() == 1) {
                        mMonth = "0" + mMonth;
                    }
                    if (mDay.length() == 1) {
                        mDay = "0" + mDay;
                    }
                    bundle.putString("time", mYear + mMonth + mDay);
                    bundle.putSerializable("tihuoList",
                            (Serializable) tihuoInfos);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        mBtnKuaipei = (Button) findViewById(R.id.btn_kuaipei);
        mBtnKuaipei.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 选择截止时间
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE, 2);//
                // 把日期往后增加一天.整数往后推,负数往前移动
                date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(date); // 当期日期
//                mYear = currentDate.substring(0, 4);
//                mMonth = currentDate.substring(4, 6);
//                mDay = currentDate.substring(6, 8);
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                mTextTime.setText(currentDate);
//                mTextTime.setText(mYear + "-" + mMonth + "-" + mDay);

            }
        });
        mSelectTime = (Button) findViewById(R.id.btn_select_time);
        mSelectTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 选择截止时间
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择起始时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setCyclic(false)
                        .setMinMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(12)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                mTextTime.setText(text);
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

//				Intent intent = new Intent(PickActivity.this,
//						CalendarActivity.class);
//				intent.putExtra("flag", false);
//				startActivityForResult(intent, TIHUO_REQUEST_CODE);

            }
        });
        listView = (ListView) findViewById(R.id.tihuoListView);

        adapter = new TihuoAdapter(tihuoInfos, PickActivity.this);
        Log.i("tihuoInfos.size()", tihuoInfos.size() + "");
        listView.setAdapter(adapter);

    }

    public String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    protected void updateTihuoshang(List<TihuoInfo> tihuoInfos2) {
        // TODO Auto-generated method stub
        EvalLossInfoDao evalLossInfoDao = new EvalLossInfoDao();
        String tihuoshangName = tihuoInfos2.get(0).getGsmc();
        evalLossInfoDao.updateTihuo(evalId, tihuoshangName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_time:
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择起始时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setCyclic(false)
                        .setMinMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(12)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                mTextTime.setText(text);
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

                break;
            case R.id.text_start_time:
                mStartYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择起始时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setCyclic(false)
                        .setMinMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(12)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                mStartTime.setText(text);
                            }
                        })
                        .build();
                mStartYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

                break;
            default:
                break;
        }
    }

    /**
     * ListView 滚动监听器（动态加载数据）
     *
     * @author iStar, 2011-7-5
     */
    class ListScrollListener implements OnScrollListener {

        private boolean mTopFreeDisplayFoot = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && mTopFreeDisplayFoot) {
                if (count < total) {
                    // new GetMorePartListTask().execute();
                } else {
                }

            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // reach bottom of the ListView
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                mTopFreeDisplayFoot = true;
            } else {
                mTopFreeDisplayFoot = false;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            mYear = bundle.getString("year");
            mMonth = bundle.getString("month");
            mDay = bundle.getString("day");
            mTextTime.setText(mYear + "-" + mMonth + "-" + mDay);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
