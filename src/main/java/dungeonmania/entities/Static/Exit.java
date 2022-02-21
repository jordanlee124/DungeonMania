package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class Exit extends Entity {

    public Exit(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public Exit(String id, String type, Position position) {
        super(id, type, position);
    }

    public static boolean playerOnExit(List<Entity> entities, Player player) {
        for (Entity e : entities) {
            if (e instanceof Exit && player.getPosition().equals(e.getPosition())) {
                return true;
            }
        }
        return false;
    }
    
}
