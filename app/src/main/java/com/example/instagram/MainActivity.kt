package com.example.instagram

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instagram.Fragments.HomeFragment
import com.example.instagram.Fragments.NotificationsFragment
import com.example.instagram.Fragments.ProfileFragment
import com.example.instagram.Fragments.SearchFragments

class MainActivity : AppCompatActivity() {



  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.nav_home -> {
        moveToFragment(HomeFragment())
        return@OnNavigationItemSelectedListener true
      }
      R.id.nav_search -> {
        moveToFragment(SearchFragments())
        return@OnNavigationItemSelectedListener true
      }
      R.id.nav_add_post -> {
        return@OnNavigationItemSelectedListener true
      }
      R.id.nav_notifications -> {
        moveToFragment(NotificationsFragment())
        return@OnNavigationItemSelectedListener true
      }
      R.id.nav_profile -> {
        moveToFragment(ProfileFragment())
        return@OnNavigationItemSelectedListener true
      }
    }

    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navView: BottomNavigationView = findViewById(R.id.nav_view)

    navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    moveToFragment(HomeFragment())
  }

  private fun moveToFragment (fragment: Fragment)
  {
    val fragmentTrans = supportFragmentManager.beginTransaction()
    fragmentTrans.replace(R.id.fragment_container, fragment)
    fragmentTrans.commit()
  }
}
