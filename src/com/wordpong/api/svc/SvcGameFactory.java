package com.wordpong.api.svc;

public class SvcGameFactory {
	public static SvcGame getSvcGame() {
		return new SvcGameImpl();
	}
}
