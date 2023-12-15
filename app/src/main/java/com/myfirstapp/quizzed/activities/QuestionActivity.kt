package com.myfirstapp.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.myfirstapp.quizzed.R
import com.myfirstapp.quizzed.adapters.OptionAdapter
import com.myfirstapp.quizzed.databinding.ActivityQuestionBinding
import com.myfirstapp.quizzed.models.Question
import com.myfirstapp.quizzed.models.Quiz

class QuestionActivity : AppCompatActivity() {
    var quizzes : MutableList<Quiz>?=null
    var questions : MutableMap<String, Question>?=null
    var index =1
    lateinit var binding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bindView()
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener{
            index--
            bindView()
        }
        binding.btnNext.setOnClickListener{
            index++
            bindView()
        }
        binding.btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ", questions.toString())
            val intent=Intent(this,ResultActivity::class.java)
            val json=Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val fireStore= FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date!=null) {

            fireStore.collection("Quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty){

                        //Log.d("DATA", it.toObjects(Quiz::class.java).toString())
                        quizzes= it.toObjects(Quiz::class.java)
                        questions= quizzes!![0].questions
                        bindView()

                    }

                }
        }
    }

    private fun bindView() {
        binding.btnPrevious.visibility=View.GONE
        binding.btnSubmit.visibility=View.GONE
        binding.btnNext.visibility=View.GONE

        if (index==1){ // first question
            binding.btnNext.visibility = View.VISIBLE
        }
        else if (index==questions!!.size){ //last question
            binding.btnPrevious.visibility= View.VISIBLE
            binding.btnSubmit.visibility = View.VISIBLE
        }

        else{ // middle question
            binding.btnPrevious.visibility=View.VISIBLE
            binding.btnNext.visibility=View.VISIBLE
        }
        val question = questions!!["question$index"]
        question?.let {
            binding.description.text= it.description
            val optionAdapter =OptionAdapter(this, it)
            binding.optionList.layoutManager= LinearLayoutManager(this)
            binding.optionList.adapter=optionAdapter
            binding.optionList.setHasFixedSize(true)

        }


    }
}