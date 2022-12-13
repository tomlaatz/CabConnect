package com.acme.cabconnect.domain.model

import androidx.room.*

@Entity(
    tableName = "fahrten"
)
data class Fahrt(
    @PrimaryKey(autoGenerate = true)
    val fahrtId: Int? = null,

    val datum: Long,

    val start: String,

    val ziel: String,

    val freierPlatz: Int,

    var geloescht: Boolean = false
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,

    val username: String,

    val bewertung: Double
)

@Entity(primaryKeys = ["userId", "fahrtId"])
data class UserFahrtRelation(
    val userId: Int,
    val fahrtId: Int
)

data class MitgliederEinerFahrt(
    @Embedded
    val fahrt: Fahrt,

    @Relation(
        parentColumn = "fahrtId",
        entityColumn = "userId",
        associateBy = Junction(UserFahrtRelation::class)
    )
    val users: List<User>
)