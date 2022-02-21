package dungeonmania.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Moving.Spider;
import dungeonmania.entities.Moving.Zombie;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.collectable.Bomb;

/**
 * Entity Class
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    /**
     * Contructor for Entity
     * @param entity  JSONObject
     */
    public Entity(JSONObject entity) {
        if (entity.getString("type").equals("key") || entity.getString("type").equals("door")) {
            this.type = entity.getString("type") + "_" + entity.getInt("key");
        } else {
            this.type = entity.getString("type");
        }
        this.position = new Position(entity.getInt("x"), entity.getInt("y"));
        this.id = entity.getString("type") + Integer.toString(this.position.getX()) + "_" + Integer.toString(this.position.getY());
        if (this instanceof Mercenary || this instanceof Spawner) {
            this.isInteractable = true;
        } else {
            this.isInteractable = false;
        }
    }

    /**
     * Contructor for Entity
     * @param id
     * @param type
     */
    public Entity(String id, String type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = false;
    }

    public Entity(String id, String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Creates an entity response
     * @return EntityResponse
     */
    public EntityResponse createResponse() {
        return new EntityResponse(this.id, this.type, this.position, this.isInteractable);
    }

    /**
     * Method which checks if enemies exist on map, give a dungeon entity list.
     * @param entites list of entities
     * @return boolean statement
     */
    public static boolean enemiesOnMap(List<Entity> entities) {
        for (Entity e : entities) {
            if (e instanceof Mercenary || e instanceof Spider || e instanceof Zombie || e instanceof Spawner) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets id of entity
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets position of entity
     * @return position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets position of entity
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets type of entity
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the number of keys
     * @return null
     */
    public Integer getKey(){
        return null;
    }

    /**
     * Sets type of entity
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets interactable of entity
     * @param isInteractable
     */
    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    /**
     * Gets interactable of entity
     * @return isInteractable
     */
    public boolean isInteractable() {
        return this.isInteractable;
    }

    /**
     * Move method which is allows entities to move within the dungeon towards
     * a specified position on the map.
     * @param pos       Position entity wants to move towards
     * @param walls     List of walls which restrict enemy movement
     * @param width     Width of dungeon
     * @param height    Height of dungeon
     * @param direction Direction of movement
     * @return  
     */
    public void move(Position pos, List<Entity> walls, int width, int height, Direction direction) {
        return;
    }

    /**
     * Move method which is allows entities to move within the dungeon away from
     * a specified position on the map.
     * @param pos       Position entity wants to move towards
     * @param walls     List of walls which restrict enemy movement
     * @return  
     */
    public void moveAway(Position pos, List<Entity> walls) {
        return;
    }

    /**
     * Sets id of entity
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method which checks if an item is a collectible entity.
     * @return boolean statement
     */
    public boolean isCollectable() {
        List<String> collectables = new ArrayList<String>();
        collectables.addAll(Arrays.asList("armour", "arrow", "health_potion", "invincibility_potion", "invisibility_potion", "key_1", "key_2", "sword", "treasure", "wood", "one_ring", "sun_stone", "anduril"));
        if (this.type.equals("bomb")) {
            Bomb b = (Bomb) this;
            if (!b.isPlaced()) {
                collectables.add("bomb");
            }
        }
        if (collectables.contains(this.type)) {
            return true;
        }
        return false;
    }
}
