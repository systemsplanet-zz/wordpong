package com.wordpong.api.svc.dao;

public class DaoTagQuestionFactory {
	public static DaoTagQuestion getDaoTagQuestion() {
		return new DaoTagQuestionImpl();
	}
}
