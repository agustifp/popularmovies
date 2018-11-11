package com.example.domain.interactor

import com.example.domain.base.BaseCoRoutineUseCase
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository

class GetSavedMoviesUseCase(private val mostPopularMoviesRepository: MostPopularMoviesRepository)
    : BaseCoRoutineUseCase<MovieListEntity, EmptyParams>() {
    override suspend fun buildRepoCall(params: EmptyParams): MovieListEntity =
            mostPopularMoviesRepository.getSavedMostPopularMovies()
}
