package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.vertx.core.eventbus.EventBus
import org.koin.standalone.get

fun main(args: Array<String>) {
    TestModule.start()
    val eventBus = TestModule.get<EventBus>()
    eventBus.consumer<Example.GeoPoint>(POINTS_TO_VISIT) {
        println("Got =====> " + it.body())
    }
    Thread.sleep(Long.MAX_VALUE)
}