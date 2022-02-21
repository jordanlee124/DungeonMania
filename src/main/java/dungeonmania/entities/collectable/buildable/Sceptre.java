package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Moving.Mercenary;

public class Sceptre extends Buildable {

    private static final int arrowsNeeded = 2;
    private static final List<String> recipe = Arrays.asList("wood", "arrow", "sun_stone", "treasure", "key_1", "key_2");

    /**
     * Constructor for Sceptre
     * @param id
     * @param type
     */
    public Sceptre(String id, String type) {
        super(id, type);
        this.setDurability(5);
    }

    @Override
    public boolean isBuildable(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);

        if (!materialCount.isEmpty()) {
            if (materialCount.containsKey("sun_stone")) {
                if (materialCount.containsKey("wood")) {
                    if (materialCount.containsKey("treasure") || materialCount.containsKey("key_1") || materialCount.containsKey("key_2")) {
                        return true;
                    }
                } else if (materialCount.containsKey("arrow")) {
                    if (materialCount.get("arrow") >= arrowsNeeded) {
                        if (materialCount.containsKey("treasure") || materialCount.containsKey("key_1") || materialCount.containsKey("key_2")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);
        Map<String, Integer> returnMap = new HashMap<>();
        if (materialCount.containsKey("wood")) {
            returnMap.put("wood", 1);
        } else if (materialCount.containsKey("arrow")) {
            returnMap.put("arrow", 2);
        }

        if (materialCount.containsKey("treasure")) {
            returnMap.put("treasure", 1);
        } else if (materialCount.containsKey("key_1")) {
            returnMap.put("key_1", 1);
        } else if (materialCount.containsKey("key_2")) {
            returnMap.put("key_2", 1);
        }

        returnMap.put("sun_stone", 1);
        return returnMap;
    }

    @Override
    public HashMap<String, Integer> getRelevantMaterialCount(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = new HashMap<>();

        for (Entity item : inventory) {
            if (recipe.contains(item.getType()) && materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), materialCount.get(item.getType()) + 1);
            } else if (recipe.contains(item.getType()) && !materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), 1);
            }
        }
        return materialCount;
    }

    @Override
    public void subtractDurability(List<Entity> inventory) {   
        this.setDurability(this.getDurability() - 1);
        if (this.getDurability() == 0) {
            inventory.remove(this);
        }     
    }

    /**
     * Handles Effects of Sceptre (Brainwash Enemy for 10 ticks)
     * @param m
     * @param inventory
     */
    public void effect(Mercenary m, List<Entity> inventory) {
        m.setBrainWashed(true);
        m.setInteractable(false);
        subtractDurability(inventory);
    }
    
}
