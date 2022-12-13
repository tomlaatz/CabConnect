package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.UserFahrtRelation
import com.acme.cabconnect.domain.repository.CabConnectRepository

class CreateFahrtRelation(
    private val repository: CabConnectRepository
) {

    suspend operator fun invoke(userFahrtRelation: UserFahrtRelation) {
        repository.addFahrtRelation(userFahrtRelation)
    }
}