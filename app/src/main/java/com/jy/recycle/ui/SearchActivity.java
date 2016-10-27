package com.jy.recycle.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.recycle.R;
import com.jy.recycle.adapter.HSDAdapter;
import com.jy.recycle.client.response.Huishoudan;
import com.jy.recycle.client.response.SpinnerItem;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.eval.EvalActivity;
import com.jy.recycle.ui.eval.EvalActivity2;
import com.jy.recycle.ui.eval.EvalActivity3;
import com.jy.recycle.ui.eval.EvalActivity4;
import com.jy.recycle.ui.eval.EvalActivity6;
import com.jy.recycle.ui.eval.EvalActivity7;
import com.jy.recycle.ui.eval.EvalOtherActivity;
import com.jy.recycle.ui.view.pickerview.TimePickerDialog;
import com.jy.recycle.ui.view.pickerview.data.Type;
import com.jy.recycle.ui.view.pickerview.listener.OnDateSetListener;
import com.jy.recycle.util.DialogUtil;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.UnicodeConverter;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnClickListener,
        OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private EditText mEditBah;
    private EditText mEditCph;
    private EditText mEditTime1;
    private EditText mEditTime2;
    private Button mBtnSearch, mBtnSearchAll;
    private SharedData mShare;
    private Spinner mSpinnerState;
    private HSDAdapter mAdapter;
    private List<Huishoudan> mList;
    private ListView mListView;
    private int mVisibleLastIndex = 0;
    private int num = 1;
    private String responseData = "";
    private String requestData = "";
    private long mExitTime = 0;
    private String bxgsgz = "";
    private String sfcp = "";
    private SwipeRefreshLayout mSwipeLayout;
    private TimePickerDialog mDialogYearMonthDay;
    private String sfid="";

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //EvalApplication.getInstance().addActivity(SearchActivity.this);
        mShare = SharedData.data();
        Bundle bundle = getIntent().getExtras();
        responseData = bundle.get("responseData").toString();
        initData();
        findViews();
    }

    private void initData() {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            bxgsgz = jsonObject.getString("bxgsgz");
            sfcp = jsonObject.getString("sfcp");
            sfid = jsonObject.getString("sfid");
            mShare.saveBxgsgz(bxgsgz);
            mShare.saveSfcp(sfcp);
            mShare.saveSfid(sfid);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mList.clear();
        searchAllHsd(num, "0");
    }

    private void findViews() {
        TextView titleback = (TextView) findViewById(R.id.menu_title_back);
        TextView titlename = (TextView) findViewById(R.id.menu_title_name);
        TextView titleMaking = (TextView) findViewById(R.id.eval_btn_outLine);
        titlename.setText("查询");
        titleMaking.setText("制作");
        titleback.setOnClickListener(this);
        titleMaking.setOnClickListener(this);
        mEditBah = (EditText) findViewById(R.id.textBah);
        mEditCph = (EditText) findViewById(R.id.textCph);
        mEditTime1 = (EditText) findViewById(R.id.textTime1);
        mEditTime2 = (EditText) findViewById(R.id.textTime2);
        mBtnSearch = (Button) findViewById(R.id.btnSearch);
        mBtnSearchAll = (Button) findViewById(R.id.btnSearchALL);
        mBtnSearch.setOnClickListener(this);
        mBtnSearchAll.setOnClickListener(this);
        mSpinnerState = (Spinner) findViewById(R.id.spinnerState);
        initSpinnerData();
        mListView = (ListView) findViewById(R.id.listviewHsd);
        mList = new ArrayList<Huishoudan>();
        mAdapter = new HSDAdapter(mList, getApplicationContext(), responseData
        );

        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
        mEditTime1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setCyclic(false)
//                        .setMinMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(12)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                mEditTime1.setText(text);
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
            }
        });
        mEditTime2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setCyclic(false)
//                        .setMinMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(12)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                mEditTime2.setText(text);
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
            }
        });
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
//		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//				android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(200);
//		mSwipeLayout.setProgressBackgroundColor(R.color.red);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
    }

    public String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        String year = "", month = "", day = "";
        if (data != null) {
            Bundle bundle = data.getExtras();
            year = bundle.getString("year");
            month = bundle.getString("month");
            day = bundle.getString("day");
        }
        switch (requestCode) {
            case 1:
                if (!year.equals(""))
                    mEditTime1.setText(year + "-" + month + "-" + day);
                else {
                    mEditTime1.setText("");
                }
                break;
            case 2:
                if (!year.equals(""))
                    mEditTime2.setText(year + "-" + month + "-" + day);
                else {
                    mEditTime2.setText("");
                }
                break;

            default:
                break;
        }
    }

    private void initSpinnerData() {
        SpinnerItem item0 = new SpinnerItem("1000", "暂存");
        SpinnerItem item1 = new SpinnerItem("1003", "供货");
        SpinnerItem item2 = new SpinnerItem("", "全部");
        List<SpinnerItem> spinnerList = new ArrayList<SpinnerItem>();
        spinnerList.add(item2);
        spinnerList.add(item0);
        spinnerList.add(item1);

        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(
                this.getApplicationContext(),
                R.layout.simple_spinner_item_blcak, spinnerList);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_black);
        mSpinnerState.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_title_back:
                finish();
                break;
            case R.id.eval_btn_outLine:
                Intent intent = null;
