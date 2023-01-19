package com.example.ifkakao.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize action bar
        setSupportActionBar(binding.toolbarContent.toolbar)

        // initialize navigation drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbarContent.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                // TODO backup status bar color and change it to transparent gray
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                // TODO restore status bar color
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)

        // initialize navigation controller
        val navView: NavigationView = binding.navView
        val navController =
            findNavController(com.example.ifkakao.R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.example.ifkakao.R.id.nav_home,
                com.example.ifkakao.R.id.nav_session,
                com.example.ifkakao.R.id.nav_coc
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}