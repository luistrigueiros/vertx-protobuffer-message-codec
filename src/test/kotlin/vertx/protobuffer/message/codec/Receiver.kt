package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.vertx.core.eventbus.EventBus
import org.koin.log.Logger.SLF4JLogger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

object Receiver : KoinComponent {
    private val eventBus : EventBus by inject()

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin(listOf(TestModule.vertModule), logger = SLF4JLogger())
        eventBus.consumer<Example.GeoPoint>(POINTS_TO_VISIT) {
            println("Got =====> " + it.body())
        }
        Thread.sleep(Long.MAX_VALUE)
    }
}