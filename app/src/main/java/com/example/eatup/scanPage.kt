package com.example.eatup

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class scanPage : AppCompatActivity() {

    lateinit var imageMenu:ActionBarDrawerToggle
    lateinit var navigationView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_page)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)


        imageMenu = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
       drawerLayout.addDrawerListener(imageMenu)
        imageMenu.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}