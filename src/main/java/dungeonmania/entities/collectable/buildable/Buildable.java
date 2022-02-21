package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.*;

public abstract class Buildable extends CollectableEntity {

    public static final List<String> BUILDABLES_LIST= Arrays.asList("bow", "shield", "sceptre", "midnight_armour");

    /**
     * Constructor for Buildable
     * @param id
     * @param type
     */
    public Buildable(String id, String type) {
        super(id, type);
    }

    public abstract boolean isBuildable(List<Entity> inventory);
    public abstract Map<String, Integer> materialNeeded(List<Entity> inventory);
    public abstract HashMap<String, Integer> getRelevantMaterialCount(List<Entity> inventory);
    public abstract void subtractDurability(List<Entity> inventory);

    /**
     * A Factory Method that retrieves buildable type
     * @param type
     * @return Buildable
     */
    public static Buildable getBuildable(String type) {
        Buildable buildable = null;
        if (type.equalsIgnoreCase("shield")) {
            buildable = new Shield("shield", "shield");
        } else if (type.equalsIgnoreCase("bow")) {
            buildable = new Bow("bow", "bow");
        } else if (type.equalsIgnoreCase("sceptre")) {
            buildable = new Sceptre("sceptre", "sceptre");
        } else if (type.equalsIgnoreCase("midnight_armour")) {
            buildable = new Midnight_Armour("midnight_armour", "midnight_armour");
        }
        return buildable;
    }

}
