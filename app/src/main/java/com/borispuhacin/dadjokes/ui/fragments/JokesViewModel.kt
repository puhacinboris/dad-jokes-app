package com.borispuhacin.dadjokes.ui.fragments

import androidx.lifecycle.*
import com.borispuhacin.dadjokes.network.JokeModel
import com.borispuhacin.dadjokes.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class JokeApiStatus { LOADING, DONE, ERROR }

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository
) : ViewModel() {

    private val _status = MutableLiveData<JokeApiStatus>()
    val status: LiveData<JokeApiStatus> = _status

    private val _joke = MutableLiveData<JokeModel>()
    val joke: LiveData<JokeModel> = _joke

    init {
        getRandomJoke()
    }

    fun getRandomJoke() {
        viewModelScope.launch {
            _status.value = JokeApiStatus.LOADING
            try {
                _joke.value = jokesRepository.getRandomJoke()
                _status.value = JokeApiStatus.DONE
            } catch(e: Exception) {
                _status.value = JokeApiStatus.ERROR
            }
        }
    }
}