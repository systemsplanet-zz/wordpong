package com.wordpong.api.svc;


public class SvcAdvertiseFactory {
	public static SvcAdvertise getSvcAdvertise() {
		return new SvcAdvertiseImpl();
	}
}
		