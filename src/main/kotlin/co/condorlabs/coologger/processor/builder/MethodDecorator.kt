package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource

typealias MethodName = String
typealias PropertiesName = String

interface MethodDecorator {

    val methodName: MethodName
    val propertiesName: PropertiesName?
    val sources: Set<LogSource>?
}
