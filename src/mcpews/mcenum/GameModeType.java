
package mcpews.mcenum;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum GameModeType {
    ALL("a", -1),
    SURVIVAL("s", 0),
    CREATIVE("c", 1);
    
    private String name;
    private int id;

    private static HashMap<String, GameModeType> fromString;

    static {
        fromString = new HashMap<>();

        for (GameModeType feature : values()) {
            fromString.put(feature.getName(), feature);
        }
    }

    GameModeType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static GameModeType fromString(String name) {
        return fromString.get(name);
    }
}
