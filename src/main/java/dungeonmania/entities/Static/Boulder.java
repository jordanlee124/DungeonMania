package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.util.Direction;

public class Boulder extends Entity {

    public Boulder(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public Player move(Direction playerDirection, Player player, List<Entity> entities) {
        if (player.getPosition().equals(this.getPosition())) {
            for (Entity e : entities) {
                if (!((e instanceof CollectableEntity) || (e instanceof FloorSwitch))) {
                    //if bulder cant be pushed
                    if (e.getPosition().equals(this.getPosition().translateBy(playerDirection))) {
                        if (playerDirection.equals(Direction.UP)) {
                            player.setPosition(player.getPosition().translateBy(Direction.DOWN));
                        } else if (playerDirection.equals(Direction.DOWN)) {
                            player.setPosition(player.getPosition().translateBy(Direction.UP));
                        } else if (playerDirection.equals(Direction.RIGHT)) {
                            player.setPosition(player.getPosition().translateBy(Direction.LEFT));
                        } else if (playerDirection.equals(Direction.LEFT)) {
                            player.setPosition(player.getPosition().translateBy(Direction.LEFT));
                        }
                        return player;
                    }
                }
            }
            this.setPosition(this.getPosition().translateBy(playerDirection));
        }
        return player;
    }
}
