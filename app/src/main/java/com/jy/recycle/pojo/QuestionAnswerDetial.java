package com.jy.recycle.pojo;

import java.io.Serializable;


public class QuestionAnswerDetial implements Serializable{
	private static final long serialVersionUID = 1L; 
	private String question_id;
	private String eval_id;
	private String pic_name;
	private String pic_path;
	
	private String question;
	private String answer;
	
	public QuestionAnswerDetial(){
		
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}


	public String getEval_id() {
		return eval_id;
	}

	public void setEval_id(String eval_id) {
		this.eval_id = eval_id;
	}

	public String getPic_name() {
		return pic_name;
	}

	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	

}
