package vertx.protobuffer.message.codec

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import org.koin.standalone.get


fun main(args: Array<String>) {
    TestModule.start()
    val eventBus = TestModule.get<EventBus>()
    TestModule.get<Vertx>().setPeriodic(1000) {
        eventBus.send(POINTS_TO_VISIT, DUBLIN)
    }
    Thread.sleep(Long.MAX_VALUE)
}

