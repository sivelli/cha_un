/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sérgio
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.jsf"})
public class AuthFilter implements Filter {
    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see Filter#destroy()
	 */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            String reqURI = req.getRequestURI();
            String username = SessionHelper.getUserName(ses);
            boolean ajaxRedirect = req.getHeader("faces-request") != null && req.getHeader("faces-request").toLowerCase().contains("ajax");
            boolean isLoginPage = reqURI.startsWith(req.getContextPath() + "/index.jsf");
            boolean hasSession = (ses != null && username != null && username.length() > 0);
            if ( hasSession || isLoginPage || reqURI.startsWith(req.getContextPath() + "/javax.faces.resource/") || reqURI.startsWith(req.getContextPath() + "/public/") ) {
            	if (ses != null && hasSession && isLoginPage) {
                    SessionHelper.invalidate(ses);
            	}
                chain.doFilter(request, response);
            }
            else {
            	if (ajaxRedirect) {
                    res.sendError(403);
            	}
            	else {
                    res.sendRedirect(req.getContextPath() + "/index.jsf"); 
            	}
            }
        }
        catch(IOException | ServletException t) {
        }
     }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
}
