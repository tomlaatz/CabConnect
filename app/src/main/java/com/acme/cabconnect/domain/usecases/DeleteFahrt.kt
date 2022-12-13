package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.repository.CabConnectRepository

class DeleteFahrt(
    private val repository: CabConnectRepository
) {
    suspend operator fun invoke(fahrtId: Int) {
        repository.deleteFahrt(fahrtId)
    }
}