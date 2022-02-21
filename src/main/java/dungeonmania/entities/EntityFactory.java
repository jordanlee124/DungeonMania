package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Moving.Assassin;
import dungeonmania.entities.Moving.Hydra;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Moving.Spider;
import dungeonmania.entities.Moving.Zombie;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Exit;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.Static.SwampTile;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Arrow;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.OneRing;
import dungeonmania.entities.collectable.SunStone;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.AndurilSword;
import dungeonmania.util.Position;

public class EntityFactory {

	/**
	 * A Factory Method that returns required Entity
	 * @param entity
	 * @param gameMode
	 * @param doorcreated
	 * @param entities
	 * @return
	 */
	public static Entity getEntity(JSONObject entity, String gameMode, boolean doorcreated, JSONObject entities) {
		Entity e = null;

		if (entity.getString("type").equalsIgnoreCase("armour")) {
			e = new Armour(entity);
		} else if (entity.getString("type").equalsIgnoreCase("arrow")) {
			e = new Arrow(entity);
		} else if (entity.getString("type").equalsIgnoreCase("bomb")) {
			e = new Bomb(entity);
		} else if (entity.getString("type").equalsIgnoreCase("health_potion")) {
			e = new HealthPotion(entity);
		} else if (entity.getString("type").equalsIgnoreCase("invincibility_potion")) {
			e = new InvincibilityPotion(entity);
		} else if (entity.getString("type").equalsIgnoreCase("invisibility_potion")) {
			e = new InvisibilityPotion(entity);
		} else if (entity.getString("type").contains("key")) {
			e = new Key(entity);
		} else if (entity.getString("type").equalsIgnoreCase("one_ring")) {
			e = new OneRing(entity);
		} else if (entity.getString("type").equalsIgnoreCase("sword")) {
			e = new Sword(entity);
		} else if (entity.getString("type").equalsIgnoreCase("treasure")) {
			e = new Treasure(entity);
		} else if (entity.getString("type").equalsIgnoreCase("wood")) {
			e = new Wood(entity);
		} else if (entity.getString("type").equalsIgnoreCase("mercenary")) {
			e = new Mercenary(entity);
		}
		else if (entity.getString("type").equalsIgnoreCase("assassin")) {
			e = new Assassin(entity);
		} else if (entity.getString("type").equalsIgnoreCase("hydra")) {
			e= new Hydra(entity);
		} else if (entity.getString("type").equalsIgnoreCase("spider")) {
			e = new Spider(entity);
		} else if (entity.getString("type").equalsIgnoreCase("zombie_toast_spawner")) {
            if (gameMode.equalsIgnoreCase("hard")) {
                e = new Spawner(entity, 15);
            } else {
                e = new Spawner(entity, 20);
            }
		} else if (entity.getString("type").equalsIgnoreCase("boulder")) {
			e = new Boulder(entity);
		} else if (entity.getString("type").contains("door")) {
			e = new Door(entity);
		} else if (entity.getString("type").equalsIgnoreCase("exit")) {
			e = new Exit(entity);
		} else if (entity.getString("type").equalsIgnoreCase("switch")) {
			e = new FloorSwitch(entity);
		} else if (entity.getString("type").equalsIgnoreCase("zombie_toast")) {
			e = new Zombie(entity);
		} else if (entity.getString("type").equalsIgnoreCase("wall")) {
			e = new Wall(entity);
		} else if (entity.getString("type").equalsIgnoreCase("sun_stone")) {
			e = new SunStone(entity);
		}  else if (entity.getString("type").equalsIgnoreCase("player")) {
			e = new Player(entity);
		} else if (entity.getString("type").equalsIgnoreCase("portal")) {
            Position coords = new Position(0,0);
            for (Object o : entities.getJSONArray("entities")){
                try {
                    if (((JSONObject)o).getString("colour").equals(((JSONObject)entity).getString("colour"))) {
                        if (((((JSONObject)o).getInt("x") != (((JSONObject)entity).getInt("x"))) || (((JSONObject)o).getInt("y") != (((JSONObject)entity).getInt("y"))))) {
                            coords = new Position(((JSONObject)o).getInt("x"), ((JSONObject)o).getInt("y"));
                        }
                    }
                } catch (Exception ex) {
                    //TODO: handle exception
                }
            }
            Portal portal = new Portal((JSONObject)entity, coords);
            portal.setType("portal_" + ((JSONObject)entity).getString("colour"));
			e = portal;
		} else if (entity.getString("type").equalsIgnoreCase("swamp_tile")) {
			e = new SwampTile(entity);
		} else if (entity.getString("type").equalsIgnoreCase("anduril")) {
			e = new AndurilSword(entity);
		}
		return e;
	}
}
