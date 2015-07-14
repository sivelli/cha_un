/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db;

import challenge.entities.collections.Pools;
import challenge.entities.collections.Users;
import java.io.Closeable;

/**
 *
 * @author Sérgio
 */
public interface Fetch<E> {
    public void add(E entity);
    public boolean fetch(DBSession session, E entity);
}
