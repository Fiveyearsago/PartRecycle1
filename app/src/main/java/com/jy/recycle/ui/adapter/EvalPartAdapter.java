package com.jy.recycle.ui.adapter;
/**
 * 定损换件列表适配器
 * @author wl
 *
 */
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.util.SharedData;


public class EvalPartAdapter extends BaseAdapter {
	private int[] colors = new int[] { Color.WHITE, Color.BLUE, Color.RED };
	private List<EvalPartInfo> data ;
	private Context context;
	private boolean[] chkb;
	public EvalPartAdapter(Context context,List<EvalPartInfo> data){
		this.context=context;
		this.data=data;
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
		return data.get(position).get_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EvalPartInfo epart = data.get(position);

		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) vi.inflate(
				R.layout.evalpart_list, parent, false);
		final CheckBox chBox = (CheckBox) layout
				.findViewById(R.id.xl_selBox);
		
		if (epart.getDeleteFlag()!=null&&"1".equals(epart.getDeleteFlag())) {
			chBox.setChecked(true);
			chBox.setClickable(false);
		}
		HashMap<String,String> map=new HashMap<>();
		map.containsKey("1");
		
		
		
		//险别
		TextView insureTerm = (TextView) layout.findViewById(R.id.repair_insure_term);
		insureTerm.setText(epart.getInsureTerm()==null || "".equals(epart.getInsureTerm().trim())? "车损险" : epart.getInsureTerm());
		//零件名称
		TextView partName = (TextView) layout.findViewById(R.id.eval_part_name);
		partName.setText(epart.getPartStandard());
		
		//数量
		TextView lossCount = (TextView) layout.findViewById(R.id.eval_part_loss_count);
		lossCount.setText(String.valueOf(epart.getLossCount()));
		
		//单价
		TextView lossPrice = (TextView) layout.findViewById(R.id.eval_part_loss_price);
		String ckjg = String.valueOf(epart.getSumPrice());
		lossPrice.setText(ckjg);
		//小计
//		TextView total = (TextView) layout.findViewById(R.id.eval_part_loss_sum);
//		total.setText(String.valueOf(epart.getSumPrice()));
		

		// 字体颜色点选：白色，手工添加：蓝色，查勘价格高于价格方案中非零的系统价格：红色
		String selFlag = epart.getSelfConfigFlag();// cursor.getString(cursor.getColumnIndex("HAND_FLAG"));
		String xtjg = String.valueOf(epart.getChgRefPrice());// .getString(cursor.getColumnIndex("SYSTEM_PRICE"));
//		int k = 0;
//		if (selFlag != null && selFlag.equals("1")) {
//			k = 1;
//		}
//		if (SharedData.data().getFloatByStr(ckjg) > SharedData.data().getFloatByStr(xtjg)
//				&& SharedData.data().getFloatByStr(xtjg) > 0) {
//			k = 2;
//		}
//		if (colors[k] != Color.WHITE) {// modified by luzhenglai
//			partName.setTextColor(colors[k]);
//			lossPrice.setTextColor(colors[k]);
//			lossCount.setTextColor(colors[k]);
////			total.setTextColor(colors[k]);
//		}
		
		
		layout.setId(position);
		return layout;
	}
	
	
	
}

