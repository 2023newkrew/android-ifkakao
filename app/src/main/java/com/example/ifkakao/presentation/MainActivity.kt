package com.example.ifkakao.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import com.example.ifkakao.presentation.main.MainFragment
import com.example.ifkakao.presentation.main.MainFragmentDirections
import com.example.ifkakao.presentation.session_list.SessionListFilterItems
import dagger.hilt.android.AndroidEntryPoint


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

        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_close_button)
            .setOnClickListener {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.menu_title)
            .setOnClickListener {
                navController.navigate(R.id.main_fragment)
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }



        binding.navView.setNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.menu_session -> {
                    val args = Bundle().apply {
                        putParcelable(
                            "FilterItems",
                            SessionListFilterItems()
                        )
                    }
                    navController.navigate(R.id.session_list_fragment, args)
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.menu_COC -> {
                    Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(CocURI), "application/pdf")
                    }.also {
                        it.resolveActivity(packageManager)?.run {
                            startActivity(it)
                        }
                    }
                    false
                }
                else -> {
                    false
                }
            }
        }

        binding.mainToolbar.setOnClickListener {
            navController.navigate(MainFragmentDirections.actionGlobalMainFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideToolbar(){
        binding.mainToolbar.visibility = View.GONE
    }

    fun showToolbar(){
        binding.mainToolbar.visibility = View.VISIBLE
    }
}