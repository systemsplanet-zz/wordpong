package com.wordpong.api.svc.dao;

public class DaoUserFactory {
	public static DaoUser getDaoUser() {
		return new DaoUserImpl();
	}
}
