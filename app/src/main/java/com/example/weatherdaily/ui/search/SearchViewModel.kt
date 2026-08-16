package com.example.weatherdaily.ui.search

import androidx.lifecycle.ViewModel
import com.example.weatherdaily.domain.model.WeatherLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query, errorMessage = null) }
    }

    fun startSearching() {
        if (_uiState.value.query.isBlank()) return
        _uiState.update { it.copy(isSearching = true, errorMessage = null) }
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
}
