package pl.bk20.android.stats.domain.usecase

import pl.bk20.android.core.domain.repository.DayRepository

class StatsChartPageUseCases(
    dayRepository: DayRepository
) {

    val getWeek: GetWeek = GetWeekImpl(dayRepository)
}