//			bxgsgz="6";
                if (bxgsgz.equals("1")) {
                    intent = new Intent(SearchActivity.this, EvalActivity.class);
                } else if (bxgsgz.equals("2")) {
                    intent = new Intent(SearchActivity.this, EvalActivity2.class);
                } else if (bxgsgz.equals("3")) {
                    intent = new Intent(SearchActivity.this, EvalActivity3.class);
                } else if (bxgsgz.equals("5")) {
                    intent = new Intent(SearchActivity.this, EvalActivity4.class);
                } else if (bxgsgz.equals("6")) {
                    intent = new Intent(SearchActivity.this, EvalActivity6.class);
                } else if (bxgsgz.equals("7")) {
                    intent = new Intent(SearchActivity.this, EvalActivity7.class);
                } else {
                    intent = new Intent(SearchActivity.this, EvalOtherActivity.class);
                }
                intent.putExtra("responseData", responseData);
                intent.putExtra("state", "");
                // EvalLossInfoAction action=new
                // EvalLossInfoAction(SearchActivity.this);
                // action.deleteAllEvalInfo();
                startActivity(intent);
                break;
            case R.id.btnSearch:
                String time1 = mEditTime1.getText().toString().trim()
                        .replace("-", "");
                String time2 = mEditTime2.getText().toString().trim()
                        .replace("-", "");

                if (!time1.equals("") && !time2.equals("")
                        && time1.compareTo(time2) > 0) {

                    DialogUtil.showPosDialog(SearchActivity.this, "截止日期不能小于起始日期", 0, new DialogUtil.DialogCallBack() {
                        @Override
                        public void response(int flag) {

                        }
                    });
                } else {
                    mList.clear();
                    num = 1;
                    searchAllHsd(num, "0");
                }
                break;
            case R.id.btnSearchALL:
                mEditTime1.setText("");
                mEditTime2.setText("");
                mEditBah.setText("");
                mEditCph.setText("");
                // mSpinnerState.setSelection(-1);
                mList.clear();
                num = 1;
                searchAllHsd(num, "0");
                break;
            default:
                break;
        }

    }

    private void searchAllHsd(int num, final String flag) {
        final String pageno = String.valueOf(num);
        final String pagesize = "10";

        final String cph = UnicodeConverter.toEncodedUnicode(mEditCph.getText()
                .toString().trim(), true);
        final String bah = mEditBah.getText().toString().trim();
        // final String state=mSpinnerState.getSelectedItem().toString();
        SpinnerItem sItem = (SpinnerItem) mSpinnerState.getSelectedItem();
        final String state = sItem.getKey();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String time1 = mEditTime1.getText().toString().trim();
        String time2 = mEditTime2.getText().toString().trim();

        try {

            if (!time1.equals("")) {
                Date date = sdf.parse(time1);
                time1 = sdf.format(date);
            } else {
                time1 = "";
            }
            if (!time2.equals("")) {
                Date date1 = sdf.parse(time2);
                time2 = sdf.format(date1);
            } else {
                time2 = "";
            }
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        final String beginTime = time1.replace("-", "");
        final String endTime = time2.replace("-", "");
        new AsyncTask<Void, Void, JSONObject>() {
            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                if (flag.equals("0"))
                    progressDialog = ProgressDialog.show(SearchActivity.this,
                            "请稍候", "正在加载中...");
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                JSONObject response = ServerApiManager.downloadHSD(
                        mShare.getUserName(), bah, cph, pageno, pagesize,
                        beginTime, endTime, state);
                return response;
            }

            @Override
            public void onPostExecute(JSONObject dataJson) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                if (flag.equals("1")) {
                    mSwipeLayout.setRefreshing(false);
                }
                if (dataJson != null) {
                    try {
                        String responseFlag = dataJson.getString("flag");
                        String responseData = dataJson.getString("data");

                        if ("0500".equals(responseFlag)) {
                            JSONObject jsonObject = new JSONObject(responseData);
                            String responseCode = jsonObject.getString("code");
                            String responseMessage = jsonObject
                                    .getString("message");

//							Log.i("responseCode:", responseCode);
                            if ("0501".equals(responseCode)) {
                                analysisJson(dataJson.toString());
                            } else {
                                Toast.makeText(SearchActivity.this,
                                        responseMessage, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//                    Toast.makeText(SearchActivity.this, "",
//                            Toast.LENGTH_LONG).show();
                }
            }

        }.execute();
    }

    private void analysisJson(String dataJson) {
        // Log.i("dataJson", dataJson);
        try {
            JSONObject jsonObject = new JSONObject(dataJson)
                    .getJSONObject("data");
            JSONArray array = jsonObject.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String id = object.getString("id");
                String bah = object.getString("bah");
                String cph = object.getString("cph");
                String car = object.getString("carProperty");
                String state = object.getString("state");
                String secondFlag = object.getString("secondFlag");
                String sureRecycle = object.getString("sureRecycle");
//                String sureRecycle = "";
                Huishoudan huishoudan = new Huishoudan(id, bah, cph, car, state, secondFlag,sureRecycle);
                mList.add(huishoudan);
            }
            // Log.i("size", mList.size()+"");
            mAdapter.notifyDataSetChanged();
            // mListView.setAdapter(mAdapter);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        int itemsLastIndex = mAdapter.getCount() - 1;
        int lastIndex = itemsLastIndex;
        // Log.i("lastIndex", lastIndex+""+" "+mVisibleLastIndex);
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && mVisibleLastIndex == lastIndex) {
            searchAllHsd(++num, "0");
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        mVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;
        if (false) {
//            Toast.makeText(this, "!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                // Intent intent = new
                // Intent(EvalActivity.this,EvalTestActivity.class);
                // startActivity(intent);
                EvalApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(false);
        searchAllHsd(0, "1");
    }
}
