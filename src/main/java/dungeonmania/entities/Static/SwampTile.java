package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class SwampTile extends Entity{
    private int movement_factor;

    public SwampTile(JSONObject entity) {
        super(entity);
    }

    public int getMovementFactor() {
        return this.movement_factor;
    }
}
