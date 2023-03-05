package com.borispuhacin.dadjokes.di

import com.borispuhacin.dadjokes.util.Constants
import com.borispuhacin.dadjokes.network.JokesApi
import com.borispuhacin.dadjokes.repository.JokesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesJokesApi() : JokesApi {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(JokesApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(jokesApi: JokesApi) = JokesRepository(jokesApi)
}