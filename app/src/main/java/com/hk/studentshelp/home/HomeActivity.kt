package com.hk.studentshelp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView
import com.hk.studentshelp.R
import com.hk.studentshelp.databinding.ActivityHomeBinding
import com.hk.studentshelp.home.ui.FaqsFragment
import com.hk.studentshelp.home.ui.HomeFragment
import com.hk.studentshelp.home.ui.SubjectsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    private val homeFragment = HomeFragment()
    private val subjectsFragment = SubjectsFragment()
    private val faqsFragment = FaqsFragment()
    private lateinit var toggle: ActionBarDrawerToggle

    private var mInterstitialAd: InterstitialAd? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.d(TAG, 'Ad was loaded.')
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d("TAG", "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }
        }





        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        replaceFragment(homeFragment)
        setUpButton()
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setUpButton() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    showAd()
                    loadAd()
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.subjects -> {
                    showAd()
                    loadAd()
                    replaceFragment(subjectsFragment)
                    true
                }
                R.id.FAQs -> {
                    showAd()
                    loadAd()
                    replaceFragment(faqsFragment)
                    // Handle Notifications menu item click
                    true
                }
                else -> false
            }
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Handle menu item clicks here
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.navBtn.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                closeNavigationDrawer()
            } else {
                openNavigationDrawer()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.navHostFragment,fragment)
            transaction.commit()
        }
    }

    private fun openNavigationDrawer() {
        binding.drawerLayout.openDrawer(binding.navView)
    }

    private fun closeNavigationDrawer() {
        binding.drawerLayout.closeDrawer(binding.navView)
    }

    private fun showAd(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    private fun loadAd(){
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.d(TAG, 'Ad was loaded.')
                mInterstitialAd = interstitialAd
            }
        })
    }

}