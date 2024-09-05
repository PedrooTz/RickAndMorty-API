package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.senai.sp.jandira.rickandmorty.screens.CharacterDetails
import br.senai.sp.jandira.rickandmorty.screens.ListAllCharacters
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                ListAllCharacters()
                }
            }
        }
    }
