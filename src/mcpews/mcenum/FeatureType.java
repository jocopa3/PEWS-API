package mcpews.mcenum;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum FeatureType {
    ENDCITY("endcity", 0),
    FORTRESS("fortress", 1),
    MINESHAFT("mineshaft", 2),
    MONUMENT("monument", 3),
    STRONGHOLD("stronghold", 4),
    TEMPLE("temple", 5),
    VILLAGE("village", 6);

    private String name;
    private int id;

    private static HashMap<String, FeatureType> fromString;

    static {
        fromString = new HashMap<>();

        for (FeatureType feature : values()) {
            fromString.put(feature.getName(), feature);
        }
    }

    FeatureType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static FeatureType fromString(String name) {
        return fromString.get(name);
    }
}
