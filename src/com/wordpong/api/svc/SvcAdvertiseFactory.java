package com.wordpong.api.svc;


public class SvcAdvertiseFactory {
	public static SvcAdvertise getAdvertiseService() {
		return new SvcAdvertiseImpl();
	}
}
		