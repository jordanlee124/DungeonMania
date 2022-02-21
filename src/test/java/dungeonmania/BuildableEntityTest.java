package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.ModuleLayer.Controller;
import java.util.Arrays;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;
import dungeonmania.entities.collectable.buildable.Sceptre;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.exceptions.InvalidActionException;

public class BuildableEntityTest {
    @Test
    public void testCraftingBow() {
        // test for crafting a bow
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller.build("bow");
        });
    }

    @Test
    public void testCraftingSceptre() {
        // test for crafting a sceptre
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);

        assertDoesNotThrow(() -> {
            controller.build("sceptre");
        });
    }

    @Test
    public void testCraftingMidnightArmour_withoutZombie() {
        // test for crafting a midnight armour
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertDoesNotThrow(() -> {
            controller.build("midnight_armour");
        });
    }

    @Test
    public void testCraftingMidnightArmour_withZombie() {
        // test for crafting a midnight armour when zombie exists on map
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting_zombie", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("midnight_armour");
        });
    }

    @Test
    public void testNotEnoughMatForBow() {
        // test for crafting a bow when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("bow");
        });
    }

    @Test
    public void testCraftingShield_Treasure() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    @Test
    public void testCraftingShield_Key() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    @Test
    public void testNotEnoughMatForShield() {
        // test for crafting a Shield when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("shield");
        });
    }

    @Test
    public void testBuildableDurability() {
        // test for buildable items durability
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        // collect items for shield and bow
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // create shield and bow
        assertDoesNotThrow(() -> {
            controller.build("shield");
            controller.build("bow");
        });

        Bow bow = (Bow) controller.currentDungeon.getItem("bow");
        bow.setDurability(1);
        Shield shield = (Shield) controller.currentDungeon.getItem("shield");
        shield.setDurability(1);
        
        // look for and fight enemies        
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        
        // shield durability == 0 && not in inventory
        assertNull(controller.currentDungeon.getItem("shield"));

        // bow durability == 0 && not in inventory
        assertNull(controller.currentDungeon.getItem("bow")); 
        
    }

    @Test
    public void testShieldEffect() {
        // Test if shield halves incoming damage
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonManiaController controller2 = new DungeonManiaController();
        
        controller1.newGame("crafting", "standard");
        controller2.newGame("crafting", "standard");

        controller1.tick(null, Direction.UP);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller1.build("shield");
        });

        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);

        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);

        assertTrue(controller1.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
    }

    @Test
    public void testMidnight_ArmourDefenceEffect() {
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonManiaController controller2 = new DungeonManiaController();

        controller1.newGame("crafting", "standard");
        controller2.newGame("crafting", "standard");

        controller1.tick(null, Direction.DOWN);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.DOWN);

        assertDoesNotThrow(() -> {
            controller1.build("midnight_armour");
        });

        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);

        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);

        assertTrue(controller1.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
    }

    @Test
    public void testArmourStacking() {
        DungeonManiaController controller1 = new DungeonManiaController();  // no armour
        DungeonManiaController controller2 = new DungeonManiaController();  // armour
        DungeonManiaController controller3 = new DungeonManiaController();  // armour shield
        DungeonManiaController controller4 = new DungeonManiaController();  // armour shield midnight_armour
        DungeonManiaController controller5 = new DungeonManiaController();  // armour midnight_armour
        DungeonManiaController controller6 = new DungeonManiaController();  // shield midnight_armour
    
        controller1.newGame("crafting", "standard");
        controller2.newGame("crafting", "standard");
        controller3.newGame("crafting", "standard");
        controller4.newGame("crafting", "standard");
        controller5.newGame("crafting", "standard");
        controller6.newGame("crafting", "standard");

        JSONObject jo1 = new JSONObject("{x: 4" + ",y: 2" + ",type: mercenary}");

        controller1.currentDungeon.addEntity(new Mercenary(jo1));
        controller2.currentDungeon.addEntity(new Mercenary(jo1));
        controller3.currentDungeon.addEntity(new Mercenary(jo1));
        controller4.currentDungeon.addEntity(new Mercenary(jo1));
        controller5.currentDungeon.addEntity(new Mercenary(jo1));
        controller6.currentDungeon.addEntity(new Mercenary(jo1));

        // Adding items to inventory that are needed for test
        controller2.currentDungeon.inventory.add(new Armour("armour", "armour"));

        controller3.currentDungeon.inventory.add(new Armour("armour", "armour"));
        controller3.currentDungeon.inventory.add(new Shield("shield", "shield"));

        controller4.currentDungeon.inventory.add(new Armour("armour", "armour"));
        controller4.currentDungeon.inventory.add(new Shield("shield", "shield"));
        controller4.currentDungeon.inventory.add(new Midnight_Armour("midnight_armour", "midnight_armour"));

        controller5.currentDungeon.inventory.add(new Armour("armour", "armour"));
        controller5.currentDungeon.inventory.add(new Midnight_Armour("midnight_armour", "midnight_armour"));

        controller6.currentDungeon.inventory.add(new Shield("shield", "shield"));
        controller6.currentDungeon.inventory.add(new Midnight_Armour("midnight_armour", "midnight_armour"));

        Armour.durability = 10;
        controller1.tick(null, Direction.NONE);

        Armour.durability = 10;
        controller2.tick(null, Direction.NONE);

        Armour.durability = 10;
        controller3.tick(null, Direction.NONE);

        Armour.durability = 10;
        controller4.tick(null, Direction.NONE);

        Armour.durability = 10;
        controller5.tick(null, Direction.NONE);
        
        Armour.durability = 10;
        controller6.tick(null, Direction.NONE);

        // Check health differences between the controller instances
        assertTrue(controller2.currentDungeon.player.getHealth() > controller1.currentDungeon.player.getHealth());
        assertTrue(controller3.currentDungeon.player.getHealth() > controller1.currentDungeon.player.getHealth());
        assertTrue(controller4.currentDungeon.player.getHealth() > controller1.currentDungeon.player.getHealth());
        assertTrue(controller5.currentDungeon.player.getHealth() > controller1.currentDungeon.player.getHealth());
        assertTrue(controller6.currentDungeon.player.getHealth() > controller1.currentDungeon.player.getHealth());

        assertTrue(controller3.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
        assertTrue(controller4.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
        assertTrue(controller5.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
        assertTrue(controller6.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());

        assertTrue(controller4.currentDungeon.player.getHealth() > controller3.currentDungeon.player.getHealth());
        assertTrue(controller5.currentDungeon.player.getHealth() > controller3.currentDungeon.player.getHealth()); // HP of player in controller 5 > controller 3 as midnight armour deals additional damage to the enemy!
        assertTrue(controller6.currentDungeon.player.getHealth() > controller3.currentDungeon.player.getHealth());

        assertTrue(controller5.currentDungeon.player.getHealth() < controller4.currentDungeon.player.getHealth());
        assertTrue(controller6.currentDungeon.player.getHealth() < controller4.currentDungeon.player.getHealth());

        assertTrue(controller6.currentDungeon.player.getHealth() == controller5.currentDungeon.player.getHealth());

    }

    @Test
    public void testSceptreEffect() {
        DungeonManiaController controller1 = new DungeonManiaController();

        controller1.newGame("crafting", "standard");

        //Create 2 mercenaries
        JSONObject jo1 = new JSONObject("{x: 4" + ",y: 2" + ",type: mercenary}");
        JSONObject jo2 = new JSONObject("{x: 4" + ",y: 3" + ",type: mercenary}");

        Mercenary m1 = new Mercenary(jo1);
        Mercenary m2 = new Mercenary(jo2);


        controller1.currentDungeon.addEntity(m1);
        controller1.currentDungeon.addEntity(m2);

        //Add Sceptre into inventory
        controller1.currentDungeon.inventory.add(new Sceptre("sceptre", "sceptre"));

        //Check prerequisites
        assertEquals(5, ((Sceptre) controller1.currentDungeon.getItem("sceptre")).getDurability());
        assertTrue(controller1.currentDungeon.getEntity("mercenary4_2").isInteractable());
        assertTrue(controller1.currentDungeon.getEntity("mercenary4_3").isInteractable());
        assertFalse(controller1.currentDungeon.getPlayer().haveAlly());

        //Check to make sure only 1 ally is possible at a time
        assertDoesNotThrow(() -> {
            controller1.interact("mercenary4_2");
        });

        assertThrows(InvalidActionException.class, () -> {
            controller1.interact("mercenary4_3");
        });

        assertFalse(controller1.currentDungeon.getEntity("mercenary4_2").isInteractable());
        assertTrue(controller1.currentDungeon.getEntity("mercenary4_3").isInteractable());
        assertTrue(controller1.currentDungeon.getPlayer().haveAlly());
        assertEquals(4, ((Sceptre) controller1.currentDungeon.getItem("sceptre")).getDurability());

        //After 11 ticks (Since sceptre effect runs from tick 0-10 and disappears on the 11th tick)
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);
        
        //Player does not have ally and mercenary is not brainwashed anymore
        assertTrue(controller1.currentDungeon.getEntity("mercenary4_2").isInteractable());
        assertFalse(controller1.currentDungeon.getPlayer().haveAlly());

        //Interact with other mercenary
        assertDoesNotThrow(() -> {
            controller1.interact("mercenary4_3");
        });

        //Check post interact state
        assertFalse(controller1.currentDungeon.getEntity("mercenary4_3").isInteractable());
        assertTrue(controller1.currentDungeon.getEntity("mercenary4_2").isInteractable());
        assertTrue(controller1.currentDungeon.getPlayer().haveAlly());
        assertEquals(3, ((Sceptre) controller1.currentDungeon.getItem("sceptre")).getDurability());

    }
}
