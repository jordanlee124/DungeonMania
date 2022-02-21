package dungeonmania.entities.Static;
import dungeonmania.entities.Entity;

import org.json.JSONObject;

import dungeonmania.entities.Moving.Zombie;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

import java.util.List;

public class Spawner extends Entity {
    
    int ticks;
    int currentTick;
    static int ids;

    public Spawner(JSONObject entity, int ticks) {
        super(entity);
        this.ticks = ticks;
        this.currentTick = 0;
        //TODO Auto-generated constructor stub
    }
    
    public Dungeon spawn(Dungeon current) {
        this.currentTick++;
        if (this.currentTick == this.ticks) {
            JSONObject obj = new JSONObject();
            obj.put("x", this.getPosition().getX());
            obj.put("y", this.getPosition().getY());
            obj.put("type", "zombie_toast");
            Zombie zombie = new Zombie(obj);
            zombie.setId("zombie_toast_" + ((Integer)ids).toString());
            List<Entity> list = current.getEntities();
            list.add(zombie);
            current.setEntities(list);
            this.currentTick = 0;
            ids++;
        }
        return current;
    }

    public boolean isInDestroyableRange(Position player) {
        for (Position i : this.getPosition().getAdjacentPositions()) {
            if (player.getX() == i.getX() && player.getY() == i.getY()) {
                return true;
            }
        }
        return false;
    }

}
