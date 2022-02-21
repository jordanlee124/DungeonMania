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

public class SunStoneTest {
    @Test
    public void DoorOpensWithSunStoneTest() {
        // Mercenary bribing with sun_stone
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("sunStoneDoor", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.currentDungeon.entry, controller.currentDungeon.player.getPosition());
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        int x = controller.currentDungeon.player.getPosition().getX();
        int y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 1 && y == 3);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        x = controller.currentDungeon.player.getPosition().getX();
        y = controller.currentDungeon.player.getPosition().getY();
        assertTrue(x == 2 && y == 1);
    }
}
