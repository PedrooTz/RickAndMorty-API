package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.rickandmorty.screens.CharacterDetails
import br.senai.sp.jandira.rickandmorty.screens.ListAllCharacters
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContent {
                    RickAndMortyTheme {
                        val controleDeNavegacao = rememberNavController()

                        NavHost(
                            navController = controleDeNavegacao,
                            startDestination = "characterList"
                        )
                        {
                            composable("characterList") {
                                ListAllCharacters(controleDeNavegacao)
                            }
                            composable(
                                route = "characterDetails/{id}"
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments?.getString("id")
                                CharacterDetails(controleDeNavegacao, id)
                            }

                        }
                    }
                }
        }
    }
