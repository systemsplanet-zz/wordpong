package com.wordpong.api.svc.dao;

public class DaoFriendRequestFactory {
	public static DaoFriendRequest getFriendRequestDao() {
		return new DaoFriendRequestImpl();
	}
}
