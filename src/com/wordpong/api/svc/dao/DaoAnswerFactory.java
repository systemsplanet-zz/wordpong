package com.wordpong.api.svc.dao;


public class DaoAnswerFactory {
	public static DaoAnswer getDaoAnswer() {
		return new DaoAnswerImpl();
	}
}
