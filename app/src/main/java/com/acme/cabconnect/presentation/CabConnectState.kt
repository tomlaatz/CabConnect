package com.acme.cabconnect.presentation

import com.acme.cabconnect.domain.model.MitgliederEinerFahrt

data class CabConnectState (
    var fahrten: List<MitgliederEinerFahrt> = emptyList(),
    var meineFahrten: List<MitgliederEinerFahrt> = emptyList()
)