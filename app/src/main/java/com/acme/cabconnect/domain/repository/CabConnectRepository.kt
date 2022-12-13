package com.acme.cabconnect.domain.repository

import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.model.UserFahrtRelation

interface CabConnectRepository {

    fun readSuchergebnisse(datum: Long, start: String, ziel: String, freierPlatz: Int): List<MitgliederEinerFahrt>

    suspend fun readMeineFahrten(userId: Int): List<MitgliederEinerFahrt>

    suspend fun fahrtBeitreten(beitreten: UserFahrtRelation)

    suspend fun fahrtAustreten(austreten: UserFahrtRelation)

    suspend fun addFahrt(fahrt: Fahrt): Long

    suspend fun addFahrtRelation(userFahrtRelation: UserFahrtRelation)

    suspend fun deleteFahrt(fahrtId: Int)
}