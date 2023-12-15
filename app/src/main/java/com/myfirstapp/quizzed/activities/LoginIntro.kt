package com.myfirstapp.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.myfirstapp.quizzed.databinding.ActivityLoginIntroBinding

class LoginIntro : AppCompatActivity() {
    lateinit var binding: ActivityLoginIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            Toast.makeText(this, "User is already logged in!", Toast.LENGTH_LONG).show()
            redirect("MAIN")
        }

        binding.btnGetStarted.setOnClickListener {
            redirect("LOGIN")

        }
    }

    private fun redirect(name:String){
        val intent = when(name){
            "LOGIN" -> Intent(this, LoginActivity:: class.java)
            "MAIN" -> Intent(this, MainActivity :: class.java)
            else -> throw Exception("No path exist")
        }
        startActivity(intent)
        finish()

    }
}