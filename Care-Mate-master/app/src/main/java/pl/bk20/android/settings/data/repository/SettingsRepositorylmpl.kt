package pl.bk20.android.settings.data.repository

import kotlinx.coroutines.flow.Flow
import pl.bk20.android.settings.data.source.SettingsStore
import pl.bk20.android.settings.domain.model.Settings
import pl.bk20.android.settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsStore: SettingsStore
) : SettingsRepository {

    override fun getSettings(): Flow<Settings> {
        return settingsStore.getSettings()
    }
}