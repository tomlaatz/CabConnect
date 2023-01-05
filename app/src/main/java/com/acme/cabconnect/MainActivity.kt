package com.acme.cabconnect

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.acme.cabconnect.di.AppModule
import com.acme.cabconnect.domain.model.*
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.presentation.fahrten.*
import com.acme.cabconnect.presentation.fahrten.components.BottomMenuContent
import com.acme.cabconnect.presentation.fahrten.components.BottomNavigationBar
import com.acme.cabconnect.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Basisklasse, wobei hier die DB mit Testdaten neu geladen wird, falls diese leer ist.
        setContent {
            CabConnectTheme {
                val darkTheme = isSystemInDarkTheme()
                val systemUiController = rememberSystemUiController()
                if (darkTheme) {
                    systemUiController.setSystemBarsColor(
                        color = Black
                    )
                } else {
                    systemUiController.setSystemBarsColor(
                        color = White
                    )
                }

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomMenuContent(
                                        "Suchen",
                                        Icons.Default.Search,
                                        Screen.SuchformularScreen.route
                                    ),
                                    BottomMenuContent(
                                        "Erstellen",
                                        Icons.Default.Add,
                                        Screen.FahrtErstellenScreen.route
                                    ),
                                    BottomMenuContent(
                                        "Meine Fahrten",
                                        Icons.Default.Star,
                                        Screen.MeineFahrtenScreen.route
                                    ),
                                    BottomMenuContent(
                                        "Nachrichten",
                                        Icons.Default.Email,
                                        Screen.NachrichtenScreen.route
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    ) {
                        Navigation(navController = navController)
                    }
                }
            }
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                val fahrten = AppModule.proideCabConnectDatabase(application)
                    .cabConnectDao
                    .getAll()

                if (fahrten.isEmpty()) {
                    Log.d("Testdaten: ", "----------DB WIRD NEU GELADEN----------")

                    val user1 = User(username = "Peter", bewertung = 4.8)
                    val user2 = User(username = "Manfred", bewertung = 4.3)
                    val user3 = User(username = "Herbert", bewertung = 4.2)
                    val user4 = User(username = "Walter", bewertung = 3.5)
                    val user5 = User(username = "Jens", bewertung = 4.9)
                    val user6 = User(username = "Andrea", bewertung = 2.1)
                    val user7 = User(username = "Daniel", bewertung = 2.8)
                    val fahrt1 = Fahrt(
                        datum = System.currentTimeMillis() + 86400000,
                        start = "Karlsruhe",
                        ziel = "Mannheim",
                        freierPlatz = 5
                    )
                    val userFahrtRelation1 = UserFahrtRelation(2, 1)
                    val fahrt2 = Fahrt(
                        datum = System.currentTimeMillis() + 92660000,
                        start = "Heidelberg",
                        ziel = "Mannheim",
                        freierPlatz = 3
                    )
                    val userFahrtRelation2 = UserFahrtRelation(3, 2)
                    val fahrt3 = Fahrt(
                        datum = System.currentTimeMillis() + 106687000,
                        start = "Germersheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 1
                    )
                    val userFahrtRelation3 = UserFahrtRelation(4, 3)
                    val fahrt4 = Fahrt(
                        datum = System.currentTimeMillis() + 109912300,
                        start = "Heidelberg",
                        ziel = "Karlsruhe",
                        freierPlatz = 2
                    )
                    val userFahrtRelation4 = UserFahrtRelation(2, 4)
                    val fahrt5 = Fahrt(
                        datum = System.currentTimeMillis() + 117002340,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 4
                    )
                    val userFahrtRelation5 = UserFahrtRelation(3, 5)
                    val fahrt6 = Fahrt(
                        datum = System.currentTimeMillis() + 86400000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 3
                    )
                    val userFahrtRelation6 = UserFahrtRelation(2, 6)
                    val fahrt7 = Fahrt(
                        datum = System.currentTimeMillis() + 92660000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 2
                    )
                    val userFahrtRelation7 = UserFahrtRelation(1, 7)
                    val fahrt8 = Fahrt(
                        datum = System.currentTimeMillis() + 106687000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 1
                    )
                    val userFahrtRelation8 = UserFahrtRelation(4, 8)
                    val fahrt9 = Fahrt(
                        datum = System.currentTimeMillis() + 95660000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 5
                    )
                    val userFahrtRelation9 = UserFahrtRelation(5, 9)
                    val fahrt10 = Fahrt(
                        datum = System.currentTimeMillis() + 268887000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 2
                    )
                    val userFahrtRelation10 = UserFahrtRelation(6, 10)
                    val fahrt11 = Fahrt(
                        datum = System.currentTimeMillis() + 88400000,
                        start = "Mannheim",
                        ziel = "Karlsruhe",
                        freierPlatz = 3
                    )
                    val userFahrtRelation11 = UserFahrtRelation(7, 11)

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user1)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user2)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user3)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user4)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user5)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user6)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createUser(user7)

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt1)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation1
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt2)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation2
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt3)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation3
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt4)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation4
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt5)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation5
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt6)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation6
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt7)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation7
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt8)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation8
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(fahrt9)
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation9
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(
                        fahrt10
                    )
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation10
                    )

                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrt(
                        fahrt11
                    )
                    AppModule.proideCabConnectDatabase(application).cabConnectDao.createFahrtRelation(
                        userFahrtRelation11
                    )

                    Log.d("Testdaten: ", "----------DB IST NEU GELADEN----------")
                }
            }
        }
    }
}
