package mcpews.response

import mcpews.message.MCResponse

open class TimeResponse : MCResponse() {

    private var time: Int = 0

    open fun getTime(): Int {
        return time
    }
}
