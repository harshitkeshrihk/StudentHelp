package com.hk.studentshelp.tests

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hk.studentshelp.R
import com.hk.studentshelp.chapters.adapters.TextPagerAdapter
import com.hk.studentshelp.databinding.FragmentTestsBinding
import com.hk.studentshelp.home.ui.SuccessFragment
import com.hk.studentshelp.model.Question


class TestsFragment : Fragment() {

    private var _binding: FragmentTestsBinding? = null
    private val binding get() = _binding!!

    private lateinit var  storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var database: FirebaseDatabase
    private var status: Boolean = false

    private val quizQuestions = listOf("1", "2", "3","4", "5", "6","7", "8", "9","10")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        storageRef = storage.reference


        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Hide the BottomNavigationView
        bottomNavigationView?.visibility = View.GONE


//      val localFile = File.createTempFile("temp", "txt")
//      fetchTextFile("derivatives", localFile)






        readQuestionsFromFile("derivatives",requireContext()) { questions ->
            val adapter = TextPagerAdapter(questions.take(10))
            binding.viewPager.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.customView = getTabView(position)
            }.attach()

        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Update button visibility based on the current position
                updateButtonVisibility(position)
            }
        })

        // Initially, hide the previous button as we start at position 0
        binding.prev.visibility = View.INVISIBLE

        // Update the initial state of the buttons
//        updateButtonVisibility(0)

        setUpButton()

    }

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab_layout, null)
        val questionNumberTextView = view.findViewById<TextView>(R.id.questionNumberTextView)
        questionNumberTextView.text = quizQuestions[position]
        return view
    }

    private fun setUpButton() {


        binding.startQuiz.setOnClickListener{
           if(!status){
               binding.card1.visibility = View.INVISIBLE
               binding.viewPager.visibility = View.VISIBLE
               binding.tabLayout.visibility = View.VISIBLE
               binding.prev.visibility = View.VISIBLE
               binding.next.visibility = View.VISIBLE
               binding.startQuizTv.text = "Submit Quiz"
               status = true
           }else{
//               Toast.makeText(requireContext(),"Work In Progress",Toast.LENGTH_SHORT).show()
               activity?.supportFragmentManager?.beginTransaction()
                   ?.replace(R.id.navHostFragment, SuccessFragment())?.addToBackStack("testFrag")?.commit()
           }
        }
        binding.prev.setOnClickListener { onPrevButtonClick() }
        binding.next.setOnClickListener { onNextButtonClick() }


    }

//    //if needed later
//    fun uploadTextFile(fileUri: Uri, fileName: String) {
//        val fileRef = storageRef.child("text_files/$fileName.txt")
//        fileRef.putFile(fileUri)
//            .addOnSuccessListener { taskSnapshot ->
//                // File uploaded successfully
//                // Now, you can save the file URL to the Firebase Realtime Database if required
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure
//            }
//    }
//
//    fun saveFileUrlToDatabase(fileName: String, fileUrl: String) {
//        val fileRef = database.reference.child("text_files").child(fileName)
//        fileRef.setValue(fileUrl)
//            .addOnSuccessListener {
//                // File URL saved to the database successfully
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure
//            }
//    }
//
//    fun fetchTextFile(fileName: String, localFile: File) {
//        val fileRef = storageRef.child("class12/Derivation/$fileName.txt")
//
//        fileRef.getFile(localFile)
//            .addOnSuccessListener {
//                // File downloaded successfully
//                // Now you can read the contents of the file from 'localFile'
//                val textContent = readTextFromFile(localFile)
////                binding.testTv.text = textContent
//
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure
//            }
//    }
//
//    fun readTextFromFile(localFile: File): String {
//        return localFile.readText()
//    }

    @SuppressLint("SuspiciousIndentation")
    fun readQuestionsFromFile(fileName: String, context: Context, onComplete: (List<Question>) -> Unit) {
        val questions = mutableListOf<Question>()
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val fileRef: StorageReference = storageRef.child("class12/Derivation/$fileName.txt")

        fileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            val text = String(bytes)
            val lines = text.lines()
            for (line in lines) {
                if (line.isNotEmpty())
                questions.add(Question(line))
            }
            onComplete(questions)
        }.addOnFailureListener {
            // Handle the failure to fetch the file from Firebase Storage
            onComplete(emptyList()) // Return an empty list in case of failure
        }
    }

    private fun updateButtonVisibility(position: Int) {
        if (position == 0) {
            binding.prev.visibility = View.INVISIBLE
        } else {
            binding.prev.visibility = View.VISIBLE
        }

        if (position == binding.viewPager.adapter?.itemCount?.minus(1)) {
            binding.next.visibility = View.INVISIBLE
        } else {
            binding.next.visibility = View.VISIBLE
        }
    }

    private fun onPrevButtonClick() {
        if (binding.viewPager.currentItem > 0) {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

    private fun onNextButtonClick() {
        if (binding.viewPager.currentItem < binding.viewPager.adapter?.itemCount?.minus(1) ?: 0) {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }

    }