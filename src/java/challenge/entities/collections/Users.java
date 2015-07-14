/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities.collections;

import challenge.entities.User;

/**
 *
 * @author Sérgio
 */
public interface Users extends EntityCollection<User> {
    public User get(String email);
    public User get(String email, String password);
}
