package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.test.KoinTest


class FirstTest : KoinTest {
    @Before
    fun before() {
        TestModule.start()
    }

    @After
    fun after() {
        get<Vertx>().close()
        stopKoin()
    }

    @Test
    fun `simple test`() {
        val DUBLIN = Example.GeoPoint.newBuilder()
                .setLat(53.350140)
                .setLng(-6.266155)
                .build()

        val vertx = get<Vertx>()
        val eventBus = get<EventBus>()
        val ADDRESS = "SOME_ADDRESS"
        eventBus.consumer<Example.GeoPoint>(ADDRESS) {
            println("Got =====> " + it.body())
        }
        vertx.setPeriodic(1000) {
            eventBus.send(ADDRESS, DUBLIN)
        }
        Thread.sleep(10 * 1000)
    }

}