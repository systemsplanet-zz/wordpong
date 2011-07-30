package com.wordpong.api.svc;




public class SvcCommonFactory {
	public static SvcCommon getSvcCommon() {
		return new SvcCommonImpl();
	}
}
		