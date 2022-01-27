package com.chibi.waie.data.remote.repository

import com.chibi.waie.data.remote.APIServices
import com.chibi.waie.data.remote.repository_interface.IBaseAPIRepository
import javax.inject.Inject

/**
 * BaseApiRepositoryImpl is created using the Factory Design Pattern,
 * where we can pass only the instance of BaseApiRepositoryImpl, so we can get the instance of all
 * other needed repository instance
 */
class BaseApiRepositoryImpl @Inject constructor(private val apiService: APIServices)
    : IBaseAPIRepository {

    override fun getAstronomyRepositoryImplInstance(): AstronomyRepositoryImpl {
        return AstronomyRepositoryImpl(apiService = apiService)
    }
}