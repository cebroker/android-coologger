package co.condorlabs.coologger.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Crash(val name: String = CRASH)

private const val CRASH = "Crash"
