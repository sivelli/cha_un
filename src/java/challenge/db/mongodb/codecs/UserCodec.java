/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb.codecs;

import challenge.db.mongodb.SessionMongoDB;
import challenge.entities.User;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

/**
 *
 * @author sergio.sivelli
 */
public class UserCodec implements CollectibleCodec<User>
{
    private final Codec<Document> documentCodec;
    private final SessionMongoDB session;
 
    public UserCodec(SessionMongoDB session) {
        this.documentCodec = new DocumentCodec();
        this.session = session;
    }
 
    public UserCodec(Codec<Document> codec, SessionMongoDB session) {
        this.documentCodec = codec;
        this.session = session;
    }    

    @Override
    public User generateIdIfAbsentFromDocument(User user) {
        user.setId(new ObjectId().toHexString());
        return user;
    }

    @Override
    public boolean documentHasId(User user) {
        return (user.getId() != null);
    }

    @Override
    public BsonValue getDocumentId(User user) {
        return new BsonObjectId(new ObjectId(user.getId()));
    }

    @Override
    public void encode(BsonWriter writer, User user, EncoderContext ec) {
        Document document = new Document();
 
        String id = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        if (id != null) {
            document.put("_id", new ObjectId(id));
        }
        if (email != null) {
            document.put("email", email);
        }
        if (password != null) {
            document.put("password", password);
        }
        documentCodec.encode(writer, document, ec);
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }

    @Override
    public User decode(BsonReader reader, DecoderContext dc) {
        Document document = documentCodec.decode(reader, dc);
        User user = new User();
        ObjectId id = (ObjectId) document.get("_id");
        user.setId(id.toHexString());
        String email = (String) document.get("email");
        user.setEmail(email);
        String password = (String) document.get("password");
        user.setPassword(password);
        return user;    
    }
}
