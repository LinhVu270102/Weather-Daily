package com.example.weatherdaily.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdaily.data.repository.WeatherRepositoryImpl
import com.example.weatherdaily.domain.repository.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import com.example.weatherdaily.domain.model.WeatherLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query, errorMessage = null) }
        searchJob?.cancel()
        if (query.trim().length < 2) {
            _uiState.update { it.copy(isSearching = false, searchResults = emptyList()) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(400)
            search()
        }
    }

    fun startSearching() {
        searchJob?.cancel()
        if (_uiState.value.query.trim().length >= 2) {
            searchJob = viewModelScope.launch { search() }
        }
    }

    fun showResults(results: List<WeatherLocation>) {
        _uiState.update { it.copy(isSearching = false, searchResults = results) }
    }

    fun addRecentLocation(location: WeatherLocation) {
        _uiState.update { state ->
            state.copy(
                recentLocations = listOf(location) + state.recentLocations
                    .filterNot { it.latitude == location.latitude && it.longitude == location.longitude }
                    .take(4),
            )
        }
    }

    fun clearQuery() {
        _uiState.update { it.copy(query = "", searchResults = emptyList()) }
    }

    fun showError(message: String) {
        _uiState.update { it.copy(isSearching = false, errorMessage = message) }
    }

    private suspend fun search() {
        _uiState.update { it.copy(isSearching = true, errorMessage = null) }
        repository.searchLocations(_uiState.value.query)
            .onSuccess(::showResults)
            .onFailure { showError(it.message ?: "Không thể tìm địa điểm") }
    }
}
