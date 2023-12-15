package com.myfirstapp.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.myfirstapp.quizzed.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity:: class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login(){
        val email=binding.etEmailAddress.text.toString()
        val password= binding.etPassword.text.toString()

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Email or Password can't be empty", Toast.LENGTH_LONG).show()
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity:: class.java)
                startActivity(intent)
                finish()

            }
            else{
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
            }
        }

    }
}