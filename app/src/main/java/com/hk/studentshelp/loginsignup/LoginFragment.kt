package com.hk.studentshelp.loginsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.FragmentLoginBinding
import com.hk.studentshelp.home.HomeActivity


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var callbackManager: CallbackManager

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "AuthenticationFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setUpButton()

       if(auth.currentUser!=null){
           moveToHome()
       }
    }

    private fun setUpButton() {
        binding.signuphere.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.add(
                R.id.signupFrame,
                SignupFragment()
            )?.addToBackStack("Login")?.commit()
        }

        binding.btSignin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                login(email, password)
            }
        }

        binding.google.setOnClickListener {
            signInWithGoogle()
        }


    }

    private fun login(email: String, password: String) {
        activity?.let {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Logged IN", Toast.LENGTH_SHORT).show()
                        moveToHome()
                    } else {
                        Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun check(){
        Toast.makeText(requireContext(), auth.currentUser.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun signInWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = activity?.let { GoogleSignIn.getClient(it, googleSignInOptions) }
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign-In failed, handle error
                Log.e(TAG, "Google Sign-In failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful, update UI with the signed-in user's information
                    Toast.makeText(requireContext(), "Logged IN", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    moveToHome()
                    // Handle the authenticated user here
                } else {
                    // Sign-in failed, display a message to the user
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun moveToHome(){
        val intent = Intent(requireContext(),HomeActivity::class.java)
        startActivity(intent)
    }

}