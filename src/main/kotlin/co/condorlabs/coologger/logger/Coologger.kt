package co.condorlabs.coologger.logger

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.exceptions.LogSourceNotSupportedException

class Coologger(private val childLoggers: Map<LogSource, Logger>) : Logger {

    override fun log(logEvent: LogEvent) {
        val eventSupporters = childLoggers.filterKeys { source ->
            logEvent.isSourceSupported(source)
        }

        if (eventSupporters.isEmpty()) {
            throw LogSourceNotSupportedException(logEvent.name)
        }

        eventSupporters.values.forEach { logger ->
            logger.log(logEvent)
        }
    }

    override fun identify(id: String) {
        childLoggers.values.forEach {
            it.identify(id)
        }
    }
}
