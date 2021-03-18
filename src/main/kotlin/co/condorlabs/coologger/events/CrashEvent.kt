package co.condorlabs.coologger.events


import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.sources.CrashlyticsSource

data class CrashEvent(
    override val name: String = CRASH_EVENT_NAME,
    val exception: Exception
) : LogEvent {

    override val properties: Map<String, String> = mutableMapOf<String, String>()
    override fun isSourceSupported(logSource: LogSource): Boolean = logSource is CrashlyticsSource
}

private const val CRASH_EVENT_NAME = "CRASH"