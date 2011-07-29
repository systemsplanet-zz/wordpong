package com.wordpong.api.svc;

public class SvcUserFactory {
	public static SvcUser getSvcUser() {
		return new SvcUserImpl();
	}
}
