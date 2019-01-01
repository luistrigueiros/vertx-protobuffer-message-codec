package vertx.protobuffer.message.codec

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import org.koin.log.Logger.SLF4JLogger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

object Sender : KoinComponent {
    private val vertx: Vertx by inject()
    private val eventBus: EventBus by inject()

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin(listOf(TestModule.vertModule), logger = SLF4JLogger())
        vertx.setPeriodic(1000) {
            eventBus.send(POINTS_TO_VISIT, DUBLIN)
        }
        Thread.sleep(Long.MAX_VALUE)
    }
}

