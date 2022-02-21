package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.entities.*;


public class MercenaryBattleTest {
    @Test
    public void mercenaryBattleRadius() {
        // test for if player bribes mercenary
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.tick(null, Direction.DOWN);
        // will battle spider

        // check if mercenary moved 2 tiles
        int x = controller.currentDungeon.getEntity("mercenary3_3").getPosition().getX();
        int y = controller.currentDungeon.getEntity("mercenary3_3").getPosition().getY();
        assertTrue(x == 2 && y == 2);
    }
}
