/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.web;

import challenge.config.Config;
import challenge.db.DBSessions;
import challenge.db.mongodb.SessionFactoryMongoDB;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Sérgio
 */
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Config.createInstance(null);
        System.out.println("URI=" + Config.getInstance().getVarToString("db_uri"));
        DBSessions.registerFactory(new SessionFactoryMongoDB(Config.getInstance().getVarToString("db_uri")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
