package co.condorlabs.coologger.exceptions

class LogSourceNotSupportedException(name: String): IllegalArgumentException("The event $name is not supported by this logger")
