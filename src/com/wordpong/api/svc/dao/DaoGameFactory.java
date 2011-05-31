package com.wordpong.api.svc.dao;

public class DaoGameFactory {
	public static DaoGame getGameDao() {
		return new DaoGameImpl();
	}
}
