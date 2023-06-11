package com.eduardo.p2cm.view.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eduardo.p2cm.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.eduardo.p2cm.R


class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id", "")
        val actor = bundle?.getString("actor","")
        val ancestry = bundle?.getString("ancestry", "")
        val eyecolour = bundle?.getString("eyecolour", "")
        val hairColor = bundle?.getString("haircolour", "")
        val dateofBirth = bundle?.getString("dateofBirth", "")
        val patronus = bundle?.getString("patronus", "")
        val image = bundle?.getString("image", "")
        val name = bundle?.getString("name", "")
        val house = bundle?.getString("house","")
        val gender = bundle?.getString("gender", "")
        val species = bundle?.getString("species", "")

        val drawable = resources.getDrawable(R.drawable.sinfoto)



        if (image != null) {
            if(image.contains("https")){

                Glide.with(this)
                    .load(image)
                    .into(binding.personaje)

            }else {
                binding.personaje.setImageDrawable(drawable)
            }
        }
        binding.Nombre.text = String.format(getString(R.string.name_datails), name)
        binding.Actor.text = String.format(getString(R.string.actor_datails), actor)
        binding.patronus.text = String.format(getString(R.string.patronus_datails), patronus)
        binding.cumpleaOs.text = String.format(getString(R.string.date_of_birth_datails), dateofBirth)
        binding.cabello.text = String.format(getString(R.string.hair_colour_datails), hairColor)
        binding.ojos.text = String.format(getString(R.string.eyes_colour_datails), eyecolour)
        binding.ancestr.text = String.format(getString(R.string.ancestry_datails), ancestry)
        binding.house.text = String.format(getString(R.string.house_datails), house)
        binding.gender.text = String.format(getString(R.string.gerder_details), gender)
        binding.species.text = String.format(getString(R.string.species_details), species)





    }
}