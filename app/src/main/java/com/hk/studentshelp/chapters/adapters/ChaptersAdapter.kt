package com.hk.studentshelp.chapters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hk.studentshelp.R

class ChaptersAdapter(private val stringList: List<String>,private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ChaptersAdapter.ChaptersViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(chapterName: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_item, parent, false)
        return ChaptersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        holder.bind(stringList[position])
        holder.itemView.setOnClickListener {
            // When an item is clicked, call the onItemClick method of the interface
            itemClickListener.onItemClick(stringList[position])
        }
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    inner class ChaptersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.chapterTv)

        fun bind(string: String) {
            textView.text = string
        }
    }
}