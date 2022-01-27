package com.chibi.waie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chibi.waie.model.Astronomy
import java.util.*

@Dao
interface IAstronomyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(astronomy: Astronomy)

    @Query("SELECT * FROM astronomy ORDER BY id DESC")
    suspend fun getAllAstronomy(): List<Astronomy>

    @Query("SELECT * FROM astronomy WHERE day= :day AND month= :month AND year= :year")
    suspend fun getAstronomyByDate(day: String, month: String, year: String): Astronomy?

}