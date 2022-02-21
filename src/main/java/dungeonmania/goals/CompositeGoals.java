package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;

public class CompositeGoals implements Goal {

    private String name;
    private boolean complete;

    ArrayList<Goal>  children = new ArrayList<Goal>();

    public CompositeGoals(String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    @Override
    public String goalsString() {
        String answer = null;
        if (children.size() == 1) {
            if (!children.get(0).isComplete()) {
                return children.get(0).goalsString();
            }
        } else {
            if (!children.get(0).isComplete() && !children.get(1).isComplete()) {
                answer = "(" + children.get(0).goalsString() + " " + this.getName() + " " + children.get(1).goalsString() + ")";
            } else if (!children.get(0).isComplete() && children.get(1).isComplete()) {
                return children.get(0).goalsString();
            } else if (children.get(0).isComplete() && !children.get(1).isComplete()) {
                return children.get(1).goalsString();
            }
        }
        return answer;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean add(Goal child) {
        children.add(child);
        return true;
    }

    @Override
    public void checkComplete() {
        if (this.name.equals("AND")) {
            if (children.get(0).isComplete() && children.get(1).isComplete()) {
                this.complete = true;
            } else {
                this.complete = false;
            }
        } else if (this.name.equals("OR")) {
            if (children.size() == 1) {
                if (children.get(0).isComplete()) {
                    this.complete = true;
                } else {
                    this.complete = false;
                }
            } else {
                if (children.get(0).isComplete() || children.get(1).isComplete()) {
                    this.complete = true;
                } else {
                    this.complete = false;
                }
            }
        } if (this.name.equals("Goal")) {
            if (children.get(0).isComplete()) {
                this.complete = true;
            } else {
                this.complete = false;
            }
        }
    }

    @Override
    public void checkGoalState(List<Entity> entities, Player player) {
        for (Goal g : children) {
            if (g instanceof CompositeGoals) {
                ((CompositeGoals)g).checkGoalState(entities, player);
                ((CompositeGoals)g).checkComplete();
            } else {
                if (((GoalLeaf)g).checkComplete(entities, player)) {
                    ((GoalLeaf)g).setComplete();
                } else {
                    ((GoalLeaf)g).setIncomplete();
                }
            }
        }
    }
}
