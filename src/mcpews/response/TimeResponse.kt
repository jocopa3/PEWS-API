package mcpews.response

import mcpews.message.MCResponse

class TimeResponse : MCResponse() {
    private var time: Int = 0

    fun getTime(): Int = time
}
