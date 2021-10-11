package co.condorlabs.coologger.processor

import co.condorlabs.coologger.annotations.LoggerDecorator
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.logger.Logger
import co.condorlabs.coologger.processor.builder.DecoratorBuilder
import co.condorlabs.coologger.processor.builder.MethodDecorator
import co.condorlabs.coologger.processor.functioncreator.CrashFunctionCreator
import co.condorlabs.coologger.processor.functioncreator.FunctionCreator
import co.condorlabs.coologger.processor.functioncreator.ScreenShownFunctionCreator
import co.condorlabs.coologger.processor.methodprocessors.CrashMethodProcessor
import co.condorlabs.coologger.processor.functioncreator.WidgetClickedFunctionCreator
import co.condorlabs.coologger.processor.methodprocessors.MethodProcessor
import co.condorlabs.coologger.processor.methodprocessors.ScreenShownMethodProcessor
import co.condorlabs.coologger.processor.methodprocessors.WidgetClickedMethodProcessor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.util.Collections
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * Useful resource to understand how it works:
 * https://medium.com/@iammert/annotation-processing-dont-repeat-yourself-generate-your-code-8425e60c6657
 */
class LoggerDecoratorProcessor : AbstractProcessor() {

    private val decoratorBuilders = mutableSetOf<DecoratorBuilder>()
    private val screenShownMethodProcessor: MethodProcessor =
        ScreenShownMethodProcessor(WidgetClickedMethodProcessor(CrashMethodProcessor()))

    private val functionCreators = setOf<FunctionCreator<MethodDecorator>>(
        ScreenShownFunctionCreator() as FunctionCreator<MethodDecorator>,
        WidgetClickedFunctionCreator() as FunctionCreator<MethodDecorator>,
        CrashFunctionCreator() as FunctionCreator<MethodDecorator>
    )

    private lateinit var filer: Filer
    private lateinit var messager: Messager
    private lateinit var processingEnvironment: ProcessingEnvironment

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return Collections.singleton(LoggerDecorator::class.java.canonicalName)
    }

    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        this.processingEnvironment = processingEnvironment
        filer = processingEnvironment.filer
        messager = processingEnvironment.messager
    }

    override fun process(
        typeElementSet: MutableSet<out TypeElement>,
        rounEnvironment: RoundEnvironment
    ): Boolean {
        val elements = getDecorators(rounEnvironment)

        if (elements == null || elements.isEmpty()) {
            return true
        }

        val elementsWrongAnnotated = elements.filter {
            it.kind != ElementKind.INTERFACE
        }

        if (elementsWrongAnnotated.isNotEmpty()) {
            messager.printMessage(Diagnostic.Kind.ERROR, LOGGER_ANNOTATION_WRONG_PLACE_ERROR_MESSAGE)
        }

        elements.map { interfaceElement ->
            decoratorBuilders.add(
                DecoratorBuilder(
                    className = "${interfaceElement.simpleName}$LOGGER_DECORATOR_SUFFIX",
                    classPackage = processingEnvironment.elementUtils.getPackageOf(interfaceElement).qualifiedName.toString(),
                    interfaceName = interfaceElement.simpleName.toString(),
                    methodDecorators = interfaceElement.enclosedElements.mapTo(mutableSetOf()) { methodElement ->
                        screenShownMethodProcessor.process(methodElement, messager)
                    }
                )
            )
        }

        writeImplementations()
        return true
    }

    private fun writeImplementations() {
        decoratorBuilders.forEach { decorator ->
            val constructor = FunSpec.constructorBuilder()
                .addParameter(LOGGER_CONSTRUCTOR_PARAM, Logger::class, KModifier.PRIVATE)
                .build()

            val property = PropertySpec.builder(LOGGER_CONSTRUCTOR_PARAM, Logger::class)
                .initializer(LOGGER_CONSTRUCTOR_PARAM)
                .build()

            val fileBuilder = FileSpec.builder(decorator.classPackage, decorator.className)
                .addImport(Logger::class.java.`package`.name, Logger::class.java.simpleName)
                .addImport(LogSource::class.java.`package`.name, LogSource::class.java.simpleName)

            val classBuilder = TypeSpec.classBuilder(decorator.className)
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL)
                .primaryConstructor(constructor)
                .addProperty(property)
                .addSuperinterface(ClassName(decorator.classPackage, decorator.interfaceName))

            decorator.methodDecorators.forEach { methodDecorator ->
                val functionCreator = functionCreators.first {
                    it.isApplicable(methodDecorator)
                }

                classBuilder.addFunction(functionCreator.create(methodDecorator))

                functionCreator.addEventImportToFileSpecBuilder(fileBuilder)
            }

            fileBuilder.addType(classBuilder.build())
                .build().writeTo(filer)
        }
    }

    private fun getDecorators(roundEnvironment: RoundEnvironment): Set<Element>? =
        roundEnvironment.getElementsAnnotatedWith(LoggerDecorator::class.java)
}

private const val LOGGER_DECORATOR_SUFFIX = "Impl"
private const val LOGGER_CONSTRUCTOR_PARAM = "logger"
private const val LOGGER_ANNOTATION_WRONG_PLACE_ERROR_MESSAGE = "Annotation should only be present in interfaces"
