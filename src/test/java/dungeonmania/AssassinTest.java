package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.ResourceBundle.Control;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.Assassin;
import dungeonmania.entities.Moving.Mercenary;


public class AssassinTest {
    @Test
    public void AssassinStats() {
        // test for if assassin has greater stats than mercenary
        Mercenary m = null;
        Assassin a = null;
        DungeonManiaController controller = new DungeonManiaController();
        //this loop is to make sure the mercenary doesnt spawn as an assasin
        while (m == null) {
            controller = new DungeonManiaController();
            controller.newGame("interactAssassin", "standard");
            for (Entity e : controller.currentDungeon.entities) {
                if (e instanceof Assassin) {
                    a = (Assassin)e;
                } else if (e instanceof Mercenary) {
                    m = (Mercenary)e;
                }
            }
        }
        assertTrue(a.getAttack() > m.getAttack());
        assertTrue(a.getHealth()> m.getHealth());
    }
    @Test
    public void playerBribesAssassin() {
        // test for if player bribes mercenary
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interactAssassin", "standard");
        Entity gold = new Entity("treasure_test", "treasure");
        controller.currentDungeon.inventory.add(gold);
        //should not be able to bribe without ring
        assertThrows(InvalidActionException.class ,() -> {
            controller.interact("assassin3_3");
        });
        // check if gold is gone from inventory
        assertFalse(controller.currentDungeon.inventory.isEmpty());
        //add the ring
        controller.currentDungeon.inventory.add(new Entity("ring_test", "one_ring"));
        //remove    
        controller.currentDungeon.inventory.remove(gold);
        //should not be able to bribe without gold
        assertThrows(InvalidActionException.class ,() -> {
            controller.interact("assassin3_3");
        });
        // check if ring is gone from inventory
        assertFalse(controller.currentDungeon.inventory.isEmpty());
        //add gold again
        controller.currentDungeon.inventory.add(gold);
        //should be able to bribe with ring and gold
        assertDoesNotThrow(() -> {
            controller.interact("assassin3_3");
        });
        // check if gold and ring is gone from inventory
        assertTrue(controller.currentDungeon.inventory.isEmpty());
    }
}
