package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.processor.builder.MethodDecorator
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

interface FunctionCreator<Decorator : MethodDecorator> {

    fun isApplicable(methodDecorator: MethodDecorator): Boolean

    fun create(methodDecorator: Decorator): FunSpec

    fun addEventImportToFileSpecBuilder(fileSpecBuilder: FileSpec.Builder)
}
