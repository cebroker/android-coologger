package co.condorlabs.coologger

import io.mockk.MockK
import io.mockk.MockKDsl
import kotlin.reflect.KClass

inline fun <reified T : Any> relaxedMockk(
    name: String? = null,
    vararg moreInterfaces: KClass<*>,
    relaxUnitFun: Boolean = false,
    block: T.() -> Unit = {}
): T = MockK.useImpl {
    MockKDsl.internalMockk(
        name,
        true,
        *moreInterfaces,
        relaxUnitFun = relaxUnitFun,
        block = block
    )
}
