/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.entities.Pool;
import challenge.entities.collections.Pools;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Sérgio
 */
public class PoolsMongoDB extends CollectionMongoDB<Pool> implements Pools {
    /**
     *
     * @param db
     */
    public PoolsMongoDB(MongoDatabase db) {
        super(db.getCollection("cha_pools", Pool.class));
    }

    @Override
    public Pool getLastActive() {
        return getCollection().find(eq("active", true)).sort(new Document("date", -1)).first();
    }
    
    @Override
    public Object getIdMongoDB(Pool pool) {
        return pool == null? null : new ObjectId(pool.getId());
    }
}
