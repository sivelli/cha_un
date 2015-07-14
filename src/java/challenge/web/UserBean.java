    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import challenge.db.DBSession;
import challenge.db.DBSessions;
import challenge.entities.User;
import challenge.entities.collections.Users;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sérgio
 */
@ManagedBean(name="userBean")
@SessionScoped
public class UserBean {
    private String username;
    private String password;
    private User user;
    
    public String getUsername() {
        if (username == null || username.length() == 0) {
            username = SessionHelper.getUserName(null);
        }
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String login() {
        try (DBSession db = DBSessions.create(null)) {
            Users users = db.getUsers();
            user = users.get(username, password);
            if (user == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                if (context != null) {
                    context.addMessage("username", new FacesMessage("Username/Password invalid"));
                }
                return null;
            }
            else {
                return "results.jsf";
            }
        }
        catch (IOException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
                context.addMessage("username", new FacesMessage("Cannot sign in now. Try later"));
            }
            return null;
        }

    }
    public String logout() {
        SessionHelper.invalidate(null);
        return "/index.jsf";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
