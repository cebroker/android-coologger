package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource

data class CrashMethodDecorator(
    val name: String,
    override val methodName: MethodName,
    override val sources: Set<LogSource>?,
    override val propertiesName: PropertiesName?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.name,
        builder.methodName,
        builder.sources,
        builder.propertiesName
    )

    class Builder constructor(
        val name: String,
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

        fun withExceptionParameterName(exceptionParameterName: PropertiesName?): Builder {
            this.propertiesName = exceptionParameterName
            return this
        }

        fun build(): CrashMethodDecorator =
            CrashMethodDecorator(this)
    }
}
