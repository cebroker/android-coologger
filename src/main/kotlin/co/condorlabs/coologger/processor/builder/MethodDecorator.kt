package co.condorlabs.coologger.processor.builder

typealias MethodName = String
typealias PropertiesName = String

interface MethodDecorator {

    val methodName: MethodName
    val propertiesName: PropertiesName?
    val sources: Set<String>?
}
