package pl.bk20.android.settings.domain.usecase

import pl.bk20.android.core.domain.model.DaySettings
import pl.bk20.android.core.domain.repository.DayRepository

interface UpdateDaySettings {

    suspend operator fun  invoke(daySettings: DaySettings)
}

class UpdateDaySettingsLmpl(
    private val dayRepository: DayRepository
) : UpdateDaySettings {

    override suspend fun invoke(daySettings: DaySettings) {
        dayRepository.updateDaySettings(daySettings)
    }
}

