package com.wordpong.app.action.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import javax.annotation.security.RolesAllowed;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcCommon;
import com.wordpong.api.svc.SvcCommonFactory;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AdminActionBean extends BaseActionBean {
    public static final String VIEW = "/WEB-INF/jsp/admin/index.jsp";

    private String message;
    private SvcCommon _svcCommon;

    public AdminActionBean() {
        _svcCommon = SvcCommonFactory.getSvcCommon();
    }

    // Make sure user is authenticated
    @Before(stages = { LifecycleStage.BindingAndValidation })
    public Resolution authorizeFilter() {
        return assertAuthenticated();
    }

    @RolesAllowed("admin")
    @DefaultHandler
    public Resolution view() {
        setMessage("Ready");
        return new ForwardResolution(VIEW);
    }

    @RolesAllowed("admin")
    @HandlesEvent("seedQuestions")
    public Resolution seedQuestions() {
        User u = getContext().getUserFromSession();
        SvcGame sg = SvcGameFactory.getSvcGame();
        try {
            sg.seedQuestions(u);
            setMessage("Questions added");
        } catch (WPServiceException e) {
            setMessage("unable to add questions. user:" + u + " `err:" + e.getMessage());
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @RolesAllowed("admin")
    @HandlesEvent("logout")
    public Resolution logout() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            c.putUserToRequestAndSession(null);
            setMessage("WordPong Logout ok");
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @RolesAllowed("admin")
    @HandlesEvent("clearCache")
    public Resolution clearCache() {
        _svcCommon.clearCacheAll();
        setMessage("Cleared Cache");
        return new ForwardResolution(VIEW);
    }

    private String time() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS ");
        fmt.setTimeZone(new SimpleTimeZone(0, ""));
        return fmt.format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = time() + message;
    }

    public String getLogoutUrl() {
        SvcCommon svcCommon = SvcCommonFactory.getSvcCommon();
        // String baseUrl = getContext().getRequest().getRequestURI();
        String result = svcCommon.getLogoutUrl("/");
        return result;
    }

    public String getIsLoggedIn() {
        SvcCommon svcCommon = SvcCommonFactory.getSvcCommon();
        String result = new Boolean(svcCommon.isLoggedIn()).toString();
        return result;
    }

    public String getIsUserAdmin() {
        SvcCommon svcCommon = SvcCommonFactory.getSvcCommon();
        String result = new Boolean(svcCommon.isUserAdmin()).toString();
        return result;
    }

    public String getUser() {
        String result = "none";
        User u = getContext().getUserFromSession();
        if (u != null) {
            return u.toString();
        }
        return result;
    }

}