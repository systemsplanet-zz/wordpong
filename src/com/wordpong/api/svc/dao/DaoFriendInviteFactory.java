package com.wordpong.api.svc.dao;

public class DaoFriendInviteFactory {
	public static DaoFriendInvite getFriendInviteDao() {
		return new DaoFriendInviteImpl();
	}
}
