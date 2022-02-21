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
import dungeonmania.entities.Moving.Hydra;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.collectable.AndurilSword;


public class HydraTest {
    @Test
    public void HydraSpawnTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "hard");
        //wait 50 ticks for spawn
        for (int i = 0; i <=51; i++) {
            controller.tick(null, Direction.UP);
        }

        //check if it is spawned
        boolean spawned = false;
        for (Entity e : controller.currentDungeon.getEntities()) {
            if (e instanceof Hydra) {
                spawned = true;
            }
        }
        assertTrue(spawned);
    }
    @Test
    public void HydraHealthRegenTest() {
        double increase = 0;
        for (int i = 0; i < 1000; i++) {
            //when players health is 5 the winrate should be 50%
            //take an average of health when fighing hydra
            DungeonManiaController controller = new DungeonManiaController();
            controller.newGame("hydra", "hard");
            controller.currentDungeon.getPlayer().setHealth(100);
            Hydra hydra = (Hydra) controller.currentDungeon.getEntity("hydra0_0");
            hydra.setHealth(2);
            //colide with hydra
            controller.tick(null, Direction.NONE);
            if (controller.currentDungeon.getPlayer().getHealth() < 99.8) {
                increase += 1;
            }
        }
        
        //check if winrate is 50%. if it is it means the hydra will heal instead of take damage 50% of the time
        assertTrue(increase/1000 > 0.45 && increase/1000 < 0.55);
    }
    @Test
    public void HydraAndurilTest() {
    // Test whether player can kill hydra with anduril
    // normally player cannot kill hydraw without anduril
    DungeonManiaController controller = new DungeonManiaController();
    controller.newGame("AndurilTest", "hard");

    // Pick up Anduril Sword
    controller.tick(null, Direction.RIGHT);
    // Fight Hydra
    controller.tick(null, Direction.RIGHT);
    controller.tick(null, Direction.RIGHT);
    AndurilSword a = (AndurilSword) controller.currentDungeon.getItem("anduril");
    // Anduril Sword loses durability
    assertTrue(a.getDurability() < 10);

    // Hydra dies    
    assertTrue(controller.currentDungeon.getEntity("hydra20") == null);
    }
}
