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
public interface DBSessionFactory {
    public DBSession getNew(String database);
}
