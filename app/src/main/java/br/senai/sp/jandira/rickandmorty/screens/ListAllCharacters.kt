package br.senai.sp.jandira.rickandmorty.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Results
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListAllCharacters(controleDeNavegacao: NavController) {
        var charactersList by remember {
            mutableStateOf(listOf<Character>())
        }

        // Efetuar chamada para a API
        val charactersCall = RetrofitFactory()
            .getCharacterService()
            .getAllCharacters()

        charactersCall.enqueue(
            object : Callback<Results> {
                override fun onResponse(p0: Call<Results>, response: Response<Results>) {
                    charactersList = response.body()!!.results
                }

                override fun onFailure(p0: Call<Results>, p1: Throwable) {
                }

            }
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFFFFFFF)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Rick & Morty",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic

                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                LazyColumn(
                ) {
                    items(charactersList) { character ->
                        CharacterCard(character, controleDeNavegacao)
                    }
                }
            }
        }

    }

    @Composable
    fun CharacterCard(character: Character, controleDeNavegacao: NavController) {

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clickable {
                    controleDeNavegacao.navigate("characterDetails/${character.id}")
                },
            colors = CardDefaults
                .cardColors(containerColor = Color.Black)
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Card(
                    modifier = Modifier.size(100.dp)

                ) {
                    AsyncImage(
                        model = character.image, contentDescription = ""
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = character.name,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = "${character.name}",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                    Text(
                        text = character.species,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = "${character.species}",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }

class ListAllCharacters()

