package dungeonmania.entities.Moving;

import dungeonmania.util.Position;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

import dungeonmania.util.Direction;
import java.util.List;
import java.lang.Math;
import java.security.KeyStore.Entry;
import java.util.ArrayList;

/**
 * Assassin Class which extends Mercenary
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Assassin extends Mercenary{
    static int ids;

    /**
     * Contructor for Assassin
     * @param entity  JSONObject
     */
    public Assassin(JSONObject entity) {
        super(entity);
        this.health = 7;
        this.attack = 2;
        this.setType("assassin");
    }

    /**
     * Spawns Assassin in the given dungeon
     * @param current   current dungeon
     * @param entry     entrance
     * @return current
     */
    public static Dungeon spawn(Dungeon currentDungeon, Position entry) {
        Assassin ass = new Assassin(new JSONObject("{x:"+ entry.getX() + ",y:"+entry.getY()+",type:mercenary}"));
        ass.setId("assassan_" + ((Integer)ids).toString());
        List<Entity> ents = currentDungeon.getEntities();
        ents.add(ass);
        currentDungeon.setEntities(ents);
        ids++;
        return currentDungeon;
    }

}
