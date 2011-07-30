package com.wordpong.app.stripes;

import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.svc.SvcCommonFactory;
import com.wordpong.app.servlet.util.AjaxUtils;
import com.wordpong.app.servlet.util.ServletUtil;

/**
 * Stripes interceptor applied to all requests returns a common header so the
 * client can detect problem
 */
@Intercepts(LifecycleStage.ActionBeanResolution)
public class BackendAvailableInterceptor implements Interceptor {

    /**
     * Intercepts the request, and returns the appropriate resolution. See class
     * documentation for more details
     * 
     * @param ctx
     *            the execution context
     * @return the appropriate resolution
     */
    // @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        AppActionBeanContext actionBeanContext = (AppActionBeanContext) ctx.getActionBeanContext();
        boolean isDuplicate = actionBeanContext.getResponse().containsHeader(ServletUtil.REPLY_HEADER_OK_KEY);
        if (isDuplicate == false) {
            boolean isDatastoreUp = SvcCommonFactory.getSvcCommon().isDatastoreUp();
            //System.out.println("Backend Intercept up:" + isDatastoreUp);
            if (isDatastoreUp == false) {
                actionBeanContext.getResponse().setHeader(ServletUtil.REPLY_HEADER_ERR_KEY,
                        ServletUtil.REPLY_HEADER_ERR_VAL);
                if (AjaxUtils.isAjaxRequest(actionBeanContext.getRequest())) {
                    return new ErrorResolution(HttpServletResponse.SC_SERVICE_UNAVAILABLE, null);
                } else {
                    return new ForwardResolution("/err/infrastructure_unavailable.html");
                }
            }
        }
        return ctx.proceed();
    }

}