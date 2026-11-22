package mcpews.message

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import mcpews.event.EventMeasurements
import mcpews.event.EventProperties
import mcpews.event.EventType

open class EventSerializer : JsonSerializer<MCEvent>, JsonDeserializer<MCEvent> {

    override fun serialize(
        event: MCEvent?,
        type: Type?,
        jsc: JsonSerializationContext?
    ): JsonElement? {
        if (event == null) {
            return null
        }

        val context = jsc!!
        val messageObj = JsonObject()

        // Get the class representing the body object from the purpose enum
        val eventType = event.getEventType()!!
        val measurementsClassType = eventType.getMeasurementsClassType()
        val propertiesClassType = eventType.getPropertiesClassType()

        messageObj.add("properties", context.serialize(event.getProperties(), propertiesClassType))
        messageObj.add("measurements", context.serialize(event.getMeasurements(), measurementsClassType))
        messageObj.add("eventName", context.serialize(eventType.getName()))

        return messageObj
    }

    override fun deserialize(
        je: JsonElement,
        type: Type?,
        jdc: JsonDeserializationContext?
    ): MCEvent {
        val eventObj = je.getAsJsonObject()
        val eventType = EventType.fromString(eventObj.get("eventName").getAsString())!!
        val context = jdc!!

        val properties: EventProperties? =
            context.deserialize<EventProperties>(eventObj.get("properties"), eventType.getPropertiesClassType())
        val measurements: EventMeasurements? =
            context.deserialize<EventMeasurements>(eventObj.get("measurements"), eventType.getMeasurementsClassType())

        return try {
            val eventClass = eventType.getEventClassType()!!
            val ctor = eventClass.getConstructor(
                EventType::class.java,
                EventProperties::class.java,
                EventMeasurements::class.java
            )
            eventClass.cast(ctor.newInstance(eventType, properties, measurements)) as MCEvent
        } catch (ex: Exception) {
            //ex.printStackTrace();
            MCEvent.create(eventType, properties, measurements)
        }
    }
}
