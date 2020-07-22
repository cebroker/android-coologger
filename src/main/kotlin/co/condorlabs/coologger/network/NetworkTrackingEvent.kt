package co.condorlabs.coologger.network

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NetworkTrackingEvent(val eventName: String)
