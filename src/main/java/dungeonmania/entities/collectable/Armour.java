package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

/**
 * Armour Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Armour extends CollectableEntity {
    public static int durability = (int)Math.floor(Math.random()*(15-4+1)+4);

    /**
     * Contructor for Armour
     * @param entity  JSONObject
     */
    public Armour(JSONObject entity) {
        super(entity);
        // this.setDurability(durability);
    }

    public Armour(String id, String type) {
        super(id, type);
    }

    /**
     * Checks if armour is broken and removes it
     * @param inventory
     *
     */
    public static void isBroken(List<Entity> inventory) {
        if (durability <= 0) {
            for (Iterator<Entity> it = inventory.iterator(); it.hasNext();){
                Entity anItem = it.next();
                if(anItem.getType().equals("armour")) {
                    it.remove();
                }
            }
        }
    }
}
