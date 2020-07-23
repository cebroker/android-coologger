package co.condorlabs.coologger.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class WidgetClicked(
    val widgetName: String,
    val widgetType: String
)
