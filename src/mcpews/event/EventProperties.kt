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
    open fun getBiome(): Biome? = mcpews.util.Biome.fromId(Biome)
    open fun getBuild(): String? = Build
    open fun getBuildPlat(): Int = BuildPlat
    open fun isCheevos(): Boolean = Cheevos
    open fun getClientId(): String? = ClientId
    open fun getDim(): Int = Dim
    open fun getDimension(): Int = Dimension
    open fun getFlavor(): String? = Flavor
    open fun getMode(): Int = Mode
    open fun getMultiplayerCorrelationId(): String? = MultiplayerCorrelationId
    open fun getNetworkType(): Int = NetworkType
    open fun getPlat(): String? = Plat
    open fun getPlayerId(): String? = PlayerId
    open fun getPlayerSessionID(): String? = PlayerSessionID
    open fun getServerId(): String? = ServerId
    open fun getUserId(): String? = UserId
    open fun getGameMode(): Int = gameMode
    open fun isVrMode(): Boolean = vrMode
}
