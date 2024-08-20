package pl.bk20.android.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.bk20.android.MyApplication
import pl.bk20.android.core.data.repository.DayRepositorylmpl
import pl.bk20.android.stats.domain.usecase.StatsSummaryUseCases

class StatsSummaryViewModel(
    private val statsSummaryUseCases: StatsSummaryUseCases
) : ViewModel() {

    private val _statsStatsSummary = MutableStateFlow(StatsSummaryState())
    val statsSummary: StateFlow<StatsSummaryState> = _statsStatsSummary.asStateFlow()

    init {
        refreshStatsSummary()
    }

    private var refreshStatsSummaryJob: Job? = null

    fun refreshStatsSummary() {
        refreshStatsSummaryJob?.cancel()
        refreshStatsSummaryJob = viewModelScope.launch {
            _statsStatsSummary.value = statsSummary.value.copy(
                isRefreshing = true
            )
            val updatedSummary = statsSummaryUseCases.getSummary()
            updatedSummary.run {
                _statsStatsSummary.value = statsSummary.value.copy(
                    isRefreshing = false,
                    treesCollected = treesCollected,
                    stepsTaken = stepsTaken,
                    calorieBurned = calorieBurned,
                    distanceTravelled = distanceTravelled,
                    carbonDioxideSaved = carbonDioxideSaved,
                )
            }
        }
    }

    companion object Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application =
                checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MyApplication
            val dayDatabase = application.forestDatabase
            val dayRepository = DayRepositorylmpl(dayDatabase.dayDao)
            val statsSummaryUseCases = StatsSummaryUseCases(dayRepository)
            return StatsSummaryViewModel(statsSummaryUseCases) as T
        }
    }
}