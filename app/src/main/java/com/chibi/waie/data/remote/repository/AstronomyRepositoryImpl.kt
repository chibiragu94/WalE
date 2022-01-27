package com.chibi.waie.data.remote.repository

import com.chibi.waie.common.EndPoints
import com.chibi.waie.common.Resource
import com.chibi.waie.data.remote.APIServices
import com.chibi.waie.data.remote.repository_interface.IAstronomyRepository
import com.chibi.waie.model.Astronomy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.HttpURLConnection

/**
 * Get Astronomy Detail from the API using the retrofit
 */
class AstronomyRepositoryImpl(private val apiService: APIServices) : IAstronomyRepository {

    /**
     * Get Astronomy from the API call's
     */
    override suspend fun getAstronomyDetails(): Flow<Resource<Astronomy?>> {
        return flow {

            val response = apiService.getAstronomyDetails(EndPoints.APOD_URL, EndPoints.API_KEY)

            when(response?.code())
            {
                HttpURLConnection.HTTP_OK -> {
                    emit(Resource.success(response.body()))
                }
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    emit(Resource.error(null,"HTTP_BAD_REQUEST"))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    emit(Resource.error(null,"User is UnAuthorized"))
                }
                else -> {
                    response?.let {
                        val msg = response.message() ?: " "
                        val q = response.errorBody() ?: ""
                        val errMessage = "${response.code()} + $q + $msg"
                        emit(Resource.error(null,errMessage))
                    }

                }
            }
        }.flowOn(Dispatchers.IO)
    }
}