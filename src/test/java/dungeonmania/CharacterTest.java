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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

public class CharacterTest {
    @Test
    public void testItemUsed() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("characterTest", "standard");
        //pickup health potion and bomb
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        assertThrows(IllegalArgumentException.class, () -> {
            controller.tick("sword2_2", Direction.NONE);
        });

        assertDoesNotThrow(() -> {
            controller.tick("bomb1_2", Direction.NONE);
            controller.tick("health_potion2_1", Direction.NONE);
        });
    }
    @Test
    public void testItemNotInInventory() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("characterTest", "standard");
        //move to collect potion

        assertThrows(InvalidActionException.class, () -> {
            controller.tick("bomb13_4", Direction.NONE);
        });
    }

    @Test
    public void testMovement() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        //player sshould nto have moved because of walls
        controller.tick(null, Direction.UP);
        int x = controller.currentDungeon.player.getPosition().getX();
        int y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 1 && y == 1);
        controller.tick(null, Direction.LEFT);
        x = controller.currentDungeon.player.getPosition().getX();
        y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 1 && y == 1);
        //player should move
        controller.tick(null, Direction.RIGHT);
        x = controller.currentDungeon.player.getPosition().getX();
        y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 2 && y == 1);
        controller.tick(null, Direction.LEFT);
        x = controller.currentDungeon.player.getPosition().getX();
        y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 1 && y == 1);
        controller.tick(null, Direction.DOWN);
        x = controller.currentDungeon.player.getPosition().getX();
        y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 1 && y == 2);

    }
}