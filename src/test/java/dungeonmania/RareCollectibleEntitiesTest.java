package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.*;
import dungeonmania.util.*;

public class RareCollectibleEntitiesTest {
    @Test
    public void TheOneRingGoneTest() { // Test if character respawns with full health after getting killed
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("oneRingMap", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        //player runs into hostile entity
        //Check if player respawns
        assertTrue(controller.currentDungeon.player.getHealth() > 9);
        //Check if one ring is one use
        assertTrue(controller.currentDungeon.inventory.isEmpty());
    }
}