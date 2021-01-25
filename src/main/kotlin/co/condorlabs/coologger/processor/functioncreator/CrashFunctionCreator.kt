package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.events.CrashEvent
import co.condorlabs.coologger.processor.builder.CrashMethodDecorator
import co.condorlabs.coologger.processor.builder.MethodDecorator
import com.squareup.kotlinpoet.FileSpec


class CrashFunctionCreator : AbstractFunctionCreator<CrashMethodDecorator>() {

    override fun isApplicable(methodDecorator: MethodDecorator): Boolean =
        methodDecorator is CrashMethodDecorator

    override fun getMethodStatement(methodDecorator: CrashMethodDecorator): String =
        if (methodDecorator.propertiesNameException == null) {
            throw IllegalArgumentException("Crash event must provide an exception")
        } else {
            """
                logger.log(CrashEvent(name="${methodDecorator.name}",exception="${methodDecorator.propertiesNameException}${
                getSourcesStatement(
                    methodDecorator.sources
                )
            }"))
           """.trimIndent()
        }

    override fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder) {
        fileSpecBuilder.addImport(
            Exception::class.java.simpleName,
            CrashEvent::class.java.`package`.name,
            CrashEvent::class.java.simpleName
        )
    }
}
