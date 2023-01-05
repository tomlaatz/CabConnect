package com.acme.cabconnect.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.model.UserFahrtRelation
import com.acme.cabconnect.domain.usecases.CabConnectUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CabConnectViewModel @Inject constructor(
    private val cabConnectUseCases: CabConnectUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CabConnectState())
    val state: StateFlow<CabConnectState> = _state

    fun onEvent(event: CabConnectEvent) {
        when(event) {
            // Event um die Suchergebnisse von der DB zu laden.
            is CabConnectEvent.GetSuchergebnisse -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.fahrten = cabConnectUseCases
                        .getSuchergebnisse(
                            event.datum,
                            event.start,
                            event.ziel,
                            event.freierPlatz,
                            event.filter,
                            event.sortAsc
                        )
                }
            }
            // Event um meine Fahrten von der DB zu laden.
            is CabConnectEvent.GetMeineFahrten -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.meineFahrten = cabConnectUseCases.getMeineFahrten(event.userId)
                }
            }
            // Event um eine bestimmte Fahrt von der DB zu löschen.
            is CabConnectEvent.DeleteFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cabConnectUseCases.deleteFahrt(event.fahrt.fahrtId!!)
                }
            }
            // Event um eine neue Fahrt in der DB zu speichern.
            is CabConnectEvent.CreateFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val fahrtId = cabConnectUseCases.createFahrt(event.fahrt)
                    val userFahrtRelation = UserFahrtRelation(event.userId, fahrtId.toInt())
                    cabConnectUseCases.createFahrtRelation(userFahrtRelation)
                }
            }
            // Event um einer beigretenen Fahrt zu verlassen.
            is CabConnectEvent.LeaveFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userFahrtRelation = UserFahrtRelation(1, event.leave.fahrtId!!)
                    cabConnectUseCases.leaveFahrt(userFahrtRelation)
                }
            }
            // Event um einer Fahrt beizutreten.
            is CabConnectEvent.JoinFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userFahrtRelation = UserFahrtRelation(1, event.join.fahrtId!!)
                    cabConnectUseCases.joinFahrt(userFahrtRelation)
                }
            }
            // Event um die Datenbank zu leeren.
            is CabConnectEvent.DeleteAll -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cabConnectUseCases.deleteAll()
                }
            }
        }
    }
}