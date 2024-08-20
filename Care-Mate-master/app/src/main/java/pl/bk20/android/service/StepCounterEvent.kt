package pl.bk20.android.service

import java.time.LocalDate

data class StepCounterEvent(
    val stepCount: Int,
    val eventDate: LocalDate,
)