package co.condorlabs.coologger.events

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.sources.FirebaseSource


class ScreenViewEvent(
    screenName: String,
    private val sources: Set<LogSource> = setOf<LogSource>(FirebaseSource),
    private val extraProperties: Map<String, String>? = null
) : LogEvent {

    override val name: String =
        SCREEN_VIEW_EVENT_NAME

    override val properties: Map<String, String> = mutableMapOf<String, String>().apply {
        put(SCREEN_NAME_EVENT_NAME, screenName)
        extraProperties?.let {
            putAll(extraProperties)
        }
    }

    override fun isSourceSupported(logSource: LogSource): Boolean = sources.contains(logSource)
}

private const val SCREEN_NAME_EVENT_NAME = "SCREEN_NAME"
private const val SCREEN_VIEW_EVENT_NAME = "SCREEN_VIEW"
