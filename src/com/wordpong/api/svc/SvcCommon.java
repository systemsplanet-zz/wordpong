package com.wordpong.api.svc;

public interface SvcCommon {
	// MEMCACHE
	void clearCacheAll();

	// USERS
	boolean isLoggedIn();

	boolean isUserAdmin();

	String getLogoutUrl(String baseUrl);
	
	boolean isDatastoreUp();
}
