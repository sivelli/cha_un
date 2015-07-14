/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sérgio
 */
public class SessionHelper {
    public static HttpSession getSession() {
    	if (FacesContext.getCurrentInstance() != null) {
            return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	}
    	return null;
    }

    public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
 
    public static String getUserName(HttpSession session) {
        if (session == null) {
            session = getSession();
        }
    	if ( session != null ) {
            return (String) session.getAttribute("username");
    	}
    	else {
            return null;
    	}
    }
    public static void invalidate(HttpSession session) {
        if (session == null) {
            session = getSession();
        }
    	if ( session != null ) {
            session.invalidate();
        }
    }
}
