package com.myfirstapp.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.myfirstapp.quizzed.R
import com.myfirstapp.quizzed.adapters.QuizAdapter
import com.myfirstapp.quizzed.databinding.ActivityMainBinding
import com.myfirstapp.quizzed.models.Quiz
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var fireStore : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //populateDummyData()

        setUpViews()

    }

    private fun populateDummyData() {
        quizList.add(Quiz("12-12-2023", "13-12-2023"))
        quizList.add(Quiz("13-12-2023", "14-12-2023"))
        quizList.add(Quiz("14-12-2023", "15-12-2023"))
        quizList.add(Quiz("15-12-2023", "16-12-2023"))
        quizList.add(Quiz("16-12-2023", "17-12-2023"))
        quizList.add(Quiz("17-12-2023", "18-12-2023"))
        quizList.add(Quiz("18-12-2023", "19-12-2023"))

    }

    fun setUpViews(){

        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        binding.btnDatePicker.setOnClickListener {
            val datePicker= MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter= SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this,QuestionActivity:: class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)


            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        fireStore= FirebaseFirestore.getInstance()

        val collectionReference = fireStore.collection("Quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value==null || error != null){

                Toast.makeText(this, "Error fetching data", Toast.LENGTH_LONG).show()
                return@addSnapshotListener

        }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll( value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter= QuizAdapter(this, quizList)
        binding.quizRecyclerView.layoutManager=GridLayoutManager(this, 2)
        binding.quizRecyclerView.adapter= adapter
    }

    fun setUpDrawerLayout(){

        setSupportActionBar(binding.appBar)
        actionBarDrawerToggle= ActionBarDrawerToggle(this, binding.mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            binding.mainDrawer.closeDrawers()
            true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}