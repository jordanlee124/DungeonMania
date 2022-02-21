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
import dungeonmania.entities.collectable.SunStone;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;

public class BribingTest {
    @Test
    public void AssassinBribingRingPlusTreasureTest() {
        // test for bribing assassin with ring and treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        
        assertDoesNotThrow(() -> {
            controller.interact("assassin3_4");
        });
        controller.tick(null, Direction.DOWN);
        assertTrue(controller.currentDungeon.player.getHealth() == 10);
    }

    @Test
    public void NotAssassinBribingRingPlusTreasureTest() {
        // test for not bribing assassin with ring and treasure and seeing if health goes down
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        
        
        controller.tick(null, Direction.DOWN);
        assertTrue(controller.currentDungeon.player.getHealth() != 10);
    }


    @Test
    public void AssassinBribingRingPlusStoneTest() {
        // test for bribing assassin with ring and stone
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        assertDoesNotThrow(() -> {
            controller.interact("assassin3_4");
        });
        controller.tick(null, Direction.LEFT);
        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        assertTrue(controller.currentDungeon.getItem("sun_stone") != null);
    }

    @Test
    public void NotAssassinBribingRingPlusStoneTest() {
        // test for not bribing assassin with ring and stone
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);

        controller.tick(null, Direction.LEFT);
        assertTrue(controller.currentDungeon.player.getHealth() != 10);
    }

    @Test
    public void MercenaryBribingTreasureTest() {
        // Mercenary bribing with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertDoesNotThrow(() -> {
            controller.interact("mercenary3_7");
        });
        controller.tick(null, Direction.DOWN);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        // Move to fight enemies
    }

    @Test
    public void MercenaryBribingStoneTest() {
        // Mercenary bribing with sun_stone
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bribing", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertDoesNotThrow(() -> {
            controller.interact("mercenary3_7");
        });
        controller.tick(null, Direction.DOWN);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);

        // making sure treasure and sun_stone arent used up
        assertTrue(controller.currentDungeon.getItem("sun_stone") != null);
        assertTrue(controller.currentDungeon.getItem("treasure") != null);
    }
}
