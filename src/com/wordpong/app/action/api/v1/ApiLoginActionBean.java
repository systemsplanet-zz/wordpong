package com.wordpong.app.action.api.v1;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.util.secure.Encrypt;

@UrlBinding("/rest/v1/login/{email}/{password}")
public class ApiLoginActionBean extends BaseActionBean {
	// public static final String VIEW = "/WEB-INF/jsp/admin/index.jsp";

	private String email;
	private String password;
	private SvcUser _svcUser;

	public ApiLoginActionBean() {
		_svcUser = SvcUserFactory.getUserService();
	}

	// @Before(stages = { LifecycleStage.BindingAndValidation })
	@DefaultHandler
	public Resolution renderJSON() throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		if (email == null || password == null) {
			obj.put("error", "500");
			obj.put("message", "Email and password is required on login URL");
			array.put(obj);
		} else {
			try {
				User user = _svcUser.findByEmail(email);	
				password = Encrypt.hashSha1(password);
				if (!user.getPassword().equals(password)) {
					obj.put("error", "501");
					obj.put("message", "Password does not match user account.");
					array.put(obj);
				} else {
					obj.put("error", "0");
					obj.put("message", "Login successful.");
					array.put(obj);
					getContext().putUserToRequestAndSession(user);
					//DEBUG
					
				    Question q = new Question();
				    q.setTitle("Favorite Dates");
				    List<String> qs = new ArrayList<String>();
				    qs.add("Q1");
				    qs.add("Q2");
				    q.setQuestions(qs);
				    SvcGame svcGame = SvcGameFactory.getGameService();
				    svcGame.saveQuestion(q);
				}
			} catch (WPServiceException e) {
				obj.put("error", "502");
				obj.put("message", "Email address not found.");
				array.put(obj);
			}
		}
		return new StreamingResolution("application/json", array.toString());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Make sure user is authenticated

	// public Resolution authorizeFilter() {
	// return assertAuthenticated();
	// }

}