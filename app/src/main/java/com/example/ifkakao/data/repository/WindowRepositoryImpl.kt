package com.example.ifkakao.data.repository

import android.content.res.Resources
import com.example.ifkakao.domain.repository.WindowRepository
import javax.inject.Inject

class WindowRepositoryImpl @Inject constructor(private val resources: Resources) : WindowRepository {
    override fun getIsDualPane() = resources.displayMetrics.widthPixels / resources.displayMetrics.density >= 600f
}