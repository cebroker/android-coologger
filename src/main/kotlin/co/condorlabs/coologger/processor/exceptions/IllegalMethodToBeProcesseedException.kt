package co.condorlabs.coologger.processor.exceptions

class IllegalMethodToBeProcesseedException(private val methodName: String) :
    IllegalArgumentException("The method $methodName contains more than one parameter, or paramter passed is not properly annotated")
