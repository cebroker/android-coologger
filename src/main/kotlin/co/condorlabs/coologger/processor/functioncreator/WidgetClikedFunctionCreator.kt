package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.events.WidgetClickedEvent
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.builder.WidgetClickedMethodDecorator
import com.squareup.kotlinpoet.FileSpec


class WidgetClikedFunctionCreator : AbstractFunctionCreator<WidgetClickedMethodDecorator>() {

    override fun isApplicable(methodDecorator: MethodDecorator): Boolean =
        methodDecorator is WidgetClickedMethodDecorator

    override fun getMethodStatement(methodDecorator: WidgetClickedMethodDecorator): String =
        if (methodDecorator.propertiesName == null) {
            """
                logger.log(WidgetClickedEvent(widgetName="${methodDecorator.widgetName}",widgetType="${methodDecorator.widgetType}${getSourcesStatement(
                methodDecorator.sources
            )}"))
           """.trimIndent()
        } else {
            """
                logger.log(WidgetClickedEvent(widgetName="${methodDecorator.widgetName}",widgetType="${methodDecorator.widgetType}",extraProperties=${methodDecorator.propertiesName}${getSourcesStatement(
                methodDecorator.sources
            )}))
            """.trimIndent()
        }

    override fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder) {
        fileSpecBuilder.addImport(
            WidgetClickedEvent::class.java.`package`.name,
            WidgetClickedEvent::class.java.simpleName
        )
    }
}
