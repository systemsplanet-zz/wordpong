package com.wordpong.cmn.svc;


public class SvcCommonFactory {
	public static SvcCommon getSvcCommon() {
		return new SvcCommonAppEngineImpl();
	}
}
		