package com.chibi.waie.data.remote.repository_interface

import com.chibi.waie.common.Resource
import com.chibi.waie.model.Astronomy
import kotlinx.coroutines.flow.Flow

interface IAstronomyRepository {

    suspend fun getAstronomyDetails() : Flow<Resource<Astronomy?>>
}