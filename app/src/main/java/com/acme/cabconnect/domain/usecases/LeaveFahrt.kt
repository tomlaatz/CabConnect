package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.UserFahrtRelation
import com.acme.cabconnect.domain.repository.CabConnectRepository

class LeaveFahrt(
    private val repository: CabConnectRepository
) {
    suspend operator fun invoke(austreten: UserFahrtRelation) {
        repository.fahrtAustreten(austreten)
    }
}