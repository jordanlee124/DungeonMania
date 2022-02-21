# **Assumptions**

### **Spawners**

- Max number of spiders : 5
- Bomb blast radius : 2
- Mercenary radius : 3
- Duabilities for gear : random durabilities from 10-30 hits

### **Player**

- Player has a max health of 10
- Only one player can be present in a dungeon (subject to change possibly)
- Player can only have 1 ally at time

### **Weapons and Armours**

- Durability of Sword is 10 attacks
- Durability of Anduril Sword is 10 attacks
- Durability of Armour is between 4 and 15 hits
- Anduril sword does the same damage as a normal sword to entities that is not a hydra
- When player has armour, incoming damage is reduced by 50%
- When player has shield AND armour, incoming damage is reduced by 75%
- When player has shield AND midnight armour, incoming damage is reduced by 75%
- When player has armour and midnight armour, incoming damage is recuded by 75%
- When player has shield AND armour AND midnight armour, incoming damage is reduced by 87.5%

### **Hydra**

- Hydra health is 15
- Hydra damage is 1

### **Building Assumptions**

- #### **Shields**

    - When building a shield, if key and treasure exists in the inventory, the treasure will be used instead of the key.
    - Shield reduces incoming damage by 50%
    - Durability of Shield is 10 attacks

- #### **Bow**

    - Durability of Bow is 20 attacks
    - Allows the player to deal damage twice to the enemy. (Damage of both attacks are equal)

- #### **Sceptre**

    - Durability of Sceptre is 5 uses
    - When building a sceptre, the following rules will be followed
        - Wood will be used before arrows when both are present in inventory
        - Treasure before Keys
    - Sceptre will be used through interaction rather than tick
    - Sceptre's brainwashing remains in effect for 10 ticks and disapprears on the 11<sup>th</sup> tick

- #### **Midnight Armour**

    - Durability of Midnight Armour is 10 attacks
    - Equipping midnight armour will deal 1 extra damage when attacking enemy
    - Equipping midnight armour will reduce incoming damange by 50%

### **Drop Chance**

- Mercenaries drop armour directly into the inventory of the player
- Mercenaries drop armour with a chance of 1/10
- All enemies can drop 'The One Ring' with a chance of 1/50
- Every map has a maximum of two keys
- Zombies can't use armour but only drop it

### **Interact Method Assumptions**

- Treasure is gold and only one gold coin is required to bribe the player
- When interacting with a spawner the weapon durability goes down by 1 when it is destroyed
- Bomb cannot be used to destroy spawner
- Mercenary always follows behind the player and never in front of the player
- If a spider has no where to move (surrounded by boulders) it gets scared and cant move anywhere


### **Potion Assumptions**

- Invincibility potion time : 10 ticks
- Invisibility potion time : 10 ticks
- Allies cannot follow you when invisibile
- Allies will ALSO run away when player has invincibility potion as they are reminded of their past
- Invincibility potion will not reduce damage in hard mode


### **Bomb Assumptions**
- Bomb has a detonation radius of 1
- Bomb cannot be picked back up once placed
