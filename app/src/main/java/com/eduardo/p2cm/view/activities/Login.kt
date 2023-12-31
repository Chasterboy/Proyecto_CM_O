package com.eduardo.p2cm.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eduardo.p2cm.R
import com.eduardo.p2cm.databinding.ActivityInicioBinding
import com.eduardo.p2cm.databinding.ActivityLoginBinding
import com.eduardo.p2cm.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


import com.eduardo.p2cm.view.activities.inicio
import com.eduardo.p2cm.view.activities.Register

import com.google.firebase.auth.FirebaseAuthException

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth


    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener{
            if (!valida()) return@setOnClickListener

            binding.pbConexionLogin.visibility = View.VISIBLE

            autenticaUsuario(email, password)



        }

        binding.buttomPassword.setOnClickListener{


            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }


    }
    private fun autenticaUsuario(usr: String, psw: String ){

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener {
            authResult ->

            if (authResult.isSuccessful){

                val intent = Intent(this, inicio::class.java)
                startActivity(intent)
                finish()


            }else{
                binding.pbConexionLogin.visibility = View.GONE
                manejaErrores(authResult)
            }

        }


    }

    private fun manejaErrores(task: Task<AuthResult>) {
        var errorCode: String? = null
        try {
            errorCode = (task.exception as FirebaseAuthException).errorCode
        } catch (e: Exception) {
            errorCode = "NO_NETWORK"
        }


        when (errorCode) {
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, "Error: El correo electrónico no tiene un formato correcto", Toast.LENGTH_SHORT).show()
                binding.email.error = "Error: El correo electrónico no tiene un formato correcto"
                binding.email.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, "Error: La contraseña no es válida", Toast.LENGTH_SHORT).show()
                binding.password.error = "La contraseña no es válida"
                binding.password.requestFocus()
                binding.password.setText("")
            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                // An account already exists with the same email address but different sign-in credentials.
                // Sign in using a provider associated with this email address.
                Toast.makeText(this, "Error: Una cuenta ya existe con el mismo correo, pero con diferentes datos de ingreso", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, "Error: el correo electrónico ya está en uso con otra cuenta.", Toast.LENGTH_LONG).show()
                binding.email.error = "Error: el correo electrónico ya está en uso con otra cuenta."
                binding.email.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, "Error: La sesión ha expirado. Favor de ingresar nuevamente.", Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, "Error: No existe el usuario con la información proporcionada.", Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, "La contraseña proporcionada es inválida", Toast.LENGTH_LONG).show()
                binding.password.error = "La contraseña debe de tener por lo menos 6 caracteres"
                binding.password.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, "Red no disponible o se interrumpió la conexión", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error. No se pudo autenticar exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }






    }

    private fun valida(): Boolean{
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        if(email.isEmpty()){
            binding.email.error = "Email Error"
            binding.email.requestFocus()
            return false
        }
        if(password.isEmpty() || password.length < 6){
            binding.password.error = "Error debe tener al menos 6 caratecters"
            binding.password.requestFocus()
            return false
        }


        return true

    }
}