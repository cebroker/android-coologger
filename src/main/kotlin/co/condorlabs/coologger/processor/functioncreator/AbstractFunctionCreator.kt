package co.condorlabs.coologger.processor.functioncreator

import co.condorlabs.coologger.processor.builder.MethodDecorator
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

abstract class AbstractFunctionCreator<Decorator : MethodDecorator> : FunctionCreator<Decorator> {

    abstract fun getMethodStatement(methodDecorator: Decorator): String

    override fun create(methodDecorator: Decorator): FunSpec {
        val funBuilder = getFunSpecBuilder(methodDecorator)
        addProperties(funBuilder, methodDecorator)
        addReturnType(funBuilder, methodDecorator)
        return funBuilder.build()
    }

    protected fun getSourcesStatement(sources: Set<String>?): String = if (sources == null) {
        ""
    } else {
        """
            , sources=setOf<LogSource>(${sources.joinToString(",")})
        """.trimIndent()
    }


    private fun getFunSpecBuilder(methodDecorator: Decorator) =
        FunSpec.builder(methodDecorator.methodName)
            .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)

    protected open fun addProperties(funSpecBuilder: FunSpec.Builder, methodDecorator: Decorator) =
        methodDecorator.propertiesName?.let { propertiesName ->
            funSpecBuilder.addParameter(
                propertiesName,
                Map::class.parameterizedBy(String::class, String::class)
            )
        }

    private fun addReturnType(funBuilder: FunSpec.Builder, methodDecorator: Decorator) {
        funBuilder.returns(Unit::class.javaObjectType).addStatement(getMethodStatement(methodDecorator))
    }
}
