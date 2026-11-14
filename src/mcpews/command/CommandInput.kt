package mcpews.command

/**
 *
 * @author Jocopa3
 */
abstract class CommandInput {

    @JvmField
    @Transient
    protected var overloadName: String = "default"

    open fun getOverload(): String {
        return overloadName
    }
}
