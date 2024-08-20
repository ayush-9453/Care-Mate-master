package pl.bk20.android.stats.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.bk20.android.core.domain.model.Day
import pl.bk20.android.core.domain.repository.DayRepository
import java.time.LocalDate

interface GetWeek {

    operator fun invoke(startingAt: LocalDate): Flow<List<Day>>
}

class GetWeekImpl(
    private val dayRepository: DayRepository
) : GetWeek {

    override fun invoke(startingAt: LocalDate): Flow<List<Day>> {
        val endingAt = startingAt.plusDays(6)
        return dayRepository.getDays(startingAt..endingAt)
    }
}