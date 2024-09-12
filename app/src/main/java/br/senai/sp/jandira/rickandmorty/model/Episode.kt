package br.senai.sp.jandira.rickandmorty.model

data class Episode(
val id: Int = 0,
val name: String = "",
val transmission_data: String = "",
val episode: String = "",
val characters: List<String> = listOf<String>(),
val url: String = "",
val created: String = ""

)
