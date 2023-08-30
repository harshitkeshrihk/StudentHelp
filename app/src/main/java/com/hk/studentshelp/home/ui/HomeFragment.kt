package com.hk.studentshelp.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.FragmentHomeBinding
import com.hk.studentshelp.databinding.FragmentSignupBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Hide the BottomNavigationView
        bottomNavigationView?.visibility = View.VISIBLE
        setUpButton()
    }

    private fun setUpButton() {
        binding.maths.setOnClickListener{
            val chaptersFragment = ChaptersFragment.newInstance("Maths") // Replace 'YourDataType' with the actual data type you're passing
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.navHostFragment, chaptersFragment)
                ?.addToBackStack("chapters")
                ?.commit()
        }
        binding.physics.setOnClickListener{
            val chaptersFragment = ChaptersFragment.newInstance("Physics") // Replace 'YourDataType' with the actual data type you're passing
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.navHostFragment, chaptersFragment)
                ?.addToBackStack("chapters")
                ?.commit()
        }
        binding.hindi.setOnClickListener{
            val chaptersFragment = ChaptersFragment.newInstance("Hindi") // Replace 'YourDataType' with the actual data type you're passing
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.navHostFragment, chaptersFragment)
                ?.addToBackStack("chapters")
                ?.commit()
        }
        binding.chemistry.setOnClickListener{
            val chaptersFragment = ChaptersFragment.newInstance("Chemistry") // Replace 'YourDataType' with the actual data type you're passing
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.navHostFragment, chaptersFragment)
                ?.addToBackStack("chapters")
                ?.commit()
        }
    }

}