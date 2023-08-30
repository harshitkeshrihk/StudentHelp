package com.hk.studentshelp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.FragmentScreen1Binding
import com.hk.studentshelp.databinding.FragmentScreen2Binding
import com.hk.studentshelp.loginsignup.LoginSignup


class Screen2 : Fragment() {

    private var _binding: FragmentScreen2Binding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScreen2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skip.setOnClickListener {
            val intent = Intent(requireContext(), LoginSignup::class.java)
            startActivity(intent)
        }
        binding.next.setOnClickListener {
            val intent = Intent(requireContext(), LoginSignup::class.java)
            startActivity(intent)
        }
    }
}