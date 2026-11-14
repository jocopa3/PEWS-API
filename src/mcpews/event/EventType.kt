package mcpews.event

enum class EventType(
    private val eventName: String,
    private val eventClass: Class<*>?,
    private val propertiesClass: Class<*>,
    private val measurementsClass: Class<*>,
) {
    ADDITIONAL_CONTENT_LOADED("AdditionalContentLoaded", null, EventProperties::class.java, EventMeasurements::class.java),
    AGENT_COMMAND("AgentCommand", null, EventProperties::class.java, EventMeasurements::class.java),
    AGENT_CREATED("AgentCreated", null, EventProperties::class.java, EventMeasurements::class.java),
    API_INIT("ApiInit", null, EventProperties::class.java, EventMeasurements::class.java),
    APP_PAUSED("AppPaused", null, EventProperties::class.java, EventMeasurements::class.java),
    APP_RESUMED("AppResumed", null, EventProperties::class.java, EventMeasurements::class.java),
    APP_SUSPENDED("AppSuspended", null, EventProperties::class.java, EventMeasurements::class.java),
    AWARD_ACHIEVEMENT("AwardAchievement", null, EventProperties::class.java, EventMeasurements::class.java),
    BLOCK_BROKEN("BlockBroken", null, EventProperties::class.java, EventMeasurements::class.java),
    BLOCK_PLACED("BlockPlaced", null, EventProperties::class.java, EventMeasurements::class.java),
    BOARD_TEXT_UPDATED("BoardTextUpdated", null, EventProperties::class.java, EventMeasurements::class.java),
    BOSS_KILLED("BossKilled", null, EventProperties::class.java, EventMeasurements::class.java),
    CAMERA_USED("CameraUsed", null, EventProperties::class.java, EventMeasurements::class.java),
    CAULDRON_USED("CauldronUsed", null, EventProperties::class.java, EventMeasurements::class.java),
    CHUNK_CHANGED("ChunkChanged", null, EventProperties::class.java, EventMeasurements::class.java),
    CHUNK_LOADED("ChunkLoaded", null, EventProperties::class.java, EventMeasurements::class.java),
    CHUNK_UNLOADED("ChunkUnloaded", null, EventProperties::class.java, EventMeasurements::class.java),
    CONFIGURATION_CHANGED("ConfigurationChanged", null, EventProperties::class.java, EventMeasurements::class.java),
    CONNECTION_FAILED("ConnectionFailed", null, EventProperties::class.java, EventMeasurements::class.java),
    CRAFTING_SESSION_COMPLETED("CraftingSessionCompleted", null, EventProperties::class.java, EventMeasurements::class.java),
    END_OF_DAY("EndOfDay", null, EventProperties::class.java, EventMeasurements::class.java),
    ENTITY_SPAWNED("EntitySpawned", null, EventProperties::class.java, EventMeasurements::class.java),
    FILE_TRANSMISSION_CANCELLED("FileTransmissionCancelled", null, EventProperties::class.java, EventMeasurements::class.java),
    FILE_TRANSMISSION_COMPLETED("FileTransmissionCompleted", null, EventProperties::class.java, EventMeasurements::class.java),
    FILE_TRANSMISSION_STARTED("FileTransmissionStarted", null, EventProperties::class.java, EventMeasurements::class.java),
    FIRST_TIME_CLIENT_OPEN("FirstTimeClientOpen", null, EventProperties::class.java, EventMeasurements::class.java),
    FOCUS_GAINED("FocusGained", null, EventProperties::class.java, EventMeasurements::class.java),
    FOCUS_LOST("FocusLost", null, EventProperties::class.java, EventMeasurements::class.java),
    GAME_SESSION_COMPLETE("GameSessionComplete", null, EventProperties::class.java, EventMeasurements::class.java),
    GAME_SESSION_START("GameSessionStart", null, EventProperties::class.java, EventMeasurements::class.java),
    HARDWARE_INFO("HardwareInfo", null, EventProperties::class.java, EventMeasurements::class.java),
    HAS_NEW_CONTENT("HasNewContent", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_ACQUIRED("ItemAcquired", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_CRAFTED("ItemCrafted", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_DESTROYED("ItemDestroyed", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_DROPPED("ItemDropped", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_ENCHANTED("ItemEnchanted", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_SMELTED("ItemSmelted", null, EventProperties::class.java, EventMeasurements::class.java),
    ITEM_USED("ItemUsed", null, EventProperties::class.java, EventMeasurements::class.java),
    JOIN_CANCELED("JoinCanceled", null, EventProperties::class.java, EventMeasurements::class.java),
    JUKEBOX_USED("JukeboxUsed", null, EventProperties::class.java, EventMeasurements::class.java),
    LICENSE_CENSUS("LicenseCensus", null, EventProperties::class.java, EventMeasurements::class.java),
    MASCOT_CREATED("MascotCreated", null, EventProperties::class.java, EventMeasurements::class.java),
    MENU_SHOWN("MenuShown", null, EventProperties::class.java, EventMeasurements::class.java),
    MOB_INTERACTED("MobInteracted", null, EventProperties::class.java, EventMeasurements::class.java),
    MOB_KILLED("MobKilled", null, EventProperties::class.java, EventMeasurements::class.java),
    MULTIPLAYER_CONNECTION_STATE_CHANGED("MultiplayerConnectionStateChanged", null, EventProperties::class.java, EventMeasurements::class.java),
    MULTIPLAYER_ROUND_END("MultiplayerRoundEnd", null, EventProperties::class.java, EventMeasurements::class.java),
    MULTIPLAYER_ROUND_START("MultiplayerRoundStart", null, EventProperties::class.java, EventMeasurements::class.java),
    NPC_PROPERTIES_UPDATED("NpcPropertiesUpdated", null, EventProperties::class.java, EventMeasurements::class.java),
    OPTIONS_UPDATED("OptionsUpdated", null, EventProperties::class.java, EventMeasurements::class.java),
    PERFORMANCE_METRICS("performanceMetrics", null, EventProperties::class.java, EventMeasurements::class.java),
    PACK_IMPORT_STAGE("PackImportStage", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_BOUNCED("PlayerBounced", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_DIED("PlayerDied", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_JOIN("PlayerJoin", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_LEAVE("PlayerLeave", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_MESSAGE("PlayerMessage", PlayerMessageEvent::class.java, PlayerMessageEvent.PlayerMessageProperties::class.java, EventMeasurements::class.java),
    PLAYER_TELEPORTED("PlayerTeleported", null, EventProperties::class.java, EventMeasurements::class.java),
    PLAYER_TRANSFORM("PlayerTransform", PlayerTransformEvent::class.java, PlayerTransformEvent.PlayerTransformProperties::class.java, EventMeasurements::class.java),
    PLAYER_TRAVELLED("PlayerTravelled", PlayerTravelledEvent::class.java, PlayerTravelledEvent.PlayerTravelledProperties::class.java, PlayerTravelledEvent.PlayerTravelledMeasurements::class.java),
    PORTAL_BUILT("PortalBuilt", null, EventProperties::class.java, EventMeasurements::class.java),
    PORTAL_USED("PortalUsed", null, EventProperties::class.java, EventMeasurements::class.java),
    PORTFOLIO_EXPORTED("PortfolioExported", null, EventProperties::class.java, EventMeasurements::class.java),
    POTION_BREWED("PotionBrewed", null, EventProperties::class.java, EventMeasurements::class.java),
    PURCHASE_ATTEMPT("PurchaseAttempt", null, EventProperties::class.java, EventMeasurements::class.java),
    PURCHASE_RESOLVED("PurchaseResolved", null, EventProperties::class.java, EventMeasurements::class.java),
    REGIONAL_POPUP("RegionalPopup", null, EventProperties::class.java, EventMeasurements::class.java),
    RESPONDED_TO_ACCEPT_CONTENT("RespondedToAcceptContent", null, EventProperties::class.java, EventMeasurements::class.java),
    SCREEN_CHANGED("ScreenChanged", null, EventProperties::class.java, EventMeasurements::class.java),
    SCREEN_HEARTBEAT("ScreenHeartbeat", null, EventProperties::class.java, EventMeasurements::class.java),
    SIGN_IN_TO_EDU("SignInToEdu", null, EventProperties::class.java, EventMeasurements::class.java),
    SIGN_IN_TO_XBOX_LIVE("SignInToXboxLive", null, EventProperties::class.java, EventMeasurements::class.java),
    SIGN_OUT_OF_XBOX_LIVE("SignOutOfXboxLive", null, EventProperties::class.java, EventMeasurements::class.java),
    SPECIAL_MOB_BUILT("SpecialMobBuilt", null, EventProperties::class.java, EventMeasurements::class.java),
    START_CLIENT("StartClient", null, EventProperties::class.java, EventMeasurements::class.java),
    START_WORLD("StartWorld", null, EventProperties::class.java, EventMeasurements::class.java),
    TEXT_TO_SPEECH_TOGGLED("TextToSpeechToggled", null, EventProperties::class.java, EventMeasurements::class.java),
    UGC_DOWNLOAD_COMPLETED("UgcDownloadCompleted", null, EventProperties::class.java, EventMeasurements::class.java),
    UGC_DOWNLOAD_STARTED("UgcDownloadStarted", null, EventProperties::class.java, EventMeasurements::class.java),
    UPLOAD_SKIN("UploadSkin", null, EventProperties::class.java, EventMeasurements::class.java),
    VEHICLE_EXITED("VehicleExited", null, EventProperties::class.java, EventMeasurements::class.java),
    WORLD_EXPORTED("WorldExported", null, EventProperties::class.java, EventMeasurements::class.java),
    WORLD_FILES_LISTED("WorldFilesListed", null, EventProperties::class.java, EventMeasurements::class.java),
    WORLD_GENERATED("WorldGenerated", null, EventProperties::class.java, EventMeasurements::class.java),
    WORLD_LOADED("WorldLoaded", null, EventProperties::class.java, EventMeasurements::class.java),
    WORLD_UNLOADED("WorldUnloaded", null, EventProperties::class.java, EventMeasurements::class.java);

    fun getName(): String = eventName
    fun getEventClassType(): Class<*>? = eventClass
    fun getPropertiesClassType(): Class<*> = propertiesClass
    fun getMeasurementsClassType(): Class<*> = measurementsClass

    companion object {
        private val events = HashMap<String, EventType>()

        init {
            for (event in values()) events[event.getName()] = event
        }

        @JvmStatic
        fun fromString(name: String?): EventType? = name?.let { events[it] }
    }
}
