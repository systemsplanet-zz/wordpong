package com.wordpong.api.svc.dao;


public class DaoInviteGameFactory {
	public static DaoInviteGame getInviteGameDao() {
		return new DaoInviteGameImpl();
	}
}
