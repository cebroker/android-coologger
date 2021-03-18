package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.events.CrashEvent
import co.condorlabs.coologger.events.ScreenViewEvent
import co.condorlabs.coologger.processor.builder.CrashMethodDecorator
import co.condorlabs.coologger.processor.builder.MethodDecorator
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec


class CrashFunctionCreator : AbstractFunctionCreator<CrashMethodDecorator>() {

    override fun getMethodStatement(methodDecorator: CrashMethodDecorator): String {
        return """
                logger.log(CrashEvent(exception=${methodDecorator.propertiesName}${
        getSourcesStatement(
            methodDecorator.sources
        )
        })) """.trimIndent()

    }

    override fun isApplicable(methodDecorator: MethodDecorator): Boolean =
        methodDecorator is CrashMethodDecorator

    override fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder) {
        fileSpecBuilder.addImport(
            CrashEvent::class.java.`package`.name,
            CrashEvent::class.java.simpleName
        )
    }

    override fun addProperties(
        funSpecBuilder: FunSpec.Builder,
        methodDecorator: CrashMethodDecorator
    ): FunSpec.Builder = funSpecBuilder.addParameter(
        methodDecorator.propertiesName,
        Exception::class
    )

}

