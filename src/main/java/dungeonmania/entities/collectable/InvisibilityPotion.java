package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.Entity;
/**
 * InvisibilityPotion Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class InvisibilityPotion extends CollectableEntity {
    /**
     * Contructor for InvisibilityPotion
     * @param entity  JSONObject
     */
    public InvisibilityPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
    /**
     * Adds effects for the potion
     * @param currentDungeon
     * @param itemUsed
     * @param player
     * @param inventory
     * @return currentDungeon
     */
    public static Dungeon addEffects(Dungeon currentDungeon, String itemUsed, Player player, List<Entity> inventory) {
        Entity invisibility = null;
        
        if (itemUsed != null) {
            if (itemUsed.contains("invisibility_potion")) {
                invisibility = currentDungeon.getItem("invisibility_potion");
                player.setInvisibilityPotionEffect(true);
            }
            if (itemUsed.contains("invisibility_potion") && player.isInvisibilityPotionEffect() == true) {
                inventory.remove(invisibility);
            } 
        }
        currentDungeon.setItems(inventory);
        currentDungeon.setPlayer(player);
        return currentDungeon;
    }
}
