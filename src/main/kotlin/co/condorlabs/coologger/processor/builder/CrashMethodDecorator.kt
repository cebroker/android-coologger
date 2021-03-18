package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource

class CrashMethodDecorator(
    override val methodName: MethodName,
    override val propertiesName: PropertiesName,
    override val sources: Set<LogSource>?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.methodName,
        builder.propertiesName,
        builder.sources
    )

    class Builder constructor(
        val methodName: MethodName
    ) {

        lateinit var propertiesName: PropertiesName

        var sources: Set<LogSource>? = null
            private set

        fun withSources(sources: Set<LogSource>): Builder {
            this.sources = sources
            return this
        }

        fun withPropertiesName(propertiesName: PropertiesName): Builder {
            this.propertiesName = propertiesName
            return this
        }

        fun build(): CrashMethodDecorator =
            CrashMethodDecorator(this)
    }
}
