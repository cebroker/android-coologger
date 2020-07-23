package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource

typealias ScreenShownName = String

data class ScreenShownMethodDecorator(
    val screenShownName: ScreenShownName,
    override val methodName: MethodName,
    override val sources: Set<LogSource>?,
    override val propertiesName: PropertiesName?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.screenShownName,
        builder.methodName,
        builder.sources,
        builder.propertiesName
    )

    class Builder constructor(
        val screenShownName: ScreenShownName,
        val methodName: MethodName
    ) {

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

        fun build(): ScreenShownMethodDecorator =
            ScreenShownMethodDecorator(this)
    }
}
