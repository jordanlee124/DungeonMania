package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.OneRing;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.buildable.Buildable;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;
import dungeonmania.goals.CompositeGoals;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalLeaf;
import dungeonmania.entities.Player;
import dungeonmania.entities.Moving.*;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Port;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Dungeon Class
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Dungeon {
    String dungeonId;
    String dungeonName;
    List<Entity> entities;
    List<Entity> inventory;
    List<String> buildables;
    Goal goalTree;
    String goals;
    List<AnimationQueue> animations;
    Player player;
    String gameMode;
    Position entry;
    int width;
    int height;
    int spiderCount;

    /**
     * Constructor for Dungeon
     * @param dungeonName   name of dungeon
     * @param entities      JSONObject entities
     * @param gamemode      mode of game
     */
    public Dungeon(String dungeonName, JSONObject entities, String gameMode) {
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName;
        this.entities = new ArrayList<Entity>();
        this.gameMode = gameMode;
        this.spiderCount = 0;
        boolean doorcreated = false;
        for (Object entity : entities.getJSONArray("entities")) {
            Entity e = EntityFactory.getEntity((JSONObject)entity, gameMode, doorcreated, entities);

            if (e instanceof Player) {
                this.player = (Player)e;
                this.entry = e.getPosition();
            }

            this.entities.add((Entity)e);
        }
        this.inventory = new ArrayList<Entity>();
        this.buildables = new ArrayList<String>();

        if (entities.has("goal-condition")) {
            if (entities.getJSONObject("goal-condition").has("subgoals")) {
                this.goalTree = new CompositeGoals("Goal", false);
                this.goalTree.add(setGoals(entities.getJSONObject("goal-condition")));        
            } else {
                this.goalTree = new GoalLeaf(entities.getJSONObject("goal-condition").getString("goal"), false);
            }
            this.goals = getGoals();
        } else {
            this.goals = null;
        }
        //set width and height of dungeon
        try {
            this.width = entities.getInt("width");
            this.height= entities.getInt("height");
        } catch (Exception e) {
            //set width and height if there are none
            int greatestW = 0;
            int greatestH = 0;
            for (Entity entity : this.entities) {
                if (entity.getPosition().getX() > greatestW) {
                    greatestW = entity.getPosition().getX();
                }
                if (entity.getPosition().getY() > greatestH) {
                    greatestH = entity.getPosition().getY();
                }
            }
            this.width = greatestW;
            this.height = greatestH;
        }
    }

    /**
     * Method which looks through a json file and sets the composite goal tree.
     * @param goals     JSONObject goals
     * @return          r of type Goal
     */
    public Dungeon(String dungeonName, String gameMode) {
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName;
        this.entities = new ArrayList<Entity>();
        this.gameMode = gameMode;
        this.entities = new ArrayList<Entity>();
        this.inventory = new ArrayList<Entity>();
        this.buildables = new ArrayList<String>();
        this.goalTree = new CompositeGoals("Goal", false);
        GoalLeaf exit = new GoalLeaf("exit", false);
        goalTree.add(exit);
        this.goals = getGoals();
        this.width = 50;
        this.height = 50;
    }

    //getters
    private Goal setGoals(JSONObject goals) {
        CompositeGoals r = new CompositeGoals(goals.getString("goal"), false);
        if (goals.equals(null)) {
            return null;
        }

        if (goals.has("subgoals")) {
            for (Object o : goals.getJSONArray("subgoals")) {
                if (((JSONObject)o).has("subgoals")) {
                    r.add(setGoals((JSONObject)o));
                } else {
                    Goal newGoal = new GoalLeaf(((JSONObject)o).getString("goal"), false);
                    r.add(newGoal);
                }
            }
        }
        return r;
    }

    /**
     * Method which takes the goal tree and returns its contents as a String.
     * @return          returns goals as a String
     */
    private String getGoals() {
        return goalTree.goalsString();
    }

    /**
     * Method which takes a string and loops through the inventory until
     * an Entity's type matches the given string, returning the object. if 
     * string is not matched to a type, returns null.
     * @param type      entity type
     * @return          entity
     */    
    public Entity getItem(String type) {
        for (Entity i : this.inventory) {
            if (i.getType().equals(type)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Method which takes a string and loops through the inventory until
     * an Entity's id matches the given string, removing the object.
     * @param stringId   entity id
     */        
    public void removeEntity(String stringId) {
        for (Iterator<Entity> entity = entities.iterator(); entity.hasNext();) {
            Entity value = entity.next();
            if (value.getId().equals(stringId)) entity.remove();
        }
    }

    /**
     * Method which takes a string and loops through the entity list until
     * an Entity's id matches the given string, returning the object. if 
     * string is not matched to a id, returns null.
     * @param stringId      entity id
     * @return              entity
     */    
    public Entity getEntity(String stringId) {
        for (Entity entity : this.entities) {
            if (entity.getId().equals(stringId)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Gets player
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * Gets dungeon entity list
     * @return entities
     */
    public List<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Sets dungeon entity list
     * @param entities  list of entities
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Adds entity to dungeon entity list
     * @param e Entity
     */
    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    /**
     * Adds entity to dungeon inventory
     * @param entities  inventory
     */
    public void setItems(List<Entity> items) {
        this.inventory = items;
    }

    /**
     * Sets dungeon player
     * @param player  a new player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets dungeon inventory
     * @return inventory
     */
    public List<Entity> getItems() {
        return this.inventory;
    }

    /**
     * Gets dungeon gamemode
     * @return gamemode
     */
    public String getGameMode() {
        return this.gameMode;
    }

    /**
     * Method which takes a string as an input and loops through inventory,
     * if itemUsed id matches string return entity, otherwise returns null.
     * @param stringId
     * @return entity
     */
    public Entity getItemUsed(String stringId) {
        for (Entity item : this.inventory) {
            if (item.getId().equals(stringId)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Method which moves all the entities besides the player in the dungeon,
     * Loops through entity list and activates move method for each entity.
     * @param direction the direction the entity is moving towards
     * @param width     the width of the dungeon
     * @param height    the height of the dungeon
     */
    public void pathing(Direction direction, int width, int height) {
        //make a list of walls
        List<Entity> walls = getWalls();
        for (Entity e : this.entities) {
            if (e instanceof Boulder) {
                walls.add(e);
            }
        }

        for (Entity e : this.entities) {
            
            if (e instanceof MovingEntity) {
                MovingEntity me = (MovingEntity) e;
                if (me.isInSwampTile(this.entities) && !me.isSlowed()) {
                    me.setSlowed(true);
                }
                else if (e instanceof Mercenary) {

                    walls.add(this.player);
                    walls.add(e);
                    
                    Mercenary entity = (Mercenary)e;
                
                    
                    if (entity.isInBattle() && this.player.isInvisibilityPotionEffect() == false) {
                        e.move(this.player.getPosition(), walls, width, height, direction);
                        me.setSlowed(false);
                    } else if (!entity.isInBattle() && this.player.isInvisibilityPotionEffect() == false) {
                        e.moveAway(this.player.getPosition(), walls);
                        me.setSlowed(false);
                    }
                } else if (!(e instanceof Player)) {
                    e.move(this.player.getPosition(), walls, width, height, direction);
                    me.setSlowed(false);
                }
            }
        }
    }

    /**
     * Method which creates a dungeon response to the frontend 
     * @return dungeon response
     */
    public DungeonResponse createResponse() {
        List<EntityResponse> entityList = new ArrayList<EntityResponse>();
        for (Entity e : this.entities) {
            // System.out.println(e.getId());
            entityList.add(e.createResponse());
        }
        List<ItemResponse> itemList = new ArrayList<ItemResponse>();
        for (Entity i : this.inventory) {
            itemList.add(new ItemResponse(i.getId(), i.getType()));
        }
        if (!inventory.isEmpty()) {
            for (String item : Buildable.BUILDABLES_LIST) {
                addOrRemoveBuildable(item);
            }
        } 
        if (this.goalTree != null) {
            this.goals = getGoals();
        }
        List<AnimationQueue> animations = new ArrayList<>();
        animations.add(new AnimationQueue("PostTick", this.player.getId(), Arrays.asList(
            "healthbar tint 0x00ff00", "healthbar set "+ this.player.getHealth()/10 + " over 1.5s"
        ), false, -1));
        animations.add(new AnimationQueue("PostTick", "player", Arrays.asList("healthbar shake, over 0.5s, ease Sin"), false, 0.5));
        return new DungeonResponse(this.dungeonId, this.dungeonName, entityList, itemList, this.buildables, this.goals, animations);
    }

    /**
     * Method which assists in creating a response for buildable items
     * @param item
     */
    public void addOrRemoveBuildable(String item) {
        if (item.equals("midnight_armour")) {
            if (((Midnight_Armour) (Buildable.getBuildable(item))).isBuildable(inventory, entities)) {
                if (!buildables.contains(item)) {buildables.add(item);}
            } else {buildables.remove(item);}
        } else {
            if (Buildable.getBuildable(item).isBuildable(inventory)) {
                if (!buildables.contains(item)) {buildables.add(item);}
            } else {buildables.remove(item);}
        }
    }

    /**
     * Method which adds a CollectibleEntity to the dungeon inventory if Player 
     * is in the same position and the CollectibleEntity, removes Entity from dungeon entites.
     */
    public void itemPickup() {
        for (Iterator<Entity> it = entities.iterator(); it.hasNext();) {
            Entity anEntity = it.next();
            EntityResponse entityResponse = anEntity.createResponse();
            if (entityResponse.getPosition().equals(player.getPosition()) && anEntity.isCollectable()) {
                if ((entityResponse.getType().equals("key_1") || entityResponse.getType().equals("key_2")) && hasKey()) {
                } else {
                    inventory.add(anEntity);
                    it.remove();
                }
            }
        }
    }

    /**
     * Method which checks if key exists in inventory, returning true or false.
     * @return boolean statement
     */
    public boolean hasKey() {
        for (Entity i : inventory) {
            if (i.getType().equals("key_1") || i.getType().equals("key_2")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method which takes in a string and loops through invetory, if Entity type matches
     * the string, Entity is removed from inventory.
     * @param type
     */
    public void removeItemFromInventory(String type) {
        for (Iterator<Entity> it = inventory.iterator(); it.hasNext();) {
            Entity anItem = it.next();
            if (anItem.getType().equals(type)) {
                it.remove();
                break;
            }
        } 
    }

    /**
     * Method which takes in a string and loops through invetory, if Entity type matches
     * the string, Entity is removed from inventory.
     * @param type
     */
    public void removeItem(String type) {
        for (Iterator<Entity> item = inventory.iterator(); item.hasNext();) {
            Entity value = item.next();
            if (value.getType().equals(type)) item.remove();
        }
    }

    /**
     * Method which creates the buidable Entity given that the inventory has
     * all the required items in the recipe.
     * @param type
     */
    public void createBuildable(String type) {
        Buildable buildable = Buildable.getBuildable(type);
        if (buildables.contains(buildable.getType())) {
            Map<String, Integer> recipe = buildable.materialNeeded(inventory);
            for (Map.Entry<String, Integer> e : recipe.entrySet()) {
                for (int i = 0; i < e.getValue(); i++) {
                    removeItemFromInventory(e.getKey());
                }
            }
            inventory.add(buildable);
        }
    }
    
    /**
     * Method which simulates enemy death, also has a chance to add Armour or One Ring 
     * to inventory upon enemy death.
     * @param enemy
     */
    public void enemyDeath(MovingEntity enemy) {
        //remove enemy from entities and give player loot
        this.entities.remove(enemy);
        if (enemy instanceof Mercenary || enemy instanceof Zombie) {
            int num = (int)Math.floor(Math.random()*(15-1+1)+1);
            if (num == 2) {
                inventory.add(new Armour("armour_drop", "armour"));
            }
        }

        if (enemy instanceof MovingEntity) {
            int num = (int)Math.floor(Math.random()*(50-1+1)+1);
            if (num == 2) {
                inventory.add(new OneRing("one_ring_drop", "one_ring"));
            }

            
        }
    }

    /**
     * Method which checks if a mercenary is in radius of a battle, if so the mercenary is able to move again.
     * @param current currentDungeon
     */
    public void MercenaryBattleMovement(Dungeon current) {
        List<Entity> walls = getWalls();
        for (Entity entity: this.entities) {
            if (entity instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) entity;
                if (mercenary.isInBattleRadius(current.getPlayer().getPosition()) && current.getPlayer().isBattling()) {
                    walls.add(this.player);
                    walls.add(entity);
                    
                    mercenary.move(this.player.getPosition(), walls, this.width, this.height, Direction.NONE);
                }
            }
        }
    }

    /**
     * Method which starts a battle between a Player and a Entity if they are in the same position in the given Dungeon.
     * Loops through entity List and checks if Player collides with Enemy. Player and Enemy take turns giving damage until
     * one of the heals drop to zero or below where the battle ends and the current Dungeon is returned.
     * @param current currentDungeon
     * @return current 
     */
    public Dungeon battle(Dungeon current) {
        for (Entity e : current.entities) {
            //for all moving entities aka enemies
            if (e instanceof MovingEntity) {
                if (e instanceof Mercenary) {
                    Mercenary mercenary = (Mercenary) e;
                    if (mercenary.isBribed() || mercenary.isBrainWashed()) {
                        continue;
                    }
                }
                MovingEntity enemy = (MovingEntity) e;
                //if the entity is on the same ssquare as character
                if (e.getPosition().equals(current.player.getPosition())) {
                    boolean battleOver = false;
                    this.getPlayer().setBattling(true);
                    while (!battleOver) {
                        //change health values
                        double playerHP = current.player.getHealth();
                        double enemyHP = enemy.getHealth();
                        double playerAD = current.player.getAttack();
                        double enemyAD = enemy.getAttack();
                        
                        enemy.takeDamage(playerHP, playerAD, this);

                        //Has an ally Mercenary
                        if (this.getPlayer().haveAlly()) {
                            enemy.setHealth(enemyHP - ((playerHP * playerAD) / 5));
                        }

                        if (enemyHP <= 0) {
                            //enemy is dead
                            current.enemyDeath(enemy);
                            battleOver = true;
                            return current;
                        }
                    
                        // Player should take damage only if invincibility potion effect is off
                        if (!this.player.isInvincibilityPotionEffect()) {
                            current.player.takeDamage(enemyHP, enemyAD, this, enemy);
                        }
                        
                        if (playerHP <= 0) {
                            //one ring
                            if (this.getItem("one_ring") != null) {
                                current.getPlayer().setHealth(10);
                                this.removeItem("one_ring");
                            }
                            //game over
                            else {
                                this.player = null;
                                return current;
                            }
                        } else if (enemyHP <= 0) {
                            //enemy is dead
                            if (enemy instanceof Spider) {
                                this.spiderCount--;
                            }
                            current.enemyDeath(enemy);
                            battleOver = true;
                        }
                    }
                    return current;
                }
            }
            
        }
        return current;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.width;
    }
    
    public boolean existsBrainwashedEntity(List<Entity> entities) {
        for (Entity e : entities) {
            if (e instanceof Mercenary || e instanceof Assassin) {
                Mercenary m = (Mercenary) e;
                if (m.isBrainWashed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Entity> getWalls() {
        List<Entity> walls = new ArrayList<Entity>();       
        for (Entity e : this.entities) {
            if (e instanceof Mercenary){
                Mercenary m = (Mercenary) e;
                if (m.isBribed() || m.isBrainWashed()) {
                    walls.add(m);
                }
            }
            else if (e instanceof Bomb) {
                Bomb b = (Bomb) e;
                if (b.isPlaced()) {
                    walls.add(e);
                }
            }
            else if (e instanceof Wall || e instanceof Door || e instanceof MovingEntity || e instanceof Spawner) {
                if (e instanceof Wall || e instanceof MovingEntity || e instanceof Spawner) {
                    walls.add(e);
                } else {
                    if (!(((Door)e).getType().equals("door_unlocked"))) {
                        walls.add(e);
                    }
                }
            }
        }
        return walls;
    }
}
