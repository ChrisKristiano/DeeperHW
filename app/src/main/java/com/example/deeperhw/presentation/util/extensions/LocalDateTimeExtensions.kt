package com.example.deeperhw.presentation.util.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toDisplayValue(): String =
    "${this.toLocalDate()} ${this.toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME)}"