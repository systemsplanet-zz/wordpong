package com.wordpong.api.svc;

public class SvcUserFactory {
	public static SvcUser getUserService() {
		return new SvcUserImpl();
	}
}
