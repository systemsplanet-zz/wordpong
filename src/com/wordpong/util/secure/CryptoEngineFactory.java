package com.wordpong.util.secure;


public class CryptoEngineFactory {
	
	public static CryptoEngine getCryptoEngine() {
		return new CryptoEngineImpl();
	}
}