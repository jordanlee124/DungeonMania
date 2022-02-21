package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

public class MovingEntitiesTest {
    @Test
    public void testItemUsed() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        controller.currentDungeon.inventory.add(new Entity("bomb13_4", "bomb"));
        controller.currentDungeon.inventory.add(new Entity("invincibility_potion11_10", "invincibility_potion"));
        assertThrows(InvalidActionException.class, () -> {
            controller.tick("bomb1", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("invincibility_potion11_10", Direction.NONE);
        });
  
    }

    @Test
    public void testItemNotInInventory() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        //collect potion
        controller.currentDungeon.inventory.add(new Entity("potion_test", "invincibility_potion"));
        assertThrows(InvalidActionException.class, () -> {
            controller.tick("bomb13_4", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("potion_test", Direction.NONE);
        });
  
    }

    @Test
    public void testMovement() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Position start = controller.currentDungeon.player.getPosition();
        //player sshould nto have moved because of walls
        controller.tick(null, Direction.UP);
        assertTrue(start.equals(controller.currentDungeon.player.getPosition()));
        controller.tick(null, Direction.LEFT);
        assertTrue(start.equals(controller.currentDungeon.player.getPosition()));
        //player should move
        controller.tick(null, Direction.RIGHT);
        assertFalse(start.equals(controller.currentDungeon.player.getPosition()));
        controller.tick(null, Direction.LEFT);
        assertTrue(start.equals(controller.currentDungeon.player.getPosition()));
        controller.tick(null, Direction.DOWN);
        assertFalse(start.equals(controller.currentDungeon.player.getPosition()));
  
    }
}