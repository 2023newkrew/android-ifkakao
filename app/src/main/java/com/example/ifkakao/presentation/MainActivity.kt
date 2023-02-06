package com.example.ifkakao.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.ifkakao.R
import com.example.ifkakao.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

const val URL_COC = "https://mk.kakaocdn.net/dn/if-kakao/2022/if_kakao_code_of_conduct_v1.1.pdf"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.sessionListFragment
            ),
            binding.drawerLayout
        )


        binding.navView.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }


        val navigationHeaderView = binding.navView.getHeaderView(0)
        navigationHeaderView.findViewById<TextView>(R.id.header_title).setOnClickListener {
            navController.navigate(R.id.homeFragment)
            binding.drawerLayout.close()
        }
        navigationHeaderView.findViewById<ImageView>(R.id.close_button).setOnClickListener {
            binding.drawerLayout.close()
        }

        binding.navView.menu.findItem(R.id.coc).setOnMenuItemClickListener {
            Intent(Intent.ACTION_VIEW)
                .apply {
                    setDataAndType(Uri.parse(URL_COC), "application/pdf")
                }.also {
                    it.resolveActivity(packageManager)?.run {
                        startActivity(it)
                    }
                }
            false
        }
    }


}

fun Context.getWidthDp(): Int {
    return (resources.displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
}