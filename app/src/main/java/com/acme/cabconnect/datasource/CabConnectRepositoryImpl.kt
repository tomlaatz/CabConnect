package com.acme.cabconnect.datasource

import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.model.UserFahrtRelation
import com.acme.cabconnect.domain.repository.CabConnectRepository

class CabConnectRepositoryImpl(
    private val dao: CabConnectDao
) : CabConnectRepository {

    override fun readSuchergebnisse(
        datum: Long,
        start: String,
        ziel: String,
        freierPlatz: Int
    ): List<MitgliederEinerFahrt> {
        return dao.getAllWithParams(datum, start, ziel, freierPlatz)
    }

    override suspend fun readMeineFahrten(userId: Int): List<MitgliederEinerFahrt> {
        return dao.getMeineFahrten(userId)
    }

    override suspend fun fahrtBeitreten(beitreten: UserFahrtRelation) {
        return dao.fahrtBeitreten(beitreten)
    }

    override suspend fun fahrtAustreten(austreten: UserFahrtRelation) {
        return dao.fahrtAustreten(austreten)
    }

    override suspend fun addFahrt(fahrt: Fahrt): Long {
        return dao.createFahrt(fahrt)
    }

    override suspend fun addFahrtRelation(userFahrtRelation: UserFahrtRelation) {
        return dao.createFahrtRelation(userFahrtRelation)
    }

    override suspend fun deleteFahrt(fahrtId: Int) {
        return dao.fahrtLoeschen(fahrtId)
    }

    override suspend fun deleteFahrten() {
        return dao.deleteAllFahrten()
    }

    override suspend fun deleteUsers() {
        return dao.deleteAllUsers()
    }

    override suspend fun deleteRelations() {
        return dao.deleteAllRelations()
    }

    override suspend fun deleteSequence() {
        return dao.deleteSequence()
    }
}