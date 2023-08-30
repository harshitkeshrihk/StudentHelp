package com.hk.studentshelp.chapters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.hk.studentshelp.R
import com.hk.studentshelp.model.Question

class QuestionPagerAdapter(private val questions: List<Question>) : PagerAdapter() {

    override fun getCount(): Int {
        return questions.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_question, container, false)

        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val question = questions[position]
        questionTextView.text = question.questionText

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
