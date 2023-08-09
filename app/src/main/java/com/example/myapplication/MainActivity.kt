package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //TESTANDO PULL REQUEST
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomBar : BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val paceFragment = PaceFragment.newInstance()
        val historyFragment = HistoryFragment.newInstance()

        bottomBar.setOnItemSelectedListener { menuItem ->
            val fragment = when(menuItem.itemId) {
                R.id.pace_fragment -> paceFragment
                R.id.history_fragment -> historyFragment
                else -> paceFragment
            }

            supportFragmentManager.commit {
                replace(R.id.ctn_fragment, fragment)
                setReorderingAllowed(true)
            }

            false
        }

    }

}