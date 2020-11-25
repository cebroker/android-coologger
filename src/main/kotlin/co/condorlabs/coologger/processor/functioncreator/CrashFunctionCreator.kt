package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.processor.builder.CrashMethodDecorator
import co.condorlabs.coologger.processor.builder.MethodDecorator
import com.squareup.kotlinpoet.FileSpec


class CrashFunctionCreator : AbstractFunctionCreator<CrashMethodDecorator>() {

    override fun isApplicable(methodDecorator: MethodDecorator): Boolean =
        methodDecorator is CrashMethodDecorator

    override fun getMethodStatement(methodDecorator: CrashMethodDecorator): String =
        if (methodDecorator.propertiesName == null) {
            throw IllegalArgumentException("Crash event must provide an exception")
        } else {
            """
                logger.log(CrashEvent(name="${methodDecorator.name}",exception="${methodDecorator.propertiesName}${
                getSourcesStatement(
                    methodDecorator.sources
                )
            }"))
           """.trimIndent()
        }

    override fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder) {
        fileSpecBuilder.addImport(
            Exception::class.java.simpleName
        )
    }
}
