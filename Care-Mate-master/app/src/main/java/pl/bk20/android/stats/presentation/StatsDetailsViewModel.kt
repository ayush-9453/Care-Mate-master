package pl.bk20.android.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.bk20.android.MyApplication
import pl.bk20.android.core.data.repository.DayRepositorylmpl
import pl.bk20.android.core.domain.usecase.DayUseCases
import pl.bk20.android.settings.data.repository.SettingsRepositoryImpl
import pl.bk20.android.stats.domain.usecase.StatsDetailsUseCases
import java.time.LocalDate
import kotlin.math.roundToInt

class StatsDetailsViewModel(
    private val dayUseCases: DayUseCases,
    statsDetailsUseCases: StatsDetailsUseCases,
    currentDateFlow: StateFlow<LocalDate>
) : ViewModel() {

    private val _day = MutableStateFlow(
        StatsDetailsState(
            date = LocalDate.MIN,
            stepsTaken = 0,
            treeCollected = false,
            calorieBurned = 0,
            distanceTravelled = 0.0,
            carbonDioxideSaved = 0.0,
            chartDateRange = currentDateFlow.value..currentDateFlow.value
        )
    )
    val day: StateFlow<StatsDetailsState> = _day.asStateFlow()

    init {
        selectDay(currentDateFlow.value)

        viewModelScope.launch {
            val firstDateFlow = statsDetailsUseCases.getFirstDate()
            firstDateFlow
                .combine(currentDateFlow) { firstDate, currentDate ->
                    firstDate..currentDate
                }.collect { dateRange ->
                    _day.value = day.value.copy(chartDateRange = dateRange)
                }
        }
    }

    private var selectDateJob: Job? = null

    fun selectDay(date: LocalDate) {
        selectDateJob?.cancel()
        selectDateJob = dayUseCases.getDay(date).onEach {
            _day.value = day.value.copy(
                date = it.date,
                stepsTaken = it.steps,
                treeCollected = it.steps >= it.goal,
                calorieBurned = it.calorieBurned.roundToInt(),
                distanceTravelled = it.distanceTravelled,
                carbonDioxideSaved = it.carbonDioxideSaved
            )
        }.launchIn(viewModelScope)
    }

    companion object Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[APPLICATION_KEY]) as MyApllication

            val dayDatabase = application.forestDatabase
            val dayRepository = DayRepositorylmpl(dayDatabase.dayDao)
            val settingsStore = application.settingsStore
            val settingsRepository = SettingsRepositoryImpl(settingsStore)
            val dayUseCases = DayUseCases(dayRepository, settingsRepository)
            val statsDetailsUseCases = StatsDetailsUseCases(dayRepository)

            return StatsDetailsViewModel(
                dayUseCases,
                statsDetailsUseCases,
                application.currentDate
            ) as T
        }
    }
}