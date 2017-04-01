package mcpews.util;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum Biome {

    OCEAN(0, "Ocean", BiomeType.NEUTRAL),
    PLAINS(1, "Plains", BiomeType.TEMPERATE),
    DESERT(2, "Desert", BiomeType.HOT),
    EXTREME_HILLS(3, "Extreme Hills", BiomeType.COLD),
    FOREST(4, "Forest", BiomeType.TEMPERATE),
    TAIGA(5, "Taiga", BiomeType.COLD),
    SWAMPLAND(6, "Swampland", BiomeType.TEMPERATE),
    RIVER(7, "River", BiomeType.TEMPERATE),
    NETHER(8, "Nether", BiomeType.HOT),
    THE_END(9, "The End", BiomeType.COLD),
    FROZEN_OCEAN(10, "Frozen Ocean", BiomeType.SNOWY),
    FROZEN_RIVER(11, "Frozen River", BiomeType.SNOWY),
    ICE_PLAINS(12, "Ice Plains", BiomeType.SNOWY),
    ICE_MOUNTAINS(13, "Ice Mountains", BiomeType.SNOWY),
    MUSHROOM_ISLAND(14, "Mushroom Island", BiomeType.NEUTRAL),
    MUSHROOM_ISLAND_SHORE(15, "Mushroom Island Shore", BiomeType.NEUTRAL),
    BEACH(16, "Beach", BiomeType.TEMPERATE),
    DESERT_HILLS(17, "Desert Hills", BiomeType.HOT),
    FOREST_HILLS(18, "Forest Hills", BiomeType.TEMPERATE),
    TAIGA_HILLS(19, "Taiga Hills", BiomeType.COLD),
    EXTREME_HILLS_EDGE(20, "Extreme Hills Edge", BiomeType.COLD),
    JUNGLE(21, "Jungle", BiomeType.TEMPERATE),
    JUNGLE_HILLS(22, "Jungle Hills", BiomeType.TEMPERATE),
    JUNGLE_EDGE(23, "Jungle Edge", BiomeType.TEMPERATE),
    DEEP_OCEAN(24, "Deep Ocean", BiomeType.NEUTRAL),
    STONE_BEACH(25, "Stone Beach", BiomeType.COLD),
    COLD_BEACH(26, "Cold Beach", BiomeType.SNOWY),
    BIRCH_FOREST(27, "Birch Forest", BiomeType.TEMPERATE),
    BIRCH_FOREST_HILLS(28, "Birch Forest Hills", BiomeType.TEMPERATE),
    ROOFED_FOREST(29, "Roofed Forest", BiomeType.TEMPERATE),
    COLD_TAIGA(30, "Cold Taiga", BiomeType.SNOWY),
    COLD_TAIGA_HILLS(31, "Cold Taiga Hills", BiomeType.SNOWY),
    MEGA_TAIGA(32, "Mega Taiga", BiomeType.COLD),
    MEGA_TAIGA_HILLS(33, "Mega Taiga Hills", BiomeType.COLD),
    EXTREME_HILLS_PLUS(34, "Extreme Hills+", BiomeType.COLD),
    SAVANNA(35, "Savanna", BiomeType.HOT),
    SAVANNA_PLATEAU(36, "Savanna Plateau", BiomeType.HOT),
    MESA(37, "Mesa", BiomeType.HOT),
    MESA_PLATEAU_F(38, "Mesa Plateau F", BiomeType.HOT),
    MESA_PLATEAU(39, "Mesa Plateau", BiomeType.HOT),
    SUNFLOWER_PLAINS(129, "Sunflower Plains", BiomeType.TEMPERATE),
    DESERT_M(130, "Desert M", BiomeType.HOT),
    EXTREME_HILLS_M(131, "Extreme Hills M", BiomeType.COLD),
    FLOWER_FOREST(132, "Flower Forest", BiomeType.TEMPERATE),
    TAIGA_M(133, "Taiga M", BiomeType.COLD),
    SWAMPLAND_M(134, "Swampland M", BiomeType.TEMPERATE),
    ICE_PLAINS_SPIKES(140, "Ice Plains Spikes", BiomeType.COLD),
    JUNGLE_M(149, "Jungle M", BiomeType.TEMPERATE),
    JUNGLE_EDGE_M(151, "Jungle Edge M", BiomeType.TEMPERATE),
    BIRCH_FOREST_M(155, "Birch Forest M", BiomeType.TEMPERATE),
    BIRCH_FOREST_HILLS_M(156, "Birch Forest Hills M", BiomeType.TEMPERATE),
    ROOFED_FOREST_M(157, "Roofed Forest M", BiomeType.TEMPERATE),
    COLD_TAIGA_M(158, "Cold Taiga M", BiomeType.SNOWY),
    MEGA_SPRUCE_TAIGA(160, "Mega Spruce Taiga", BiomeType.COLD),
    REDWOOD_TAIGA_HILLS_M(161, "Redwood Taiga Hills M", BiomeType.COLD),
    EXTREME_HILLS_PLUS_M(162, "Extreme Hills+ M", BiomeType.COLD),
    SAVANNA_M(163, "Savanna M", BiomeType.HOT),
    SAVANNA_PLATEAU_M(164, "Savanna Plateau M", BiomeType.HOT),
    MESA_BRYCE(165, "Mesa (Bryce)", BiomeType.HOT),
    MESA_PLATEAU_F_M(166, "Mesa Plateau F M", BiomeType.HOT),
    MESA_PLATEAU_M(167, "Mesa Plateau M", BiomeType.HOT);

    private static final HashMap<Integer, Biome> fromId;
    
    static {
        fromId = new HashMap<>();

        for(Biome biome : Biome.values()){
            fromId.put(biome.id, biome);
        }
    }

    private int id;
    private String name;
    private BiomeType type;

    Biome(int id, String name, BiomeType type){
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BiomeType getType() {
        return type;
    }

    public static Biome fromId(int id){
        return fromId.get(id);
    }
}