package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.AndurilSword;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;

/**
 * Hydra Class which extends Zombie
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class Hydra extends Zombie {
    static int ids;

    /**
     * Contructor for Hydra
     * @param entity  JSONObject
     */
    public Hydra(JSONObject entity) {
        super(entity);
        this.health = 15;
        this.attack = 1;
    }
    
    /**
     * Spawns Hydra in the given dungeon
     * @param current   current dungeon
     * @param entry     entrance
     * @return current
     */
    public static Dungeon spawn(Dungeon currentDungeon, Position entry) {
        Hydra ass = new Hydra(new JSONObject("{x:"+ entry.getX() + ",y:"+entry.getY()+",type:hydra}"));
        ass.setId("hydra_" + ((Integer)ids).toString());
        List<Entity> ents = currentDungeon.getEntities();
        ents.add(ass);
        currentDungeon.setEntities(ents);
        ids++;
        return currentDungeon;
    }

    @Override
    public void takeDamage(double playerHP, double playerAD, Dungeon dungeon) {
        Random random = new Random();
        if (random.nextBoolean() && dungeon.getItem("anduril") == null) {
            this.setHealth(this.health + ((playerHP * playerAD) / 5));
            if(this.health > 15) {
                this.setHealth(15);
            }
        } else {
            if (dungeon.getItem("anduril") != null) {
                this.setHealth(this.health - 3);
                AndurilSword as = (AndurilSword) dungeon.getItem("anduril");
                as.setDurability(as.getDurability() - 1);
                as.isBroken(dungeon.getItems());
                // decrease sword durability by 1 // TODO
            }
            else if (dungeon.getItem("sword") != null) {
                this.setHealth(this.health - 1);
                Sword s = (Sword) dungeon.getItem("sword");
                s.setDurability(s.getDurability() - 1);
                s.isBroken(dungeon.getItems());
            }
            //Bow allows player to attack twice
            if (dungeon.getItem("bow") != null) {
                Bow bow = (Bow) dungeon.getItem("bow");
                bow.effect(this, this.health, playerHP, playerAD, dungeon.getItems());
            }
            
            if (dungeon.getItem("midnight_armour") != null) {
                Midnight_Armour m_armour = (Midnight_Armour) dungeon.getItem("midnight_armour");
                this.setHealth(this.health + m_armour.effect(-1, dungeon.getItems()));
            }
            this.setHealth(this.health - ((playerHP * playerAD) / 5));
        }
    }
}  
