package com.borispuhacin.dadjokes.repository

import com.borispuhacin.dadjokes.network.JokeModel
import com.borispuhacin.dadjokes.network.JokesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesRepository @Inject constructor(private val jokesApi: JokesApi) {

    suspend fun getRandomJoke() : JokeModel {
        return jokesApi.getRandomJoke()
    }
}