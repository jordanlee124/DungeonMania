package dungeonmania.entities.Static;
import dungeonmania.entities.Entity;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Portal extends Entity {

    Position coords;

    public Portal(JSONObject entity, Position coords) {
        super(entity);
        this.coords = coords;
        //TODO Auto-generated constructor stub
    }
    
    public Position getCoords() {
        return this.coords;
    }

}
