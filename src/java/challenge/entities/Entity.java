/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.entities;

import challenge.db.DBSession;

/**
 *
 * @author Sérgio
 */
public abstract class Entity {
    transient private boolean byId;
    transient private DBSession session;

    public Entity() {
        this.byId = false;
    }

    public boolean isById() {
        return byId;
    }
    
    protected void setById(boolean byId) {
        this.byId = byId;
    }

    public void needFetch(DBSession session) {
        this.byId = true;
        this.session = session;
        this.session.needFetch(this);
    }

    public void fetch() throws EntityException {
        if (byId && this.session != null) {
            this.session.fetch(this);
        }
        if (byId) {
            throw new EntityException("Incomplete Entity");
        }
    }
    
    public void load(Entity entity) {
        setById(false);
    }
    
    public abstract Object getId();

    @Override
    public int hashCode() {
        return (getId() == null? 0 : getId().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if (getId() == null || other.getId() == null) {
            return getId().equals(other);
        }
        return true;
    }
    
    
}
