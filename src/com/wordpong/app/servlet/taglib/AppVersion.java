package com.wordpong.app.servlet.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.google.appengine.api.utils.SystemProperty;

public class AppVersion implements Tag {

    private PageContext pageContext;
    private Tag parent;

    public AppVersion() { 
    }

    public int doStartTag() {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("v" + SystemProperty.applicationVersion.get());
        } catch (java.io.IOException ex) {
            throw new JspException("IO Exception");
        }
        return EVAL_PAGE;
    }

    public void release() {
    }

    public void setPageContext(PageContext p) {
        pageContext = p;
    }

    public void setParent(Tag t) {
        parent = t;
    }

    public Tag getParent() {
        return parent;
    }
}
