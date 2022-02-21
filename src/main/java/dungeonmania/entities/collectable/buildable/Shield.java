package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;

public class Shield extends Buildable {

    private static final int woodNeeded = 2;
    private static final int keyNeeded = 1;
    private static final int treasureNeeded = 1;
    private static final List<String> recipe = Arrays.asList("wood", "treasure", "key_1", "key_2");

    public Shield(String id, String type) {
        super(id, type);
        this.setDurability(10);
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
    public boolean isBuildable(List<Entity> inventory) {

        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);

        if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("key_1")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("key_1") >= keyNeeded)) {
                return true;
            } 
        } else if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("treasure")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("treasure") >= treasureNeeded)) {
                return true;
            }
        } else if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("key_2")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("key_2") >= keyNeeded)) {
                return true;
            }
        }
            
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Entity> inventory) {
        
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);
        Map<String, Integer> returnMap = new HashMap<>();

        if (materialCount.containsKey("treasure")) {
            returnMap = Map.of(
                "wood", 2,
                "treasure", 1
            );
        } else if (materialCount.containsKey("key_1")) {
            returnMap = Map.of(
                "wood", 2,
                "key_1", 1
            );
        } else if (materialCount.containsKey("key_2")) {
            returnMap = Map.of(
                "wood", 2,
                "key_2", 1
            );
        }
        return returnMap;
    }

    public double effect(double damage, List<Entity> inventory) {
        this.subtractDurability(inventory);
        return damage/2;
    }

    @Override
    public void subtractDurability(List<Entity> inventory) {
        this.setDurability(this.getDurability() - 1);
        if (this.getDurability() == 0) {
            inventory.remove(this);
        }
    }

}
