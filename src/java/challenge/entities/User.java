/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities;

/**
 *
 * @author Sérgio
 */
public class User extends Entity {
    private String id;
    private String email;
    private String password;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void load(Entity user) {
        if (user.getClass() == User.class) {
            setEmail(((User)user).getEmail());
            setPassword(((User)user).getPassword());
            setById(false);
        }
        else {
            throw new EntityException("Cannot load " + user.getClass() + " in " + User.class);
        }
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + '}';
    }

}
