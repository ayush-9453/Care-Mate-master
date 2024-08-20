package pl.bk20.android.stats.domain.usecase

import pl.bk20.android.core.domain.repository.DayRepository

class StatsDetailsUseCases(
    dayRepository: DayRepository
) {

    val getFirstDate: GetFirstDate = GetFirstDateImpl(dayRepository)
}