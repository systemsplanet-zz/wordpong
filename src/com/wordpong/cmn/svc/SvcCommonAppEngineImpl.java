package com.wordpong.cmn.svc;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserServiceFactory;

public class SvcCommonAppEngineImpl implements SvcCommon {
	SvcCommonAppEngineImpl() {
	}
	@Override
	public void clearCacheAll() {
		MemcacheServiceFactory.getMemcacheService().clearAll();
	}

	public boolean isLoggedIn() {
		return UserServiceFactory.getUserService().isUserLoggedIn();
	}

	public boolean isUserAdmin() {
		return isLoggedIn() && UserServiceFactory.getUserService().isUserAdmin();
	}
	
	public String getLogoutUrl(String baseUrl) {
		return UserServiceFactory.getUserService().createLogoutURL(baseUrl);
	}
}
