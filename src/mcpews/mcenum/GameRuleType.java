package mcpews.mcenum;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum GameRuleType {
    FALL_DAMAGE("falldamage"),
    PVP("pvp"),
    DROWNING_DAMAGE("drowningdamage"),
    FIRE_DAMAGE("firedamage"),
    IMMUTABLE_WORLD("immutableworld");

    private String name;

    private static HashMap<String, GameRuleType> fromString;

    static {
        fromString = new HashMap<>();
        for (GameRuleType gameRule : values()) {
            fromString.put(gameRule.getName(), gameRule);
        }
    }

    GameRuleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GameRuleType fromString(String name) {
        return fromString.get(name);
    }
}
