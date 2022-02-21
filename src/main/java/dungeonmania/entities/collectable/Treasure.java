package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

/**
 * Treasure Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Treasure extends CollectableEntity {
    /**
     * Contructor for Treasure
     * @param entity  JSONObject
     */
    public Treasure(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    /**
     * Checks if trasure exists in map
     * @param entities lists of entities
     * @return boolean 
     */
    public static boolean treasureOnMap(List<Entity> entities) {
        for (Entity e : entities) {
            if (e.getType().equals("treasure")) {
                return true;
            }
        }
        return false;
    }
    
}
