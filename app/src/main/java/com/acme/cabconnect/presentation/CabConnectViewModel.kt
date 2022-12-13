package com.acme.cabconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            is CabConnectEvent.GetSuchergebnisse -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.fahrten = cabConnectUseCases
                        .getSuchergebnisse(
                            event.datum,
                            event.start,
                            event.ziel,
                            event.freierPlatz
                        )
                }
            }
            is CabConnectEvent.GetMeineFahrten -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.meineFahrten = cabConnectUseCases.getMeineFahrten(event.userId)
                }
            }
            is CabConnectEvent.DeleteFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cabConnectUseCases.deleteFahrt(event.fahrt.fahrtId!!)
                }
            }
            is CabConnectEvent.CreateFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val fahrtId = cabConnectUseCases.createFahrt(event.fahrt)
                    val userFahrtRelation = UserFahrtRelation(event.userId, fahrtId.toInt())
                    cabConnectUseCases.createFahrtRelation(userFahrtRelation)
                }
            }
            is CabConnectEvent.LeaveFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userFahrtRelation = UserFahrtRelation(1, event.leave.fahrtId!!)
                    cabConnectUseCases.leaveFahrt(userFahrtRelation)
                }
            }
            is CabConnectEvent.JoinFahrt -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userFahrtRelation = UserFahrtRelation(1, event.join.fahrtId!!)
                    cabConnectUseCases.joinFahrt(userFahrtRelation)
                }
            }
        }
    }
}