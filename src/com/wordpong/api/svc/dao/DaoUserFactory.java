package com.wordpong.api.svc.dao;

public class DaoUserFactory {
	public static DaoUser getUserDao() {
		return new DaoUserImpl();
	}
}
