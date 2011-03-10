package com.wordpong.app.stripes;

import java.lang.reflect.Method;

import net.sourceforge.stripes.action.ActionBean;

import org.stripesstuff.plugin.security.InstanceBasedSecurityManager;

import com.wordpong.app.action.BaseActionBean;

public class AppSecurityManager extends InstanceBasedSecurityManager {

	@Override
	protected Boolean isUserAuthenticated(ActionBean bean, Method handler) {
		Boolean result = false;
		BaseActionBean b = (BaseActionBean) bean;
		if (b != null) {
			AppActionBeanContext c = b.getContext();
			if (c != null) {
				result = c.isAuthenticated();
			}
		}
		return result;
	}

	@Override
	protected Boolean hasRoleName(ActionBean bean, Method handler, String role) {
		boolean result = false;
		BaseActionBean b = ((BaseActionBean) bean);
		if (b != null && role != null) {
			AppActionBeanContext a = b.getContext();
			if (a != null) {
				result = a.hasRole(role);
			}
		}
		return result;
	}
}
