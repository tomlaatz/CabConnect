package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.repository.CabConnectRepository

class DeleteAll(
    private val repository: CabConnectRepository
) {
    suspend operator fun invoke() {
        repository.deleteRelations()
        repository.deleteSequence()
        repository.deleteFahrten()
        repository.deleteUsers()
    }
}