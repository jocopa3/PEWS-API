package mcpews.mcenum;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum EntityType {
    // 1.0.4.0
    CHICKEN("chicken", 0x130A),
    COW("cow", 0x130B),
    PIG("pig", 0x130C),
    SHEEP("sheep", 0x130D),
    WOLF("wolf", 0x530E),
    VILLAGER("villager", 0x30F),
    MUSHROOMCOW("mushroomcow", 0x1310),
    SQUID("squid", 0x2311),
    RABBIT("rabbit", 0x1312),
    BAT("bat", 0x8113),
    IRONGOLEM("irongolem", 0x314),
    SNOWGOLEM("snowgolem", 0x315),
    OCELOT("ocelot", 0x5316),
    HORSE("horse", 0x5317),
    DONKEY("donkey", 0x5318),
    MULE("mule", 0x5319),
    SKELETONHORSE("skeletonhorse", 0x5B1A),
    ZOMBIEHORSE("zombiehorse", 0x5B1B),
    POLARBEAR("polarbear", 0x131C),
    ZOMBIE("zombie", 0xB20),
    CREEPER("creeper", 0xB21),
    SKELETON("skeleton", 0xB22),
    SPIDER("spider", 0xB23),
    PIG_ZOMBIE("pig_zombie", 0xB24),
    SLIME("slime", 0xB25),
    ENDERMAN("enderman", 0xB26),
    SILVERFISH("silverfish", 0xB27),
    CAVESPIDER("cavespider", 0xB28),
    GHAST("ghast", 0xB29),
    MAGMACUBE("magmacube", 0xB2A),
    BLAZE("blaze", 0xB2B),
    ZOMBIE_VILLAGER("zombie_villager", 0xB2C),
    WITCH("witch", 0xB2D),
    STRAY("skeleton.stray", 0xB2E),
    HUSK("husk", 0xB2F),
    WITHER_SKELETON("skeleton.wither", 0xB30),
    GUARDIAN("guardian", 0xB31),
    ELDER_GUARDIAN("guardian.elder", 0xB32),
    WITHER_BOSS("wither.boss", 0xB34),
    DRAGON("dragon", 0xB35),
    SHULKER("shulker", 0xB36),
    ENDERMITE("endermite", 0xB37),
    PLAYER("player", 0x13F),
    ITEM("item", 64),
    TNT("tnt", 65),
    FALLING_BLOCK("falling_block", 66),
    EXPERIENCE_POTION("potion.experience", 68),
    XPORB("xporb", 69),
    EYEOF("eyeofEnder", 70),
    ENDERCRYSTAL("endercrystal", 71),
    SHULKER_BULLET("shulker_bullet", 76),
    FISHINGHOOK("fishinghook", 77),
    FIREBALL_DRAGON("fireball.dragon", 79),
    ARROW_SKELETON("arrow.skeleton", 80),
    SNOWBALL("snowball", 81),
    THROWNEGG("thrownegg", 82),
    PAINTING("painting", 83),
    MINECART("minecart", 84),
    FIREBALL_LARGE("fireball.large", 85),
    THROWNPOTION("thrownpotion", 86),
    THROWNENDERPEARL("thrownenderpearl", 87),
    LEASHKNOT("leashknot", 88),
    WITHER_SKULL("wither.skull", 89),
    BOAT("boat", 90),
    LIGHTNINGBOLT("lightningbolt", 93),
    FIREBALL_SMALL("fireball.small", 94),
    AREAEFFECTCLOUD("areaeffectcloud", 95),
    MINECARTHOPPER("minecarthopper", 96),
    MINECARTTNT("minecarttnt", 97),
    MINECARTCHEST("minecartchest", 98),
    LINGERINGPOTION("lingeringpotion", 101),
    UNKNOWN("unknown", -1);
    
    private String name;
    private int id;
    
    private static HashMap<String, EntityType> fromString;
    static {
        fromString = new HashMap<>();
        for(EntityType entity : values()) {
            fromString.put(entity.getName(), entity);
        }
    }
    
    EntityType(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public int getId() {
        return id & 0xFF;
    }
    
    public int getFullId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public static EntityType fromString(String name) {
        return fromString.get(name);
    }
}
