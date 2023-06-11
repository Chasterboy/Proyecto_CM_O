package com.eduardo.p2cm.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardo.p2cm.R
import com.eduardo.p2cm.databinding.ActivityMainBinding
import com.eduardo.p2cm.model.personajes_hp
import com.eduardo.p2cm.network.personajes_API
import com.eduardo.p2cm.network.RetrofitService
import com.eduardo.p2cm.utils.Constants
import com.eduardo.p2cm.view.adapters.personajesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras

        val opcion = intent.getStringExtra("opcion")

        val call = RetrofitService.getRetrofit().create(personajes_API::class.java)
            .getGames(opcion)

        call.enqueue(object : Callback<ArrayList<personajes_hp>> {
            override fun onResponse(
                call: Call<ArrayList<personajes_hp>>,
                response: Response<ArrayList<personajes_hp>>
            ) {
                binding.pbConexion.visibility = View.GONE
                binding.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvMenu.adapter = personajesAdapter(this@MainActivity, response.body()!!) { selectedGame: personajes_hp ->
                    gameClicked(selectedGame)
                }

            }

            override fun onFailure(call: Call<ArrayList<personajes_hp>>, t: Throwable) {
                binding.pbConexion.visibility = View.GONE
                Toast.makeText(this@MainActivity, getString(R.string.Noconexion), Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun gameClicked(game: personajes_hp) {

        val bundle = Bundle()

        bundle.putString("id", game.id)
        bundle.putString("name", game.name)
        bundle.putString("actor",game.actor)
        bundle.putString("ancestry", game.ancestry)
        bundle.putString("eyecolour", game.eyeColour)
        bundle.putString("haircolour", game.hairColour)
        bundle.putString("dateofBirth", game.dateOfBirth)
        bundle.putString("patronus", game.patronus)
        bundle.putString("image", game.image)
        bundle.putString("house", game.house)
        bundle.putString("gender", game.gender)
        bundle.putString("species", game.species)

        val intent = Intent(this, Details::class.java)

        intent.putExtras(bundle)

        startActivity(intent)
    }
}