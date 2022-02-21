package dungeonmania.goals;

import dungeonmania.entities.Player;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Exit;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.collectable.Treasure;

import java.util.List;

public class GoalLeaf implements Goal {
    private String name;
    private boolean complete;
    
    /**
     * Constructor for GoalLeaf
     * @param name
     * @param complete
     */
    public GoalLeaf (String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }
    
    @Override
    public String goalsString() {
        return ":"+this.name;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean add(Goal child) {
        return false;
    }

    /**
     * Complete setter
     */
    public void setComplete() {
        this.complete = true;
    }

    /**
     * Incomplete setter
     */
    public void setIncomplete() {
        this.complete = false;
    }

    /**
     * Checks if specific leaf goal is complete
     * @param entities
     * @param player
     * @return true if complete/ false when incomplete
     */
    public boolean checkComplete(List<Entity> entities, Player player) {
        if (this.name.equals("treasure")) {
            if (Treasure.treasureOnMap(entities)) {
                return false;
            } else return true;
        } else if (this.name.equals("enemies")) {
            if (Entity.enemiesOnMap(entities)) {
                return false;
            } else return true;
        } else if (this.name.equals("exit")) {
            if (Exit.playerOnExit(entities, player)) {
                return true;
            } else return false;
        } else if (this.name.equals("boulders")) {
            if (FloorSwitch.allSwitchTriggered(entities)) {
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public void checkGoalState(List<Entity> entities, Player player) {        
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void checkComplete() {
        // TODO Auto-generated method stub
        
    }
}  
