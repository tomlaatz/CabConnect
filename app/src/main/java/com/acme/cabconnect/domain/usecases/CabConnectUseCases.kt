package com.acme.cabconnect.domain.usecases


data class CabConnectUseCases(
    val getSuchergebnisse: GetSuchergebnisse,
    val getMeineFahrten: GetMeineFahrten,
    val createFahrt: CreateFahrt,
    val createFahrtRelation: CreateFahrtRelation,
    val deleteFahrt: DeleteFahrt,
    val deleteAll: DeleteAll,
    val joinFahrt: JoinFahrt,
    val leaveFahrt: LeaveFahrt
)