package com.jy.recycle.ui.eval.part;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.adapter.DialogTypeListAdapter;
import com.jy.recycle.ui.view.CustomePJZMustSpinner;
import com.jy.recycle.util.CustomDialog;
import com.jy.recycle.util.SpinnerItem;

/**
 * @author Administrator �������������ѡ������,ʵ��������״�ṹ
 */
public class PartGroupSelectActivity extends JyBaseActivity {
    private Context context;

    private ArrayList<SpinnerItem> fljData;
    private List<Map<String, String>> groupData;

    private String vehicelId;
    private boolean forQuery;// ����Ƿ�Ϊ�����ѯ

    private CustomePJZMustSpinner fljSel;

    private TextView groupText;
    private ImageView imagevGroup;
    private CustomDialog groupListDialog;
    private String ljzId;

    private ExpandableListView groupList;
    private ProgressDialog progressDialog;
    private String strLj;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    if (groupData != null && groupData.size() != 0) {
                        groupList = (ExpandableListView) findViewById(R.id.lj_groupList);
                        MyExpandableListAdapter mAdapter = new MyExpandableListAdapter(
                                PartGroupSelectActivity.this, groupData);
                        groupList.setAdapter(mAdapter);
                        groupList
                                .setOnChildClickListener(new OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(
                                            ExpandableListView arg0,
                                            View arg1, int arg2,
                                            int arg3, long arg4) {
                                        // �����ȡֵ���� ���������ݷ�����
                                        String name = ((TextView) arg1)
                                                .getText().toString();
                                        final String id = ((TextView) arg1)
                                                .getContentDescription()
                                                .toString();
                                        Log.i("id", id);
                                        strLj = name + ";" + id;
                                        Intent it = new Intent();
                                        it.putExtra("strLj", strLj);
                                        setResult(RESULT_OK, it);
                                        finish();
                                        return false;
                                    }
                                });
                    } else {
                        Intent it = new Intent();
                        it.putExtra("strLj", strLj);
                        setResult(RESULT_OK, it);
                        finish();
                    }
                    break;
                case 1:
                    if (groupData != null && groupData.size() != 0) {
                        groupList = (ExpandableListView) findViewById(R.id.lj_groupList);
                        MyExpandableListAdapter mAdapter = new MyExpandableListAdapter(
                                PartGroupSelectActivity.this, groupData);
                        groupList.setAdapter(mAdapter);
                        groupList
                                .setOnChildClickListener(new OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(
                                            ExpandableListView arg0,
                                            View arg1, int arg2,
                                            int arg3, long arg4) {
                                        // �����ȡֵ���� ���������ݷ�����
                                        String name = ((TextView) arg1)
                                                .getText().toString();
                                        final String id = ((TextView) arg1)
                                                .getContentDescription()
                                                .toString();
                                        Log.i("id", id);
                                        strLj = name + ";" + id;
//									4028801c0c3834e4010c3834e62a004e
                                        new Thread() {

                                            @Override
                                            public void run() {
                                                groupData = ServerApiManager.getLjGroupData(
                                                        vehicelId, id);// ��ȡ������������б�
                                                handler.sendEmptyMessage(3);
                                            }
                                        }.start();

//									Intent it = new Intent();
//									it.putExtra("strLj", strLj);
//									setResult(RESULT_OK, it);
//									finish();
                                        return false;
                                    }
                                });
                    } else {
                        showDialog("��������ݣ�");
                    }
                    break;
                case 2:
                    if (fljData != null) {
                        ArrayAdapter<SpinnerItem> cxadapter = new ArrayAdapter<SpinnerItem>(
                                PartGroupSelectActivity.this,
                                android.R.layout.simple_spinner_item, fljData);
                        cxadapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        fljSel.setList(fljData);
                        fljSel.setAdapter(cxadapter);
                        fljSel.setOnItemSelectedListener(new OnItemSelectedListener() {
                            // ѡ���������ʱʱ�г��ӷ�����Ϣ
                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int arg2, long arg3) {
                                int pos = fljSel.getSelectedItemPosition();
                                final String ljzId = fljData.get(pos).getID(); // �ڶ��������ID
                                Log.i("ljzId", ljzId);
                                new Thread() {

                                    @Override
                                    public void run() {
                                        groupData = ServerApiManager.getLjGroupData(
                                                vehicelId, ljzId);// ��ȡ������������б�
                                        handler.sendEmptyMessage(1);
                                    }
                                }.start();
//							groupData = ServerApiManager.getLjGroupData(
//									vehicelId, ljzId);// ��ȡ������������б�

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                context);
                        builder.setMessage("�˳��޶�Ӧ��������Զ���������!")
                                .setCancelable(false)
                                .setPositiveButton("ȷ��",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog, int id) {
                                                PartGroupSelectActivity.this
                                                        .finish();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    progressDialog.dismiss();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lj_selpartgroup);
        TextView titleText = (TextView) findViewById(R.id.partgroup_title);
        titleText.setText("ѡ�����");

        context = this;

        fljSel = (CustomePJZMustSpinner) findViewById(R.id.lj_fljSel);

        groupText = (TextView) findViewById(R.id.text_dx_partgroup);
        groupText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // showGroupListDialog();
            }
        });
        imagevGroup = (ImageView) findViewById(R.id.imagevBtn_partgroup_chex);
        imagevGroup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // showGroupListDialog();
            }
        });

        // new GetGroupByVehicleIdTask().execute();

        // ���ѯʹ�ã��ٶ���ʹ��
        forQuery = getIntent().getBooleanExtra("forQuery", false);
        vehicelId = getIntent().getStringExtra("vehicelId");

        progressDialog = ProgressDialog.show(context, "���Ժ�", "���ڲ�ѯ...");
        new Thread() {

            @Override
            public void run() {
                fljData = ServerApiManager.getFljData(vehicelId);// ��ȡ�����������

                handler.sendEmptyMessage(2);
            }
        }.start();
    }


    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private Context mContext = null;
        private List<Map<String, String>> mgroupData;
        private List<Map<String, String>> mchildData;

        public MyExpandableListAdapter(Context context,
                                       List<Map<String, String>> groupData) {
            this.mContext = context;
            this.mgroupData = groupData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mchildData.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getChildrenCount(final int groupPosition) {
            // �ж���û���������ݣ�û�о�ֱ�ӷ��أ�������м���
            // TODO Auto-generated method stub
            final String id = (String) mgroupData.get(groupPosition).get("id");

            new AsyncTask<Void, Void, List<Map<String, String>>>() {
                @Override
                public void onPreExecute() {

                }
                @Override
                protected List<Map<String, String>> doInBackground(Void... params) {
                    mchildData = ServerApiManager.getLjGroupData(vehicelId, id);
                    return mchildData;
                }

                @Override
                public void onPostExecute(List<Map<String, String>> result) {

                }
            }.execute();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mchildData == null) {
                return 0;
            } else {
                return mchildData.size();
            }

        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            TextView text = null;
            if (convertView == null) {
                text = new TextView(mContext);
            } else {
                text = (TextView) convertView;
            }

            String name = "";
            String id = "";
            // ��ȡ�ӽڵ�Ҫ��ʾ������
            if (mchildData != null && childPosition < mchildData.size()) {
                name = (String) mchildData.get(childPosition).get("fzmc");
                id = (String) mchildData.get(childPosition).get("id");
                // �����ı���ͼ���������
                text.setTextSize(16);
                text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                text.setPadding(45, 10, 0, 10);
                text.setTextColor(getResources().getColor(R.color.blueMainText));
                text.setText(name);
                text.setContentDescription(id);
            }
            return text;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mgroupData.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return mgroupData == null ? 0 : mgroupData.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            TextView text = null;
            if (convertView == null) {
                text = new TextView(mContext);
            } else {
                text = (TextView) convertView;
            }
            String name = (String) mgroupData.get(groupPosition).get("fzmc");
            text.setTextSize(16);
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            text.setPadding(70, 10, 0, 10);
            text.setTextColor(getResources().getColor(R.color.black));
            text.setText(name);
            return text;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        /*
         * ����* �жϷ����Ƿ�Ϊ�գ����Ϊ��ʱ����true ����
         */
        public boolean isEmpty() {
            return false;
        }

        /*
         * �����б�ʱҪ����Ķ����������
         */
        public void onGroupCollapsed(int groupPosition) {

        }

        /*
         * չ���б�ʱҪ����Ķ����������
         */
        public void onGroupExpanded(int groupPosition) {
            String id = (String) mgroupData.get(groupPosition).get("id");
            if (mchildData == null || mchildData.size() == 0) {
                String name = (String) mgroupData.get(groupPosition)
                        .get("fzmc");
                String strLj = name + ";" + id;
                Intent intent = new Intent();
                intent.putExtra("strLj", strLj);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * ��ȡ������������б�
     *
     * @author Administrator
     */
    private class GetGroupListByGroupIdTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = null;

        @Override
        public void onPreExecute() {
            progressDialog = ProgressDialog
                    .show(context, "���Ժ�", "���ڼ������������...");
            // int position = seriSel.getSelectedItemPosition();
            if (ljzId == null) {
                ljzId = fljData.get(0).getID();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (ljzId != null && !"".equals(ljzId)) {
                groupData = ServerApiManager.getLjGroupData(vehicelId, ljzId);// ��ȡ������������б�

            }
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            progressDialog.dismiss();
            if (groupData != null) {
                groupList = (ExpandableListView) findViewById(R.id.lj_groupList);
                MyExpandableListAdapter mAdapter = new MyExpandableListAdapter(
                        PartGroupSelectActivity.this, groupData);
                groupList.setAdapter(mAdapter);
                groupList.setOnChildClickListener(new OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView arg0,
                                                View arg1, int arg2, int arg3, long arg4) {
                        // �����ȡֵ���� ���������ݷ�����
                        String name = ((TextView) arg1).getText().toString();
                        String id = ((TextView) arg1).getContentDescription()
                                .toString();
                        String strLj = name + ";" + id;

                        Intent it = new Intent();
                        it.putExtra("strLj", strLj);
                        setResult(RESULT_OK, it);
                        finish();
                        return false;
                    }
                });
            } else {
                showDialog("��������ݣ�");
            }
        }
    }

    /**
     * ��ȡ�����������
     */
    private class GetGroupByVehicleIdTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = null;

        @Override
        public void onPreExecute() {
            progressDialog = ProgressDialog
                    .show(context, "���Ժ�", "���ڼ������������...");
        }

        @Override
        protected Void doInBackground(Void... params) {

            fljData = ServerApiManager.getFljData(vehicelId);// ��ȡ�����������

            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            progressDialog.dismiss();
            if (fljData != null && fljData.size() > 0) {
                groupText.setText(fljData.get(0).getValue());
                new GetGroupListByGroupIdTask().execute();
            }
        }
    }

    private void showGroupListDialog() {
        groupListDialog = new CustomDialog(this, R.layout.dialog_pricefa_type,
                R.style.Dialog);

        TextView titleText = (TextView) groupListDialog
                .findViewById(R.id.text_title);
        titleText.setText("��ѡ�������");
        titleText.setTextSize(16);

        ListView typeListView = (ListView) groupListDialog
                .findViewById(R.id.dialog_typeList);
        if (fljData != null && fljData.size() > 0) {
            DialogTypeListAdapter diaAdp = new DialogTypeListAdapter(context,
                    fljData);
            typeListView.setAdapter(diaAdp);
        }
        typeListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position >= 0 && fljData != null) {
                    ljzId = fljData.get(position).getID();

                    new GetGroupListByGroupIdTask().execute();
                }
                groupListDialog.dismiss();
            }
        });
        groupListDialog.show();
    }
}
