package co.condorlabs.coologger.processor.methodprocessors

import co.condorlabs.coologger.annotations.ScreenShown
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.builder.ScreenShownMethodDecorator
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

class ScreenShownMethodProcessor(nextProcessor: MethodProcessor? = null) :
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

    private fun getMethodMethodDecoratorBuilder(methodElement: ExecutableElement): ScreenShownMethodDecorator.Builder {
        val screeName =
            (methodElement.getAnnotation(ScreenShown::class.java) as ScreenShown).name

        return ScreenShownMethodDecorator.Builder(screeName, getMethodName(methodElement))
    }

    override fun canMethodBeProcessed(methodElement: Element): Boolean =
        methodElement.getAnnotationsByType(ScreenShown::class.java).isNotEmpty()
}
