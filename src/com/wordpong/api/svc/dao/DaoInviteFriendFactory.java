package com.wordpong.api.svc.dao;


public class DaoInviteFriendFactory {
	public static DaoInviteFriend getFriendInviteDao() {
		return new DaoInviteFriendImpl();
	}
}
