package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.repository.CabConnectRepository

class GetSuchergebnisse(
    private val repository: CabConnectRepository
) {

    operator fun invoke(datum: Long, start: String, ziel: String, freierPlatz: Int): List<MitgliederEinerFahrt> {
        return repository.readSuchergebnisse(datum, start, ziel, freierPlatz)
    }
}