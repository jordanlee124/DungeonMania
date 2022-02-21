package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Spider Class which extends MovingEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Spider extends MovingEntity {
    
    boolean spawned;
    int pathnum;
    static int ids = 0;
    List<Direction> directions = new ArrayList<Direction>(
        Arrays.asList(Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.RIGHT, Direction.RIGHT, Direction.UP, Direction.UP, Direction.LEFT)
    );
    
    /**
     * Contructor for Spider
     * @param entity  JSONObject
     */
    public Spider(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1; 
        this.spawned = true;
        this.pathnum = 0;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void move(Position pos, List<Entity> walls, int width, int height, Direction direction) {
        List<Position> spider_walls = new ArrayList<Position>();
        for (Entity e : walls) {
            if (e instanceof Boulder) {
                spider_walls.add(e.getPosition());
            }
        }
        
        if (!spider_walls.contains(this.getPosition().translateBy(Direction.UP)) && this.spawned) {
            this.setPosition(this.getPosition().translateBy(Direction.UP));
            this.spawned = false;
            return;
        }

        if (!spider_walls.contains(this.getPosition().translateBy(Direction.UP))) {
            this.setPosition(this.getPosition().translateBy(directions.get(pathnum)));
        }

        //move in circle
        this.pathnum++;
        if (this.pathnum == 8) {
            this.pathnum = 0;
        }
    }

    /**
     * Spawns spiders in the given dungeon
     * @param current  current dungeon
     * @return current
     */
    static public Dungeon spawn(Dungeon current) {
        JSONObject obj = new JSONObject();
        obj.put("x", Math.random()*current.getWidth());
        obj.put("y", Math.random()*current.getHeight());
        obj.put("type", "spider");
        Spider spider = new Spider(obj);
        spider.setId("spider_" + ((Integer)ids).toString());
        List<Entity> list = current.getEntities();
        list.add(spider);
        current.setEntities(list);
        ids++;
        return current;
    }

}
