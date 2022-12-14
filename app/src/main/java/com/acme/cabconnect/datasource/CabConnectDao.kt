package com.acme.cabconnect.datasource

import androidx.room.*
import com.acme.cabconnect.domain.model.*

@Dao
interface CabConnectDao {

    @Transaction
    @Query("SELECT * FROM fahrten " +
            "WHERE datum >= :datum AND UPPER(start) LIKE '%' || :start || '%' " +
            "AND UPPER(ziel) LIKE '%' || :ziel || '%' AND freierPlatz >= :freierPlatz AND geloescht = 0 " +
            "ORDER BY datum ASC")
   fun getAllWithParams(datum: Long, start: String, ziel: String, freierPlatz: Int): List<MitgliederEinerFahrt>

    @Transaction
    @Query("SELECT * FROM fahrten f INNER JOIN UserFahrtRelation ufr " +
            "ON f.fahrtId=ufr.fahrtId WHERE ufr.userId = :userId AND f.geloescht = 0 " +
            "AND datum >= :datum ORDER BY datum ASC")
    suspend fun getMeineFahrten(userId: Int,datum: Long = System.currentTimeMillis()-86400000): List<MitgliederEinerFahrt>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun fahrtBeitreten(beitreten: UserFahrtRelation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFahrt(neueFahrt: Fahrt): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createFahrtRelation(neueFahrt: UserFahrtRelation)

    @Delete
    suspend fun fahrtAustreten(austreten: UserFahrtRelation)

    @Query("UPDATE fahrten SET geloescht = 1 WHERE fahrtId = :fahrtId")
    suspend fun fahrtLoeschen(fahrtId: Int)

    // Für Mock-Daten:
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(neuerUser: User)

    @Query("SELECT * FROM fahrten")
    fun getAll(): List<Fahrt>

}