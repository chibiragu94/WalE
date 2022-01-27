package com.chibi.waie.data.remote.repository_interface

import com.chibi.waie.data.remote.repository.AstronomyRepositoryImpl

interface IBaseAPIRepository {
    fun getAstronomyRepositoryImplInstance() : AstronomyRepositoryImpl
}