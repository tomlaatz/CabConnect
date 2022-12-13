package com.acme.cabconnect.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acme.cabconnect.domain.model.User
import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.domain.model.UserFahrtRelation


@Database(entities = [User::class, Fahrt::class, UserFahrtRelation::class], version = 1)
abstract class CabConnectDatabase : RoomDatabase() {

    abstract val cabConnectDao: CabConnectDao

    companion object {
        const val DATABASE_NAME = "cabconnect_db"
    }
}