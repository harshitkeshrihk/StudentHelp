package com.hk.studentshelp.loginsignup

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.ActivityLoginSignupBinding

class LoginSignup : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Set the status bar icon color to black for Android Marshmallow and above
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                    this.getWindow().getDecorView().getWindowInsetsController()?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar icon color to black for Android Lollipop and above
            window.statusBarColor = Color.BLACK
        }

        supportFragmentManager.beginTransaction().replace(binding.signupFrame.id, LoginFragment()).commit()
    }
}