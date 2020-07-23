package co.condorlabs.coologger.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class LogSources(val logSources: Array<String>)
