package pl.bk20.android.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pl.bk20.android.core.domain.model.Day
import pl.bk20.android.core.domain.model.of
import pl.bk20.android.core.domain.repository.DayRepository
import pl.bk20.android.settings.domain.repository.SettingsRepository
import java.time.LocalDate

class GetDayImpl(
    private val dayRepository: DayRepository,
    private val settingsRepository: SettingsRepository,
) : GetDay {

    override fun invoke(date: LocalDate): Flow<Day> {
        val settingsFlow = settingsRepository.getSettings()
        val dayFlow = dayRepository.getDay(date)

        return settingsFlow.combine(dayFlow) { settings, day ->
            day ?: Day.of(date, settings, steps = 0)
        }
    }
}