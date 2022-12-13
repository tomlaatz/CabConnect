package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.domain.repository.CabConnectRepository

class CreateFahrt(
    private val repository: CabConnectRepository
) {

    suspend operator fun invoke(fahrt: Fahrt): Long {
        return repository.addFahrt(fahrt)
    }
}