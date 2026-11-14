package mcpews.command

import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.mcenum.CommandTarget
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class ExecuteCommand : MCCommand {

    enum class DetectType {
        detect
    }

    open class ExecuteDetectInput : CommandInput {

        private var origin: CommandTarget? = null
        private var position: BlockPos? = null
        private var detect: DetectType? = null
        private var detectBlock: String? = null
        private var detectData: Int = 0
        private var command: String? = null

        constructor(target: CommandTarget?, position: BlockPos?, detect: DetectType?, block: String?, data: Int, command: String?) : super() {
            this.overloadName = "detect"
            this.origin = target
            this.position = position
            this.detect = detect
            this.detectBlock = block
            this.detectData = data
            this.command = command
        }

        constructor(target: CommandTarget?, position: BlockPos?, detect: DetectType?, block: BlockType?, command: String?) :
            this(target, position, detect, block!!.getName(), block!!.getData(), command)
    }

    open class ExecuteAsOtherInput : CommandInput {

        private var origin: CommandTarget? = null
        private var position: BlockPos? = null
        private var command: String? = null

        constructor(target: CommandTarget?, position: BlockPos?, command: String?) : super() {
            this.overloadName = "asOther"
            this.origin = target
            this.position = position
            this.command = command
        }

        constructor(target: CommandTarget?, position: BlockPos?, vararg command: String) : super() {
            this.overloadName = "asOther"
            this.origin = target
            this.position = position

            val joined = StringBuilder()
            for ((index, part) in command.withIndex()) {
                if (index > 0) {
                    joined.append(' ')
                }
                joined.append(part)
            }
            this.command = joined.toString()
        }
    }

    constructor(input: ExecuteAsOtherInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.EXECUTE.getName())
        setVersion(1)
    }

    constructor(input: ExecuteDetectInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.EXECUTE.getName())
        setVersion(1)
    }
}
