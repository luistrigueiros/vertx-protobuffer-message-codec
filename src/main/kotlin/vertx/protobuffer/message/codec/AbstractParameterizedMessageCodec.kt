package vertx.protobuffer.message.codec

import io.vertx.core.eventbus.MessageCodec

abstract class AbstractParameterizedMessageCodec<T : Any>(private val clazz: Class<T>) : MessageCodec<T, T> {
    override fun name(): String = javaClass.simpleName
    override fun transform(payload: T): T = payload
    fun type() = clazz
    override fun systemCodecID(): Byte = -1
}