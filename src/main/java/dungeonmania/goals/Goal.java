package dungeonmania.goals;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;

public interface Goal {
    /**
     * ToString method for all Goal related classes
     * @return
     */
    public String goalsString();

    /**
     * Getter for complete
     * @return
     */
    public boolean isComplete();

    /**
     * Add child node to Composite Goal
     * @param child
     * @return
     */
    public boolean add(Goal child);

    /**
     * Check the state of the Goal (Complete/Incomplete)
     * @param entities
     * @param player
     */
    public void checkGoalState(List<Entity> entities, Player player);

    /**
     * Gets the name of the instance
     * @return
     */
    public String getName();
    
    /**
     * Check if Goal is complete
     */
    public void checkComplete();
}
