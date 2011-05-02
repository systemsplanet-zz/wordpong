package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.User;
import com.wordpong.api.pojo.QuestionView;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerAddActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(AnswerAddActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_questionList.jsp";

    private SvcGame _svcGame;

    private User user;

    public AnswerAddActionBean() {
        _svcGame = SvcGameFactory.getGameService();
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(AnswerListActionBean.class);
    }


    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }


    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        AppActionBeanContext c = getContext();
        if (c != null) {
            // Todo: validate email list
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }


    public List<QuestionView> getQuestionList() {
        user = getContext().getUserFromSession();
        
        List<QuestionView> result = new ArrayList<QuestionView>();
        QuestionView a = new QuestionView();
        a.setId("id1");
        a.setQuestionInfo("Favorites: Colors");
        result.add(a);
        a = new QuestionView();
        a.setId("id2");
        a.setQuestionInfo("Favorites: Dates");
        result.add(a);
            //TODO populate using _svcGame
        return result;
    }

}
