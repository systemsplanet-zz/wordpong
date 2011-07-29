package com.wordpong.app.action.api.v1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.gson.Gson;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.action.api.ApiResult;
import com.wordpong.app.action.api.pojo.QuestionCreation;
import com.wordpong.app.stripes.AppActionBeanContext;

@UrlBinding("/rest/v1/question")
public class ApiQuestionActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(ApiQuestionActionBean.class.getName());

    public ApiQuestionActionBean() {
    }

    @DefaultHandler
    public Resolution renderJSON() throws  IOException {
        List<HashMap<String, String>> result   = new ArrayList<HashMap<String,String>>();
        AppActionBeanContext c = getContext();
        // Make sure user is authenticated
        User user = c.getUserFromSession();
        if (user == null) {
            result.add(ApiResult.ERR503_LOGIN_REQUIRED);
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
                //TODO: Validate the params
                Gson gson = new Gson();
                QuestionCreation qc = gson.fromJson(data, QuestionCreation.class);                
                // Create a question object to add to the database
                Question q = new Question(qc);
                q.setUser(user.getKey());
                // Persist the questions
                SvcGame svcGame = SvcGameFactory.getSvcGame();
                //TODO: make sure question title is unique
                svcGame.createQuestion(q);
                result.add(ApiResult.ERR000_SUCCESS);
            } catch (Exception e) {
                ApiResult err = ApiResult.addMessage(ApiResult.ERR504_GENERAL_ERROR, e.getMessage());
                result.add(err);
            }
        }
        log.info("API login result:" + result);
        return new StreamingResolution("application/json", result.toString());
    }
}