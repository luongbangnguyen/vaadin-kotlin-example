package com.vaadin.example.service

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class LocalDateAdapter : JsonSerializer<LocalDate> {

    override fun serialize(date: LocalDate, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}