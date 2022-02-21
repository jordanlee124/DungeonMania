package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.CompositeGoals;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import spark.utils.IOUtils;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.Assassin;
import dungeonmania.entities.Moving.Hydra;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Moving.Spider;
import dungeonmania.entities.Moving.Zombie;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Exit;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.buildable.Buildable;
import dungeonmania.entities.collectable.buildable.Sceptre;
import dungeonmania.entities.collectable.Bomb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DungeonManiaController
 * @author Gio Ko, Neeraj Mirashi, Michael Earey, Jordan Lee
 *
 */
public class DungeonManiaController {
    Dungeon currentDungeon;
    int ticknum;
    int invincibilityTicks;
    int invisibilityTicks;
    int hydratick;

    public DungeonManiaController() {
        this.ticknum = 0;
        this.invincibilityTicks = 0;
        this.invisibilityTicks = 0;
        this.hydratick = 0;
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        //create list of entity response based on json from dungeon
        JSONObject obj;
        
        // If gamemode doesnt exist
        if (!gameMode.equalsIgnoreCase("standard") && !gameMode.equalsIgnoreCase("peaceful") && !gameMode.equalsIgnoreCase("hard")) {
            throw new IllegalArgumentException();
        }

        
        try {
            obj = new JSONObject(FileLoader.loadResourceFile("/dungeons" + "/" + dungeonName + ".json"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        Dungeon newDungeon = new Dungeon(dungeonName, obj, gameMode);
        currentDungeon = newDungeon;
        return newDungeon.createResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        //turn a dungeon class into a .json file and save it
        JSONArray entities = new JSONArray();
        JSONObject saveGame = new JSONObject();
        //width, height, entities
        for (Entity e : currentDungeon.entities) {
            //x, y, type
            if ((e instanceof Door) || (e instanceof Key)) {
                entities.put(new JSONObject("{x:" + e.getPosition().getX() + ",y:" + e.getPosition().getY() + ",type:" + e.getType() +  ",key:" + e.getKey() + "}"));
            } else {
                entities.put(new JSONObject("{x:" + e.getPosition().getX() + ",y:" + e.getPosition().getY() + ",type:" + e.getType() + "}"));
            }
        }
        saveGame.put("entities", entities);
        saveGame.put("gamemode", currentDungeon.gameMode);
        //turn into file
        FileWriter filewriter;
        try {
            File file = new File("src" + File.separator + "main" + File.separator + "java" + File.separator + "dungeonmania" + File.separator + "saves" + File.separator + name + ".json");
            file.setWritable(true);
            file.setReadable(true);
            file.createNewFile();
            filewriter = new FileWriter(file);
            file.getAbsolutePath();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return null;
        }

        try {
            filewriter.write(saveGame.toString());
            filewriter.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return null;
        }

        return currentDungeon.createResponse();

    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        //should be the same as new game
        //create list of entity response based on json from dungeon
        JSONObject obj;
        try {
            InputStream is = new FileInputStream("src" + File.separator + "main" + File.separator + "java" + File.separator + "dungeonmania" + File.separator + "saves" + File.separator + name + ".json");
            obj = new JSONObject(IOUtils.toString(is));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        String gameMode = obj.getString("gamemode");
        // If gamemode doesnt exist
        if (!gameMode.equalsIgnoreCase("standard") && !gameMode.equalsIgnoreCase("peaceful") && !gameMode.equalsIgnoreCase("hard")) {
            throw new IllegalArgumentException();
        }
        
        Dungeon newDungeon = new Dungeon(name, obj, gameMode);
        currentDungeon = newDungeon;
        return newDungeon.createResponse();
    }

    public List<String> allGames() {
        File folder = new File("src/main/java/dungeonmania/saves");
        File[] listOfFiles = folder.listFiles();
        List<String> result = new ArrayList<String>();
        for (File f : listOfFiles) {
            result.add(f.getName().replace(".json", ""));
        }
        return result;
    }

    public boolean checkIfDungeonExists(String dungeonName, ArrayList<String> dungeonNames) {
        boolean exists = false;
        for (String name : dungeonNames) {
            if (name.equals(dungeonName)) {
                exists = true;
            }
        }
        return exists;
    }
    
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        if (currentDungeon == null) {
            throw new NullPointerException("YOU ARE DEAD!");
        }
        if (itemUsed != null) {
            Entity item = currentDungeon.getItemUsed(itemUsed);
            if (currentDungeon.getItemUsed(itemUsed) == null) {
                throw new InvalidActionException("No item in inventory");
            }
            else if (item.getType().equals("bomb") || item.getType().equals("health_potion") || item.getType().equals("invincibility_potion") 
                    || item.getType().equals("invisibility_potion")) {
                    if (item.getType().equals("bomb")) {
                        Bomb bomb = (Bomb) item;
                        bomb.placeBomb(currentDungeon);
                        currentDungeon.removeItem("bomb");
                    }
            }
            else throw new IllegalArgumentException("itemUsed is not a valid item");
        }

        //gets the item that is used 
        if (ticknum >= 25 && currentDungeon.spiderCount <= 4) {
            currentDungeon = Spider.spawn(currentDungeon);
            currentDungeon.spiderCount++;
            if (Math.random() <= 0.3) {
                currentDungeon = Assassin.spawn(currentDungeon, currentDungeon.entry);
            } else {
                currentDungeon = Mercenary.spawn(currentDungeon, currentDungeon.entry);
            }
            this.ticknum = 0;
        }
        this.ticknum++;

        // POTION LOGIC
        // Invincibility potion
        if (invincibilityTicks >= 10 && !currentDungeon.gameMode.equalsIgnoreCase("Hard")) {
            currentDungeon.player.setInvincibilityPotionEffect(false);

            this.invincibilityTicks = 0;
        }
        if (currentDungeon.player.isInvincibilityPotionEffect() && !currentDungeon.gameMode.equalsIgnoreCase("Hard")) {
            
            this.invincibilityTicks++;
        }

        
        currentDungeon = InvincibilityPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);

        // Invisibility potion
        if (invisibilityTicks >= 10) {
            currentDungeon.player.setInvisibilityPotionEffect(false);
            this.invisibilityTicks = 0;
        }
        if (currentDungeon.player.isInvisibilityPotionEffect()) {
            this.invisibilityTicks++;
            
        }
        currentDungeon = InvisibilityPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);

        //hydra spawning
        if (currentDungeon.gameMode.equalsIgnoreCase("hard")) {
            if (this.hydratick >= 50) {
                Hydra.spawn(currentDungeon, currentDungeon.entry);
                this.hydratick = 0;
            }
            this.hydratick++;
        }
        

        // Health potion
        currentDungeon = HealthPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);

        // ENEMY PATHING
        currentDungeon.player.move(currentDungeon.player.getPosition().translateBy(movementDirection), currentDungeon.getWalls(), currentDungeon.width, currentDungeon.height, Direction.NONE);
        if (!currentDungeon.gameMode.equalsIgnoreCase("Peaceful") && !currentDungeon.player.isInvisibilityPotionEffect()) {
            // making sure that enemy interactions dont happen when on the peaceful game mode
            currentDungeon.battle(currentDungeon);
            if (currentDungeon.player == null) {
                currentDungeon = null;
                throw new NullPointerException("YOU ARE DEAD!");
            }
        }
        
        currentDungeon.pathing(movementDirection, currentDungeon.width, currentDungeon.height);

        // ENEMY PATHING
        if (!currentDungeon.gameMode.equalsIgnoreCase("Peaceful") && !currentDungeon.player.isInvisibilityPotionEffect()) {
            // making sure that enemy interactions dont happen when on the peaceful game mode
            currentDungeon.battle(currentDungeon);
            if (currentDungeon.player == null) {
                currentDungeon = null;
                throw new NullPointerException("YOU ARE DEAD!");
            }
        }
        //mercenary moves again if battling
        currentDungeon.MercenaryBattleMovement(currentDungeon);
        currentDungeon.getPlayer().setBattling(false);

        List<Spawner> spawners = new ArrayList<>();
        for (Entity e : currentDungeon.entities) {
            //spawn zombies
            if (e instanceof Spawner) {
                spawners.add((Spawner)e);
            }
        }
        for (Spawner s : spawners) {
            s.spawn(currentDungeon);
        }
        // SIMPLE AND COMPLEX GOALS
        for (Entity e : currentDungeon.entities) {
            //boulder movement and floor switch
            if (e instanceof Boulder) {
                currentDungeon.player = ((Boulder)e).move(movementDirection, currentDungeon.player, currentDungeon.entities);
            }
            if (e instanceof FloorSwitch) {
                ((FloorSwitch)e).trigger(currentDungeon.entities);
            }
            //doors
            if (e instanceof Door) {
                currentDungeon = ((Door)e).unlock(currentDungeon.entities, currentDungeon.inventory, currentDungeon, currentDungeon.player, movementDirection);
            }
            if (e instanceof Portal) {
                if (e.getPosition().equals(currentDungeon.player.getPosition())) {
                    currentDungeon.player.setPosition(((Portal)e).getCoords().translateBy(movementDirection));
                }
            }
            if (e instanceof Mercenary) {
                Mercenary notInBattle = (Mercenary)e;
                if (currentDungeon.player.isInvincibilityPotionEffect()) {
                    notInBattle.setInBattle(false);
                } else {
                    notInBattle.setInBattle(true);
                }
                
            }
        }
        
        //BOMB LOGIC
        List<Bomb> bombs = new ArrayList<>();
        for (Entity e : currentDungeon.getEntities()) {
            // bomb explosion
            if (e instanceof Bomb) {
                Bomb b = (Bomb) e;
                if (b.isActivated(currentDungeon) && b.isPlaced()) {
                    bombs.add(b);
                }
            }
        }
        for (Bomb bomb : bombs) {
            bomb.explode(currentDungeon);
            currentDungeon.removeEntity(bomb.getId());
        }

        // Enemies Brainwashed by Sceptre
        for (Entity e : currentDungeon.entities) {
            if (e instanceof Mercenary || e instanceof Assassin) {
                Mercenary m = (Mercenary) e;
                if (m.isBrainWashed()) {
                    if (m.getBrainWashTick() >= 10) {
                        m.setBrainWashed(false);
                        m.setInteractable(true);
                        m.setBrainWashTick(0);
                        currentDungeon.getPlayer().setAlly(false);
                    } else {
                        m.setBrainWashTick(m.getBrainWashTick() + 1);
                    }
                }
            }
        }
        
        
        // ITEM PICKUP
        currentDungeon.itemPickup();
        if (currentDungeon.goalTree != null && !currentDungeon.goalTree.isComplete()) {
            currentDungeon.goalTree.checkGoalState(currentDungeon.entities, currentDungeon.player);
            currentDungeon.goalTree.checkComplete();
        }
        return currentDungeon.createResponse();
    }
    
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = currentDungeon.getEntity(entityId);
        if (currentDungeon.getEntity(entityId) == null) {
            throw new IllegalArgumentException("entityId is not a valid entity ID");
        } else if (entity instanceof Mercenary) {
            if (!currentDungeon.existsBrainwashedEntity(currentDungeon.getEntities())) {
                if (entity instanceof Assassin) {
                    Assassin assassin = (Assassin) currentDungeon.getEntity(entityId);
                    // Bribing the assassin with either (one_ring + treasure) or (one_ring + sun_stone) with preference for (one_ring + sun_stone)
                    if (assassin.isInBribableRange(currentDungeon.getPlayer().getPosition())) {
                        if (currentDungeon.getItem("sceptre") != null) {
                            ((Sceptre) (currentDungeon.getItem("sceptre"))).effect(assassin, currentDungeon.inventory);
                            currentDungeon.getPlayer().setAlly(true);
                        } else if (currentDungeon.getItem("one_ring") != null && currentDungeon.getItem("sun_stone") != null) {
                            // Use one_ring but dont use sun_stone
                            currentDungeon.removeItem("one_ring");
                            assassin.setBribed(true);
                            assassin.setInteractable(false);
                            currentDungeon.getPlayer().setAlly(true);
                        } else if (currentDungeon.getItem("one_ring") != null && currentDungeon.getItem("treasure") != null) {
                            // Use one_ring and treasure
                            currentDungeon.removeItem("treasure");
                            currentDungeon.removeItem("one_ring");
                            assassin.setBribed(true);
                            assassin.setInteractable(false);
                            currentDungeon.getPlayer().setAlly(true);
                        } else {
                            // Throw exception if player doesnt have either (one_ring + treasure) or (one_ring + sun_stone)
                            throw new InvalidActionException("Missing materials to bribe Assassin");
                        }
                    }
                    else {
                        throw new InvalidActionException("Mercenary not in range");
                    }
                } else {
                    Mercenary mercenary = (Mercenary) currentDungeon.getEntity(entityId);
                    // Bribing the Mercenary with either (one_ring + treasure) or (one_ring + sun_stone) with preference for (one_ring + sun_stone)
                    if (mercenary.isInBribableRange(currentDungeon.getPlayer().getPosition())) {
                        if (currentDungeon.getItem("sceptre") != null) {
                            ((Sceptre) (currentDungeon.getItem("sceptre"))).effect(mercenary, currentDungeon.inventory);
                            currentDungeon.getPlayer().setAlly(true);
                        } else if (currentDungeon.getItem("sun_stone") != null) {
                            // Use one_ring but dont use sun_stone

                            mercenary.setBribed(true);
                            mercenary.setInteractable(false);
                            currentDungeon.getPlayer().setAlly(true);
                        } else if (currentDungeon.getItem("treasure") != null) {
                            // Use one_ring and treasure
                            currentDungeon.removeItem("treasure");
                            mercenary.setBribed(true);
                            mercenary.setInteractable(false);
                            currentDungeon.getPlayer().setAlly(true);
                        } else {
                            // Throw exception if player doesnt have either (one_ring + treasure) or (one_ring + sun_stone)
                            throw new InvalidActionException("Missing materials to bribe Mercenary");
                        }
                    }
                    else {
                        throw new InvalidActionException("Mercenary not in range");
                    }
                }
            }
            else {
                throw new InvalidActionException("You already have an ally!");
            }
        }
        else if (currentDungeon.getEntity(entityId).getType().equals("zombie_toast_spawner")) {
            Spawner spawner = (Spawner) currentDungeon.getEntity(entityId);
            if (spawner.isInDestroyableRange(currentDungeon.getPlayer().getPosition())) {
                if (currentDungeon.getItem("sword") == null && currentDungeon.getItem("bow") == null) {
                    throw new InvalidActionException("No weapon in inventory");
                }
                else {
                    for (Entity i : currentDungeon.inventory) {
                        if (i.getType().equals("sword") || i.getType().equals("bow")) {   
                            CollectableEntity item = (CollectableEntity) i;
                            int newDurability = item.getDurability() - 1;
                            item.setDurability(newDurability);
                            currentDungeon.removeEntity(entityId);
                        }
                    }
                }
            } 
            else {
                throw new InvalidActionException("Spawner not in range");
            }
        }
        return currentDungeon.createResponse();
    }
    
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!Buildable.BUILDABLES_LIST.contains(buildable)) {
            throw new IllegalArgumentException();
        }
        
        if (Zombie.zombieExistOnMap(currentDungeon.entities) && buildable.equals("midnight_armour")) {
            throw new InvalidActionException("Zombie Toasts Exist on Map");
        }

        if (!currentDungeon.buildables.contains(buildable)) {
            throw new InvalidActionException("Not Enough Materials");
        }

        currentDungeon.createBuildable(buildable);

        return currentDungeon.createResponse();

    }

    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException {
        int w = xEnd;
        int h = yEnd;
        boolean[][] maze = new boolean[w][h];
        int[][] directions2 = { //distance of 2 to each side
            { 0 ,-2}, // north
            { 0 , 2}, // south
            { 2 , 0}, // east
            {-2 , 0}, // west
        };
        int[][] directions1 = { //distance of 1 to each side
            { 0 ,-1}, // north
            { 0 , 1}, // south
            { 1 , 0}, // east
            {-1 , 0}, // west
        };

        Random random = new Random();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                maze[x][y] = false;
            }
        }
        
        // maze[start] = empty
      
        Position start = new Position(random.nextInt(xEnd), random.nextInt(yEnd));
        while (start.getX() == xStart || start.getY() == yStart || start.getX() == xEnd - 1 || start.getY() == yEnd - 1) {
            start = new Position(random.nextInt(xEnd), random.nextInt(yEnd));
        }
        Position end = new Position(random.nextInt(xEnd), random.nextInt(yEnd));

        while (end == start || end.getX() == xStart || end.getY() == yStart || end.getX() == xEnd - 1 || end.getY() == yEnd - 1) {
            end = new Position(random.nextInt(xEnd), random.nextInt(yEnd));
        }

        maze[start.getX()][start.getY()] = true;
        
        // let options be a list of positions
        List<Position> options = new ArrayList<Position>();

        // add to options all neighbours of 'start' not on boundary that are of distance 2 away and are walls
        for (int[] direction : directions2) {
            if (start.getX() + direction[0] == xStart || start.getY() + direction[1] == yStart || start.getX() + direction[0] == xEnd || start.getY() + direction[1] == yEnd) continue;
            try {
            if (maze[start.getX() + direction[0]][start.getY() + direction[1]] == false) options.add(new Position(start.getX() + direction[0], start.getY() + direction[1]));
            } catch (Exception e) { // ignore ArrayIndexOutOfBounds
            continue;
            }
        }
        // while options is not empty:
        while (!options.isEmpty()) {
            //     let next = remove random from options
            Position next = options.remove(random.nextInt(options.size()));
            //     let neighbours = each neighbour of distance 2 from next not on boundary that are empty
            List<Position> neighbours = new ArrayList<Position>();
            for (int[] direction : directions2) {
                if (next.getX() + direction[0] == xStart || next.getY() + direction[1] == yStart || next.getX() + direction[0] == xEnd - 1 || next.getY() + direction[1] == yEnd - 1) continue;
                try {
                if (maze[next.getX() + direction[0]][next.getY() + direction[1]] == true) neighbours.add(new Position(next.getX() + direction[0], next.getY() + direction[1]));
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                continue;
                }
            }
            //     if neighbours is not empty:
            if (!neighbours.isEmpty()) {
                //         let neighbour = random from neighbours
                Position neighbour = neighbours.get(random.nextInt(neighbours.size()));
                //         maze[ next ] = empty (i.e. true)
                maze[next.getX()][next.getY()] = true;
                //         maze[ position inbetween next and neighbour ] = empty (i.e. true)
                maze[(int)((next.getX() + neighbour.getX())/2)][(int)((next.getY() + neighbour.getY())/2)] = true;
                //         maze[ neighbour ] = empty (i.e. true)
                maze[neighbour.getX()][neighbour.getY()] = true;
                //     add to options all neighbours of 'next' not on boundary that are of distance 2 away and are walls
                for (int[] direction : directions2) {
                    if (next.getX() + direction[0] == xStart || next.getY() + direction[1] == yStart || next.getX() + direction[0] == xEnd - 1 || next.getY() + direction[1] == yEnd - 1) continue;
                    try {
                    if (maze[next.getX() + direction[0]][next.getY() + direction[1]] == false) options.add(new Position(next.getX() + direction[0], next.getY() + direction[1]));
                    } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                    }
                }
            }
        }
        
        // // at the end there is still a case where our end position isn't connected to the map
        // // we don't necessarily need this, you can just keep randomly generating maps (was original intention)
        // // but this will make it consistently have a pathway between the two.
        // if maze[end] is a wall:
        if (!maze[end.getX()][end.getY()]) {
            //     maze[end] = empty
            maze[end.getX()][end.getY()] = true;
            //     let neighbours = neighbours not on boundary of distance 1 from maze[end]
            List<Position> neighbours = new ArrayList<Position>();
            for (int[] direction : directions1) {
                if (end.getX() + direction[0] == xStart || end.getY() + direction[1] == yStart || end.getX() + direction[0] == xEnd - 1 || end.getY() + direction[1] == yEnd - 1) continue;
                try {
                if (maze[end.getX() + direction[0]][end.getY() + direction[1]] == true) neighbours.add(new Position(end.getX() + direction[0], end.getY() + direction[1]));
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                continue;
                }
            }
            //     if there are no cells in neighbours that are empty:
            boolean isFull = true;
            for (Position p : neighbours) {
                if (maze[p.getX()][p.getY()] == true) {
                    isFull = false;
                    break;
                }
            }

            if(isFull && neighbours.isEmpty()) {
                //         // let's connect it to the grid
                //         let neighbour = random from neighbours
                //         maze[neighbour] = empty
            }
                Position neighbour = neighbours.get(random.nextInt(neighbours.size()));
                maze[neighbour.getX()][neighbour.getY()] = true;
            }

            Dungeon newDungeon = new Dungeon(gameMode, gameMode);
            currentDungeon = newDungeon;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    Entity e = null;
                    if (x == start.getX() && y == start.getY()) {
                        e = new Player("player", "player", new Position(start.getX(), start.getY()));
                        currentDungeon.player = (Player) e;
                        currentDungeon.entry = e.getPosition();
                    } else if (x == end.getX() && y == end.getY()) {
                        e = new Exit("exit", "exit", new Position(end.getX(), end.getY()));
                    } else if (maze[x][y] == false) {
                        e = new Wall("wall" + Integer.toString(x) +"_"+ Integer.toString(y), "wall", new Position(x, y));
                    }
                    if (e != null) {
                        currentDungeon.entities.add(e);
                    }
                }
            }

        return newDungeon.createResponse();
    }

}