package com.wordpong.api.svc.dao;

public class DaoTagQuestionFactory {
	public static DaoTagQuestion getTagQuestionDao() {
		return new DaoTagQuestionImpl();
	}
}
