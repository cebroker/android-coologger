package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.events.WidgetType

data class WidgetClickedMethodDecorator(
    val widgetName: String,
    val widgetType: WidgetType,
    override val methodName: MethodName,
    override val sources: Set<LogSource>?,
    override val propertiesName: PropertiesName?,
    override val propertiesNameException: PropertiesNameException?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.widgetName,
        builder.widgetType,
        builder.methodName,
        builder.sources,
        builder.propertiesName,
        builder.propertiesNameException
    )

    class Builder constructor(
        val widgetName: ScreenShownName,
        val widgetType: WidgetType,
        val methodName: MethodName

    ) {

        var propertiesNameException: PropertiesNameException? = null
            private set

        var propertiesName: PropertiesName? = null
            private set

        var sources: Set<LogSource>? = null
            private set

        fun withSources(sources: Set<LogSource>): Builder {
            this.sources = sources
            return this
        }

        fun withPropertiesName(propertiesName: PropertiesName?): Builder {
            this.propertiesName = propertiesName
            return this
        }

        fun build(): WidgetClickedMethodDecorator =
            WidgetClickedMethodDecorator(this)
    }
}
