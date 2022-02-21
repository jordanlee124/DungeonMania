package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
/**
 * CollectibleEntity Class which extends Entity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class CollectableEntity extends Entity {
    private int durability;

    /**
     * Contructor for CollectibleEntity
     * @param entity  JSONObject
     */
    public CollectableEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    /**
     * Contructor for CollectibleEntity
     * @param entity  JSONObject
     */
    public CollectableEntity(String id, String type) {
        super(id, type);
    }
    
    /**
     * gets durability
     * @return durability
     */
    public int getDurability() {
        return durability;
    }

    /**
     * sets durability
     * @param durability
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * creates item response
     * @return
     */
    public ItemResponse createItemResponse() {
        return new ItemResponse(this.getId(), this.getType());
    }
}
