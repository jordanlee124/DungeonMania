package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;

public class PotionsTest {
    @Test
    public void healthPotionNotUsedTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        assertTrue(controller.currentDungeon.player.getHealth() != 10);
    }


    @Test
    public void healthPotionUsedTest() {
        // test for healthPotion used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        controller.tick("health_potion2_1", Direction.NONE);
        assertTrue(controller.currentDungeon.player.getHealth() == 10);
    }

    @Test
    public void invincibilityPotionUsedTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick("invincibility_potion3_1", Direction.NONE);
        controller.tick(null, Direction.DOWN);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        // Move to fight enemies
    }

    @Test
    public void invincibilityPotionNotUsedTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertTrue(controller.currentDungeon.player.getHealth() != 10);
        // Move to fight enemies
    }

    @Test
    public void mercenariesRunningAwayTest() {
        // test for invincibility_potion used and mercenaries running away
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick("invincibility_potion3_1", Direction.NONE);
        controller.tick(null, Direction.RIGHT);

        int x = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getX();
        int y = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getY();
        assertTrue(x == 2 && y == 1);
        controller.tick(null, Direction.RIGHT);
        x = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getX();
        y = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getY();
        assertTrue(x == 1 && y == 1);
        // Move to fight enemies
    }

    @Test
    public void invisibilityPotionTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick("invisibility_potion4_1", Direction.NONE);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        int x = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getX();
        int y = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getY();
        assertTrue(x == 2 && y == 1);
        controller.tick(null, Direction.RIGHT);
        x = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getX();
        y = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getY();
        assertTrue(x == 2 && y == 1);
        // Move to fight enemies
    }


    @Test
    public void invisibilityPassByTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick("invisibility_potion4_1", Direction.NONE);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);

        int x = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getX();
        int y = controller.currentDungeon.getEntity("mercenary3_5").getPosition().getY();
        assertTrue(x == 2 && y == 1);
    }

    @Test
    public void invincibilityNotWorkInHardModeTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "hard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick("invincibility_potion3_1", Direction.NONE);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertTrue(controller.currentDungeon.player.getHealth() != 10);
    }


}
