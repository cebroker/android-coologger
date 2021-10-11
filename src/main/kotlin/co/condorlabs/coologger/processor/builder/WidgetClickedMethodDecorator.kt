package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.events.WidgetType

data class WidgetClickedMethodDecorator(
    val widgetName: String,
    val widgetType: WidgetType,
    override val methodName: MethodName,
    override val sources: Set<String>?,
    override val propertiesName: PropertiesName?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.widgetName,
        builder.widgetType,
        builder.methodName,
        builder.sources,
        builder.propertiesName
    )

    class Builder constructor(val widgetName: ScreenShownName, val widgetType: WidgetType, val methodName: MethodName) {
        var propertiesName: PropertiesName? = null
            private set

        var sources: Set<String>? = null
            private set

        fun withSources(sources: Set<String>): Builder {
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
