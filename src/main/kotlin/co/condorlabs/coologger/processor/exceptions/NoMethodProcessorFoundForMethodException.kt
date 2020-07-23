package co.condorlabs.coologger.processor.exceptions

class NoMethodProcessorFoundForMethodException(private val methodName: String) :
    IllegalArgumentException("Method $methodName is not supported by this processor")
