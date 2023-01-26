package com.example.ifkakao.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ifkakao.ARG_KEY_TRACK
import com.example.ifkakao.ARG_KEY_TYPE
import com.example.ifkakao.R
import com.example.ifkakao.URL_COC
import com.example.ifkakao.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize action bar
        val toolbar = binding.includeToolbar.toolbar
        toolbar.setOnClickListener { navController.navigateUp() }
        setSupportActionBar(binding.includeToolbar.toolbar)

        // initialize navigation drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.includeToolbar.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                // backup status bar color
                if (navController.currentDestination?.id == R.id.nav_home)
                    viewModel.backupStatusBarColor = window.statusBarColor

                // set status bar color
                window.statusBarColor = getColor(R.color.gray_transparent)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                // restore status bar color
                viewModel.backupStatusBarColor?.let {
                    if (navController.currentDestination?.id == R.id.nav_home) // prevent session status bar blue
                        window.statusBarColor = it
                }
                viewModel.backupStatusBarColor = null
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)

        // initialize navigation view
        val navigationView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_session,
                R.id.nav_coc
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
        navigationView.menu.findItem(R.id.nav_coc).setOnMenuItemClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(URL_COC), "application/pdf")
            }
            startActivity(browserIntent)
            false
        }

        // initialize navigation header
        val navigationHeaderView = navigationView.getHeaderView(0)
        navigationHeaderView.findViewById<TextView>(R.id.header_title).setOnClickListener {
            navController.navigateUp()
            drawerLayout.close()
        }
        navigationHeaderView.findViewById<ImageView>(R.id.close_button).setOnClickListener {
            drawerLayout.close()
        }

        // set navigation width to overlap full screen
        val navigationLayoutParams = navigationView.layoutParams
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            navigationLayoutParams.width = windowMetrics.bounds.width()
        } else {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            navigationLayoutParams.width = metrics.widthPixels
        }
        navigationView.layoutParams = navigationLayoutParams
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun navigateToHome() {
        // TODO
    }

    fun navigateToSession(type: String?, track: String?) {
        val args = Bundle()
        args.putString(ARG_KEY_TYPE, type)
        args.putString(ARG_KEY_TRACK, track)
        navController.navigate(R.id.action_home_to_session, args)
    }
}