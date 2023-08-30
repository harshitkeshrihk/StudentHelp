package com.hk.studentshelp.chapters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hk.studentshelp.R
import com.hk.studentshelp.model.Question

class TextPagerAdapter(private val textList: List<Question>) :
    RecyclerView.Adapter<TextPagerAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(textList[position].questionText)
    }

    override fun getItemCount(): Int = textList.size

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.questionTextView)

        fun bind(text: String) {
            textView.text = text
        }
    }
}