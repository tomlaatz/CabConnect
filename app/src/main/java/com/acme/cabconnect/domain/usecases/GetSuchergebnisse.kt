package com.acme.cabconnect.domain.usecases

import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.domain.repository.CabConnectRepository

class GetSuchergebnisse(
    private val repository: CabConnectRepository
) {

    operator fun invoke(datum: Long, start: String, ziel: String, freierPlatz: Int, filter: String, sortAsc: Boolean): List<MitgliederEinerFahrt> {
        val ergebnisse = repository.readSuchergebnisse(datum, start, ziel, freierPlatz)
        return when (filter) {
             "Datum" -> if (sortAsc) ergebnisse.sortedBy { it.fahrt.datum } else ergebnisse.sortedByDescending { it.fahrt.datum }
             "Platz" -> if (sortAsc) ergebnisse.sortedBy { it.fahrt.freierPlatz } else ergebnisse.sortedByDescending { it.fahrt.freierPlatz }
             "Rating" -> if (sortAsc) ergebnisse.sortedBy { it.users[0].bewertung } else ergebnisse.sortedByDescending { it.users[0].bewertung }
            else -> ergebnisse
        }
    }
}