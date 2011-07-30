package com.wordpong.api.svc.dao;


public class DaoQuestionFactory {
	public static DaoQuestion getDaoQuestion() {
		return new DaoQuestionImpl();
	}
}
