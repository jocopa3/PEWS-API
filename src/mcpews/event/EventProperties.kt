package mcpews.event

import mcpews.util.Biome

open class EventProperties {
    @JvmField var Biome: Int = 0
    @JvmField var Build: String? = null
    @JvmField var BuildPlat: Int = 0
    @JvmField var Cheevos: Boolean = false
    @JvmField var ClientId: String? = null
    @JvmField var Dim: Int = 0
    @JvmField var Dimension: Int = 0
    @JvmField var Flavor: String? = null
    @JvmField var Mode: Int = 0
    @JvmField var MultiplayerCorrelationId: String? = null
    @JvmField var NetworkType: Int = 0
    @JvmField var Plat: String? = null
    @JvmField var PlayerId: String? = null
    @JvmField var PlayerSessionID: String? = null
    @JvmField var ServerId: String? = null
    @JvmField var UserId: String? = null
    @JvmField var gameMode: Int = 0
    @JvmField var vrMode: Boolean = false
    fun getBiome(): Biome? = mcpews.util.Biome.fromId(Biome)
    fun getBuild(): String? = Build
    fun getBuildPlat(): Int = BuildPlat
    fun isCheevos(): Boolean = Cheevos
    fun getClientId(): String? = ClientId
    fun getDim(): Int = Dim
    fun getDimension(): Int = Dimension
    fun getFlavor(): String? = Flavor
    fun getMode(): Int = Mode
    fun getMultiplayerCorrelationId(): String? = MultiplayerCorrelationId
    fun getNetworkType(): Int = NetworkType
    fun getPlat(): String? = Plat
    fun getPlayerId(): String? = PlayerId
    fun getPlayerSessionID(): String? = PlayerSessionID
    fun getServerId(): String? = ServerId
    fun getUserId(): String? = UserId
    fun getGameMode(): Int = gameMode
    fun isVrMode(): Boolean = vrMode
}
