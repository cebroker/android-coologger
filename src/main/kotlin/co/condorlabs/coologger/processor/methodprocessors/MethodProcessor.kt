package co.condorlabs.coologger.processor.methodprocessors

import co.condorlabs.coologger.processor.builder.MethodDecorator
import javax.annotation.processing.Messager
import javax.lang.model.element.Element

interface MethodProcessor {

    fun process(methodElement: Element, messager: Messager): MethodDecorator
}

internal const val PROPERTIES_POSITION_IN_PARAMETER = 0
internal const val ALLOWED_PARAMETERS_SIZE = 1
internal const val WRONG_NUMBERS_OF_PARAMETERS_FOR_ANNOTATED_METHODS =
    "Annotated methods can only have 1 parameter and it should be annotated withLogEventProperties"
