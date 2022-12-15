package com.acme.cabconnect.presentation

import com.acme.cabconnect.domain.model.Fahrt

sealed class CabConnectEvent {
    data class GetSuchergebnisse(val datum: Long, val start: String, val ziel: String, val freierPlatz: Int): CabConnectEvent()
    data class GetMeineFahrten(val userId: Int): CabConnectEvent()
    data class DeleteFahrt(val fahrt: Fahrt): CabConnectEvent()
    object DeleteAll: CabConnectEvent()
    data class CreateFahrt(val fahrt: Fahrt, val userId: Int): CabConnectEvent()
    data class JoinFahrt(val join: Fahrt): CabConnectEvent()
    data class LeaveFahrt(val leave: Fahrt): CabConnectEvent()
}