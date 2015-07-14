/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import challenge.mail.Teste;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sérgio
 */
@ManagedBean
@ViewScoped
public class ChaView {

    @ManagedProperty(value="#{userBean}")
    private UserBean userBean;
    /**
     * Creates a new instance of View
     */
    public ChaView() {
    }

    @PostConstruct
    public void init() {
    }
    
    public void subscribe() {
        Teste.main(null);
    }
}
