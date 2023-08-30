package com.hk.studentshelp.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.ActivityOnBoardingBinding
import com.hk.studentshelp.home.HomeActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityOnBoardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Set the status bar icon color to black for Android Marshmallow and above
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                    this.getWindow().getDecorView().getWindowInsetsController()?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar icon color to black for Android Lollipop and above
            window.statusBarColor = Color.BLACK
        }

        supportFragmentManager.beginTransaction().replace(binding.frame.id,Screen1()).commit()

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            moveToHome()
        }
    }

    private fun moveToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}