package com.example.ifkakao.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setOnClickListener {
            navController.navigateUp()
            binding.toolbar.setTitle(R.string.label_main)
        }
        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        if (savedInstanceState == null) {
            navController.setGraph(graph, intent.extras)
        }
        //appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.main,
                R.id.list
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener {
            it.isChecked = true
            binding.drawerLayout.close()

            val id: Int = it.itemId

            if (id == R.id.list) {
                // 계속 위에 추가되는 문제 방지
                if (navController.currentDestination != navController.findDestination(R.id.list))
                    navController.navigate(R.id.list)
            } else if (id == R.id.CoC) {
                it.isChecked = false
                val browserIntent: Intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "https://mk.kakaocdn.net/dn/if-kakao/2022/if_kakao_code_of_conduct_v1.1.pdf"
                    )
                )
                if (browserIntent.resolveActivity(packageManager) != null) {
                    startActivity(browserIntent)
                }
            }
            false
        }


        val navHeaderView = binding.navView.getHeaderView(0)
        // nav drawer close
        navHeaderView.findViewById<ImageView>(R.id.nav_close).setOnClickListener {
            binding.drawerLayout.close()
        }
        navHeaderView.findViewById<TextView>(R.id.nav_home).setOnClickListener {
            navController.navigateUp()
            binding.drawerLayout.close()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
