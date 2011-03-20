package com.wordpong.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.SimpleError;

import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.util.servlet.AjaxUtils;
import com.wordpong.app.util.servlet.ServletUtil;

public abstract class BaseActionBean implements ActionBean {
//	private static final Logger log = Logger.getLogger(BaseActionBean.class
//			.getName());

	private AppActionBeanContext context;

	public BaseActionBean() {
		// log.info("BaseActionBean created.");
	}

	@Before
	public void before() {
		//log.fine("Before event");
	}

	@After
	public void after() {
		//log.fine("After event");
	}

	public AppActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (AppActionBeanContext) context;
	}

	public void addGlobalActionError(String errorMessage) {
		getContext().getValidationErrors().addGlobalError(
				new SimpleError(errorMessage));
	}

	public void addGlobalActionMessage(String message) {
		context.getMessages().add(new SimpleMessage(message));
	}

	protected Object getSessionAttribute(String attributeName) {
		return ServletUtil
				.sessionGet(context.getRequest(), attributeName, null);
	}

	protected void setSessionAttribute(String attributeName, Object value) {
		ServletUtil.sessionSet(context.getRequest(), attributeName, value);
	}

	public Resolution assertAuthenticated() {
		if (getContext().isAuthenticated() == false) {
			if (AjaxUtils.isAjaxRequest(getContext().getRequest())) {
				return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN,
						null);
			}
			return new RedirectResolution(LoginActionBean.class);
		}
		return null;
	}

	protected LocalizableMessage getLocalizableMessage(String key,
			Object... parameters) {
		return new LocalizableMessage(getClass().getName() + "." + key,
				parameters);
	}

	// errorResolution(HttpServletResponse.SC_FORBIDDEN,
	// "this action should not be called in production")
	protected Resolution errorResolution(int reason, String msg) {
		return new ErrorResolution(reason, msg);
	}

	@SuppressWarnings("unchecked")
	public String getLastUrl() {
		HttpServletRequest req = getContext().getRequest();
		StringBuilder sb = new StringBuilder();

		// Start with the URI and the path
		String uri = (String) req
				.getAttribute("javax.servlet.forward.request_uri");
		String path = (String) req
				.getAttribute("javax.servlet.forward.path_info");
		if (uri == null) {
			uri = req.getRequestURI();
			path = req.getPathInfo();
		}
		sb.append(uri);
		if (path != null) {
			sb.append(path);
		}

		// Now the request parameters
		sb.append('?');
		Map<String, String[]> map = new HashMap<String, String[]>(req
				.getParameterMap());

		// Remove previous locale parameter, if present.
		map.remove("locale");

		// Append the parameters to the URL
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			for (String value : values) {
				sb.append(key).append('=').append(value).append('&');
			}
		}
		// Remove the last '&'
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

}
