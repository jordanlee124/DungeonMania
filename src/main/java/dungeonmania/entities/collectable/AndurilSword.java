package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

/**
 * AndurilSword Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class AndurilSword extends Sword {
    /**
     * Contructor for Anduril Sword
     * @param entity  JSONObject
     */
    public AndurilSword(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public AndurilSword(String id, String type) {
        super(id,type);
    }
}
