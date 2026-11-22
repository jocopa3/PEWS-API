package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class ListDetailCommand : MCCommand {

    enum class Detail {
        ids,
        stats,
        uuids
    }

    open class ListDetailsInput : CommandInput {
        private var details: String? = null

        constructor(detail: Detail) : super() {
            details = detail.name
        }

        constructor(detail: String?) : super() {
            details = detail
        }
    }

    constructor() : super(
        EmptyInput(),
        BasicOrigin("player"),
        CommandType.LISTD.getName(),
        1
    )

    constructor(input: ListDetailsInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.LISTD.getName())
        setVersion(1)
    }
}
