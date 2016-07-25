package com.jy.recycle.dao;

import java.util.ArrayList;
import java.util.List;

import com.jy.recycle.pojo.QuestionAnswerDetial;

import android.content.ContentValues;
import android.database.Cursor;

public class QuestionDetailsDao extends BaseDao {
	private final static String TABLE = "M_VINNO_PHONE";
	private final static String TABLE02 = "M_VINNO_QUSTION";
	private static QuestionDetailsDao questionDetailsDao = null;

	public static QuestionDetailsDao getInstance() {
		if (questionDetailsDao == null) {
			questionDetailsDao = new QuestionDetailsDao();
		}
		return questionDetailsDao;
	}

	public void addQuestionDetails(QuestionAnswerDetial details) {
		ContentValues values = new ContentValues();
		values.put("EVAL_ID", details.getEval_id());
		values.put("VIN_QUSTION_ID", details.getQuestion_id());
		values.put("VIN_PIC_NAME", details.getPic_name());
		values.put("VIN_PIC_PATH", details.getPic_path());

		db.insert(TABLE, null, values);
	}

	public List<QuestionAnswerDetial> getQuestionDetails(String eval_id) {
		String[] args = { eval_id };
		QuestionAnswerDetial detial = null;
		List<QuestionAnswerDetial> list = new ArrayList<QuestionAnswerDetial>();
		Cursor c = db.querySql("select * from M_VINNO_QUSTION where EVAL_ID=?",
				args);
		if (c != null) {
			if (c.getCount() > 0) {
				while (c.moveToNext()) {
					detial = new QuestionAnswerDetial();

					detial.setQuestion_id(c.getString(c
							.getColumnIndex("VIN_QUSTION_ID")));
					detial.setQuestion(c.getString(c
							.getColumnIndex("VIN_QUSTION")));
					detial.setAnswer(c.getString(c.getColumnIndex("VIN_ANSWRE")));

					list.add(detial);
				}
			}
			c.close();
		}
		return list;
	}
	
	
	public List<QuestionAnswerDetial> getQuestionDetailsFromM_VINNO_PHONE(String eval_id) {
		String[] args = { eval_id };
		QuestionAnswerDetial detial = null;
		List<QuestionAnswerDetial> list = new ArrayList<QuestionAnswerDetial>();
		Cursor c = db.querySql("select * from M_VINNO_PHONE where EVAL_ID=?",
				args);
		if (c != null) {
			if (c.getCount() > 0) {
				while (c.moveToNext()) {
					detial = new QuestionAnswerDetial();
					
					detial.setQuestion_id(c.getString(c
							.getColumnIndex("VIN_QUSTION_ID")));
					detial.setPic_name(c.getString(c
							.getColumnIndex("VIN_PIC_NAME")));
					
					list.add(detial);
				}
			}
			c.close();
		}
		return list;
	}

	public List<String> getPicPath(String eval_id, String questionId) {
		List<String> list = new ArrayList<String>();
		String[] args = { eval_id, questionId };
		Cursor c = db
				.querySql(
						"select * from M_VINNO_PHONE where EVAL_ID=? and VIN_QUSTION_ID=?",
						args);
		if (c != null) {
			if (c.getCount() > 0) {
				while (c.moveToNext()) {
					String name = c.getString(c.getColumnIndex("VIN_PIC_PATH"));
					list.add(name);
				}
			}
			c.close();
		}
		return list;
	}

	public List<String> getPicName(String eval_id, String questionId) {
		List<String> list = new ArrayList<String>();
		String[] args = { eval_id, questionId };
		Cursor c = db
				.querySql(
						"select * from M_VINNO_PHONE where EVAL_ID=? and VIN_QUSTION_ID=?",
						args);
		if (c != null) {
			if (c.getCount() > 0) {
				while (c.moveToNext()) {
					String name = c.getString(c.getColumnIndex("VIN_PIC_NAME"));
					list.add(name);
				}
			}
			c.close();
		}
		return list;
	}
	public String getQuestion(String eval_id, String questionId) {
		String name=null;
		String[] args = { eval_id, questionId };
		Cursor c = db
				.querySql(
						"select * from M_VINNO_QUSTION where EVAL_ID=? and VIN_QUSTION_ID=?",
						args);
		if (c != null) {
			if (c.getCount() > 0) {
				while (c.moveToNext()) {
					name = c.getString(c.getColumnIndex("VIN_QUSTION"));
				}
			}
			c.close();
		}
		return name;
	}
	
	public void delete(String eval_id){
		String [] arg = { eval_id };
		try {
			db.delete(TABLE, "EVAL_ID=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public void deleteId(long eval_id){
		String [] arg = { String.valueOf(eval_id) };
		try {
			db.delete(TABLE, "EVAL_ID=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void deletePicPath(String path) {
		String[] args = { path };
		db.delete(TABLE, "VIN_PIC_PATH=?", args);
	}
}
