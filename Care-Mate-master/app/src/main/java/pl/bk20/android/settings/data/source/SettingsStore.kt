package pl.bk20.android.settings.data.source

import kotlinx.coroutines.flow.Flow
import pl.bk20.android.settings.domain.model.Settings

interface SettingsStore {

    fun getSettings(): Flow<Settings>
}