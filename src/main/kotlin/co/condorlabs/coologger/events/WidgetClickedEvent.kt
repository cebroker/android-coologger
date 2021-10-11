package co.condorlabs.coologger.events

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.sources.FirebaseSource

class WidgetClickedEvent(
    widgetName: String,
    private val widgetType: String,
    private val sources: Set<LogSource> = setOf<LogSource>(FirebaseSource),
    private val extraProperties: Map<String, String>? = null
) : LogEvent {

    override val name: String
        get() = "$widgetType$WIDGET_ACTION_SUFFIX"

    override val properties: Map<String, String> = mutableMapOf<String, String>().apply {
        put("$widgetType$WIDGET_NAME_SUFFIX", widgetName)
        extraProperties?.let {
            putAll(it)
        }
    }

    override fun isSourceSupported(logSource: LogSource): Boolean = sources.contains(logSource)
}

private const val WIDGET_NAME_SUFFIX = "_NAME"
private const val WIDGET_ACTION_SUFFIX = "_CLICK"
