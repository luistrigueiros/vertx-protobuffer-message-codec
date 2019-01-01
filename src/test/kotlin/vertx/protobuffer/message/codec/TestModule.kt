package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.koin.dsl.module.module
import java.util.concurrent.CompletableFuture

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

val vertModule = module {
    single { clusterVertxCreation().get() }
    single(createOnStart = true) {
        get<Vertx>().eventBus()
                .apply {
                    registerCodecFor<Example.GeoPoint>()
                }
    }
}

