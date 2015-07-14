/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.db.mongodb;

import challenge.entities.Entity;
import challenge.entities.collections.EntityCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Sérgio
 * @param <E>
 */
public class CollectionMongoDB<E extends Entity> implements EntityCollection<E> {
    private final MongoCollection<E> col;
    private final Map<E, E> mapFetch = new LinkedHashMap<>();

    public CollectionMongoDB(MongoCollection<E> col) {
        this.col = col;
    }

    public MongoCollection<E> getCollection() {
        return col;
    }
    
    @Override
    public List<E> getById(Collection<E> collection) {
        List<Object> listId = new ArrayList<>(collection.size());
        List<E> list = new ArrayList<>(collection.size());
        collection.stream().forEach((e) -> {
            listId.add(getIdMongoDB(e));
        });
        FindIterable<E> it = col.find(in("_id", listId));
        for (E e: it) {
            list.add(e);
        }
        return list;
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<>();
        FindIterable<E> it = col.find();
        for (E e: it) {
            list.add(e);
        }
        return list;
    }

    @Override
    public void needFetch(E entity) {
        if (entity.isById()) {
            mapFetch.put(entity, entity);
        }
    }

    @Override
    public boolean fetch(E entity) {
        if (entity.isById()) {
            E fetched = mapFetch.get(entity);
            if (fetched == null || fetched.isById()) {
                List<E> listToFetch = new ArrayList<>();
                if (fetched == null) {
                    listToFetch.add(entity);
                }
                mapFetch.keySet().stream().filter((e) -> (mapFetch.get(e).isById())).forEach((e) -> {
                    listToFetch.add(e);
                });
                List<E> listFetched = getById(listToFetch);
                if (listFetched != null) {
                    listFetched.stream().forEach((e) -> {
                        Entity orig = mapFetch.get(e);
                        orig.load(e);
                    });
                }
                mapFetch.remove(entity);
            }
            return !entity.isById();
        }
        else {
            return true;
        }
    }

    @Override
    public void add(E entity) {
        col.insertOne(entity);
    }
    
    public Object getIdMongoDB(E entity) {
        return entity.getId();
    }

}
