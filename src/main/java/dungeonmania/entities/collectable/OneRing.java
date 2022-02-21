package dungeonmania.entities.collectable;

import org.json.JSONObject;
/**
 * OneRing Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class OneRing extends CollectableEntity{
    /**
     * Contructor for OneRing
     * @param entity  JSONObject
     */
    public OneRing(JSONObject entity) {
        super(entity);
    }

    public OneRing(String id, String type) {
        super(id, type);
    }
}
