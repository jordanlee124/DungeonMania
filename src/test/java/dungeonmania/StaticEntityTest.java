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

public class StaticEntityTest {
    //wall already tested in charactertests

    //exit test

    @Test
    public void testExit() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("exit", "standard");
        controller.tick(null, Direction.RIGHT);
        //test if the game is now complete.
  
    }

    @Test
    public void testSwampTile() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampTileTest", "standard");
        Position swampTile = controller.currentDungeon.getEntity("swamp_tile4_1").getPosition();
        controller.tick(null, Direction.LEFT);
        Position mercenary = controller.currentDungeon.getEntity("mercenary5_1").getPosition();
        assertTrue(swampTile.getX() == mercenary.getX() && swampTile.getY() == mercenary.getY());
        controller.tick(null, Direction.LEFT);
        mercenary = controller.currentDungeon.getEntity("mercenary5_1").getPosition();
        assertTrue(swampTile.getX() == mercenary.getX() && swampTile.getY() == mercenary.getY());
        controller.tick(null, Direction.LEFT);
        mercenary = controller.currentDungeon.getEntity("mercenary5_1").getPosition();
        assertFalse(swampTile.getX() == mercenary.getX() && swampTile.getY() == mercenary.getY());
    }

    /*
    //boulder test
    @Test
    public void testBoulder() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse start = controller.newGame("boulders", "standard");
        DungeonResponse end  = start;
        //test pushing one boulder
        assertTrue(start.equals(end));
        end = controller.tick(null, Direction.RIGHT);
        assertFalse(start.equals(end));
        controller.tick(null, Direction.UP);
        start = controller.tick(null, Direction.RIGHT);
        end = start;
        assertTrue(start.equals(end));
        //cant push moroe than one boulder
        start = controller.tick(null, Direction.DOWN);
        assertTrue(start.equals(end));
    }

    //switch test
    //test bombs exploding when next to a switch

    //door test
    @Test
    public void testDoor() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse start = controller.newGame("doors", "standard");
        DungeonResponse end  = start;
        //test opening door without key
        assertTrue(start.equals(end));
        end = controller.tick(null, Direction.RIGHT);
        assertTrue(start.equals(end));
        //test open door with wrong key
        controller.tick(null, Direction.DOWN);
        start = controller.tick(null, Direction.UP);
        end = start;
        assertTrue(start.equals(end));
        start = controller.tick(null, Direction.RIGHT);
        assertTrue(start.equals(end));
        //test open door with right key
        controller.tick(null, Direction.LEFT);
        start = controller.tick(null, Direction.RIGHT);
        end = start;
        controller.tick(null, Direction.RIGHT);
        assertFalse(start.equals(end));

    }

    //portal test
    //test that player moves to correct location when going through portal using multiple porttals for different colours

    //zombie spawner test
    //make sure zombie is spawned every 20 ticks in standard mode and every 15 in hard mode

    @Test
    public void testSpawnerStandard() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse start = controller.newGame("spawners", "standard");
        DungeonResponse end  = start;
        //test spawn after 20 ticks
        assertTrue(start.equals(end));
        //15 ticks
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        end = controller.tick(null, Direction.NONE);
        //none should spawn after 15 ticks becuase its in standard mode
        assertTrue(start.equals(end));
        //5 more ticks
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        end = controller.tick(null, Direction.NONE);
        //one should have spawned at 20 ticks
        assertFalse(start.equals(end));

    }

    @Test
    public void testSpawnerHard() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse start = controller.newGame("spawners", "hard");
        DungeonResponse end  = start;
        //test spawn after 15 ticks
        assertTrue(start.equals(end));
        //15 ticks
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        end = controller.tick(null, Direction.NONE);
        //should spawn after 15 ticks becuase its in hard mode
        assertFalse(start.equals(end));
    }
    */
}