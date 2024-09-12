package br.senai.sp.jandira.rickandmorty.service

import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Episode
import br.senai.sp.jandira.rickandmorty.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CharacterService {

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>

    @GET("character")
    fun getAllCharacters(): Call<Results>

    @GET("episode/{id}/")
    fun getEpisodeById(@Path("id") id: Int): Call<Episode>
}