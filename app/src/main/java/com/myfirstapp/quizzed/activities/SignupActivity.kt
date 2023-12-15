package com.myfirstapp.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.myfirstapp.quizzed.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener{
            signUpUser()
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity:: class.java)
            startActivity(intent)
        }


    }
    private fun signUpUser(){
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword= binding.etConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirmPassword){
            Toast.makeText(this, "Password and Confirm Password  not match", Toast.LENGTH_LONG).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity:: class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Error creating users", Toast.LENGTH_LONG).show()

                }
            }

    }
}