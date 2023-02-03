package com.example.ifkakao.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


val CoC_URI = "https://mk.kakaocdn.net/dn/if-kakao/2022/if_kakao_code_of_conduct_v1.1.pdf"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        navController.setGraph(graph, intent.extras)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.main_fragment,
                R.id.session_list_fragment,
                R.id.session_detail_fragment
            ),
            binding.mainDrawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        val closeButton =
            binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_close_button)
        closeButton.setOnClickListener {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.menu_session -> {
                    navController.navigate(R.id.session_list_fragment)
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menu_COC -> {
                    val browseIntent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(CoC_URI), "application/pdf")
                    }.also {
                        it.resolveActivity(packageManager)?.run{
                            startActivity(it)
                        }
                    }
                    false
                }
                else -> {
                    true
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}