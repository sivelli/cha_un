/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities.collections;

import challenge.entities.Pool;

/**
 *
 * @author Sérgio
 */
public interface Pools extends EntityCollection<Pool>{
    public Pool getLastActive();
}
