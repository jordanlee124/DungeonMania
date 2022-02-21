package dungeonmania.entities.collectable;

import org.json.JSONObject;
/**
 * Key Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Key extends CollectableEntity {

    int key;
    /**
     * Contructor for Key
     * @param entity  JSONObject
     */
    public Key(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
        this.key = entity.getInt("key");
    }
    
    public Integer getKey(){
        return this.key;
    }

}
