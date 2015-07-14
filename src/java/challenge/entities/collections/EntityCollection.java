/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities.collections;

import challenge.entities.Entity;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Sérgio
 * @param <E>
 */
public interface EntityCollection<E extends Entity> {
    public void add(E entity);
    public List<E> getAll();
    public List<E> getById(Collection<E> collection);
    public void needFetch(E entity);
    public boolean fetch(E entity);
}
