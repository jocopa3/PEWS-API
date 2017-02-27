package mcpews.mcenum;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum DimensionType {
    OVERWORLD("overworld", 0),
    NETHER("nether", 1),
    END("end", 2);

    private String name;
    private int id;

    private static HashMap<String, DimensionType> fromString;

    static {
        fromString = new HashMap<>();
        for (DimensionType dimension : values()) {
            fromString.put(dimension.getName(), dimension);
        }
    }

    DimensionType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public static DimensionType fromString(String name) {
        return fromString.get(name);
    }
}
