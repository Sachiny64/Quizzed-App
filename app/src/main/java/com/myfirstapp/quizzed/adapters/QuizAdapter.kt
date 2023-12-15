package com.myfirstapp.quizzed.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.myfirstapp.quizzed.R
import com.myfirstapp.quizzed.activities.QuestionActivity
import com.myfirstapp.quizzed.models.Quiz
import com.myfirstapp.quizzed.ulils.ColorPicker
import com.myfirstapp.quizzed.ulils.IconPicker

class QuizAdapter(val context: Context, val quizzes:List<Quiz> ):RecyclerView.Adapter<QuizAdapter.QuizViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.quiz_item,parent, false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text=quizzes[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context, quizzes[position].title, Toast.LENGTH_SHORT).show()
            val intent= Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE", quizzes[position].title)
            context.startActivity(intent)
        }
    }
    inner class QuizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var textViewTitle: TextView=itemView.findViewById(R.id.quizTitle)
        var iconView : ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer: CardView = itemView.findViewById(R.id.cardContainer)
    }
}