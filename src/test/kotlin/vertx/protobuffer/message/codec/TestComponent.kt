package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.koin.dsl.module.module
import org.koin.log.Logger.SLF4JLogger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import java.util.concurrent.CompletableFuture

const val POINTS_TO_VISIT = "app.point.to.visit"

val DUBLIN = Example.GeoPoint.newBuilder()
        .setLat(53.350140)
        .setLng(-6.266155)
        .build()


private fun clusterVertxCreation(): CompletableFuture<Vertx> {
    val future = CompletableFuture<Vertx>()
    val options = VertxOptions().apply {
        maxEventLoopExecuteTime = Long.MAX_VALUE
    }
    Vertx.clusteredVertx(options) { ar ->
        if (ar.succeeded()) {
            future.complete(ar.result())
        } else {
            future.completeExceptionally(ar.cause())
        }
    }
    return future
}


object TestModule {
    val vertModule = module {
        single { clusterVertxCreation().get() }
        single(createOnStart = true) {
            get<Vertx>().eventBus()
                    .apply {
                        registerCodecFor<Example.GeoPoint>()
                        registerCodecFor<Example.PhoneNumber>()
                        registerCodecFor<Example.CurrencyAmount>()
                    }
        }
    }
}