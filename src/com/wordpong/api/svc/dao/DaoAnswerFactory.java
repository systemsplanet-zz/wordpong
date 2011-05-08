package com.wordpong.api.svc.dao;

public class DaoAnswerFactory {
	public static DaoAnswer getAnswerDao() {
		return new DaoAnswerImpl();
	}
}
