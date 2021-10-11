package co.condorlabs.coologger.logger

import co.condorlabs.coologger.event.LogEvent

interface Logger {

    fun log(logEvent: LogEvent)

    fun identify(id: String)
}
