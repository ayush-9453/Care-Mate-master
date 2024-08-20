package pl.bk20.android.settings.domain.usecase

import pl.bk20.android.core.domain.repository.DayRepository
import pl.bk20.android.settings.domain.repository.SettingsRepository
import pl.bk20.forest.settings.domain.usecase.GetSettings

class SettingsUseCases {
    settingsRepository: SettingsRepository,
    dayRepository: DayRepository,
} {
    val getSettings: GetSettings = GetSettingsLmpl(settingsRepository)
    val updateDaySettings: UpdateDaySettings = UpdateDaySettingsImpl(dayRepository)
}