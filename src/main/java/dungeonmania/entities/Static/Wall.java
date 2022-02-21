package dungeonmania.entities.Static;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

import org.json.JSONObject;

public class Wall extends Entity {

    public Wall(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor 
        this.setInteractable(false);
    }

    public Wall(String id, String type, Position position) {
        super(id, type, position);
        this.setInteractable(false);
    }
    
}
