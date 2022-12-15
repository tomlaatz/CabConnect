package com.acme.cabconnect.di

import android.app.Application
import androidx.room.Room
import com.acme.cabconnect.datasource.CabConnectDatabase
import com.acme.cabconnect.datasource.CabConnectRepositoryImpl
import com.acme.cabconnect.domain.repository.CabConnectRepository
import com.acme.cabconnect.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun proideCabConnectDatabase(app: Application): CabConnectDatabase {
        return Room.databaseBuilder(
            app,
            CabConnectDatabase::class.java,
            CabConnectDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun proideCabConnectRepository(db: CabConnectDatabase): CabConnectRepository {
        return CabConnectRepositoryImpl(db.cabConnectDao)
    }

    @Provides
    @Singleton
    fun provideCabConnectUseCases(repository: CabConnectRepository): CabConnectUseCases {
        return CabConnectUseCases(
            getSuchergebnisse = GetSuchergebnisse(repository),
            deleteFahrt = DeleteFahrt(repository),
            createFahrt = CreateFahrt(repository),
            joinFahrt = JoinFahrt(repository),
            leaveFahrt = LeaveFahrt(repository),
            getMeineFahrten = GetMeineFahrten(repository),
            createFahrtRelation = CreateFahrtRelation(repository),
            deleteAll = DeleteAll(repository)
        )
    }
}