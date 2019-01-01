package vertx.protobuffer.message.codec

import com.example.wire.Example
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin

const val POINTS_TO_VISIT = "app.point.to.visit"

val DUBLIN = Example.GeoPoint.newBuilder()
        .setLat(53.350140)
        .setLng(-6.266155)
        .build()


class TestModule : KoinComponent {
    init {
        startKoin(listOf(vertModule))
    }
}