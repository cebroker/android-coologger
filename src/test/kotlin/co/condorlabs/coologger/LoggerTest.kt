package co.condorlabs.coologger

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.event.LogSource
import co.condorlabs.coologger.exceptions.LogSourceNotSupportedException
import co.condorlabs.coologger.logger.Coologger
import co.condorlabs.coologger.logger.Logger
import io.mockk.Called
import io.mockk.every
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.lang.Exception

object FirebaseSource : LogSource {
    override val name: String
        get() = "F"
}

object CrashlyticsSource : LogSource {
    override val name: String
        get() = "G"
}

object AnotherSource : LogSource {
    override val name: String
        get() = "H"
}

class LoggerTest {

    private val firebaseLogger = relaxedMockk<Logger>()
    private val crashlytcisLogger = relaxedMockk<Logger>()
    private val event = relaxedMockk<LogEvent>()
    private lateinit var logger: Coologger

    @Before
    fun setup() {
        every { event.isSourceSupported(FirebaseSource) } answers { false }
        every { event.isSourceSupported(CrashlyticsSource) } answers { false }
        every { event.isSourceSupported(AnotherSource) } answers { false }
        logger = Coologger(
            mapOf(
                (FirebaseSource to firebaseLogger),
                (CrashlyticsSource to crashlytcisLogger)
            )
        )
    }

    @Test
    fun `given an event supported by one source when log is called then it should be logged just to the child logger that support the source`() {
        every { event.isSourceSupported(FirebaseSource) } answers { true }

        logger.log(event)

        verify {
            firebaseLogger.log(event)
            crashlytcisLogger wasNot Called
        }
    }

    @Test
    fun `given an event supported by multiple source when log is called then it should be logged is all the sources`() {
        every { event.isSourceSupported(FirebaseSource) } answers { true }
        every { event.isSourceSupported(CrashlyticsSource) } answers { true }

        logger.log(event)

        verify {
            firebaseLogger.log(event)
            crashlytcisLogger.log(event)
        }
    }

    @Test
    fun `given an event with a source not supported by any child logger when log is called then it should throw LogSourceNotSupportedException`() {

        try {
            logger.log(event)
        }catch (e: LogSourceNotSupportedException){
            assert(true)
        }

        verify {
            firebaseLogger wasNot Called
            crashlytcisLogger wasNot Called
        }
    }

    @Test
    fun `given a unique identifier for the event when identify is called then it should identify in all the loggers`() {
        logger.identify("1")

        verify {
            firebaseLogger.identify("1")
            crashlytcisLogger.identify("1")
        }
    }

}
