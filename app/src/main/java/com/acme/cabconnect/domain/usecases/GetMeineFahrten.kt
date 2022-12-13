package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.repository.CabConnectRepository

class GetMeineFahrten (
    private val repository: CabConnectRepository
) {

    suspend operator fun invoke(userId: Int): List<MitgliederEinerFahrt> {
        return repository.readMeineFahrten(userId)
    }
}
