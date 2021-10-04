package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.events.ScreenViewEvent
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.builder.ScreenShownMethodDecorator
import com.squareup.kotlinpoet.FileSpec

class ScreenShownFunctionCreator : AbstractFunctionCreator<ScreenShownMethodDecorator>() {

    override fun isApplicable(methodDecorator: MethodDecorator): Boolean =
        methodDecorator is ScreenShownMethodDecorator

    override fun getMethodStatement(methodDecorator: ScreenShownMethodDecorator): String {
        return if (methodDecorator.propertiesName == null) {
            """
                logger.log(ScreenViewEvent(screenName="${methodDecorator.screenShownName}"${getSourcesStatement(
                methodDecorator.sources
            )}))
            """.trimIndent()
        } else {
            """
                logger.log(ScreenViewEvent(screenName="${methodDecorator.screenShownName}", extraProperties=${methodDecorator.propertiesName}${getSourcesStatement(
                methodDecorator.sources
            )}))
            """.trimIndent()
        }
    }

    override fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder) {
        fileSpecBuilder.addImport(
            ScreenViewEvent::class.java.`package`.name,
            ScreenViewEvent::class.java.simpleName
        )
    }
}
