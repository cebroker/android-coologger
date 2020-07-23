package co.condorlabs.coologger.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class ScreenShown(val name: String)
