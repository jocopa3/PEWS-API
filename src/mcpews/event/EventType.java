package mcpews.event;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum EventType {
    // Event classes must not be null!
    ADDITIONAL_CONTENT_LOADED("AdditionalContentLoaded", null, EventProperties.class, EventMeasurements.class),
    AGENT_COMMAND("AgentCommand", null, EventProperties.class, EventMeasurements.class),
    AGENT_CREATED("AgentCreated", null, EventProperties.class, EventMeasurements.class),
    API_INIT("ApiInit", null, EventProperties.class, EventMeasurements.class),
    APP_PAUSED("AppPaused", null, EventProperties.class, EventMeasurements.class),
    APP_RESUMED("AppResumed", null, EventProperties.class, EventMeasurements.class),
    APP_SUSPENDED("AppSuspended", null, EventProperties.class, EventMeasurements.class),
    AWARD_ACHIEVEMENT("AwardAchievement", null, EventProperties.class, EventMeasurements.class),
    BLOCK_BROKEN("BlockBroken", null, EventProperties.class, EventMeasurements.class),
    BLOCK_PLACED("BlockPlaced", null, EventProperties.class, EventMeasurements.class),
    BOARD_TEXT_UPDATED("BoardTextUpdated", null, EventProperties.class, EventMeasurements.class),
    BOSS_KILLED("BossKilled", null, EventProperties.class, EventMeasurements.class),
    CAMERA_USED("CameraUsed", null, EventProperties.class, EventMeasurements.class),
    CAULDRON_USED("CauldronUsed", null, EventProperties.class, EventMeasurements.class),
    CHUNK_CHANGED("ChunkChanged", null, EventProperties.class, EventMeasurements.class),
    CHUNK_LOADED("ChunkLoaded", null, EventProperties.class, EventMeasurements.class),
    CHUNK_UNLOADED("ChunkUnloaded", null, EventProperties.class, EventMeasurements.class),
    CONFIGURATION_CHANGED("ConfigurationChanged", null, EventProperties.class, EventMeasurements.class),
    CONNECTION_FAILED("ConnectionFailed", null, EventProperties.class, EventMeasurements.class),
    CRAFTING_SESSION_COMPLETED("CraftingSessionCompleted", null, EventProperties.class, EventMeasurements.class),
    END_OF_DAY("EndOfDay", null, EventProperties.class, EventMeasurements.class),
    ENTITY_SPAWNED("EntitySpawned", null, EventProperties.class, EventMeasurements.class),
    FILE_TRANSMISSION_CANCELLED("FileTransmissionCancelled", null, EventProperties.class, EventMeasurements.class),
    FILE_TRANSMISSION_COMPLETED("FileTransmissionCompleted", null, EventProperties.class, EventMeasurements.class),
    FILE_TRANSMISSION_STARTED("FileTransmissionStarted", null, EventProperties.class, EventMeasurements.class),
    FIRST_TIME_CLIENT_OPEN("FirstTimeClientOpen", null, EventProperties.class, EventMeasurements.class),
    FOCUS_GAINED("FocusGained", null, EventProperties.class, EventMeasurements.class),
    FOCUS_LOST("FocusLost", null, EventProperties.class, EventMeasurements.class),
    GAME_SESSION_COMPLETE("GameSessionComplete", null, EventProperties.class, EventMeasurements.class),
    GAME_SESSION_START("GameSessionStart", null, EventProperties.class, EventMeasurements.class),
    HARDWARE_INFO("HardwareInfo", null, EventProperties.class, EventMeasurements.class),
    HAS_NEW_CONTENT("HasNewContent", null, EventProperties.class, EventMeasurements.class),
    ITEM_ACQUIRED("ItemAcquired", null, EventProperties.class, EventMeasurements.class),
    ITEM_CRAFTED("ItemCrafted", null, EventProperties.class, EventMeasurements.class),
    ITEM_DESTROYED("ItemDestroyed", null, EventProperties.class, EventMeasurements.class),
    ITEM_DROPPED("ItemDropped", null, EventProperties.class, EventMeasurements.class),
    ITEM_ENCHANTED("ItemEnchanted", null, EventProperties.class, EventMeasurements.class),
    ITEM_SMELTED("ItemSmelted", null, EventProperties.class, EventMeasurements.class),
    ITEM_USED("ItemUsed", null, EventProperties.class, EventMeasurements.class),
    JOIN_CANCELED("JoinCanceled", null, EventProperties.class, EventMeasurements.class),
    JUKEBOX_USED("JukeboxUsed", null, EventProperties.class, EventMeasurements.class),
    LICENSE_CENSUS("LicenseCensus", null, EventProperties.class, EventMeasurements.class),
    MASCOT_CREATED("MascotCreated", null, EventProperties.class, EventMeasurements.class),
    MENU_SHOWN("MenuShown", null, EventProperties.class, EventMeasurements.class),
    MOB_INTERACTED("MobInteracted", null, EventProperties.class, EventMeasurements.class),
    MOB_KILLED("MobKilled", null, EventProperties.class, EventMeasurements.class),
    MULTIPLAYER_CONNECTION_STATE_CHANGED("MultiplayerConnectionStateChanged", null, EventProperties.class, EventMeasurements.class),
    MULTIPLAYER_ROUND_END("MultiplayerRoundEnd", null, EventProperties.class, EventMeasurements.class),
    MULTIPLAYER_ROUND_START("MultiplayerRoundStart", null, EventProperties.class, EventMeasurements.class),
    NPC_PROPERTIES_UPDATED("NpcPropertiesUpdated", null, EventProperties.class, EventMeasurements.class),
    OPTIONS_UPDATED("OptionsUpdated", null, EventProperties.class, EventMeasurements.class),
    PERFORMANCE_METRICS("performanceMetrics", null, EventProperties.class, EventMeasurements.class),
    PACK_IMPORT_STAGE("PackImportStage", null, EventProperties.class, EventMeasurements.class),
    PLAYER_BOUNCED("PlayerBounced", null, EventProperties.class, EventMeasurements.class),
    PLAYER_DIED("PlayerDied", null, EventProperties.class, EventMeasurements.class),
    PLAYER_JOIN("PlayerJoin", null, EventProperties.class, EventMeasurements.class),
    PLAYER_LEAVE("PlayerLeave", null, EventProperties.class, EventMeasurements.class),
    PLAYER_MESSAGE("PlayerMessage", PlayerMessageEvent.class, PlayerMessageEvent.PlayerMessageProperties.class, EventMeasurements.class),
    PLAYER_TELEPORTED("PlayerTeleported", null, EventProperties.class, EventMeasurements.class),
    PLAYER_TRANSFORM("PlayerTransform", PlayerTransformEvent.class, PlayerTransformEvent.PlayerTransformProperties.class, EventMeasurements.class),
    PLAYER_TRAVELLED("PlayerTravelled", PlayerTravelledEvent.class, PlayerTravelledEvent.PlayerTravelledProperties.class, PlayerTravelledEvent.PlayerTravelledMeasurements.class),
    PORTAL_BUILT("PortalBuilt", null, EventProperties.class, EventMeasurements.class),
    PORTAL_USED("PortalUsed", null, EventProperties.class, EventMeasurements.class),
    PORTFOLIO_EXPORTED("PortfolioExported", null, EventProperties.class, EventMeasurements.class),
    POTION_BREWED("PotionBrewed", null, EventProperties.class, EventMeasurements.class),
    PURCHASE_ATTEMPT("PurchaseAttempt", null, EventProperties.class, EventMeasurements.class),
    PURCHASE_RESOLVED("PurchaseResolved", null, EventProperties.class, EventMeasurements.class),
    REGIONAL_POPUP("RegionalPopup", null, EventProperties.class, EventMeasurements.class),
    RESPONDED_TO_ACCEPT_CONTENT("RespondedToAcceptContent", null, EventProperties.class, EventMeasurements.class),
    SCREEN_CHANGED("ScreenChanged", null, EventProperties.class, EventMeasurements.class),
    SCREEN_HEARTBEAT("ScreenHeartbeat", null, EventProperties.class, EventMeasurements.class),
    SIGN_IN_TO_EDU("SignInToEdu", null, EventProperties.class, EventMeasurements.class),
    SIGN_IN_TO_XBOX_LIVE("SignInToXboxLive", null, EventProperties.class, EventMeasurements.class),
    SIGN_OUT_OF_XBOX_LIVE("SignOutOfXboxLive", null, EventProperties.class, EventMeasurements.class),
    SPECIAL_MOB_BUILT("SpecialMobBuilt", null, EventProperties.class, EventMeasurements.class),
    START_CLIENT("StartClient", null, EventProperties.class, EventMeasurements.class),
    START_WORLD("StartWorld", null, EventProperties.class, EventMeasurements.class),
    TEXT_TO_SPEECH_TOGGLED("TextToSpeechToggled", null, EventProperties.class, EventMeasurements.class),
    UGC_DOWNLOAD_COMPLETED("UgcDownloadCompleted", null, EventProperties.class, EventMeasurements.class),
    UGC_DOWNLOAD_STARTED("UgcDownloadStarted", null, EventProperties.class, EventMeasurements.class),
    UPLOAD_SKIN("UploadSkin", null, EventProperties.class, EventMeasurements.class),
    VEHICLE_EXITED("VehicleExited", null, EventProperties.class, EventMeasurements.class),
    WORLD_EXPORTED("WorldExported", null, EventProperties.class, EventMeasurements.class),
    WORLD_FILES_LISTED("WorldFilesListed", null, EventProperties.class, EventMeasurements.class),
    WORLD_GENERATED("WorldGenerated", null, EventProperties.class, EventMeasurements.class),
    WORLD_LOADED("WorldLoaded", null, EventProperties.class, EventMeasurements.class),
    WORLD_UNLOADED("WorldUnloaded", null, EventProperties.class, EventMeasurements.class);

    private static HashMap<String, EventType> events = new HashMap<>();

    static {
        for (EventType event : values()) {
            events.put(event.getName(), event);
        }
    }

    private String eventName;
    private Class eventClass;
    private Class propertiesClass;
    private Class measurementsClass;

    EventType(String name, Class eventClass, Class propertiesClass, Class measurementsClass) {
        eventName = name;
        this.eventClass = eventClass;
        this.propertiesClass = propertiesClass;
        this.measurementsClass = measurementsClass;
    }

    public String getName() {
        return eventName;
    }

    public Class getEventClassType() {
        return eventClass;
    }

    public Class getPropertiesClassType() {
        return propertiesClass;
    }

    public Class getMeasurementsClassType() {
        return measurementsClass;
    }

    public static EventType fromString(String name) {
        return events.get(name);
    }
}
