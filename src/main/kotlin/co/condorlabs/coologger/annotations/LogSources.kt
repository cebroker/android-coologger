package co.condorlabs.coologger.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class LogSources(val logSources: Array<KClass<*>>)
