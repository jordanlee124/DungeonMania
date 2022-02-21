package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

public class bombTest {
    @Test
    public void testBomb() {
        // test for crafting a bow
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bombTest", "standard");
        
        //Picks up bomb
        controller.tick(null, Direction.DOWN);
        assertTrue(!(controller.currentDungeon.getItem("bomb") == null));
        assertTrue(controller.currentDungeon.getEntity("bomb1_2") == null);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        //Place bomb down
        controller.tick("bomb1_2", Direction.NONE);
        assertTrue(controller.currentDungeon.getItem("bomb") == null);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        //Push boudler into switch
        controller.tick(null, Direction.RIGHT);
        //Walls within one radius of bomb should be removed
        assertTrue(controller.currentDungeon.getEntity("wall4_1") == null);
        assertTrue(controller.currentDungeon.getEntity("wall5_1") == null);
        assertTrue(controller.currentDungeon.getEntity("wall5_2") == null);
        assertTrue(controller.currentDungeon.getEntity("wall5_3") == null);
        assertTrue(controller.currentDungeon.getEntity("wall4_3") == null);
        assertTrue(controller.currentDungeon.getEntity("boulder2_2") == null);
        assertTrue(controller.currentDungeon.getEntity("switch3_2") == null);
        assertTrue(controller.currentDungeon.getEntity("bomb1_2") == null);
    }

}
