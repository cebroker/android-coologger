package co.condorlabs.coologger.processor.methodprocessors

import co.condorlabs.coologger.annotations.LogEventProperties
import co.condorlabs.coologger.annotations.LogSources
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.exceptions.IllegalMethodToBeProcesseedException
import co.condorlabs.coologger.processor.exceptions.NoMethodProcessorFoundForMethodException
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.MirroredTypesException
import javax.tools.Diagnostic


abstract class AbstractMethodProcessor(private val nextProcessor: MethodProcessor? = null) :
    MethodProcessor {

    protected abstract fun processInternally(methodElement: ExecutableElement, messager: Messager): MethodDecorator

    protected abstract fun canMethodBeProcessed(methodElement: Element): Boolean

    override fun process(methodElement: Element, messager: Messager): MethodDecorator {
        require(methodElement is ExecutableElement)
        assert(checkMethodParamterSize(methodElement, messager))

        return if (canMethodBeProcessed(methodElement)) {
            processInternally(methodElement, messager)
        } else {
            nextProcessor?.process(methodElement, messager)
                ?: throw NoMethodProcessorFoundForMethodException(getMethodName(methodElement))
        }
    }

    private fun checkMethodParamterSize(methodElement: ExecutableElement, messager: Messager): Boolean {
        if (methodElement.parameters.size > ALLOWED_PARAMETERS_SIZE) {
            messager.printMessage(Diagnostic.Kind.ERROR, WRONG_NUMBERS_OF_PARAMETERS_FOR_ANNOTATED_METHODS)
            throw IllegalMethodToBeProcesseedException(getMethodName(methodElement))
        } else if (methodElement.parameters.size == ALLOWED_PARAMETERS_SIZE) {
            val logEventPropertiesCount =
                methodElement.parameters[PROPERTIES_POSITION_IN_PARAMETERS].getAnnotationsByType(
                    LogEventProperties::class.java
                ).count()

            if (logEventPropertiesCount != ALLOWED_PARAMETERS_SIZE) {
                messager.printMessage(Diagnostic.Kind.ERROR, WRONG_NUMBERS_OF_PARAMETERS_FOR_ANNOTATED_METHODS)
                throw IllegalMethodToBeProcesseedException(getMethodName(methodElement))
            }
        }

        return true
    }

    protected fun getSources(methodElement: ExecutableElement): Set<String>? {
        if (methodElement.getAnnotationsByType(LogSources::class.java).isEmpty()) return null

        val logSources = methodElement.getAnnotation(LogSources::class.java) as LogSources

        return try {
            logSources.logSources.map { it.simpleName!! }.toSet()
        } catch (e: MirroredTypesException) {
            e.printStackTrace()
            e.typeMirrors.map { it.toString() }.toSet()
        }
    }

    protected fun getMethodName(methodElement: ExecutableElement) = methodElement.simpleName.toString()
}

private const val PROPERTIES_POSITION_IN_PARAMETERS = 0
