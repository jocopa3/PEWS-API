package mcpews.message

import mcpews.command.CommandInput
import mcpews.command.CommandOrigin

open class MCCommand protected constructor() : MCBody(MessagePurposeType.COMMAND_REQUEST) {

    @JvmField
    internal var input: CommandInput? = null

    @JvmField
    internal var origin: CommandOrigin? = null

    @JvmField
    internal var name: String? = null

    @JvmField
    internal var version: Int = 0

    @JvmField
    internal var overload: String? = null

    constructor(
        input: CommandInput,
        origin: CommandOrigin?,
        name: String?,
        version: Int
    ) : this() {
        this.input = input
        this.origin = origin
        this.name = name
        this.version = version
        this.overload = input.getOverload()
    }

    protected open fun setInput(input: CommandInput) {
        this.input = input
        this.overload = input.getOverload()
    }

    protected open fun setOrigin(origin: CommandOrigin?) {
        this.origin = origin
    }

    open fun getName(): String? {
        return name
    }

    protected open fun setName(name: String?) {
        this.name = name
    }

    protected open fun setVersion(version: Int) {
        this.version = version
    }

    open fun getOverload(): String? {
        return overload
    }
}
