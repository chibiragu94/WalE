package com.chibi.waie.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chibi.waie.common.DateUtils
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Data, Entity class holding the data of the Astronomy
 */
@Entity(tableName = "astronomy")
@Parcelize
@Keep
data class Astronomy(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "day")
    var insertedDay : String = DateUtils.getCurrentDay(),
    @ColumnInfo(name = "month")
    var insertedMonth : String = DateUtils.getCurrentMonth(),
    @ColumnInfo(name = "year")
    var insertedYear : String = DateUtils.getCurrentYear(),
    @SerializedName("copyright") val copyright : String,
    @SerializedName("date") val date : String,
    @SerializedName("explanation") val explanation : String,
    @SerializedName("hdurl") val hdurl : String,
    @SerializedName("media_type") val media_type : String,
    @SerializedName("service_version") val service_version : String,
    @SerializedName("title") val title : String,
    @SerializedName("url") val url : String,
) : Parcelable

