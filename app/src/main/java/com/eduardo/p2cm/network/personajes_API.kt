package com.eduardo.p2cm.network

import com.eduardo.p2cm.model.personajes_hp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface personajes_API {


    @GET
    fun getGames(
        @Url url: String?    //getGames("cm/games/games_list.php")
    ): Call<ArrayList<personajes_hp>>


}
