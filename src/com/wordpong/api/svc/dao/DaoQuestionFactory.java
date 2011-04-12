package com.wordpong.api.svc.dao;

public class DaoQuestionFactory {
	public static DaoQuestion getQuestionDao() {
		return new DaoQuestionImpl();
	}
}
