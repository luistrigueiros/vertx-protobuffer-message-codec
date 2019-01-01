package vertx.protobuffer.message.codec

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.Message
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.EventBus
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

private object RegistryHolder {
    val extensionRegistry: ExtensionRegistry = ExtensionRegistry.newInstance()
}

private object ProtoBufferBuilderCreator {
    private val methodCache = ConcurrentHashMap<Class<Message>, Method>()

    fun getMessageBuilder(clazz: Class<Message>): Message.Builder {
        val method = methodCache.computeIfAbsent(clazz) {
            it.getMethod("newBuilder")
        }
        val any = method.invoke(clazz)
        return any as Message.Builder
    }
}

inline fun <reified T : Message> EventBus.registerCodecFor() {
    logger().info("Register codec for ${T::class.java.name}")
    this.registerDefaultCodec(T::class.java, ProtoBufferMessageCodec(T::class.java))
}


class ProtoBufferMessageCodec<T : Message>(clazz: Class<T>) : AbstractParameterizedMessageCodec<T>(clazz) {

    override fun encodeToWire(buffer: Buffer, mgs: T) {
        with(mgs.toByteArray()) {
            buffer.appendInt(this.size)
            buffer.appendBytes(this)
        }
    }

    override fun decodeFromWire(pos: Int, buffer: Buffer): T {
        return rehydrate(buffer, pos, getMessageBuilder())
                .let { it.build() }
                .let(type()::cast)
    }

    private fun rehydrate(buffer: Buffer, pos: Int, builder: Message.Builder): Message.Builder {
        return buffer.getInt(pos)
                .let {
                    buffer.getBytes(pos + 4, pos + 4 + it)
                }.let {
                    builder.mergeFrom(it, RegistryHolder.extensionRegistry)
                }
    }

    private fun getMessageBuilder(): Message.Builder {
        val clazz = type() as Class<Message>
        return ProtoBufferBuilderCreator.getMessageBuilder(clazz)
    }
}