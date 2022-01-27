package com.chibi.waie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chibi.waie.data.local.dao.IAstronomyDAO
import com.chibi.waie.model.Astronomy

@Database(entities = [Astronomy::class], version = 1, exportSchema = true)
abstract class WaieDataBase : RoomDatabase() {
    abstract fun astronomyDAO() : IAstronomyDAO
}
