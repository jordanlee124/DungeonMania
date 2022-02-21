package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Zombie Class which extends MovingEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Zombie extends MovingEntity {
    
    /**
     * Contructor for Zombie
     * @param entity  JSONObject
     */
    public Zombie(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
    }
    
    @Override
    public void move(Position pos, List<Entity> walls, int width, int height, Direction direction) {
        boolean move = true;
        List<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.DOWN);
        directions.add(Direction.RIGHT);

        Random rand = new Random();

        pos = this.getPosition().translateBy(directions.get(rand.nextInt(directions.size())));
        for (Entity w : walls) {
            if (w.getPosition().equals(pos)) {
                move = false;
            }
        }
        if (move) {
            this.setPosition(pos);
        }
    }

    /**
     * Method which checks if zombies exists in the dungeon
     * @param entities  list of entities in the dungeon
     * @return boolean statement
     */
    public static boolean zombieExistOnMap(List<Entity> entities) {
        for (Entity e : entities) {
            if (e instanceof Zombie) {
                return true;
            }
        }
        return false;
    }

}  
