package com.wordpong.app.action.api.v1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.action.api.ApiResult;
import com.wordpong.app.stripes.AppActionBeanContext;

@UrlBinding("/rest/v1/question")
public class ApiQuestionActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(ApiQuestionActionBean.class.getName());

    public ApiQuestionActionBean() {
    }

    @DefaultHandler
    public Resolution renderJSON() throws JSONException, IOException {
        JSONArray result = new JSONArray();
        AppActionBeanContext c = getContext();
        // Make sure user is authenticated
        User user = c.getUserFromSession();
        if (user == null) {
            result.put(ApiResult.ERR503_LOGIN_REQUIRED);
        } else {
            try {
                // Parse posted date into a string
                InputStream is = getContext().getRequest().getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte buf[] = new byte[1024];
                int letti;
                while ((letti = is.read(buf)) > 0)
                    baos.write(buf, 0, letti);
                String data = new String(baos.toByteArray());
                // parse the string into a JSON object
                JSONObject o = (JSONObject) new JSONTokener(data).nextValue();
                //TODO: Validate the params
                String title = o.getString("title");
                String desc = o.getString("description");
                String visibility = o.getString("visibility");
                String intimacy = o.getString("intimacy");
                String locale = o.getString("locale");
                // Create a question object to add to the database
                Question q = new Question();
                q.setTitle(title);
                q.setDescription(desc);
                q.setVisibility(Integer.parseInt(visibility));
                q.setIntimacyLevel(Integer.parseInt(intimacy));
                q.setUser(user.getKey());
                q.setLocaleString(locale);
                List<String> qs = new ArrayList<String>();
                JSONArray qa = o.getJSONArray("questions");
                for (int i = 0; i < qa.length(); i++) {
                    String s = (String) qa.get(i);
                    qs.add(s);
                }
                q.setQuestions(qs);
                // Persist the questions
                SvcGame svcGame = SvcGameFactory.getGameService();
                //TODO: make sure question title is unique
                svcGame.createQuestion(q);
                result.put(ApiResult.ERR000_SUCCESS);
            } catch (Exception e) {
                JSONObject err = ApiResult.addMessage(ApiResult.ERR504_GENERAL_ERROR, e.getMessage());
                result.put(err);
            }
        }
        log.info("API login result:" + result);
        return new StreamingResolution("application/json", result.toString());
    }
}