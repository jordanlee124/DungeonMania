package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Player;
import dungeonmania.Dungeon;

import org.json.JSONObject;
import java.util.List;
import dungeonmania.util.Direction;
import java.lang.Math;
import java.util.ArrayList;

/**
 * Bomb Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Bomb extends CollectableEntity {
    private boolean placed;

    /**
     * Contructor for Bomb
     * @param entity  JSONObject
     */
    public Bomb(JSONObject entity) {
        super(entity);
        this.placed = false;
        //TODO Auto-generated constructor stub
    }
    
    /**
     * Place bomb in dungeon
     * @param current
     * @return current
     */
    public Dungeon placeBomb(Dungeon current) {
        JSONObject obj = new JSONObject();
        obj.put("x", current.getPlayer().getPosition().getX());
        obj.put("y", current.getPlayer().getPosition().getY());
        obj.put("type", "bomb");
        Bomb bomb = new Bomb(obj);
        bomb.setId(this.getId());
        bomb.setPlaced(true);
        List<Entity> list = current.getEntities();
        list.add(bomb);
        current.setEntities(list);
        return current;
    }

    /**
     * Checks if bomb is activated
     * @param current
     * @return boolean statement
     */
    public boolean isActivated(Dungeon current) { 
        for (Entity entity1 : current.getEntities()) {
            if (entity1 instanceof FloorSwitch) {
                for (Entity entity2 : current.getEntities()) {
                    if (entity2 instanceof Boulder) {
                        if (entity1.getPosition().equals(entity2.getPosition()) && this.isInRange(entity1.getPosition(), null)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Explode bomb in dungeon
     * @param current
     * @return current
     */
    public Dungeon explode(Dungeon current) {
        List<String> toRemove = new ArrayList<String>();
        for (Entity entity : current.getEntities()) {
            if (this.isInRange(entity.getPosition(), "explosion") && !(entity instanceof Player)) {
                toRemove.add(entity.getId());
            }
        }

        for (String entityId : toRemove) {
            current.removeEntity(entityId);
        }
        return current;
    }

    /**
     * Check if bomb is in range of explosion or switch
     * @param pos
     * @param mode
     * @return boolean
     */
    public boolean isInRange(Position pos, String mode) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y));

        if (mode != null) {
            if (mode.equals("explosion")) {
                adjacentPositions.add(new Position(x-1, y-1));
                adjacentPositions.add(new Position(x+1, y-1));
                adjacentPositions.add(new Position(x+1, y+1));
                adjacentPositions.add(new Position(x-1, y+1));
            }
        }

        for (Position i : adjacentPositions) {
            if (i.getX() == pos.getX() && i.getY() == pos.getY()) return true;
        }

        return false;
    }

    public boolean isPlaced() {
        return this.placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
