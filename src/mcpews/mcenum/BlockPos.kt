package mcpews.mcenum

/**
 *
 * @author Jocopa3
 */
class BlockPos {

    private var x: Any? = null
    private var y: Any? = null
    private var z: Any? = null

    @Transient
    private var xPos: Double = 0.0

    @Transient
    private var yPos: Double = 0.0

    @Transient
    private var zPos: Double = 0.0

    constructor(x: String, y: String, z: String) {
        this.x = if (x == "~") "~" else Integer.parseInt(x)
        this.y = if (y == "~") "~" else Integer.parseInt(y)
        this.z = if (z == "~") "~" else Integer.parseInt(z)
    }

    constructor(x: Double, y: Double, z: Double) {
        xPos = x
        this.x = Math.floor(x).toInt()
        yPos = y
        this.y = Math.floor(y).toInt()
        zPos = z
        this.z = Math.floor(z).toInt()
    }

    fun getX(): Double = xPos

    fun getY(): Double = yPos

    fun getZ(): Double = zPos

    fun delta(position: BlockPos): BlockPos {
        return BlockPos(xPos - position.xPos, yPos - position.yPos, zPos - position.zPos)
    }

    fun sub(position: BlockPos): BlockPos {
        return BlockPos(xPos - position.xPos, yPos - position.yPos, zPos - position.zPos)
    }

    fun add(position: BlockPos): BlockPos {
        return BlockPos(xPos + position.xPos, yPos + position.yPos, zPos + position.zPos)
    }

    fun distance(pos: BlockPos): Double {
        return Math.sqrt(Math.pow(xPos - pos.xPos, 2.0) + Math.pow(yPos - pos.yPos, 2.0) + Math.pow(zPos - pos.zPos, 2.0))
    }

    override fun toString(): String {
        return "X: " + xPos + ", Y: " + yPos + ", Z: " + zPos
    }
}
