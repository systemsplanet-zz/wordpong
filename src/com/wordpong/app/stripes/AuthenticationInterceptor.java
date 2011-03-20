package com.wordpong.app.stripes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

//import com.googlecode.memwords.web.account.CreateAccountActionBean;

import com.wordpong.app.action.ForgotPasswordActionBean;
import com.wordpong.app.action.ForgotPasswordChangeActionBean;
import com.wordpong.app.action.LoginActionBean;
import com.wordpong.app.action.RegisterActionBean;
import com.wordpong.app.util.servlet.AjaxUtils;

/**
 * Stripes interceptor which restores the secret key, if found in a cookie, into the user
 * information (see {@link AppActionBeanContext#getUserEncryptedInfo()}.
 * It then verifies that the user is authenticated.
 * If the user is authenticated, the filter doesn't do anything.
 * If the request is for the index, create account, or login page, it doesn't do
 * anything.
 * If the request is a GET request and not an Ajax request, then a redirect is sent
 * to the login page, which will then redirect to the original request URL.
 * If the request is a POST request and not an Ajax request, then a redirect is sent
 * to the login page, which will then redirect to the index page.
 * If the request is an AJAX request, then a response with the status 403 (Forbidden) is
 * sent, so that the client ajax handler redirects to the login page.
 */
@Intercepts(LifecycleStage.HandlerResolution)
public class AuthenticationInterceptor implements Interceptor {

    /**
     * Intercepts the request, and returns the appropriate resolution.
     * See class documentation for more details
     * @param ctx the execution context
     * @return the appropriate resolution
     */
//    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        AppActionBeanContext actionBeanContext = (AppActionBeanContext) ctx.getActionBeanContext();
        actionBeanContext.getUserEncryptedInfo();
        if (isPermittedWithoutLogin(ctx.getActionBean())) {
            actionBeanContext.getResponse().setHeader("Stripes-Success", "OK");
            return ctx.proceed();
        }
        else if (!actionBeanContext.isAuthenticated()) {
        	// Ajax doesnt redirect to login
            if (AjaxUtils.isAjaxRequest(actionBeanContext.getRequest())) {
                return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN, null);
            }
            addRequestedUrlToContext(actionBeanContext);
            return new ForwardResolution(LoginActionBean.class);
        }
        else {
            return ctx.proceed();
        }
    }

    /**
     * Adds the requested URL to the context if the request is a GET request
     * @param ctx the context to which the requested URL must be added
     */
    private void addRequestedUrlToContext(AppActionBeanContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        if ("GET".equals(request.getMethod())) {
            String url = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                url = url + "?" + request.getQueryString();
            }
            ctx.putUrlToRequest(url);
        }
    }

    /**
     * Determines if login is needed in order to access the given action bean
     * @param actionBean the action bean being requested
     * @return <code>true</code> if the action bean may be accessed without login,
     * <code>false</code> otherwise
     */
    private boolean isPermittedWithoutLogin(ActionBean actionBean) {
        Class<?> actionBeanClass = actionBean.getClass();
        return
        //actionBeanClass.equals(IndexActionBean.class) ||
        actionBeanClass.equals(ForgotPasswordActionBean.class)||
        actionBeanClass.equals(ForgotPasswordChangeActionBean.class)||
        actionBeanClass.equals(LoginActionBean.class)||
                      actionBeanClass.equals(RegisterActionBean.class) 
               //|| actionBeanClass.equals(CreateAccountActionBean.class)
               //|| actionBeanClass.equals(IntegrationTestsActionBean.class)
               //|| actionBeanClass.equals(ScreenshotsActionBean.class)
               //|| actionBeanClass.equals(ToolsActionBean.class)
               ;
    }
}