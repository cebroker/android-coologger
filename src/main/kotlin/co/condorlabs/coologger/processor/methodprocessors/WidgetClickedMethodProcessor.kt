package co.condorlabs.coologger.processor.methodprocessors

import co.condorlabs.coologger.annotations.WidgetClicked
import co.condorlabs.coologger.events.WidgetType
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.builder.WidgetClickedMethodDecorator
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

class WidgetClickedMethodProcessor(nextProcessor: MethodProcessor? = null) :
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
            val parameter = methodElement.parameters[PROPERTIES_POSITION_IN_PARAMETER]
            methodDecoratorBuilder.withPropertiesName(parameter.simpleName.toString())
        }

        return methodDecoratorBuilder.build()
    }

    private fun getMethodMethodDecoratorBuilder(methodElement: ExecutableElement): WidgetClickedMethodDecorator.Builder {
        val widgetClickedAnnotation =
            methodElement.getAnnotation(WidgetClicked::class.java) as WidgetClicked

        return WidgetClickedMethodDecorator.Builder(
            widgetClickedAnnotation.widgetName,
            WidgetType.createFromString(widgetClickedAnnotation.widgetType),
            getMethodName(methodElement)
        )
    }

    override fun canMethodBeProcessed(methodElement: Element): Boolean =
        methodElement.getAnnotationsByType(WidgetClicked::class.java).isNotEmpty()
}
