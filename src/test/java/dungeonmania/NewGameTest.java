package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class NewGameTest {
    @Test
    public void testIllegalGameMode() {
        // Test when illegal game mode is specified
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> {controller.newGame("advanced", "GameModeDoesntExist");});

    }

    @Test
    public void testIllegalGameName() {
        DungeonManiaController controller = new DungeonManiaController();
        // Test when illegal game name is specified
        assertThrows(IllegalArgumentException.class, () -> {controller.newGame("Game1", "Peacful");});
    }
}
