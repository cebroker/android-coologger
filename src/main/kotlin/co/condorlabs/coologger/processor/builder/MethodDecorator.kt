package co.condorlabs.coologger.processor.builder

import co.condorlabs.coologger.event.LogSource

typealias MethodName = String
typealias PropertiesName = String
typealias PropertiesNameException = Exception

interface MethodDecorator {

    val methodName: MethodName
    val propertiesName: PropertiesName?
    val propertiesNameException: PropertiesNameException?
    val sources: Set<LogSource>?
}


