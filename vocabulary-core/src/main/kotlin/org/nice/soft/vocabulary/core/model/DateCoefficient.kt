package org.nice.soft.vocabulary.core.model

import java.time.LocalDate
import java.time.Period

object DateCoefficient {

    fun get(date: LocalDate?): Double {
        if (date == null) return 0.0
        val now = LocalDate.now()
        var days = Period.between(date, now).days
        if (days > 5) days = 5
        return days * 0.2
    }
}