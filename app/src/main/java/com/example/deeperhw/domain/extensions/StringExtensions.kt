package com.example.deeperhw.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.toLocalDateTime(): LocalDateTime? = try {
    LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
} catch (e: DateTimeParseException) { null }