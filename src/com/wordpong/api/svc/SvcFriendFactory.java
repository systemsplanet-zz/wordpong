package com.wordpong.api.svc;

public class SvcFriendFactory {
	public static SvcFriend getFriendService() {
		return new SvcFriendImpl();
	}
}
