/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.entities.User;
import challenge.entities.collections.Users;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.types.ObjectId;

/**
 *
 * @author Sérgio
 */
public class UsersMongoDB extends CollectionMongoDB<User> implements Users {
    
    public UsersMongoDB(MongoDatabase db) {
        super(db.getCollection("cha_users", User.class));
    }
    
    @Override
    public User get(String email) {
        return getCollection().find(eq("email", email)).first();
    }

    @Override
    public Object getIdMongoDB(User user) {
        return user == null? null : new ObjectId(user.getId());
    }

    @Override
    public User get(String email, String password) {
        if (password != null) {
            User user = get(email);
            if (user != null && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
