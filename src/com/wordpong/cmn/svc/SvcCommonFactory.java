package com.wordpong.cmn.svc;


public class SvcCommonFactory {
	public static SvcCommon getCommonService() {
		return new SvcCommonAppEngineImpl();
	}
}
		