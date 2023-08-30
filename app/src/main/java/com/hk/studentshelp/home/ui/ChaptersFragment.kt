package com.hk.studentshelp.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hk.studentshelp.R
import com.hk.studentshelp.chapters.adapters.ChaptersAdapter
import com.hk.studentshelp.databinding.FragmentChaptersBinding
import com.hk.studentshelp.tests.TestsFragment

class ChaptersFragment : Fragment() ,ChaptersAdapter.OnItemClickListener{
    private var _binding: FragmentChaptersBinding? = null
    private val binding get() = _binding!!

    private val dummyStringList = listOf(
        "Polynomial",
        "Algebra",
        "Trignometry",
        "Geometry",
        "Logical"
    )

    companion object {
        private const val ARG_DATA_KEY = "data_key"

        fun newInstance(data: String): ChaptersFragment {
            val fragment = ChaptersFragment()
            val args = Bundle()
            args.putSerializable(ARG_DATA_KEY, data) // Use putParcelable if passing Parcelable data
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChaptersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Hide the BottomNavigationView
        bottomNavigationView?.visibility = View.VISIBLE

        val data = arguments?.getSerializable(ARG_DATA_KEY) as String?
        binding.subjectTv.text = data

        setupRecyclerView()



    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.subjectsRv.layoutManager = layoutManager

        val adapter = ChaptersAdapter(dummyStringList,this)
        binding.subjectsRv.adapter = adapter
    }

    override fun onItemClick(chapterName: String) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.navHostFragment, TestsFragment())?.addToBackStack("chapter")?.commit()
    }

}