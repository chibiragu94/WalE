package com.chibi.waie.data.local.repository

import androidx.annotation.WorkerThread
import com.chibi.waie.common.DateUtils
import com.chibi.waie.data.local.dao.IAstronomyDAO
import com.chibi.waie.model.Astronomy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Local Repository class where insert Astronomy,
 * getAllAstronomy,
 * getAstronomyByDate functionalities are handled
 */
class AstronomyLocalRepository @Inject constructor (private val astronomyDao : IAstronomyDAO) {

    @WorkerThread
    suspend fun insert(astronomy: Astronomy) = withContext(Dispatchers.IO){
        astronomyDao.insert(astronomy)
    }

    /**
     * Get All Astronomy in Desc Order in which it is inserted
     */
    suspend fun getAllAstronomy() : Flow<List<Astronomy>> {
        return flow {
            val astronomy = astronomyDao.getAllAstronomy()
            emit(astronomy)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Get Astronomy by passing the current Day, Month and Year
     * It will give result once matched
     */
    suspend fun getAstronomyByDate() : Flow<Astronomy?>{
        return flow {
            val astronomy = astronomyDao.getAstronomyByDate(DateUtils.getCurrentDay(),
                DateUtils.getCurrentMonth(), DateUtils.getCurrentYear())
            if (null != astronomy) {
                emit(astronomy)
            }else{
                emit(null)
            }

        }.flowOn(Dispatchers.IO)
    }
}