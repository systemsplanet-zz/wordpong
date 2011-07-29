package com.wordpong.api.svc.dao;

public class DaoGameFactory {
	public static DaoGame getDaoGame() {
		return new DaoGameImpl();
	}
}
