package com.wordpong.util.debug;

import com.google.appengine.api.utils.SystemProperty;

public class AppEngine {
	public static boolean isProduction() {
		return SystemProperty.environment.value() != SystemProperty.Environment.Value.Development;
	}
}
