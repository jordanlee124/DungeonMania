package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class FloorSwitch extends Entity {

    private boolean triggered;

    public FloorSwitch(JSONObject entity) {
        super(entity);
        this.triggered = false;
        //TODO Auto-generated constructor stub
    }

    public void trigger(List<Entity> entities) {
        this.triggered = false;
        for (Entity e : entities) {
            if (e instanceof Boulder) {
                if (e.getPosition().equals(this.getPosition())){
                    this.triggered = true;
                }
            }
        }
    }

    public boolean getTriggered() {
        return this.triggered;
    }

    public static boolean allSwitchTriggered(List<Entity> entities) {
        for (Entity e : entities) {
            if (e.getType().equals("switch")){
                if (!((FloorSwitch)e).getTriggered()) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
