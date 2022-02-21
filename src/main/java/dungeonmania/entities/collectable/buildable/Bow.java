package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Moving.MovingEntity;
import dungeonmania.entities.Entity;

public class Bow extends Buildable {

    private static final List<String> recipe = Arrays.asList("wood", "arrow");
    private static final int woodNeeded = 1;
    private static final int arrowNeeded = 3;

    /**
     * Constructor for Bow
     * @param id
     * @param type
     */
    public Bow(String id, String type) {
        super(id, type);
        this.setDurability(20);
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

        if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("arrow")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("arrow") >= arrowNeeded)) {
                return true;
            } 
        }
            
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Entity> inventory) {
        
        Map<String, Integer> returnMap = Map.of(
            "wood", 1,
            "arrow", 3
        );

        return returnMap;
    }    

    /**
     * Handles Effect of Bow (Allows player to attack once more)
     * @param e
     * @param enemyHP
     * @param playerHP
     * @param playerAD
     * @param inventory
     */
    public void effect(MovingEntity e, double enemyHP, double playerHP, double playerAD, List<Entity> inventory) {
        e.setHealth(enemyHP - ((playerHP * playerAD) / 5));
        this.subtractDurability(inventory);
    }

    @Override
    public void subtractDurability(List<Entity> inventory) {
        this.setDurability(this.getDurability() - 1);
        if (this.getDurability() == 0) {
            inventory.remove(this);
        }
    }

}
