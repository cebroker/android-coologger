package co.condorlabs.coologger.processor.builder

typealias ScreenShownName = String

data class ScreenShownMethodDecorator(
    val screenShownName: ScreenShownName,
    override val methodName: MethodName,
    override val sources: Set<String>?,
    override val propertiesName: PropertiesName?
) : MethodDecorator {

    private constructor(builder: Builder) : this(
        builder.screenShownName,
        builder.methodName,
        builder.sources,
        builder.propertiesName
    )

    class Builder constructor(val screenShownName: ScreenShownName, val methodName: MethodName) {
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

        fun build(): ScreenShownMethodDecorator =
            ScreenShownMethodDecorator(this)
    }
}
