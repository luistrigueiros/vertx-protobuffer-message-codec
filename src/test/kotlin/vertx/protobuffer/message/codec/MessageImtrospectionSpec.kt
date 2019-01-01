package vertx.protobuffer.message.codec

import com.example.wire.Example
import io.kotlintest.specs.FunSpec

class MessageImtrospectionSpec : FunSpec({
    test("Simple test") {
        val kClass = Example.GeoPoint::class.java
        kClass.methods.forEach {
            println(it.name)
        }
    }
})