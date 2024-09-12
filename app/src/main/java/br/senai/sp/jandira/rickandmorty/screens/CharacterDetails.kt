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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Episode
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
    fun CharacterDetails(navController: NavController, id: String?) {

    var episodeList by remember {
        mutableStateOf(listOf<Episode>())
    }
        val id = id ?: "0"

        var character by remember {
            mutableStateOf(Character())
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                            val callCharacter = RetrofitFactory()
                                .getCharacterService()
                                .getCharacterById(id.toInt())

                            callCharacter.enqueue(object : Callback<Character> {
                                override fun onResponse(
                                    p0: Call<Character>,
                                    response: Response<Character>
                                ) {
                                    character = response.body()!!

                                    if (response.isSuccessful) {
                                        character = response.body()!!
                                        character.episode?.let {
                                            fetchEpisodes(it) { episodes ->
                                                episodeList = episodes
                                            }
                                        }
                                    }
                                }

                                override fun onFailure(p0: Call<Character>, t: Throwable)  {
                                }
                            })

                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier.size(200.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = character.image,
                        contentDescription = ""
                    )

                }
                Text(text = "Nome: ${character.name}")
                Text(text = "Espécie: ${character.species}")
                Text(text = "Origem: ${character.origin.name}")
                Text(text = "Localização: ${character.location?.name}")
                Text(text = "Status: ${character.status}")
                Text(text = "Gênero: ${character.gender}")
                Text(text = "Tipo: ${character.type}")
                Text(text = "Episódios: ${character.episode?.size}")

                LazyColumn {
                    items(episodeList) { episode ->
                        EpisodeList(episode)
                    }
                }
            }

        }
    }
fun fetchEpisodes(episodeUrls: List<String>, callback: (List<Episode>) -> Unit) {
    val episodeService = RetrofitFactory().getEpisodeService()
    val episodes = mutableListOf<Episode>()

    val requests = episodeUrls.map { episodeUrl ->
        val episodeId = episodeUrl.substringAfterLast("/").toIntOrNull() ?: 0
        episodeService.getEpisodeById(episodeId)
    }

    requests.forEach { call ->
        call.enqueue(object : Callback<Episode> {
            override fun onResponse(p0: Call<Episode>, response: Response<Episode>) {
                if (response.isSuccessful) {
                    response.body()?.let { episodes.add(it) }
                    if (episodes.size == requests.size) {
                        callback(episodes)
                    }
                }
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {

            }
        })
    }
}

@Composable
fun EpisodeList(episode: Episode) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                Toast
                    .makeText(context, "Clicar no episódio", Toast.LENGTH_SHORT)
                    .show()
            },
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Row (modifier = Modifier.padding(horizontal =12.dp)){

            Column {


                Text(
                    text = "Número do episódio:",
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 10.dp)
                )
                Text(
                    text = "${episode.episode}",
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Column {

                Text(
                    text = "Nome do episódio:",
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 10.dp)
                )
                Text(
                    text = "${episode.name}",
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
                Text(
                    text = "Data de lançamento:",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 10.dp)
                )
                Text(
                    text = "${episode.transmission_data}",
                    color = Color(0xFFFFFBFF),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }
    }



/* @Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CharacterDetailsPreview() {
    CharacterDetails()

}*/
