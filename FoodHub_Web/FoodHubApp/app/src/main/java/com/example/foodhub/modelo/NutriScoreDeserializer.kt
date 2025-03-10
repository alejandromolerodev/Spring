package com.example.foodhub.modelo

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class NutriScoreDeserializer : JsonDeserializer<NutriScore> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): NutriScore {
        // Aseguramos que el valor no sea nulo, y retornamos UNKNOWN si es necesario.
        return NutriScore.fromString(json.asString) ?: NutriScore.UNKNOWN
    }
}
