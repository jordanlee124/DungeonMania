package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

/**
 * Sword Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Sword extends CollectableEntity {
    /**
     * Contructor for Sword
     * @param entity  JSONObject
     */
    public Sword(JSONObject entity) {
        super(entity);
        setDurability(10);
        //TODO Auto-generated constructor stub
    }

    /**
     * Contructor for Sword
     * @param id
     * @param type
     */
    public Sword(String id, String type) {
        super(id,type);
    }

    public void isBroken(List<Entity> inventory) {
        if (this.getDurability() <= 0) {
            for (Iterator<Entity> it = inventory.iterator(); it.hasNext();){
                Entity anItem = it.next();
                if(anItem.getType().equals("sword")) {
                    it.remove();
                }
            }
        }
    }
    

}
