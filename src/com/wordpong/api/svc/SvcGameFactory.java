package com.wordpong.api.svc;

public class SvcGameFactory {
	public static SvcGame getGameService() {
		SvcGameImpl svcGameImpl = new SvcGameImpl();
		SvcGame svcGame = svcGameImpl;
		return svcGame;
	}
}
