package com.wordpong.cmn.svc;

import java.util.logging.Logger;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityState;
import com.google.appengine.api.capabilities.CapabilityStatus;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserServiceFactory;

public class SvcCommonAppEngineImpl implements SvcCommon {
    private static final Logger log = Logger.getLogger(SvcCommonAppEngineImpl.class.getName());

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

    @Override
    public boolean isDatastoreUp() {
        CapabilitiesService cs = CapabilitiesServiceFactory.getCapabilitiesService();
        CapabilityState state = cs.getStatus(Capability.DATASTORE_WRITE);
        CapabilityStatus status = state.getStatus();
        log.info("isDatastoreUp:" + status);
        return status == CapabilityStatus.ENABLED;
    }
}
