package com.hk.studentshelp.home.ui

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.FragmentSignupBinding
import com.hk.studentshelp.databinding.FragmentSuccessBinding
import com.hk.studentshelp.home.HomeActivity
import java.io.File


class SuccessFragment : Fragment() {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater,container,false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Hide the BottomNavigationView
        bottomNavigationView?.visibility = View.GONE
        binding.DownloadPdf.isEnabled = true
    }

    private fun setupButton() {
        binding.BackToHomeScreen.setOnClickListener {
           clearBackStackAndNavigateToFirstFragment()
        }

        binding.DownloadPdf.setOnClickListener{
            downloadPdf()
        }
    }

    private fun downloadPdf() {
        val storage = Firebase.storage
        val storageReference = storage.reference
        val pdfFileName = "derivatives.txt"
            val localFile = File(
                activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                pdfFileName
            )

            val pdfRef = storageReference.child("class12/Derivation/$pdfFileName")

            pdfRef.getFile(localFile)
                .addOnSuccessListener {
                    Log.d("Dowmload", "File downloaded successfully: ${localFile.path}")
                    Toast.makeText(requireContext(),"Downloaded Successfully",Toast.LENGTH_SHORT).show()
                    binding.DownloadPdf.isEnabled = false
                    // Handle successful download here
                }
                .addOnFailureListener { exception ->
                    Log.e("Download", "Error downloading file: ${exception.message}")
                    // Handle download failure here
                    Toast.makeText(requireContext(),"Network Error",Toast.LENGTH_SHORT).show()

                }
    }

    private fun clearBackStackAndNavigateToFirstFragment() {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentCount = fragmentManager?.backStackEntryCount

            // Pop fragments from the back stack up to the first fragment
            for (i in 0 until fragmentCount!!) {
                fragmentManager.popBackStack()
                if (fragmentManager.fragments.isEmpty()) {
                    // If the back stack is empty, navigate to the first fragment
                    fragmentManager.commit {
                        replace(R.id.navHostFragment, HomeFragment())
                    }
                }
            }
        }

}