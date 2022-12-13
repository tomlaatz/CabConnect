package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.UserFahrtRelation
import com.acme.cabconnect.domain.repository.CabConnectRepository

class JoinFahrt(
    private val repository: CabConnectRepository
) {
    suspend operator fun invoke(beitreten: UserFahrtRelation) {
        repository.fahrtBeitreten(beitreten)
    }
}