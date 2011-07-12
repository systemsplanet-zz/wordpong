package com.wordpong.app.action.api.v1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.action.game.GameActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.util.secure.Encrypt;

@UrlBinding("/rest/v1/question")
public class ApiQuestionActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(ApiQuestionActionBean.class.getName());
	// public static final String VIEW = "/WEB-INF/jsp/admin/index.jsp";

	private String question;
	private String title;
	private String description;
	private List<String> questions;
	private SvcUser _svcUser;
	//intimacy
	//visibility
	//locale
	//userEmail
	//private int visibility;
	
	public ApiQuestionActionBean() {
		_svcUser = SvcUserFactory.getUserService();
		
	}

	// @Before(stages = { LifecycleStage.BindingAndValidation })
	@DefaultHandler
	public Resolution renderJSON() throws JSONException, IOException {
		//question = getContext().getResponse().getInputStream().read( byteArray, offset,Len-offset);
		//question = getContext().getRequest().getAttribute(question).toString();
		//JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		
		//getResponse().getOutputStream().write("Hello".getBytes());
		//HttpServletRequest request = this.
		//String content = request.getContentType();
		//getContext().getResponse().getInputStream().read( byteArray, offset,Len-offset)
		//Request request = getContext().getRequest();
		
		//AppActionBeanContext c = getContext();
		//c.getUserFromSession();

		//if(question == null) {
		//	obj.put("error","no question in post");
		//} else {
			//obj = obj.getJSONObject(question);
			
		//	obj.put("question input", question);
		//}
		
		try{ 
			String data=null; 

			InputStream is = getContext().getRequest().getInputStream(); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 

			byte buf[] = new byte[1024]; 
			int letti; 

			while ((letti = is.read(buf)) > 0) 
			baos.write(buf, 0, letti); 

			data = new String(baos.toByteArray()); 
			object = (JSONObject) new JSONTokener(data).nextValue();
			String title = object.getString("title");
			String desc = object.getString("description");

			}catch(Exception e){ 
				log.info("e:" + e.getMessage());
			}
		
		
		//obj.put("json object", "false info");
		//array.put(object);
		return new StreamingResolution("application/json", object.toString());
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	
	// Make sure user is authenticated

	// public Resolution authorizeFilter() {
	// return assertAuthenticated();
	// }

}