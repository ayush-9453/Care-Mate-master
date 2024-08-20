package pl.bk20.android.stats.domain.usecase

import pl.bk20.android.core.domain.repository.DayRepository

class StatsSummaryUseCases(
    dayRepository: DayRepository
) {

    val getSummary: GetSummary = GetSummaryImpl(dayRepository)
}