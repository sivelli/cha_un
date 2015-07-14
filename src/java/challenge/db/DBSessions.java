/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db;

/**
 *
 * @author Sérgio
 */
public class DBSessions {
    private static DBSessionFactory factory;

    public static void registerFactory(DBSessionFactory factory) {
        DBSessions.factory = factory;
    }

    public static DBSession create(String database) {
        if (DBSessions.factory != null) {
            return DBSessions.factory.getNew(database);
        }
        else {
            return null;
        }
    }
}
