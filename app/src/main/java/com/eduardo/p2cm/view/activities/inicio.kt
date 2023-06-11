package com.eduardo.p2cm.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.eduardo.p2cm.databinding.ActivityInicioBinding

class inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun EnviarEstudiante(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("opcion", "api/characters/students")
        intent.putExtras(bundle)
        startActivity(intent)


    }
    fun EnviarPersonal(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()

        bundle.putString("opcion", "api/characters/staff")



        intent.putExtras(bundle)

        startActivity(intent)

    }
}