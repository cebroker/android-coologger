package co.condorlabs.coologger.processor.builder

data class DecoratorBuilder(
    val className: String,
    val classPackage: String,
    val interfaceName: String,
    val methodDecorators: Set<MethodDecorator>
)
