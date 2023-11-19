package com.example.hellohi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.project5.R
import com.google.android.material.bottomnavigation.BottomNavigationView


// MainActivity.kt
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Replace the FrameLayout with the HelloFragment when MainActivity is created
        if (savedInstanceState == null) {
            replaceFragment(HelloFragment())
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Hellofrag -> {
                    replaceFragment(HelloFragment())
                    true
                }
                R.id.Hifrag -> {
                    replaceFragment(HiFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
