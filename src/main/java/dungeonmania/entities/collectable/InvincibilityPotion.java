package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.Entity;
/**
 * InvincibilityPotion Class which extends CollectableEntity
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class InvincibilityPotion extends CollectableEntity {
    /**
     * Contructor for InvincibilityPotion
     * @param entity  JSONObject
     */
    public InvincibilityPotion(JSONObject entity) {
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
        Entity invincibility = null;
        if (currentDungeon.getGameMode().equalsIgnoreCase("Hard")) {
            if (itemUsed != null) {
                if (itemUsed.contains("invincibility_potion")) {
                    invincibility = currentDungeon.getItem("invincibility_potion");
                    inventory.remove(invincibility);
                }
            }
            currentDungeon.setItems(inventory);
            currentDungeon.setPlayer(player);
            return currentDungeon;
        }
        if (itemUsed != null) {
            if (itemUsed.contains("invincibility_potion")) {
                invincibility = currentDungeon.getItem("invincibility_potion");
                
                player.setInvincibilityPotionEffect(true);
            }
            if (itemUsed.contains("invincibility_potion") && player.isInvincibilityPotionEffect() == true) {
                inventory.remove(invincibility);
            } 
        }
        currentDungeon.setItems(inventory);
        currentDungeon.setPlayer(player);
        
        return currentDungeon;
        
    }
    
}
