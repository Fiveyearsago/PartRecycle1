package com.jy.recycle.action;

import java.util.List;

import android.content.Context;

import com.jy.recycle.dao.QuestionDetailsDao;
import com.jy.recycle.pojo.QuestionAnswerDetial;

public class QuestionDetailAction extends BaseAction {
	QuestionDetailsDao questionDetailsDao = null;

	public QuestionDetailAction(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.questionDetailsDao = QuestionDetailsDao.getInstance();
	}

	public void insertQuestionAnswerDetial(QuestionAnswerDetial details) {
		questionDetailsDao.addQuestionDetails(details);
	}

	/**
	 * 查找 问题 答案的数据
	 * 
	 * @param eval_id
	 * @return
	 */
	public List<QuestionAnswerDetial> getQuestionData(String eval_id) {
		List<QuestionAnswerDetial> list = questionDetailsDao
				.getQuestionDetails(eval_id);
//		if (list != null && list.size() > 0) {
			return list;
//		} else
//			return null;
	}
	/**
	 * 查找 问题 答案的数据
	 * 
	 * @param eval_id
	 * @return
	 */
	public List<QuestionAnswerDetial> getQuestionDataFromM_VINNO_PHONE(String eval_id) {
		List<QuestionAnswerDetial> list = questionDetailsDao
				.getQuestionDetailsFromM_VINNO_PHONE(eval_id);
		if (list != null && list.size() > 0) {
			return list;
		} else
			return null;
	}

	/**
	 * 查找 图片存放的 地址
	 * 
	 * @param eval_id
	 * @param questionId
	 * @return
	 */
	public List<String> getPicPath(String eval_id, String questionId) {
		List<String> list = questionDetailsDao.getPicPath(eval_id, questionId);

		return list;

	}
	/**
	 * 查找 图片名称
	 * @param eval_id
	 * @param questionId
	 * @return
	 */
	public List<String> getPicName(String eval_id, String questionId) {
		List<String> list = questionDetailsDao.getPicName(eval_id, questionId);
		
		return list;
		
	}
	
	public String getQuestions(String eval_id, String questionId){
		return questionDetailsDao.getQuestion(eval_id, questionId);
	}

	/**
	 * 
	 */
	public void deletePicPath(String path) {
		questionDetailsDao.deletePicPath(path);
	}
}
