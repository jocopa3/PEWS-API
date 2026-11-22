package mcpews.command

import mcpews.mcenum.CommandTarget
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class EnchantCommand : MCCommand {

    open class EnchantByIdInput : CommandInput {

        private var player: CommandTarget? = null
        private var enchantmentId: Int = 0
        private var level: Int = 1 // Default level

        constructor(player: CommandTarget?, id: Int?) : this(player, id, 1)

        constructor(player: CommandTarget?, id: Int?, level: Int?) : super() {
            overloadName = "byId"
            this.player = player
            enchantmentId = id!!
            this.level = level!!
        }
    }

    open class EnchantByNameInput : CommandInput {

        private var player: CommandTarget? = null
        private var enchantmentName: String? = null
        private var level: Int = 0

        constructor(player: CommandTarget?, name: String?) : this(player, name, 1)

        constructor(player: CommandTarget?, name: String?, level: Int?) : super() {
            overloadName = "byName"
            this.player = player
            enchantmentName = name
            this.level = level!!
        }
    }

    constructor(input: EnchantByNameInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.ENCHANT.getName())
        setVersion(1)
    }

    constructor(input: EnchantByIdInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.ENCHANT.getName())
        setVersion(1)
    }
}
