package co.condorlabs.coologger.processor.methodprocessors

import co.condorlabs.coologger.annotations.Crash
import co.condorlabs.coologger.processor.builder.CrashMethodDecorator
import co.condorlabs.coologger.processor.builder.MethodDecorator
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

class CrashMethodProcessor(nextProcessor: MethodProcessor? = null) :
    AbstractMethodProcessor(nextProcessor) {

    override fun processInternally(
        methodElement: ExecutableElement,
        messager: Messager
    ): MethodDecorator {
        val methodDecoratorBuilder = getMethodMethodDecoratorBuilder(methodElement)

        getSources(methodElement)?.let {
            methodDecoratorBuilder.withSources(it)
        }

        if (methodElement.parameters.size == ALLOWED_PARAMETERS_SIZE) {
            val paramether = methodElement.parameters[PROPERTIES_POSITION_IN_PARAMETER]
            methodDecoratorBuilder.withPropertiesName(paramether.simpleName.toString())
        }

        return methodDecoratorBuilder.build()
    }

    private fun getMethodMethodDecoratorBuilder(methodElement: ExecutableElement): CrashMethodDecorator.Builder {
        return CrashMethodDecorator.Builder(getMethodName(methodElement))
    }

    override fun canMethodBeProcessed(methodElement: Element): Boolean =
        methodElement.getAnnotationsByType(Crash::class.java).isNotEmpty()

}
