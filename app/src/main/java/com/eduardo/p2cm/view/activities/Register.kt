package com.eduardo.p2cm.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eduardo.p2cm.R
import com.eduardo.p2cm.databinding.ActivityLoginBinding
import com.eduardo.p2cm.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth


    private var email = ""
    private var password1 = ""
    private var password2 = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttomPasswordR.setOnClickListener{
            if (!valida()) return@setOnClickListener

            binding.pbConexionLogin.visibility = View.VISIBLE

            registraUsuario(email, password1)



        }
    }
    private fun registraUsuario(usr: String, psw: String) {
        firebaseAuth.createUserWithEmailAndPassword(usr, psw)
            .addOnCompleteListener { registrationTask ->
                if (registrationTask.isSuccessful) {

                    val intent = Intent(this, inicio::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    binding.pbConexionLogin.visibility = View.GONE
                    manejaErrores(registrationTask)
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
                binding.emailr.error = "Error: El correo electrónico no tiene un formato correcto"
                binding.emailr.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, "Error: La contraseña no es válida", Toast.LENGTH_SHORT).show()
                binding.passwordR1.error = "La contraseña no es válida"
                binding.passwordR1.requestFocus()
                binding.passwordR1.setText("")
            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                // An account already exists with the same email address but different sign-in credentials.
                // Sign in using a provider associated with this email address.
                Toast.makeText(this, "Error: Una cuenta ya existe con el mismo correo, pero con diferentes datos de ingreso", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, "Error: el correo electrónico ya está en uso con otra cuenta.", Toast.LENGTH_LONG).show()
                binding.emailr.error = "Error: el correo electrónico ya está en uso con otra cuenta."
                binding.emailr.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, "Error: La sesión ha expirado. Favor de ingresar nuevamente.", Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, "Error: No existe el usuario con la información proporcionada.", Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, "La contraseña proporcionada es inválida", Toast.LENGTH_LONG).show()
                binding.passwordR1.error = "La contraseña debe de tener por lo menos 6 caracteres"
                binding.passwordR1.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, "Red no disponible o se interrumpió la conexión", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error. No se pudo autenticar exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun valida(): Boolean {
        email = binding.emailr.text.toString().trim()
        password1 = binding.passwordR1.text.toString().trim()
        password2 = binding.passwordR2.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailr.error = "Email Error"
            binding.emailr.requestFocus()
            return false
        }
        if (password1.isEmpty() || password1.length < 6) {
            binding.passwordR1.error = "Error: debe tener al menos 6 caracteres"
            binding.passwordR1.requestFocus()
            return false
        }
        if (password1 != password2) {
            binding.passwordR2.error = "Error: las contraseñas no coinciden"
            binding.passwordR2.requestFocus()
            return false
        }

        return true
    }

}